import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

// Components
import Navbar from './components/common/Navbar';
import Home from './components/common/Home';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import ForgotPassword from './components/auth/ForgotPassword';
import ResetPassword from './components/auth/ResetPassword';

// Admin Components
import AdminDashboard from './components/admin/AdminDashboard';
import BookManagement from './components/admin/BookManagement';
import CategoryManagement from './components/admin/CategoryManagement';
import OrderManagement from './components/admin/OrderManagement';

// Customer Components
import CustomerDashboard from './components/customer/CustomerDashboard';
import BookList from './components/customer/BookList';
import BookDetail from './components/customer/BookDetail';
import Cart from './components/customer/Cart';
import Checkout from './components/customer/Checkout';
import OrderHistory from './components/customer/OrderHistory';

// Context
import { AuthProvider } from './context/AuthContext';
import { CartProvider } from './context/CartContext';

// Protected Route Component
const ProtectedRoute = ({ children, allowedRoles }) => {
  const token = localStorage.getItem('token');
  const userRole = localStorage.getItem('userRole');
  
  if (!token) {
    return <Navigate to="/login" replace />;
  }
  
  if (allowedRoles && !allowedRoles.includes(userRole)) {
    return <Navigate to="/" replace />;
  }
  
  return children;
};

function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <Router>
          <div className="App">
            <Navbar />
            <div className="container-fluid">
              <Routes>
                {/* Public Routes */}
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/forgot-password" element={<ForgotPassword />} />
                <Route path="/reset-password" element={<ResetPassword />} />
                
                {/* Admin Routes */}
                <Route 
                  path="/admin" 
                  element={
                    <ProtectedRoute allowedRoles={['ROLE_ADMIN']}>
                      <AdminDashboard />
                    </ProtectedRoute>
                  } 
                />
                <Route 
                  path="/admin/books" 
                  element={
                    <ProtectedRoute allowedRoles={['ROLE_ADMIN']}>
                      <BookManagement />
                    </ProtectedRoute>
                  } 
                />
                <Route 
                  path="/admin/categories" 
                  element={
                    <ProtectedRoute allowedRoles={['ROLE_ADMIN']}>
                      <CategoryManagement />
                    </ProtectedRoute>
                  } 
                />
                <Route 
                  path="/admin/orders" 
                  element={
                    <ProtectedRoute allowedRoles={['ROLE_ADMIN']}>
                      <OrderManagement />
                    </ProtectedRoute>
                  } 
                />
                
                {/* Customer Routes */}
                <Route 
                  path="/customer" 
                  element={
                    <ProtectedRoute allowedRoles={['ROLE_CUSTOMER']}>
                      <CustomerDashboard />
                    </ProtectedRoute>
                  } 
                />
                <Route 
                  path="/books" 
                  element={
                    <ProtectedRoute allowedRoles={['ROLE_CUSTOMER', 'ROLE_ADMIN']}>
                      <BookList />
                    </ProtectedRoute>
                  } 
                />
                <Route 
                  path="/books/:id" 
                  element={
                    <ProtectedRoute allowedRoles={['ROLE_CUSTOMER']}>
                      <BookDetail />
                    </ProtectedRoute>
                  } 
                />
                <Route 
                  path="/cart" 
                  element={
                    <ProtectedRoute allowedRoles={['ROLE_CUSTOMER']}>
                      <Cart />
                    </ProtectedRoute>
                  } 
                />
                <Route 
                  path="/checkout" 
                  element={
                    <ProtectedRoute allowedRoles={['ROLE_CUSTOMER']}>
                      <Checkout />
                    </ProtectedRoute>
                  } 
                />
                <Route 
                  path="/orders" 
                  element={
                    <ProtectedRoute allowedRoles={['ROLE_CUSTOMER']}>
                      <OrderHistory />
                    </ProtectedRoute>
                  } 
                />
                {/* Fallback route */}
                <Route 
                  path="*" 
                  element={
                    localStorage.getItem('token') ? <Navigate to="/books" /> : <Navigate to="/login" />
                  }
                />
              </Routes>
            </div>
          </div>
        </Router>
      </CartProvider>
    </AuthProvider>
  );
}

export default App;
