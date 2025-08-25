import React, { createContext, useContext, useState, useEffect } from 'react';
import axios from 'axios';

const AuthContext = createContext();

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [token, setToken] = useState(localStorage.getItem('token'));

  // Set up axios defaults
  useEffect(() => {
    if (token) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    } else {
      delete axios.defaults.headers.common['Authorization'];
    }
  }, [token]);

  // Check if user is authenticated on app load
  useEffect(() => {
    const checkAuth = async () => {
      const storedToken = localStorage.getItem('token');
      const storedUser = localStorage.getItem('user');
      
      if (storedToken) {
        setToken(storedToken);
        try {
          const jwtPayload = JSON.parse(atob(storedToken.split('.')[1]));
          console.log('JWT Payload:', jwtPayload);
          
          // Extract all user data from JWT claims
          const userFromJwt = {
            id: jwtPayload?.userId,
            email: jwtPayload?.sub, // JWT subject is the email
            userName: jwtPayload?.userName,
            authorities: jwtPayload?.authorities || []
          };
          
          console.log('User from JWT:', userFromJwt);

          // Use JWT data directly - no need to hit /users/email
          console.log('Using JWT user data with ID:', userFromJwt.id);
          setUser(userFromJwt);
          localStorage.setItem('user', JSON.stringify(userFromJwt));
          localStorage.setItem('userRole', userFromJwt.authorities?.[0]);
        } catch (e) {
          console.error('Error parsing JWT token:', e);
          // Fallback to stored user if parsing fails
          if (storedUser) {
            setUser(JSON.parse(storedUser));
          }
        }
      }

      setLoading(false);
    };

    checkAuth();
  }, []);

  const login = async (email, password) => {
    try {
      const response = await axios.post('http://localhost:8080/users/signin', {
        email,
        password
      });

      const { jwt, message } = response.data;

      // Decode JWT payload to get all user data
      const jwtPayload = JSON.parse(atob(jwt.split('.')[1]));
      console.log('Login JWT Payload:', jwtPayload);
      
      // Create user object directly from JWT claims
      const userFromJwt = {
        id: jwtPayload?.userId,
        email: jwtPayload?.sub, // JWT subject is the email
        userName: jwtPayload?.userName,
        authorities: jwtPayload?.authorities || []
      };
      
      console.log('Login User from JWT:', userFromJwt);

      // Persist token and user data
      localStorage.setItem('token', jwt);
      localStorage.setItem('user', JSON.stringify(userFromJwt));
      localStorage.setItem('userRole', userFromJwt.authorities?.[0]);

      setToken(jwt);
      setUser(userFromJwt);

      return { success: true, message };
    } catch (error) {
      return { 
        success: false, 
        message: error.response?.data?.message || 'Login failed' 
      };
    }
  };

  const register = async (userData) => {
    try {
      const response = await axios.post('http://localhost:8080/users/signup', userData);
      return { success: true, message: response.data.message };
    } catch (error) {
      return { 
        success: false, 
        message: error.response?.data?.message || 'Registration failed' 
      };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('userRole');
    setToken(null);
    setUser(null);
    delete axios.defaults.headers.common['Authorization'];
  };

  const isAuthenticated = () => {
    return !!token;
  };

  const isAdmin = () => {
    return user?.authorities?.includes('ROLE_ADMIN');
  };

  const isCustomer = () => {
    return user?.authorities?.includes('ROLE_CUSTOMER');
  };

  const value = {
    user,
    token,
    loading,
    login,
    register,
    logout,
    isAuthenticated,
    isAdmin,
    isCustomer
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}; 