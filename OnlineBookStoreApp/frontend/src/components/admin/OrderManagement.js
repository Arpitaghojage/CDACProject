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
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faShoppingCart, 
  faEye
} from '@fortawesome/free-solid-svg-icons';
import { formatINR } from '../../utils/currencyUtils';
import axios from 'axios';
import { useAuth } from '../../context/AuthContext';

const OrderManagement = () => {
  const { token, loading: authLoading } = useAuth();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [alert, setAlert] = useState({ show: false, message: '', variant: '' });

  useEffect(() => {
    if (authLoading) return; // wait until auth initializes

    if (!token) {
      setAlert({
        show: true,
        message: 'Unauthorized: missing token. Please log in again.',
        variant: 'danger'
      });
      setLoading(false);
      return;
    }

    fetchOrders(token);
  }, [authLoading, token]);

  const fetchOrders = async (jwt) => {
    try {
      const response = await axios.get('http://localhost:8080/order', {
        headers: { Authorization: `Bearer ${jwt}` }
      });
      setOrders(response.data);
    } catch (error) {
      console.error('Error fetching orders:', error);
      setAlert({
        show: true,
        message: `Error fetching orders: ${error.response?.data?.message || error.message}`,
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
          <p className="mt-3">Loading orders...</p>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <Row className="mb-4">
        <Col>
          <h1 className="mb-3">
            <FontAwesomeIcon icon={faShoppingCart} className="text-primary me-3" />
            Order Management
          </h1>
          <p className="text-muted">View and manage customer orders.</p>
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

      <Card className="shadow">
        <Card.Header>
          <h5 className="mb-0">All Orders ({orders.length} orders)</h5>
        </Card.Header>
        <Card.Body className="p-0">
          <Table responsive hover className="mb-0">
            <thead className="table-light">
              <tr>
                <th>Order ID</th>
                <th>Customer ID</th>
                <th>Order Date</th>
                <th>Total Amount</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {orders.map((order) => (
                <tr key={order.id}>
                  <td>#{order.id}</td>
                  <td>{order.userId}</td>
                  <td>{new Date(order.orderDate).toLocaleDateString()}</td>
                  <td>{formatINR(order.totalAmount)}</td>
                  <td>
                    <Badge bg="success">Completed</Badge>
                  </td>
                  <td>
                    <Button variant="outline-primary" size="sm">
                      <FontAwesomeIcon icon={faEye} />
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default OrderManagement; 