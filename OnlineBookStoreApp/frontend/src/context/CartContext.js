import React, { createContext, useContext, useState, useEffect } from 'react';

const CartContext = createContext();

export const useCart = () => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error('useCart must be used within a CartProvider');
  }
  return context;
};

export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState([]);
  const [cartTotal, setCartTotal] = useState(0);

  // Load cart from localStorage on component mount
  useEffect(() => {
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
      setCartItems(JSON.parse(savedCart));
    }
  }, []);

  // Update cart total whenever cart items change
  useEffect(() => {
    const total = cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    setCartTotal(total);
    
    // Save cart to localStorage
    localStorage.setItem('cart', JSON.stringify(cartItems));
  }, [cartItems]);

  const addToCart = (book, quantity = 1) => {
    setCartItems(prevItems => {
      // Create a unique identifier for the book
      const bookKey = book.id || `${book.title}-${book.author}`;
      
      const existingItem = prevItems.find(item => {
        const itemKey = item.id || `${item.title}-${item.author}`;
        return itemKey === bookKey;
      });
      
      if (existingItem) {
        // Update quantity if item already exists
        return prevItems.map(item => {
          const itemKey = item.id || `${item.title}-${item.author}`;
          return itemKey === bookKey
            ? { ...item, quantity: item.quantity + quantity }
            : item;
        });
      } else {
        // Add new item to cart
        return [...prevItems, { ...book, quantity }];
      }
    });
  };

  const removeFromCart = (bookId) => {
    setCartItems(prevItems => prevItems.filter(item => item.id !== bookId));
  };

  const updateQuantity = (bookId, quantity) => {
    if (quantity <= 0) {
      removeFromCart(bookId);
      return;
    }

    setCartItems(prevItems =>
      prevItems.map(item =>
        item.id === bookId
          ? { ...item, quantity }
          : item
      )
    );
  };

  const clearCart = () => {
    setCartItems([]);
    localStorage.removeItem('cart');
  };

  const getCartItemCount = () => {
    return cartItems.reduce((total, item) => total + item.quantity, 0);
  };

  const getCartItem = (bookId) => {
    return cartItems.find(item => item.id === bookId);
  };

  const value = {
    cartItems,
    cartTotal,
    addToCart,
    removeFromCart,
    updateQuantity,
    clearCart,
    getCartItemCount,
    getCartItem
  };

  return (
    <CartContext.Provider value={value}>
      {children}
    </CartContext.Provider>
  );
}; 