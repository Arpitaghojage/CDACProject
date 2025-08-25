import React, { useState } from 'react';
import { 
  Container, 
  Row, 
  Col, 
  Card, 
  Button, 
  Badge,
  Alert,
  Image
} from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faShoppingCart, 
  faTrash, 
  faMinus, 
  faPlus,
  faArrowLeft,
  faCreditCard
} from '@fortawesome/free-solid-svg-icons';
import { useCart } from '../../context/CartContext';
import { formatINR } from '../../utils/currencyUtils';

const Cart = () => {
  const { 
    cartItems, 
    cartTotal, 
    removeFromCart, 
    updateQuantity, 
    clearCart 
  } = useCart();
  const [alert, setAlert] = useState({ show: false, message: '', variant: '' });
  const navigate = useNavigate();

  // Debug logging
  console.log('Cart component rendered with items:', cartItems);
  console.log('Cart total:', cartTotal);

  const handleQuantityChange = (bookId, newQuantity) => {
    if (newQuantity <= 0) {
      removeFromCart(bookId);
      setAlert({
        show: true,
        message: 'Item removed from cart',
        variant: 'info'
      });
    } else {
      updateQuantity(bookId, newQuantity);
    }
  };

  const handleRemoveItem = (bookId) => {
    removeFromCart(bookId);
    setAlert({
      show: true,
      message: 'Item removed from cart',
      variant: 'success'
    });
  };

  const handleClearCart = () => {
    if (window.confirm('Are you sure you want to clear your cart?')) {
      clearCart();
      setAlert({
        show: true,
        message: 'Cart cleared successfully',
        variant: 'info'
      });
    }
  };

  const handleCheckout = () => {
    if (cartItems.length === 0) {
      setAlert({
        show: true,
        message: 'Your cart is empty',
        variant: 'warning'
      });
      return;
    }
    navigate('/checkout');
  };

  const renderImage = (imageData) => {
    if (!imageData) return null;
    if (imageData.startsWith('data:image')) return imageData;
    return `data:image/jpeg;base64,${imageData}`;
  };

  if (cartItems.length === 0) {
    return (
      <Container className="py-5">
        <Row className="justify-content-center">
          <Col md={8} className="text-center">
            <FontAwesomeIcon 
              icon={faShoppingCart} 
              size="4x" 
              className="text-muted mb-4" 
            />
            <h2 className="mb-3">Your Cart is Empty</h2>
            <p className="text-muted mb-4">
              Looks like you haven't added any books to your cart yet.
            </p>
            <Button as={Link} to="/books" variant="primary" size="lg">
              <FontAwesomeIcon icon={faArrowLeft} className="me-2" />
              Continue Shopping
            </Button>
          </Col>
        </Row>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <Row className="mb-4">
        <Col>
          <h1 className="mb-3">
            <FontAwesomeIcon icon={faShoppingCart} className="text-primary me-3" />
            Shopping Cart
          </h1>
          <p className="text-muted">
            Review your items and proceed to checkout.
          </p>
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

      <Row>
        <Col lg={8}>
          <Card className="shadow">
            <Card.Header className="d-flex justify-content-between align-items-center">
              <h5 className="mb-0">Cart Items ({cartItems.length})</h5>
              <Button 
                variant="outline-danger" 
                size="sm"
                onClick={handleClearCart}
              >
                <FontAwesomeIcon icon={faTrash} className="me-1" />
                Clear Cart
              </Button>
            </Card.Header>
            <Card.Body className="p-0">
              {cartItems.map((item) => (
                <div key={item.id} className="cart-item p-3 border-bottom">
                  <Row className="align-items-center">
                    <Col md={2}>
                      {item.imageUrl && (
                        <Image
                          src={renderImage(item.imageUrl)}
                          alt={item.title}
                          fluid
                          className="rounded"
                        />
                      )}
                    </Col>
                    <Col md={4}>
                      <h6 className="mb-1">{item.title}</h6>
                      <p className="text-muted small mb-1">by {item.author}</p>
                      {item.category && (
                        <Badge bg="info" className="small">
                          {item.category.categoryName}
                        </Badge>
                      )}
                    </Col>
                    <Col md={2} className="text-center">
                      <span className="fw-bold text-primary">{formatINR(item.price)}</span>
                    </Col>
                    <Col md={2} className="text-center">
                      <div className="d-flex align-items-center justify-content-center">
                        <Button
                          variant="outline-secondary"
                          size="sm"
                          onClick={() => handleQuantityChange(item.id, item.quantity - 1)}
                        >
                          <FontAwesomeIcon icon={faMinus} />
                        </Button>
                        <span className="mx-3 fw-bold">{item.quantity}</span>
                        <Button
                          variant="outline-secondary"
                          size="sm"
                          onClick={() => handleQuantityChange(item.id, item.quantity + 1)}
                        >
                          <FontAwesomeIcon icon={faPlus} />
                        </Button>
                      </div>
                    </Col>
                    <Col md={1} className="text-center">
                      <span className="fw-bold">{formatINR(item.price * item.quantity)}</span>
                    </Col>
                    <Col md={1} className="text-center">
                      <Button
                        variant="outline-danger"
                        size="sm"
                        onClick={() => handleRemoveItem(item.id)}
                      >
                        <FontAwesomeIcon icon={faTrash} />
                      </Button>
                    </Col>
                  </Row>
                </div>
              ))}
            </Card.Body>
          </Card>
        </Col>

        <Col lg={4}>
          <Card className="shadow">
            <Card.Header>
              <h5 className="mb-0">Order Summary</h5>
            </Card.Header>
            <Card.Body>
              <div className="d-flex justify-content-between mb-2">
                <span>Subtotal ({cartItems.length} items):</span>
                <span className="fw-bold">{formatINR(cartTotal)}</span>
              </div>
              <div className="d-flex justify-content-between mb-2">
                <span>Shipping:</span>
                <span className="text-success">Free</span>
              </div>
              <hr />
              <div className="d-flex justify-content-between mb-3">
                <span className="h5 mb-0">Total:</span>
                <span className="h5 mb-0 text-primary">{formatINR(cartTotal)}</span>
              </div>
              
              <div className="d-grid gap-2">
                <Button 
                  variant="success" 
                  size="lg"
                  onClick={handleCheckout}
                >
                  <FontAwesomeIcon icon={faCreditCard} className="me-2" />
                  Proceed to Checkout
                </Button>
                <Button 
                  as={Link} 
                  to="/books" 
                  variant="outline-primary"
                >
                  <FontAwesomeIcon icon={faArrowLeft} className="me-2" />
                  Continue Shopping
                </Button>
              </div>
            </Card.Body>
          </Card>

          {/* Additional Info */}
          <Card className="mt-3 shadow">
            <Card.Body>
              <h6 className="text-primary mb-3">
                <FontAwesomeIcon icon={faShoppingCart} className="me-2" />
                Shopping Benefits
              </h6>
              <ul className="list-unstyled mb-0">
                <li className="mb-2">
                  <small className="text-muted">
                    ✓ Free shipping on orders over ₹4,150
                  </small>
                </li>
                <li className="mb-2">
                  <small className="text-muted">
                    ✓ Secure payment processing
                  </small>
                </li>
                <li className="mb-2">
                  <small className="text-muted">
                    ✓ 30-day return policy
                  </small>
                </li>
                <li>
                  <small className="text-muted">
                    ✓ Customer support available
                  </small>
                </li>
              </ul>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Cart; 