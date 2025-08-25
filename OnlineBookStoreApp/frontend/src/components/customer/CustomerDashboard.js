import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button, Badge, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faBook, 
  faShoppingCart, 
  faUser, 
  faHeart,
  faHistory,
  faStar
} from '@fortawesome/free-solid-svg-icons';
import { useAuth } from '../../context/AuthContext';
import { useCart } from '../../context/CartContext';
import { formatINR } from '../../utils/currencyUtils';
import axios from 'axios';

const CustomerDashboard = () => {
  const { user } = useAuth();
  const { getCartItemCount } = useCart();
  const [recentBooks, setRecentBooks] = useState([]);
  const [orderHistory, setOrderHistory] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRecentBooks = async () => {
      try {
        const booksResponse = await axios.get('http://localhost:8080/books');
        const recentBooksData = booksResponse.data.slice(0, 6);
        setRecentBooks(recentBooksData);
      } catch (error) {
        console.error('Error fetching recent books:', error);
        setRecentBooks([]);
      } finally {
        setLoading(false);
      }
    };
    fetchRecentBooks();
  }, []);

  useEffect(() => {
    const fetchUserOrders = async (userId) => {
      try {
        console.log('Fetching orders for user ID:', userId);
        const ordersResponse = await axios.get(`http://localhost:8080/order/user/${userId}`);
        setOrderHistory(ordersResponse.data.slice(0, 5));
      } catch (error) {
        console.error('Error fetching orders:', error);
        setOrderHistory([]);
      }
    };
    
    if (user?.id) {
      fetchUserOrders(user.id);
    } else if (user && !user.id) {
      console.log('User ID not available, cannot fetch orders. User:', user);
      setOrderHistory([]);
    }
  }, [user?.id, user]);

  const QuickActionCard = ({ title, description, icon, link, variant }) => (
    <Col md={4} className="mb-4">
      <Card className="h-100 shadow">
        <Card.Body className="p-4">
          <div className="text-center mb-3">
            <FontAwesomeIcon 
              icon={icon} 
              className={`text-${variant} mb-2`} 
              size="2x"
            />
          </div>
          <Card.Title className="text-center mb-3">{title}</Card.Title>
          <Card.Text className="text-center text-muted mb-4">
            {description}
          </Card.Text>
          <div className="d-grid">
            <Button as={Link} to={link} variant={variant}>
              {title}
            </Button>
          </div>
        </Card.Body>
      </Card>
    </Col>
  );

  const BookCard = ({ book }) => (
    <Col md={4} className="mb-4">
      <Card className="h-100 shadow">
        {book.imageUrl && (
          <Card.Img
            variant="top"
            src={`data:image/jpeg;base64,${btoa(String.fromCharCode(...new Uint8Array(book.imageUrl)))}`}
            alt={book.title}
            style={{ height: '200px', objectFit: 'cover' }}
          />
        )}
        <Card.Body className="d-flex flex-column">
          <Card.Title className="h6 mb-2">{book.title}</Card.Title>
          <Card.Text className="text-muted small mb-2">by {book.author}</Card.Text>
          <div className="d-flex justify-content-between align-items-center mb-3">
            <span className="fw-bold text-primary">{formatINR(book.price)}</span>
            <Badge bg={book.stock > 0 ? 'success' : 'danger'}>
              {book.stock > 0 ? 'In Stock' : 'Out of Stock'}
            </Badge>
          </div>
          <Button 
            as={Link} 
            to={`/books/${book.id}`} 
            variant="outline-primary" 
            size="sm"
            className="mt-auto"
          >
            View Details
          </Button>
        </Card.Body>
      </Card>
    </Col>
  );

  if (loading) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
          <p className="mt-3">Loading your dashboard...</p>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <Row className="mb-4">
        <Col>
          <h1 className="mb-3">
            <FontAwesomeIcon icon={faUser} className="text-primary me-3" />
            Welcome back, {user?.fullName || user?.userName || 'Customer'}!
          </h1>
          <p className="text-muted">Here's what's happening with your account and our latest books.</p>
        </Col>
      </Row>

      {/* Quick Stats */}
      <Row className="mb-5">
        <Col md={3} className="mb-4">
          <Card className="text-center shadow">
            <Card.Body className="p-4">
              <FontAwesomeIcon 
                icon={faShoppingCart} 
                className="text-primary mb-3" 
                size="2x"
              />
              <Card.Title className="h3 mb-2">{getCartItemCount()}</Card.Title>
              <Card.Text className="text-muted">Items in Cart</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={3} className="mb-4">
          <Card className="text-center shadow">
            <Card.Body className="p-4">
              <FontAwesomeIcon 
                icon={faHistory} 
                className="text-success mb-3" 
                size="2x"
              />
              <Card.Title className="h3 mb-2">{orderHistory.length}</Card.Title>
              <Card.Text className="text-muted">Recent Orders</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={3} className="mb-4">
          <Card className="text-center shadow">
            <Card.Body className="p-4">
              <FontAwesomeIcon 
                icon={faBook} 
                className="text-info mb-3" 
                size="2x"
              />
              <Card.Title className="h3 mb-2">{recentBooks.length}</Card.Title>
              <Card.Text className="text-muted">New Books</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={3} className="mb-4">
          <Card className="text-center shadow">
            <Card.Body className="p-4">
              <FontAwesomeIcon 
                icon={faStar} 
                className="text-warning mb-3" 
                size="2x"
              />
              <Card.Title className="h3 mb-2">4.8</Card.Title>
              <Card.Text className="text-muted">Avg Rating</Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Quick Actions */}
      <Row className="mb-5">
        <Col>
          <h3 className="mb-4">Quick Actions</h3>
        </Col>
      </Row>

      <Row>
        <QuickActionCard
          title="Browse Books"
          description="Explore our vast collection of books across different genres"
          icon={faBook}
          link="/books"
          variant="primary"
        />
        <QuickActionCard
          title="View Cart"
          description="Check your shopping cart and proceed to checkout"
          icon={faShoppingCart}
          link="/cart"
          variant="success"
        />
        <QuickActionCard
          title="Order History"
          description="View your past orders and track current deliveries"
          icon={faHistory}
          link="/orders"
          variant="info"
        />
      </Row>

      {/* Recent Books */}
      <Row className="mt-5">
        <Col>
          <div className="d-flex justify-content-between align-items-center mb-4">
            <h3>Recently Added Books</h3>
            <Button as={Link} to="/books" variant="outline-primary">
              View All Books
            </Button>
          </div>
        </Col>
      </Row>

      <Row>
        {recentBooks.map((book) => (
          <BookCard key={book.id} book={book} />
        ))}
      </Row>

      {/* Recent Orders */}
      {orderHistory.length > 0 && (
        <>
          <Row className="mt-5">
            <Col>
              <div className="d-flex justify-content-between align-items-center mb-4">
                <h3>Recent Orders</h3>
                <Button as={Link} to="/orders" variant="outline-primary">
                  View All Orders
                </Button>
              </div>
            </Col>
          </Row>

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
                      {orderHistory.map((order) => (
                        <tr key={order.id}>
                          <td>#{order.id}</td>
                          <td>{new Date(order.orderDate).toLocaleDateString()}</td>
                          <td>{formatINR(order.totalAmount)}</td>
                          <td>
                            <Badge bg="success">Completed</Badge>
                          </td>
                          <td>
                            <Button as={Link} to={`/orders/${order.id}`} variant="outline-primary" size="sm">
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
        </>
      )}

      {/* Recommendations */}
      <Row className="mt-5">
        <Col>
          <Card className="bg-light border-0">
            <Card.Body>
              <h6 className="text-primary mb-3">
                <FontAwesomeIcon icon={faHeart} className="me-2" />
                Personalized Recommendations
              </h6>
              <p className="mb-0">
                Based on your reading preferences, we recommend exploring our fiction and mystery categories. 
                Check out our latest arrivals and bestsellers for great deals!
              </p>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default CustomerDashboard; 