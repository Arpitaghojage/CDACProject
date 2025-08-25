import React, { useState, useEffect } from 'react';
import { 
  Container, 
  Row, 
  Col, 
  Card, 
  Button, 
  Table, 
  Modal, 
  Form, 
  Alert,
  Badge,
  Image
} from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faBook, 
  faPlus, 
  faEdit, 
  faTrash, 
  faEye,
  faSearch
} from '@fortawesome/free-solid-svg-icons';
import { formatINR } from '../../utils/currencyUtils';
import axios from 'axios';

const BookManagement = () => {
  const [books, setBooks] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingBook, setEditingBook] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [alert, setAlert] = useState({ show: false, message: '', variant: '' });

  const [formData, setFormData] = useState({
    title: '',
    author: '',
    price: '',
    stock: '',
    categoryName: '',
    image: null
  });

  useEffect(() => {
    fetchBooks();
    fetchCategories();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await axios.get('http://localhost:8080/books');
      setBooks(response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
      setAlert({
        show: true,
        message: 'Error fetching books',
        variant: 'danger'
      });
    } finally {
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await axios.get('http://localhost:8080/category');
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value, files } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: files ? files[0] : value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const formDataToSend = new FormData();
    formDataToSend.append('title', formData.title);
    formDataToSend.append('author', formData.author);
    formDataToSend.append('price', formData.price);
    formDataToSend.append('stock', formData.stock);
    formDataToSend.append('categoryName', formData.categoryName);
    if (formData.image) {
      formDataToSend.append('image', formData.image);
    }

    try {
      if (editingBook) {
        await axios.put(`http://localhost:8080/books/update/${editingBook.id}`, formDataToSend, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });
        setAlert({
          show: true,
          message: 'Book updated successfully!',
          variant: 'success'
        });
      } else {
        await axios.post('http://localhost:8080/books/add-with-image', formDataToSend, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });
        setAlert({
          show: true,
          message: 'Book added successfully!',
          variant: 'success'
        });
      }
      
      handleCloseModal();
      fetchBooks();
    } catch (error) {
      setAlert({
        show: true,
        message: error.response?.data || 'Error saving book',
        variant: 'danger'
      });
    }
  };

  const handleEdit = (book) => {
    setEditingBook(book);
    setFormData({
      title: book.title,
      author: book.author,
      price: book.price,
      stock: book.stock,
      categoryName: book.category?.categoryName || '',
      image: null
    });
    setShowModal(true);
  };

  const handleDelete = async (bookId) => {
    if (window.confirm('Are you sure you want to delete this book?')) {
      try {
        await axios.delete(`http://localhost:8080/books/${bookId}`);
        setAlert({
          show: true,
          message: 'Book deleted successfully!',
          variant: 'success'
        });
        fetchBooks();
      } catch (error) {
        setAlert({
          show: true,
          message: 'Error deleting book',
          variant: 'danger'
        });
      }
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingBook(null);
    setFormData({
      title: '',
      author: '',
      price: '',
      stock: '',
      categoryName: '',
      image: null
    });
  };

  const filteredBooks = books.filter(book =>
    book.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
    book.author.toLowerCase().includes(searchTerm.toLowerCase()) ||
    book.category?.categoryName.toLowerCase().includes(searchTerm.toLowerCase())
  );

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
            Book Management
          </h1>
          <p className="text-muted">Manage your book inventory, add new books, and update existing ones.</p>
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

      <Row className="mb-4">
        <Col md={8}>
          <Form.Group>
            <Form.Label>
              <FontAwesomeIcon icon={faSearch} className="me-2" />
              Search Books
            </Form.Label>
            <Form.Control
              type="text"
              placeholder="Search by title, author, or category..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </Form.Group>
        </Col>
        <Col md={4} className="d-flex align-items-end">
          <Button 
            variant="primary" 
            onClick={() => setShowModal(true)}
            className="w-100"
          >
            <FontAwesomeIcon icon={faPlus} className="me-2" />
            Add New Book
          </Button>
        </Col>
      </Row>

      <Card className="shadow">
        <Card.Header>
          <h5 className="mb-0">Book Inventory ({filteredBooks.length} books)</h5>
        </Card.Header>
        <Card.Body className="p-0">
          <Table responsive hover className="mb-0">
            <thead className="table-light">
              <tr>
                <th>Image</th>
                <th>Title</th>
                <th>Author</th>
                <th>Category</th>
                <th>Price</th>
                <th>Stock</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredBooks.map((book) => (
                <tr key={book.id}>
                  <td>
                    {book.imageUrl && (
                      <Image
                        src={renderImage(book.imageUrl)}
                        alt={book.title}
                        width="50"
                        height="60"
                        className="object-fit-cover rounded"
                      />
                    )}
                  </td>
                  <td>
                    <strong>{book.title}</strong>
                  </td>
                  <td>{book.author}</td>
                  <td>
                    <Badge bg="info">
                      {book.category?.categoryName || 'N/A'}
                    </Badge>
                  </td>
                  <td>{formatINR(book.price)}</td>
                  <td>
                    <Badge bg={book.stock > 0 ? 'success' : 'danger'}>
                      {book.stock} in stock
                    </Badge>
                  </td>
                  <td>
                    <Button
                      variant="outline-primary"
                      size="sm"
                      className="me-2"
                      onClick={() => handleEdit(book)}
                    >
                      <FontAwesomeIcon icon={faEdit} />
                    </Button>
                    <Button
                      variant="outline-danger"
                      size="sm"
                      onClick={() => handleDelete(book.id)}
                    >
                      <FontAwesomeIcon icon={faTrash} />
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Card.Body>
      </Card>

      {/* Add/Edit Book Modal */}
      <Modal show={showModal} onHide={handleCloseModal} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>
            <FontAwesomeIcon icon={editingBook ? faEdit : faPlus} className="me-2" />
            {editingBook ? 'Edit Book' : 'Add New Book'}
          </Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            <Row>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Title *</Form.Label>
                  <Form.Control
                    type="text"
                    name="title"
                    value={formData.title}
                    onChange={handleInputChange}
                    required
                  />
                </Form.Group>
              </Col>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Author *</Form.Label>
                  <Form.Control
                    type="text"
                    name="author"
                    value={formData.author}
                    onChange={handleInputChange}
                    required
                  />
                </Form.Group>
              </Col>
            </Row>

            <Row>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Price *</Form.Label>
                  <Form.Control
                    type="number"
                    name="price"
                    value={formData.price}
                    onChange={handleInputChange}
                    step="0.01"
                    min="0"
                    required
                  />
                </Form.Group>
              </Col>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Stock *</Form.Label>
                  <Form.Control
                    type="number"
                    name="stock"
                    value={formData.stock}
                    onChange={handleInputChange}
                    min="0"
                    required
                  />
                </Form.Group>
              </Col>
            </Row>

            <Form.Group className="mb-3">
              <Form.Label>Category *</Form.Label>
              <Form.Select
                name="categoryName"
                value={formData.categoryName}
                onChange={handleInputChange}
                required
              >
                <option value="">Select a category</option>
                {categories.map((category) => (
                  <option key={category.id} value={category.categoryName}>
                    {category.categoryName}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Book Image</Form.Label>
              <Form.Control
                type="file"
                name="image"
                onChange={handleInputChange}
                accept="image/*"
              />
              <Form.Text className="text-muted">
                Supported formats: JPG, JPEG, PNG, GIF (Max 2MB)
              </Form.Text>
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Cancel
            </Button>
            <Button variant="primary" type="submit">
              {editingBook ? 'Update Book' : 'Add Book'}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </Container>
  );
};

export default BookManagement; 