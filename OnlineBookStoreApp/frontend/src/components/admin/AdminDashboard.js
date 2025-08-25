import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faBook, 
  faUsers, 
  faShoppingCart, 
  faDollarSign,
  faPlus,
  faList,
  faChartBar
} from '@fortawesome/free-solid-svg-icons';
import { formatINR } from '../../utils/currencyUtils';
import axios from 'axios';

const AdminDashboard = () => {
  const [stats, setStats] = useState({
    totalBooks: 0,
    totalOrders: 0,
    totalCustomers: 0,
    totalRevenue: 0
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDashboardStats();
  }, []);

  const fetchDashboardStats = async () => {
    try {
      // Fetch books count
      const booksResponse = await axios.get('http://localhost:8080/books');
      const totalBooks = booksResponse.data.length;

      // Fetch orders count
      const ordersResponse = await axios.get('http://localhost:8080/order');
      const totalOrders = ordersResponse.data.length;

      // Calculate total revenue from orders
      const totalRevenue = ordersResponse.data.reduce((sum, order) => {
        const orderAmount = Number(order.totalAmount);
        return sum + (Number.isNaN(orderAmount) ? 0 : orderAmount);
      }, 0);

      // For demo purposes, we'll estimate customers based on unique users in orders
      const uniqueCustomers = new Set(ordersResponse.data.map(order => order.userId)).size;

      setStats({
        totalBooks,
        totalOrders,
        totalCustomers: uniqueCustomers,
        totalRevenue
      });
    } catch (error) {
      console.error('Error fetching dashboard stats:', error);
    } finally {
      setLoading(false);
    }
  };

  const StatCard = ({ title, value, icon, color, link }) => (
    <Col md={3} className="mb-4">
      <Card className="text-center h-100 shadow">
        <Card.Body className="p-4">
          <FontAwesomeIcon 
            icon={icon} 
            className={`text-${color} mb-3`} 
            size="2x"
          />
          <Card.Title className="h3 mb-2">{value}</Card.Title>
          <Card.Text className="text-muted">{title}</Card.Text>
          {link && (
            <Button as={Link} to={link} variant="outline-primary" size="sm">
              View Details
            </Button>
          )}
        </Card.Body>
      </Card>
    </Col>
  );

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

  if (loading) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
          <p className="mt-3">Loading dashboard...</p>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <Row className="mb-4">
        <Col>
          <h1 className="mb-3">
            <FontAwesomeIcon icon={faChartBar} className="text-primary me-3" />
            Admin Dashboard
          </h1>
          <p className="text-muted">Welcome to the admin panel. Here's an overview of your store.</p>
        </Col>
      </Row>

      {/* Statistics Cards */}
      <Row className="mb-5">
        <StatCard
          title="Total Books"
          value={stats.totalBooks}
          icon={faBook}
          color="primary"
          link="/admin/books"
        />
        <StatCard
          title="Total Orders"
          value={stats.totalOrders}
          icon={faShoppingCart}
          color="success"
          link="/admin/orders"
        />
        <StatCard
          title="Total Customers"
          value={stats.totalCustomers}
          icon={faUsers}
          color="info"
        />
        <StatCard
          title="Total Revenue"
          value={formatINR(stats.totalRevenue)}
          icon={faDollarSign}
          color="warning"
        />
      </Row>

      {/* Quick Actions */}
      <Row className="mb-4">
        <Col>
          <h3 className="mb-4">Quick Actions</h3>
        </Col>
      </Row>

      <Row>
        <QuickActionCard
          title="Add New Book"
          description="Add a new book to the store inventory with image upload"
          icon={faPlus}
          link="/admin/books"
          variant="primary"
        />
        <QuickActionCard
          title="Manage Books"
          description="View, edit, or delete existing books in the store"
          icon={faList}
          link="/admin/books"
          variant="success"
        />
        <QuickActionCard
          title="View Orders"
          description="Monitor and manage customer orders and deliveries"
          icon={faShoppingCart}
          link="/admin/orders"
          variant="info"
        />
      </Row>

      {/* Recent Activity Section */}
      <Row className="mt-5">
        <Col>
          <Card className="shadow">
            <Card.Header>
              <h5 className="mb-0">
                <FontAwesomeIcon icon={faChartBar} className="me-2" />
                Recent Activity
              </h5>
            </Card.Header>
            <Card.Body>
              <p className="text-muted">
                This section will show recent orders, new book additions, and other important activities.
              </p>
              <div className="d-flex justify-content-between align-items-center">
                <div>
                  <p className="mb-1"><strong>Latest Orders:</strong> {stats.totalOrders} orders processed</p>
                  <p className="mb-1"><strong>Inventory:</strong> {stats.totalBooks} books available</p>
                  <p className="mb-0"><strong>Revenue:</strong> {formatINR(stats.totalRevenue)} generated</p>
                </div>
                <Button as={Link} to="/admin/orders" variant="outline-primary">
                  View All Activity
                </Button>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Tips Section */}
      <Row className="mt-4">
        <Col>
          <Card className="bg-light border-0">
            <Card.Body>
              <h6 className="text-primary mb-3">
                <FontAwesomeIcon icon={faBook} className="me-2" />
                Admin Tips
              </h6>
              <ul className="mb-0">
                <li>Regularly update book inventory to ensure accurate stock levels</li>
                <li>Monitor customer orders and ensure timely delivery</li>
                <li>Keep book images and descriptions up to date</li>
                <li>Review sales reports to understand customer preferences</li>
              </ul>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default AdminDashboard; 