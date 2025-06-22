import React, { useState, useEffect } from 'react';
import { Row, Col, Card, Button, Alert } from 'react-bootstrap';
import axios from 'axios';

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const basketId = localStorage.getItem('basketId') || 'basket-123';
  const storeId = localStorage.getItem('storeId') || 'store-001';
  const [addingProduct, setAddingProduct] = useState(null);
  const [notification, setNotification] = useState(null);

  useEffect(() => {
    // Save basketId and storeId to localStorage
    localStorage.setItem('basketId', basketId);
    localStorage.setItem('storeId', storeId);

    // Fetch products
    fetchProducts();
  }, [basketId, storeId]);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      const response = await axios.get('/api/products');
      setProducts(response.data);
      setError(null);
    } catch (err) {
      console.error('Error fetching products:', err);
      setError('Failed to load products. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  const addToBasket = async (productId) => {
    try {
      setAddingProduct(productId);
      const response = await axios.post(`/api/stores/${storeId}/baskets/${basketId}/items`, {
        productId: productId
      });

      // Show success notification
      setNotification({
        type: 'success',
        message: `${response.data.item.productName} added to basket`
      });

      // Clear notification after 3 seconds
      setTimeout(() => setNotification(null), 3000);
    } catch (err) {
      console.error('Error adding product to basket:', err);
      setNotification({
        type: 'danger',
        message: 'Failed to add product to basket. Please try again.'
      });

      // Clear notification after 3 seconds
      setTimeout(() => setNotification(null), 3000);
    } finally {
      setAddingProduct(null);
    }
  };

  if (loading) {
    return <div className="text-center mt-5">Loading products...</div>;
  }

  if (error) {
    return (
      <Alert variant="danger" className="mt-3">
        {error}
        <Button 
          variant="outline-danger" 
          className="ms-3" 
          onClick={fetchProducts}
        >
          Try Again
        </Button>
      </Alert>
    );
  }

  return (
    <div className="product-list-container">
      <h2 className="mb-4">Available Products</h2>

      {notification && (
        <Alert 
          variant={notification.type} 
          className="mb-3" 
          dismissible 
          onClose={() => setNotification(null)}
        >
          {notification.message}
        </Alert>
      )}

      <Row>
        {products.map(product => (
          <Col key={product.productId} xs={12} sm={6} md={4} lg={3} className="mb-4">
            <Card className="h-100 product-card">
              {product.imageUrl ? (
                <Card.Img 
                  variant="top" 
                  src={product.imageUrl} 
                  alt={product.productName}
                  className="product-image"
                />
              ) : (
                <div className="product-image bg-light d-flex align-items-center justify-content-center">
                  <span className="text-muted">No image</span>
                </div>
              )}
              <Card.Body className="d-flex flex-column">
                <Card.Title>{product.productName}</Card.Title>
                <Card.Text className="text-muted mb-2">
                  {product.category && `Category: ${product.category}`}
                </Card.Text>
                <Card.Text>
                  {product.description || 'No description available'}
                </Card.Text>
                <div className="mt-auto">
                  <Card.Text className="fw-bold fs-5 mb-2">
                    ${product.price.toFixed(2)}
                  </Card.Text>
                  <Button 
                    variant="primary" 
                    className="w-100"
                    onClick={() => addToBasket(product.productId)}
                    disabled={addingProduct === product.productId}
                  >
                    {addingProduct === product.productId ? 'Adding...' : 'Add to Basket'}
                  </Button>
                </div>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      {products.length === 0 && (
        <Alert variant="info">
          No products available at this time.
        </Alert>
      )}
    </div>
  );
};

export default ProductList;
