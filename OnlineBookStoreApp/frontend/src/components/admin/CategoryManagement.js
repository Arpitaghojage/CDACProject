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
  Badge
} from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faTags, 
  faPlus, 
  faEdit, 
  faTrash 
} from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';

const CategoryManagement = () => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingCategory, setEditingCategory] = useState(null);
  const [alert, setAlert] = useState({ show: false, message: '', variant: '' });

  const [formData, setFormData] = useState({
    categoryName: ''
  });

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const response = await axios.get('http://localhost:8080/category');
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
      setAlert({
        show: true,
        message: 'Error fetching categories',
        variant: 'danger'
      });
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      if (editingCategory) {
        // For update, we'll need to implement this in the backend
        setAlert({
          show: true,
          message: 'Category update functionality needs backend implementation',
          variant: 'warning'
        });
      } else {
        await axios.post('http://localhost:8080/category', formData);
        setAlert({
          show: true,
          message: 'Category added successfully!',
          variant: 'success'
        });
      }
      
      handleCloseModal();
      fetchCategories();
    } catch (error) {
      setAlert({
        show: true,
        message: error.response?.data || 'Error saving category',
        variant: 'danger'
      });
    }
  };

  const handleEdit = (category) => {
    setEditingCategory(category);
    setFormData({
      categoryName: category.categoryName
    });
    setShowModal(true);
  };

  const handleDelete = async (categoryName) => {
    if (window.confirm('Are you sure you want to delete this category?')) {
      try {
        await axios.delete(`http://localhost:8080/category/${categoryName}`);
        setAlert({
          show: true,
          message: 'Category deleted successfully!',
          variant: 'success'
        });
        fetchCategories();
      } catch (error) {
        setAlert({
          show: true,
          message: 'Error deleting category',
          variant: 'danger'
        });
      }
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingCategory(null);
    setFormData({
      categoryName: ''
    });
  };

  if (loading) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
          <p className="mt-3">Loading categories...</p>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <Row className="mb-4">
        <Col>
          <h1 className="mb-3">
            <FontAwesomeIcon icon={faTags} className="text-primary me-3" />
            Category Management
          </h1>
          <p className="text-muted">Manage book categories and organize your inventory.</p>
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
        <Col className="d-flex justify-content-end">
          <Button 
            variant="primary" 
            onClick={() => setShowModal(true)}
          >
            <FontAwesomeIcon icon={faPlus} className="me-2" />
            Add New Category
          </Button>
        </Col>
      </Row>

      <Card className="shadow">
        <Card.Header>
          <h5 className="mb-0">Book Categories ({categories.length} categories)</h5>
        </Card.Header>
        <Card.Body className="p-0">
          <Table responsive hover className="mb-0">
            <thead className="table-light">
              <tr>
                <th>ID</th>
                <th>Category Name</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {categories.map((category) => (
                <tr key={category.id}>
                  <td>{category.id}</td>
                  <td>
                    <Badge bg="info" className="fs-6">
                      {category.categoryName}
                    </Badge>
                  </td>
                  <td>
                    <Button
                      variant="outline-primary"
                      size="sm"
                      className="me-2"
                      onClick={() => handleEdit(category)}
                    >
                      <FontAwesomeIcon icon={faEdit} />
                    </Button>
                    <Button
                      variant="outline-danger"
                      size="sm"
                      onClick={() => handleDelete(category.categoryName)}
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

      {/* Add/Edit Category Modal */}
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>
            <FontAwesomeIcon icon={editingCategory ? faEdit : faPlus} className="me-2" />
            {editingCategory ? 'Edit Category' : 'Add New Category'}
          </Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            <Form.Group className="mb-3">
              <Form.Label>Category Name *</Form.Label>
              <Form.Control
                type="text"
                name="categoryName"
                value={formData.categoryName}
                onChange={handleInputChange}
                placeholder="Enter category name"
                required
              />
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Cancel
            </Button>
            <Button variant="primary" type="submit">
              {editingCategory ? 'Update Category' : 'Add Category'}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </Container>
  );
};

export default CategoryManagement; 