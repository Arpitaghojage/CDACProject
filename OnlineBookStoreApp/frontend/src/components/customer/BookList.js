import React, { useState, useEffect } from 'react';
import { 
  Container, 
  Row, 
  Col, 
  Card, 
  Button, 
  Form, 
  Badge,
  Pagination,
  Alert
} from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faBook, 
  faSearch, 
  faShoppingCart,
  faFilter,
  faSort
} from '@fortawesome/free-solid-svg-icons';
import { useCart } from '../../context/CartContext';
import { formatINR } from '../../utils/currencyUtils';
import axios from 'axios';

const BookList = () => {
  const [books, setBooks] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('');
  const [sortBy, setSortBy] = useState('title');
  const [currentPage, setCurrentPage] = useState(1);
  const [booksPerPage] = useState(12);
  const [alert, setAlert] = useState({ show: false, message: '', variant: '' });

  const { addToCart } = useCart();

  useEffect(() => {
    console.log('BookList component mounted');
    fetchBooks();
    fetchCategories();
  }, []);

  const fetchBooks = async () => {
    try {
      const token = localStorage.getItem('token');
      const config = {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      };
      
      console.log('Fetching books with token:', token ? 'Token present' : 'No token');
      const response = await axios.get('http://localhost:8080/books', config);
      console.log('Books response:', response.data);
      setBooks(response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
      console.error('Error details:', error.response?.data);
      setAlert({
        show: true,
        message: `Error fetching books: ${error.response?.data?.message || error.message}`,
        variant: 'danger'
      });
    } finally {
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    try {
      const token = localStorage.getItem('token');
      const config = {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      };
      
      const response = await axios.get('http://localhost:8080/category', config);
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  const handleAddToCart = (book) => {
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
    
    try {
      // Handle different image data formats
      if (typeof imageData === 'string') {
        // If it's already a base64 string
        if (imageData.startsWith('data:image')) {
          return imageData;
        }
        // If it's a base64 string without data URL prefix
        return `data:image/jpeg;base64,${imageData}`;
      } else if (imageData instanceof ArrayBuffer || ArrayBuffer.isView(imageData)) {
        // If it's binary data
        const base64String = btoa(String.fromCharCode(...new Uint8Array(imageData)));
        return `data:image/jpeg;base64,${base64String}`;
      }
      return null;
    } catch (error) {
      console.error('Error rendering image:', error);
      return null;
    }
  };

  // Filter and sort books
  const filteredBooks = books
    .filter(book => {
      const matchesSearch = book.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
                           book.author.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesCategory = !selectedCategory || book.category?.categoryName === selectedCategory;
      return matchesSearch && matchesCategory;
    })
    .sort((a, b) => {
      switch (sortBy) {
        case 'title':
          return a.title.localeCompare(b.title);
        case 'author':
          return a.author.localeCompare(b.author);
        case 'price-low':
          return a.price - b.price;
        case 'price-high':
          return b.price - a.price;
        default:
          return 0;
      }
    });

  // Pagination
  const indexOfLastBook = currentPage * booksPerPage;
  const indexOfFirstBook = indexOfLastBook - booksPerPage;
  const currentBooks = filteredBooks.slice(indexOfFirstBook, indexOfLastBook);
  const totalPages = Math.ceil(filteredBooks.length / booksPerPage);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  if (loading) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
          <p className="mt-3">Loading books...</p>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <Row className="mb-4">
        <Col>
          <h1 className="mb-3">
            <FontAwesomeIcon icon={faBook} className="text-primary me-3" />
            Browse Books
          </h1>
          <p className="text-muted">Discover our collection of books across various genres and categories.</p>
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

      {/* Filters and Search */}
      <Row className="mb-4">
        <Col md={4}>
          <Form.Group>
            <Form.Label>
              <FontAwesomeIcon icon={faSearch} className="me-2" />
              Search Books
            </Form.Label>
            <Form.Control
              type="text"
              placeholder="Search by title or author..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </Form.Group>
        </Col>
        <Col md={3}>
          <Form.Group>
            <Form.Label>
              <FontAwesomeIcon icon={faFilter} className="me-2" />
              Category
            </Form.Label>
            <Form.Select
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
            >
              <option value="">All Categories</option>
              {categories.map((category) => (
                <option key={category.id} value={category.categoryName}>
                  {category.categoryName}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
        </Col>
        <Col md={3}>
          <Form.Group>
            <Form.Label>
              <FontAwesomeIcon icon={faSort} className="me-2" />
              Sort By
            </Form.Label>
            <Form.Select
              value={sortBy}
              onChange={(e) => setSortBy(e.target.value)}
            >
              <option value="title">Title A-Z</option>
              <option value="author">Author A-Z</option>
              <option value="price-low">Price: Low to High</option>
              <option value="price-high">Price: High to Low</option>
            </Form.Select>
          </Form.Group>
        </Col>
        <Col md={2} className="d-flex align-items-end">
          <div className="text-muted">
            {filteredBooks.length} books found
          </div>
        </Col>
      </Row>

      {/* Books Grid */}
      <Row>
        {currentBooks.map((book, index) => (
          <Col key={book.id || `book-${index}-${book.title}`} lg={3} md={4} sm={6} className="mb-4">
            <Card className="h-100 shadow">
              {book.imageUrl && (
                <Card.Img
                  variant="top"
                  src={renderImage(book.imageUrl)}
                  alt={book.title}
                  style={{ height: '250px', objectFit: 'cover' }}
                />
              )}
              <Card.Body className="d-flex flex-column">
                <Card.Title className="h6 mb-2">{book.title}</Card.Title>
                <Card.Text className="text-muted small mb-2">by {book.author}</Card.Text>
                
                {book.category && (
                  <Badge bg="info" className="mb-2 align-self-start">
                    {book.category.categoryName}
                  </Badge>
                )}
                
                <div className="d-flex justify-content-between align-items-center mb-3">
                  <span className="fw-bold text-primary h5 mb-0">{formatINR(book.price)}</span>
                  <Badge bg={book.stock > 0 ? 'success' : 'danger'}>
                    {book.stock > 0 ? `${book.stock} in stock` : 'Out of Stock'}
                  </Badge>
                </div>
                
                <div className="mt-auto">
                  <Row>
                    <Col>
                      <Button 
                        as={Link} 
                        to={`/books/${book.id}`} 
                        variant="outline-primary" 
                        size="sm"
                        className="w-100 mb-2"
                      >
                        View Details
                      </Button>
                    </Col>
                    <Col>
                      <Button 
                        variant="success" 
                        size="sm"
                        className="w-100"
                        onClick={() => handleAddToCart(book)}
                        disabled={book.stock === 0}
                      >
                        <FontAwesomeIcon icon={faShoppingCart} className="me-1" />
                        Add to Cart
                      </Button>
                    </Col>
                  </Row>
                </div>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      {/* Pagination */}
      {totalPages > 1 && (
        <Row className="mt-4">
          <Col className="d-flex justify-content-center">
            <Pagination>
              <Pagination.First 
                onClick={() => paginate(1)}
                disabled={currentPage === 1}
              />
              <Pagination.Prev 
                onClick={() => paginate(currentPage - 1)}
                disabled={currentPage === 1}
              />
              
              {Array.from({ length: totalPages }, (_, i) => i + 1).map(number => (
                <Pagination.Item
                  key={number}
                  active={number === currentPage}
                  onClick={() => paginate(number)}
                >
                  {number}
                </Pagination.Item>
              ))}
              
              <Pagination.Next 
                onClick={() => paginate(currentPage + 1)}
                disabled={currentPage === totalPages}
              />
              <Pagination.Last 
                onClick={() => paginate(totalPages)}
                disabled={currentPage === totalPages}
              />
            </Pagination>
          </Col>
        </Row>
      )}

      {/* No Results */}
      {filteredBooks.length === 0 && (
        <Row className="mt-5">
          <Col className="text-center">
            <FontAwesomeIcon icon={faBook} size="3x" className="text-muted mb-3" />
            <h4 className="text-muted">No books found</h4>
            <p className="text-muted">
              Try adjusting your search criteria or browse all categories.
            </p>
            <Button 
              variant="primary" 
              onClick={() => {
                setSearchTerm('');
                setSelectedCategory('');
                setSortBy('title');
              }}
            >
              Clear Filters
            </Button>
          </Col>
        </Row>
      )}
    </Container>
  );
};

export default BookList; 