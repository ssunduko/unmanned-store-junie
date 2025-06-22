import React, { useState, useEffect, useCallback } from 'react';
import { Card, ListGroup, Button, Alert, Row, Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import axios from 'axios';

const ShoppingBasket = () => {
  const [basketContents, setBasketContents] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [removingItem, setRemovingItem] = useState(null);
  const [notification, setNotification] = useState(null);

  const basketId = localStorage.getItem('basketId') || 'basket-123';
  const storeId = localStorage.getItem('storeId') || 'store-001';

  const fetchBasketContents = useCallback(async () => {
    try {
      setLoading(true);
      const response = await axios.get(`/api/stores/${storeId}/baskets/${basketId}/items`);
      setBasketContents(response.data);
      setError(null);
    } catch (err) {
      console.error('Error fetching basket contents:', err);
      setError('Failed to load basket contents. Please try again later.');
    } finally {
      setLoading(false);
    }
  }, [storeId, basketId]);

  useEffect(() => {
    fetchBasketContents();
  }, [fetchBasketContents]);

  const removeItem = async (itemId) => {
    try {
      setRemovingItem(itemId);
      await axios.delete(`/api/stores/${storeId}/baskets/${basketId}/items/${itemId}`);

      // Update basket contents after removing item
      fetchBasketContents();

      // Show success notification
      setNotification({
        type: 'success',
        message: 'Item removed from basket'
      });

      // Clear notification after 3 seconds
      setTimeout(() => setNotification(null), 3000);
    } catch (err) {
      console.error('Error removing item from basket:', err);
      setNotification({
        type: 'danger',
        message: 'Failed to remove item from basket. Please try again.'
      });

      // Clear notification after 3 seconds
      setTimeout(() => setNotification(null), 3000);
    } finally {
      setRemovingItem(null);
    }
  };

  if (loading) {
    return <div className="text-center mt-5">Loading basket contents...</div>;
  }

  if (error) {
    return (
      <Alert variant="danger" className="mt-3">
        {error}
        <Button 
          variant="outline-danger" 
          className="ms-3" 
          onClick={fetchBasketContents}
        >
          Try Again
        </Button>
      </Alert>
    );
  }

  if (!basketContents || basketContents.items.length === 0) {
    return (
      <div className="basket-empty text-center mt-5">
        <h2 className="mb-4">Your Basket</h2>
        <Alert variant="info">
          Your basket is empty. <Link to="/">Browse products</Link> to add items to your basket.
        </Alert>
      </div>
    );
  }

  return (
    <div className="basket-container">
      <h2 className="mb-4">Your Basket</h2>

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
        <Col md={8}>
          <Card>
            <Card.Header>
              <div className="d-flex justify-content-between align-items-center">
                <span>Items ({basketContents.itemCount})</span>
                <span>Last Updated: {new Date(basketContents.lastUpdatedAt).toLocaleString()}</span>
              </div>
            </Card.Header>
            <ListGroup variant="flush">
              {basketContents.items.map(item => (
                <ListGroup.Item key={item.itemId} className="d-flex justify-content-between align-items-center">
                  <div className="d-flex align-items-center">
                    {item.imageUrl && (
                      <img 
                        src={item.imageUrl} 
                        alt={item.productName} 
                        style={{ width: '50px', height: '50px', marginRight: '15px', objectFit: 'cover' }}
                      />
                    )}
                    <div>
                      <h5 className="mb-1">{item.productName}</h5>
                      <p className="mb-0 text-muted">Quantity: {item.quantity}</p>
                    </div>
                  </div>
                  <div className="d-flex align-items-center">
                    <span className="me-3 fw-bold">${item.price ? item.price.toFixed(2) : '0.00'}</span>
                    <Button 
                      variant="outline-danger" 
                      size="sm"
                      onClick={() => removeItem(item.itemId)}
                      disabled={removingItem === item.itemId}
                    >
                      {removingItem === item.itemId ? 'Removing...' : 'Remove'}
                    </Button>
                  </div>
                </ListGroup.Item>
              ))}
            </ListGroup>
          </Card>
        </Col>

        <Col md={4}>
          <Card className="basket-summary">
            <Card.Header>Order Summary</Card.Header>
            <Card.Body>
              {/* Calculate subtotal by summing price * quantity for each item */}
              {(() => {
                // Calculate subtotal
                const subtotal = basketContents.items.reduce(
                  (sum, item) => sum + (item.price || 0) * (item.quantity || 1),
                  0
                );
                // Calculate tax (8.25%)
                const taxRate = 0.0825;
                const tax = subtotal * taxRate;
                // Calculate total
                const total = subtotal + tax;

                return (
                  <>
                    <div className="d-flex justify-content-between mb-3">
                      <span>Subtotal ({basketContents.itemCount} items):</span>
                      <span>${subtotal.toFixed(2)}</span>
                    </div>
                    <div className="d-flex justify-content-between mb-3">
                      <span>Tax (8.25%):</span>
                      <span>${tax.toFixed(2)}</span>
                    </div>
                    <hr />
                    <div className="d-flex justify-content-between mb-3 fw-bold">
                      <span>Total:</span>
                      <span>${total.toFixed(2)}</span>
                    </div>
                  </>
                );
              })()}
              <Button 
                as={Link} 
                to="/checkout" 
                variant="success" 
                className="w-100 mt-3"
              >
                Proceed to Checkout
              </Button>
              <Button 
                as={Link} 
                to="/" 
                variant="outline-primary" 
                className="w-100 mt-2"
              >
                Continue Shopping
              </Button>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default ShoppingBasket;
