import React from 'react';

class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, error: null };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true, error };
  }

  componentDidCatch(error, errorInfo) {
    // You can integrate logging here
    if (typeof window !== 'undefined' && window?.console) {
      // eslint-disable-next-line no-console
      console.error('ErrorBoundary caught an error:', error, errorInfo);
    }
  }

  handleRetry = () => {
    this.setState({ hasError: false, error: null });
    if (typeof window !== 'undefined') {
      window.location.reload();
    }
  };

  render() {
    if (this.state.hasError) {
      return (
        <div className="container py-5">
          <div className="alert alert-danger" role="alert">
            <h4 className="alert-heading mb-3">Something went wrong.</h4>
            <p className="mb-3">An unexpected error occurred while rendering this page.</p>
            {this.props.showDetails && this.state.error && (
              <pre className="mb-3" style={{ whiteSpace: 'pre-wrap' }}>
                {String(this.state.error?.message || this.state.error)}
              </pre>
            )}
            <button type="button" className="btn btn-outline-danger" onClick={this.handleRetry}>
              Reload Page
            </button>
          </div>
        </div>
      );
    }

    return this.props.children;
  }
}

export default ErrorBoundary;


