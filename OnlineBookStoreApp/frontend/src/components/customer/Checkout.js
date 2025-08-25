import React, { useState } from 'react';
import { 
  Container, 
  Row, 
  Col, 
  Card, 
  Button, 
  Form,
  Alert
} from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faCreditCard,
  faArrowLeft
} from '@fortawesome/free-solid-svg-icons';
import { useCart } from '../../context/CartContext';
import { formatINR } from '../../utils/currencyUtils';
import { useAuth } from '../../context/AuthContext';
import { useEffect } from 'react';

// Razorpay Key ID (public, safe for frontend)
const RAZORPAY_KEY_ID = 'rzp_test_h2lluOKhKWDtDs'; // TODO: Replace with your actual Razorpay Key ID

function loadRazorpayScript(src) {
  return new Promise((resolve) => {
    const script = document.createElement('script');
    script.src = src;
    script.onload = () => resolve(true);
    script.onerror = () => resolve(false);
    document.body.appendChild(script);
  });
}

const Checkout = () => {
  const { cartItems, cartTotal, clearCart } = useCart();
  const { user } = useAuth();
  const [loading, setLoading] = useState(false);
  const [alert, setAlert] = useState({ show: false, message: '', variant: '' });
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
    address: '',
    city: '',
    zipCode: '',
    cardNumber: '',
    expiryDate: '',
    cvv: ''
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    // Simulate order processing
    setTimeout(() => {
      setAlert({
        show: true,
        message: 'Order placed successfully! Thank you for your purchase.',
        variant: 'success'
      });
      
      clearCart();
      
      setTimeout(() => {
        navigate('/customer');
      }, 2000);
    }, 2000);
  };

  const handleRazorpayPayment = async () => {
    setLoading(true);
    const token = localStorage.getItem('token');
    const res = await fetch('http://localhost:8080/payment/razorpay-order', {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/x-www-form-urlencoded',
        'Authorization': `Bearer ${token}`
      },
      body: `amount=${cartTotal}&currency=INR&receipt=rcptid_${Date.now()}`
    });
    const data = await res.json ? await res.json() : await res.text();
    const order = typeof data === 'string' ? JSON.parse(data) : data;
    await loadRazorpayScript('https://checkout.razorpay.com/v1/checkout.js');
    const options = {
      key: RAZORPAY_KEY_ID,
      amount: order.amount,
      currency: order.currency,
      name: 'Online Book Store',
      description: 'Book Purchase',
      order_id: order.id,
      handler: async function (response) {
        // Create Order first, then save payment
        try {
          // 1) Create Order
          const orderResp = await fetch('http://localhost:8080/order', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
              orderDate: new Date().toISOString().split('T')[0],
              totalAmount: cartTotal,
              userId: (JSON.parse(localStorage.getItem('user'))?.id)
            })
          });
          const createdOrder = await orderResp.json();

          // 2) Save Payment linked to created order
          await fetch('http://localhost:8080/payment', {
            method: 'POST',
            headers: { 
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
              paymentDate: new Date().toISOString().split('T')[0],
              amount: cartTotal,
              method: 'RAZORPAY',
              status: 'PAID',
              orderId: createdOrder.id
            })
          });
        } catch (err) {
          // Optionally handle error
        }
        setAlert({ show: true, message: 'Payment successful! Payment ID: ' + response.razorpay_payment_id, variant: 'success' });
        clearCart();
        setTimeout(() => navigate('/customer'), 2000);
      },
      prefill: {
        name: formData.fullName,
        email: formData.email,
        contact: ''
      },
      theme: { color: '#3399cc' }
    };
    const rzp = new window.Razorpay(options);
    rzp.open();
    setLoading(false);
  };

  if (cartItems.length === 0) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <FontAwesomeIcon icon={faCreditCard} size="3x" className="text-muted mb-3" />
          <h4 className="text-muted">Your cart is empty</h4>
          <p className="text-muted">Add some books to your cart before checkout.</p>
          <Button as={Link} to="/books" variant="primary">
            <FontAwesomeIcon icon={faArrowLeft} className="me-2" />
            Continue Shopping
          </Button>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <Row className="mb-4">
        <Col>
          <Button as={Link} to="/cart" variant="outline-primary" className="mb-3">
            <FontAwesomeIcon icon={faArrowLeft} className="me-2" />
            Back to Cart
          </Button>
          <h1 className="mb-3">
            <FontAwesomeIcon icon={faCreditCard} className="text-primary me-3" />
            Checkout
          </h1>
          <p className="text-muted">Complete your purchase by providing your details.</p>
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
            <Card.Header>
              <h5 className="mb-0">Shipping Information</h5>
            </Card.Header>
            <Card.Body>
              <Form onSubmit={handleSubmit}>
                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>Full Name *</Form.Label>
                      <Form.Control
                        type="text"
                        name="fullName"
                        value={formData.fullName}
                        onChange={handleInputChange}
                        required
                      />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>Email *</Form.Label>
                      <Form.Control
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        required
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <Form.Group className="mb-3">
                  <Form.Label>Address *</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={3}
                    name="address"
                    value={formData.address}
                    onChange={handleInputChange}
                    required
                  />
                </Form.Group>

                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>City *</Form.Label>
                      <Form.Control
                        type="text"
                        name="city"
                        value={formData.city}
                        onChange={handleInputChange}
                        required
                      />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>ZIP Code *</Form.Label>
                      <Form.Control
                        type="text"
                        name="zipCode"
                        value={formData.zipCode}
                        onChange={handleInputChange}
                        required
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <hr />

                <h5>Payment Information</h5>
                <Row>
                  <Col md={12}>
                    <Form.Group className="mb-3">
                      <Form.Label>Card Number *</Form.Label>
                      <Form.Control
                        type="text"
                        name="cardNumber"
                        value={formData.cardNumber}
                        onChange={handleInputChange}
                        placeholder="1234 5678 9012 3456"
                        required
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>Expiry Date *</Form.Label>
                      <Form.Control
                        type="text"
                        name="expiryDate"
                        value={formData.expiryDate}
                        onChange={handleInputChange}
                        placeholder="MM/YY"
                        required
                      />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>CVV *</Form.Label>
                      <Form.Control
                        type="text"
                        name="cvv"
                        value={formData.cvv}
                        onChange={handleInputChange}
                        placeholder="123"
                        required
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <div className="d-grid">
                  <Button 
                    type="button"
                    variant="success"
                    size="lg"
                    disabled={loading}
                    onClick={handleRazorpayPayment}
                  >
                    {loading ? 'Processing...' : `Pay with Razorpay - ${formatINR(cartTotal)}`}
                  </Button>
                </div>
              </Form>
            </Card.Body>
          </Card>
        </Col>

        <Col lg={4}>
          <Card className="shadow">
            <Card.Header>
              <h5 className="mb-0">Order Summary</h5>
            </Card.Header>
            <Card.Body>
              {cartItems.map((item) => (
                <div key={item.id} className="d-flex justify-content-between mb-2">
                  <span>{item.title} x {item.quantity}</span>
                  <span>{formatINR(item.price * item.quantity)}</span>
                </div>
              ))}
              <hr />
              <div className="d-flex justify-content-between mb-2">
                <span>Subtotal:</span>
                <span>{formatINR(cartTotal)}</span>
              </div>
              <div className="d-flex justify-content-between mb-2">
                <span>Shipping:</span>
                <span className="text-success">Free</span>
              </div>
              <hr />
              <div className="d-flex justify-content-between">
                <strong>Total:</strong>
                <strong className="text-primary">{formatINR(cartTotal)}</strong>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Checkout; 