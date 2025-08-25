import React, { useState, useEffect } from 'react';
import { 
  Container, 
  Row, 
  Col, 
  Card, 
  Button, 
  Badge,
  Alert
} from 'react-bootstrap';
import { useParams, Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faArrowLeft,
  faShoppingCart,
  faBook
} from '@fortawesome/free-solid-svg-icons';
import { useCart } from '../../context/CartContext';
import { formatINR } from '../../utils/currencyUtils';
import axios from 'axios';

const BookDetail = () => {
  const { id } = useParams();
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);
  const [alert, setAlert] = useState({ show: false, message: '', variant: '' });
  const { addToCart } = useCart();

  useEffect(() => {
    fetchBook();
  }, [id]);

  const fetchBook = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/books/${id}`);
      setBook(response.data);
    } catch (error) {
      console.error('Error fetching book:', error);
      setAlert({
        show: true,
        message: 'Error fetching book details',
        variant: 'danger'
      });
    } finally {
      setLoading(false);
    }
  };

  const handleAddToCart = () => {
    if (book.stock > 0) {
      addToCart(book, 1);
      setAlert({
        show: true,
        message: `${book.title} added to cart!`,
        variant: 'success'
      });
    } else {
      setAlert({
        show: true,
        message: 'This book is out of stock',
        variant: 'warning'
      });
    }
  };

  const renderImage = (imageData) => {
    if (!imageData) return null;
    if (imageData.startsWith('data:image')) return imageData;
    return `data:image/jpeg;base64,${imageData}`;
  };

  if (loading) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
          <p className="mt-3">Loading book details...</p>
        </div>
      </Container>
    );
  }

  if (!book) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <FontAwesomeIcon icon={faBook} size="3x" className="text-muted mb-3" />
          <h4 className="text-muted">Book not found</h4>
          <p className="text-muted">The book you're looking for doesn't exist.</p>
          <Button as={Link} to="/books" variant="primary">
            <FontAwesomeIcon icon={faArrowLeft} className="me-2" />
            Back to Books
          </Button>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <Row className="mb-4">
        <Col>
          <Button as={Link} to="/books" variant="outline-primary" className="mb-3">
            <FontAwesomeIcon icon={faArrowLeft} className="me-2" />
            Back to Books
          </Button>
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
        <Col lg={4} className="mb-4">
          <Card className="shadow">
            {book.imageUrl && (
              <Card.Img
                variant="top"
                src={renderImage(book.imageUrl)}
                alt={book.title}
                style={{ height: '400px', objectFit: 'cover' }}
              />
            )}
            <Card.Body className="text-center">
              <Button 
                variant="success" 
                size="lg"
                className="w-100"
                onClick={handleAddToCart}
                disabled={book.stock === 0}
              >
                <FontAwesomeIcon icon={faShoppingCart} className="me-2" />
                {book.stock > 0 ? 'Add to Cart' : 'Out of Stock'}
              </Button>
            </Card.Body>
          </Card>
        </Col>

        <Col lg={8}>
          <Card className="shadow">
            <Card.Body className="p-4">
              <div className="mb-3">
                <h1 className="mb-2">{book.title}</h1>
                <p className="text-muted h5">by {book.author}</p>
              </div>

              <div className="mb-4">
                {book.category && (
                  <Badge bg="info" className="me-2 mb-2">
                    {book.category.categoryName}
                  </Badge>
                )}
                <Badge bg={book.stock > 0 ? 'success' : 'danger'} className="mb-2">
                  {book.stock > 0 ? `${book.stock} in stock` : 'Out of Stock'}
                </Badge>
              </div>

              <div className="mb-4">
                <h3 className="text-primary mb-3">{formatINR(book.price)}</h3>
                <p className="text-muted">
                  This book is available for purchase. Add it to your cart to proceed with checkout.
                </p>
              </div>

              <div className="mb-4">
                <h5>Book Details</h5>
                <hr />
                <Row>
                  <Col md={6}>
                    <p><strong>Title:</strong> {book.title}</p>
                    <p><strong>Author:</strong> {book.author}</p>
                  </Col>
                  <Col md={6}>
                    <p><strong>Price:</strong> {formatINR(book.price)}</p>
                    <p><strong>Stock:</strong> {book.stock} copies</p>
                  </Col>
                </Row>
              </div>

              <div className="mb-4">
                <h5>Description</h5>
                <hr />
                <p className="text-muted">
                  This is a wonderful book that offers great value to readers. 
                  It's perfect for anyone interested in expanding their knowledge 
                  and enjoying a good read.
                </p>
              </div>

              <div className="d-grid gap-2 d-md-flex justify-content-md-start">
                <Button 
                  variant="success" 
                  size="lg"
                  onClick={handleAddToCart}
                  disabled={book.stock === 0}
                >
                  <FontAwesomeIcon icon={faShoppingCart} className="me-2" />
                  Add to Cart
                </Button>
                <Button as={Link} to="/cart" variant="outline-primary" size="lg">
                  View Cart
                </Button>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default BookDetail; 