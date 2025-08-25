import React, { useState, useEffect } from 'react';
import { 
  Container, 
  Row, 
  Col, 
  Card, 
  Table, 
  Badge,
  Alert,
  Button
} from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faHistory,
  faEye
} from '@fortawesome/free-solid-svg-icons';
import { useAuth } from '../../context/AuthContext';
import { formatINR } from '../../utils/currencyUtils';
import axios from 'axios';

const OrderHistory = () => {
  const { user } = useAuth();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [alert, setAlert] = useState({ show: false, message: '', variant: '' });

  useEffect(() => {
    if (user?.id) {
      fetchOrders();
    } else if (user && !user.id) {
      // User is logged in but doesn't have an ID - show appropriate message
      setLoading(false);
      setAlert({
        show: true,
        message: 'Unable to load orders: User ID not available. Please try logging out and logging back in.',
        variant: 'warning'
      });
    } else {
      // No user at all
      setLoading(false);
    }
  }, [user]);

  const fetchOrders = async () => {
    try {
      console.log('Fetching orders for user ID:', user.id);
      const token = localStorage.getItem('token');
      const response = await axios.get(`http://localhost:8080/order/user/${user.id}` , {
        headers: { Authorization: `Bearer ${token}` }
      });
      setOrders(response.data);
    } catch (error) {
      console.error('Error fetching orders:', error);
      setAlert({
        show: true,
        message: `Error fetching order history: ${error.response?.data?.message || error.message}`,
        variant: 'danger'
      });
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
          <p className="mt-3">Loading order history...</p>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <Row className="mb-4">
        <Col>
          <h1 className="mb-3">
            <FontAwesomeIcon icon={faHistory} className="text-primary me-3" />
            Order History
          </h1>
          <p className="text-muted">View your past orders and track their status.</p>
        </Col>
      </Row>

      {alert.show && (
        <Alert 
          variant={alert.variant} 
          dismissible 
          onClose={() => setAlert({ show: false, message: '', variant: '' })}
        >
          {alert.message}
        </Alert>
      )}

      {orders.length === 0 ? (
        <Row className="justify-content-center">
          <Col md={8} className="text-center">
            <FontAwesomeIcon 
              icon={faHistory} 
              size="4x" 
              className="text-muted mb-4" 
            />
            <h3 className="mb-3">No Orders Yet</h3>
            <p className="text-muted mb-4">
              You haven't placed any orders yet. Start shopping to see your order history here.
            </p>
            <Button as={Link} to="/books" variant="primary" size="lg">
              Start Shopping
            </Button>
          </Col>
        </Row>
      ) : (
        <Row>
          <Col>
            <Card className="shadow">
              <Card.Body className="p-0">
                <Table responsive hover className="mb-0">
                  <thead className="table-light">
                    <tr>
                      <th>Order ID</th>
                      <th>Date</th>
                      <th>Total</th>
                      <th>Status</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    {orders.map((order) => (
                      <tr key={order.id}>
                        <td>#{order.id}</td>
                        <td>{new Date(order.orderDate).toLocaleDateString()}</td>
                        <td>{formatINR(order.totalAmount)}</td>
                        <td>
                          <Badge bg="success">Completed</Badge>
                        </td>
                        <td>
                          <Button as={Link} to={`/orders/${order.id}`} variant="outline-primary" size="sm">
                            <FontAwesomeIcon icon={faEye} className="me-1" />
                            View Details
                          </Button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </Table>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      )}
    </Container>
  );
};

export default OrderHistory; 