import React from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBook, faUser, faShoppingCart, faSignInAlt } from '@fortawesome/free-solid-svg-icons';
import { useAuth } from '../../context/AuthContext';

const Home = () => {
  const { isAuthenticated, isAdmin, isCustomer } = useAuth();

  return (
    <Container className="py-5">
      <Row className="justify-content-center text-center mb-5">
        <Col md={8}>
          <h1 className="display-4 mb-4">
            <FontAwesomeIcon icon={faBook} className="text-primary me-3" />
            Welcome to Online Book Store
          </h1>
          <p className="lead text-muted">
            Discover thousands of books across various genres. 
            Browse, shop, and enjoy your reading journey with us.
          </p>
        </Col>
      </Row>

      {!isAuthenticated() ? (
        <Row className="justify-content-center">
          <Col md={6}>
            <Card className="text-center shadow">
              <Card.Body className="p-5">
                <FontAwesomeIcon 
                  icon={faSignInAlt} 
                  className="text-primary mb-3" 
                  size="3x"
                />
                <Card.Title className="mb-3">Get Started</Card.Title>
                <Card.Text className="mb-4">
                  Sign in to your account or create a new one to start shopping for books.
                </Card.Text>
                <div className="d-grid gap-2">
                  <Button as={Link} to="/login" variant="primary" size="lg">
                    <FontAwesomeIcon icon={faUser} className="me-2" />
                    Login
                  </Button>
                  <Button as={Link} to="/register" variant="outline-primary" size="lg">
                    Register
                  </Button>
                </div>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      ) : (
        <Row className="justify-content-center">
          {isCustomer() ? (
            <Col md={8}>
              <Row>
                <Col md={6} className="mb-4">
                  <Card className="text-center h-100 shadow">
                    <Card.Body className="p-4">
                      <FontAwesomeIcon 
                        icon={faBook} 
                        className="text-primary mb-3" 
                        size="2x"
                      />
                      <Card.Title>Browse Books</Card.Title>
                      <Card.Text>
                        Explore our vast collection of books across different categories.
                      </Card.Text>
                      <Button as={Link} to="/books" variant="primary">
                        Browse Books
                      </Button>
                    </Card.Body>
                  </Card>
                </Col>
                <Col md={6} className="mb-4">
                  <Card className="text-center h-100 shadow">
                    <Card.Body className="p-4">
                      <FontAwesomeIcon 
                        icon={faShoppingCart} 
                        className="text-success mb-3" 
                        size="2x"
                      />
                      <Card.Title>View Cart</Card.Title>
                      <Card.Text>
                        Check your shopping cart and proceed to checkout.
                      </Card.Text>
                      <Button as={Link} to="/cart" variant="success">
                        View Cart
                      </Button>
                    </Card.Body>
                  </Card>
                </Col>
              </Row>
            </Col>
          ) : isAdmin() ? (
            <Col md={8}>
              <Row>
                <Col md={6} className="mb-4">
                  <Card className="text-center h-100 shadow">
                    <Card.Body className="p-4">
                      <FontAwesomeIcon 
                        icon={faBook} 
                        className="text-primary mb-3" 
                        size="2x"
                      />
                      <Card.Title>Manage Books</Card.Title>
                      <Card.Text>
                        Add, edit, or remove books from the store inventory.
                      </Card.Text>
                      <Button as={Link} to="/admin/books" variant="primary">
                        Manage Books
                      </Button>
                    </Card.Body>
                  </Card>
                </Col>
                <Col md={6} className="mb-4">
                  <Card className="text-center h-100 shadow">
                    <Card.Body className="p-4">
                      <FontAwesomeIcon 
                        icon={faUser} 
                        className="text-info mb-3" 
                        size="2x"
                      />
                      <Card.Title>Admin Dashboard</Card.Title>
                      <Card.Text>
                        View store statistics and manage orders.
                      </Card.Text>
                      <Button as={Link} to="/admin" variant="info">
                        Dashboard
                      </Button>
                    </Card.Body>
                  </Card>
                </Col>
              </Row>
            </Col>
          ) : null}
        </Row>
      )}

      <Row className="mt-5 pt-5">
        <Col md={12}>
          <h2 className="text-center mb-4">Why Choose Our Book Store?</h2>
          <Row>
            <Col md={4} className="text-center mb-4">
              <FontAwesomeIcon 
                icon={faBook} 
                className="text-primary mb-3" 
                size="2x"
              />
              <h5>Wide Selection</h5>
              <p className="text-muted">
                Thousands of books across various genres and categories.
              </p>
            </Col>
            <Col md={4} className="text-center mb-4">
              <FontAwesomeIcon 
                icon={faShoppingCart} 
                className="text-success mb-3" 
                size="2x"
              />
              <h5>Easy Shopping</h5>
              <p className="text-muted">
                Simple and secure shopping experience with fast checkout.
              </p>
            </Col>
            <Col md={4} className="text-center mb-4">
              <FontAwesomeIcon 
                icon={faUser} 
                className="text-info mb-3" 
                size="2x"
              />
              <h5>Customer Support</h5>
              <p className="text-muted">
                Dedicated customer support to help you with any queries.
              </p>
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default Home; 