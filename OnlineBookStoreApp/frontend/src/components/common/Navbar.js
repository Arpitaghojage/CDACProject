import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Navbar, Nav, Container, Button, Badge } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBook, faShoppingCart, faUser, faSignOutAlt, faHome } from '@fortawesome/free-solid-svg-icons';
import { useAuth } from '../../context/AuthContext';
import { useCart } from '../../context/CartContext';

const NavigationBar = () => {
  const { user, isAuthenticated, isAdmin, isCustomer, logout } = useAuth();
  const { getCartItemCount } = useCart();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <Navbar bg="primary" variant="dark" expand="lg" className="mb-4">
      <Container>
        <Navbar.Brand as={Link} to="/" className="d-flex align-items-center">
          <FontAwesomeIcon icon={faBook} className="me-2" />
          Online Book Store
        </Navbar.Brand>
        
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/">
              <FontAwesomeIcon icon={faHome} className="me-1" />
              Home
            </Nav.Link>
            
            {isAuthenticated() && isCustomer() && (
              <>
                <Nav.Link as={Link} to="/books">
                  <FontAwesomeIcon icon={faBook} className="me-1" />
                  Books
                </Nav.Link>
                <Nav.Link as={Link} to="/cart" className="position-relative">
                  <FontAwesomeIcon icon={faShoppingCart} className="me-1" />
                  Cart
                  {getCartItemCount() > 0 && (
                    <Badge 
                      bg="danger" 
                      className="position-absolute top-0 start-100 translate-middle"
                      style={{ fontSize: '0.7rem' }}
                    >
                      {getCartItemCount()}
                    </Badge>
                  )}
                </Nav.Link>
                <Nav.Link as={Link} to="/orders">
                  <FontAwesomeIcon icon={faUser} className="me-1" />
                  My Orders
                </Nav.Link>
              </>
            )}
            
            {isAuthenticated() && isAdmin() && (
              <>
                <Nav.Link as={Link} to="/admin">
                  <FontAwesomeIcon icon={faUser} className="me-1" />
                  Dashboard
                </Nav.Link>
                <Nav.Link as={Link} to="/admin/books">
                  <FontAwesomeIcon icon={faBook} className="me-1" />
                  Manage Books
                </Nav.Link>
                <Nav.Link as={Link} to="/admin/categories">
                  Categories
                </Nav.Link>
                <Nav.Link as={Link} to="/admin/orders">
                  Orders
                </Nav.Link>
              </>
            )}
          </Nav>
          
          <Nav>
            {!isAuthenticated() ? (
              <>
                <Nav.Link as={Link} to="/login">
                  <FontAwesomeIcon icon={faUser} className="me-1" />
                  Login
                </Nav.Link>
                <Nav.Link as={Link} to="/register">
                  Register
                </Nav.Link>
              </>
            ) : (
              <div className="d-flex align-items-center">
                <span className="text-light me-3">
                  Welcome, {user?.name || user?.email}!
                </span>
                <Button 
                  variant="outline-light" 
                  size="sm"
                  onClick={handleLogout}
                >
                  <FontAwesomeIcon icon={faSignOutAlt} className="me-1" />
                  Logout
                </Button>
              </div>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default NavigationBar; 