# Online Book Store - Frontend

A modern React.js frontend for the Online Book Store application with separate admin and customer portals.

## Features

### Admin Portal
- **Dashboard**: Overview of store statistics (books, orders, customers, revenue)
- **Book Management**: CRUD operations for books with image upload
- **Category Management**: Manage book categories
- **Order Management**: View and manage customer orders

### Customer Portal
- **Dashboard**: Personalized customer dashboard with recent books and order history
- **Book Browsing**: Search, filter, and sort books by various criteria
- **Shopping Cart**: Add/remove items, update quantities
- **Checkout**: Complete purchase with shipping and payment information
- **Order History**: View past orders and their status

### Authentication
- JWT-based authentication
- Role-based access control (Admin/Customer)
- Protected routes
- User registration and login

## Technology Stack

- **React.js 19.1.1** - Frontend framework
- **React Router DOM 6.20.0** - Client-side routing
- **Bootstrap 5.3.2** - UI framework
- **React Bootstrap 2.9.0** - Bootstrap components for React
- **Axios 1.6.0** - HTTP client for API calls
- **FontAwesome 6.4.2** - Icons

## Project Structure

```
frontend/
├── public/
├── src/
│   ├── components/
│   │   ├── admin/
│   │   │   ├── AdminDashboard.js
│   │   │   ├── BookManagement.js
│   │   │   ├── CategoryManagement.js
│   │   │   └── OrderManagement.js
│   │   ├── auth/
│   │   │   ├── Login.js
│   │   │   └── Register.js
│   │   ├── common/
│   │   │   ├── Home.js
│   │   │   └── Navbar.js
│   │   └── customer/
│   │       ├── BookDetail.js
│   │       ├── BookList.js
│   │       ├── Cart.js
│   │       ├── Checkout.js
│   │       ├── CustomerDashboard.js
│   │       └── OrderHistory.js
│   ├── context/
│   │   ├── AuthContext.js
│   │   └── CartContext.js
│   ├── App.js
│   ├── App.css
│   └── index.js
├── package.json
└── README.md
```

## Getting Started

### Prerequisites

- Node.js (version 14 or higher)
- npm or yarn
- Backend server running on `http://localhost:8080`

### Installation

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

## API Endpoints

The frontend communicates with the following backend endpoints:

### Authentication
- `POST /users/signin` - User login
- `POST /users/signup` - User registration

### Books
- `GET /books` - Get all books
- `GET /books/{id}` - Get book by ID
- `POST /books/add-with-image` - Add new book (Admin only)
- `PUT /books/update/{id}` - Update book (Admin only)
- `DELETE /books/{id}` - Delete book (Admin only)

### Categories
- `GET /category` - Get all categories
- `POST /category` - Add new category (Admin only)
- `DELETE /category/{categoryName}` - Delete category (Admin only)

### Orders
- `GET /order` - Get all orders (Admin only)
- `GET /order/user/{userId}` - Get user orders
- `POST /order` - Create new order

## Usage

### Admin Access
1. Register or login with admin credentials
2. Access admin dashboard at `/admin`
3. Manage books, categories, and view orders

### Customer Access
1. Register or login with customer credentials
2. Browse books at `/books`
3. Add items to cart and complete checkout

## Features in Detail

### Book Management (Admin)
- Add new books with image upload
- Edit existing book details
- Delete books from inventory
- Search and filter books
- View book statistics

### Shopping Cart (Customer)
- Add books to cart
- Update quantities
- Remove items
- View cart total
- Proceed to checkout

### Responsive Design
- Mobile-friendly interface
- Bootstrap grid system
- Responsive navigation
- Touch-friendly interactions

## Security Features

- JWT token authentication
- Protected routes based on user roles
- Secure API communication
- Form validation
- Input sanitization

## Styling

The application uses:
- Custom CSS with modern design principles
- Bootstrap 5 for responsive layout
- FontAwesome icons
- Gradient backgrounds and shadows
- Smooth animations and transitions

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For support and questions, please contact the development team.
