import React, { useState, useEffect, useCallback } from 'react';
import { Card, Form, Button, Alert, Row, Col, ListGroup } from 'react-bootstrap';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

const Checkout = () => {
  const [basketContents, setBasketContents] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [paymentProcessing, setPaymentProcessing] = useState(false);
  const [checkoutComplete, setCheckoutComplete] = useState(false);
  const [formValidated, setFormValidated] = useState(false);

  const basketId = localStorage.getItem('basketId') || 'basket-123';
  const storeId = localStorage.getItem('storeId') || 'store-001';
  const navigate = useNavigate();

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

  const handleSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;

    if (form.checkValidity() === false) {
      event.stopPropagation();
      setFormValidated(true);
      return;
    }

    processPayment();
  };

  const processPayment = async () => {
    // In a real application, this would make a request to a payment processing API
    // For this demo, we'll simulate a successful payment after a short delay
    setPaymentProcessing(true);

    try {
      // Simulate API call delay
      await new Promise(resolve => setTimeout(resolve, 2000));

      // Store the current basketId for later use
      const currentBasketId = basketId;

      // Simulate successful payment
      setCheckoutComplete(true);

      // In a real application, we would create a new empty basket for the user
      // and redirect them to an order confirmation page

      // We'll delay changing the basketId until the user clicks "Continue Shopping"
      // This ensures the checkout summary has access to the basket contents
      // The basketId will be changed in the "Continue Shopping" button click handler

    } catch (err) {
      console.error('Error processing payment:', err);
      setError('Failed to process payment. Please try again.');
    } finally {
      setPaymentProcessing(false);
    }
  };

  if (loading) {
    return <div className="text-center mt-5">Loading checkout information...</div>;
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
      <div className="checkout-empty text-center mt-5">
        <h2 className="mb-4">Checkout</h2>
        <Alert variant="info">
          Your basket is empty. <Link to="/">Browse products</Link> to add items to your basket.
        </Alert>
      </div>
    );
  }

  if (checkoutComplete) {
    return (
      <div className="checkout-complete text-center mt-5">
        <h2 className="mb-4">Order Complete!</h2>
        <Alert variant="success">
          <h4>Thank you for your purchase!</h4>
          <p>Your order has been successfully processed.</p>
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

            return <p>Order Total: ${total.toFixed(2)}</p>;
          })()}
          <p>A receipt has been sent to your email.</p>
          <Button 
            variant="primary" 
            onClick={() => {
              // Create a new shopping session by setting a new basketId
              localStorage.removeItem('basketId');

              // Generate a new random basketId to restart the shopping session
              const newBasketId = 'basket-' + Math.random().toString(36).substring(2, 10);
              localStorage.setItem('basketId', newBasketId);

              // Navigate to products page to start a new shopping session
              navigate('/');
            }}
            className="mt-3"
          >
            Continue Shopping
          </Button>
        </Alert>
      </div>
    );
  }

  return (
    <div className="checkout-container">
      <h2 className="mb-4">Checkout</h2>

      <Row>
        <Col md={7}>
          <Card className="mb-4">
            <Card.Header>Payment Information</Card.Header>
            <Card.Body>
              <Form noValidate validated={formValidated} onSubmit={handleSubmit}>
                <Row className="mb-3">
                  <Form.Group as={Col} md="6" controlId="validationCustom01">
                    <Form.Label>First name</Form.Label>
                    <Form.Control
                      required
                      type="text"
                      placeholder="First name"
                    />
                    <Form.Control.Feedback type="invalid">
                      Please provide your first name.
                    </Form.Control.Feedback>
                  </Form.Group>
                  <Form.Group as={Col} md="6" controlId="validationCustom02">
                    <Form.Label>Last name</Form.Label>
                    <Form.Control
                      required
                      type="text"
                      placeholder="Last name"
                    />
                    <Form.Control.Feedback type="invalid">
                      Please provide your last name.
                    </Form.Control.Feedback>
                  </Form.Group>
                </Row>

                <Form.Group className="mb-3" controlId="validationCustom03">
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    required
                    type="email"
                    placeholder="Email"
                  />
                  <Form.Control.Feedback type="invalid">
                    Please provide a valid email.
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group className="mb-3" controlId="validationCustom04">
                  <Form.Label>Card Number</Form.Label>
                  <Form.Control
                    required
                    type="text"
                    placeholder="Card Number"
                    pattern="[0-9]{16}"
                  />
                  <Form.Control.Feedback type="invalid">
                    Please provide a valid 16-digit card number.
                  </Form.Control.Feedback>
                </Form.Group>

                <Row className="mb-3">
                  <Form.Group as={Col} md="6" controlId="validationCustom05">
                    <Form.Label>Expiration Date</Form.Label>
                    <Form.Control
                      required
                      type="text"
                      placeholder="MM/YY"
                      pattern="(0[1-9]|1[0-2])\/[0-9]{2}"
                    />
                    <Form.Control.Feedback type="invalid">
                      Please provide a valid expiration date (MM/YY).
                    </Form.Control.Feedback>
                  </Form.Group>
                  <Form.Group as={Col} md="6" controlId="validationCustom06">
                    <Form.Label>CVV</Form.Label>
                    <Form.Control
                      required
                      type="text"
                      placeholder="CVV"
                      pattern="[0-9]{3,4}"
                    />
                    <Form.Control.Feedback type="invalid">
                      Please provide a valid CVV.
                    </Form.Control.Feedback>
                  </Form.Group>
                </Row>

                <Form.Group className="mb-3">
                  <Form.Check
                    required
                    label="I agree to the terms and conditions"
                    feedback="You must agree before submitting."
                    feedbackType="invalid"
                  />
                </Form.Group>

                <Button 
                  type="submit" 
                  variant="success" 
                  className="w-100"
                  disabled={paymentProcessing}
                >
                  {paymentProcessing ? 'Processing Payment...' : 'Complete Purchase'}
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>

        <Col md={5}>
          <Card className="checkout-summary">
            <Card.Header>Order Summary</Card.Header>
            <Card.Body>
              <ListGroup variant="flush">
                {basketContents.items.map(item => (
                  <ListGroup.Item key={item.itemId} className="d-flex justify-content-between align-items-center">
                    <div>
                      <span>{item.productName}</span>
                      <span className="text-muted ms-2">x{item.quantity}</span>
                    </div>
                    <span>${item.price ? item.price.toFixed(2) : '0.00'}</span>
                  </ListGroup.Item>
                ))}
              </ListGroup>

              <hr />

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
                    <div className="d-flex justify-content-between mb-2">
                      <span>Subtotal ({basketContents.itemCount} items):</span>
                      <span>${subtotal.toFixed(2)}</span>
                    </div>
                    <div className="d-flex justify-content-between mb-2">
                      <span>Tax (8.25%):</span>
                      <span>${tax.toFixed(2)}</span>
                    </div>
                    <div className="d-flex justify-content-between mb-2">
                      <span>Shipping:</span>
                      <span>Free</span>
                    </div>

                    <hr />

                    <div className="d-flex justify-content-between checkout-total">
                      <span>Total:</span>
                      <span>${total.toFixed(2)}</span>
                    </div>
                  </>
                );
              })()}

              <Button 
                as={Link} 
                to="/basket" 
                variant="outline-primary" 
                className="w-100 mt-3"
              >
                Back to Basket
              </Button>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default Checkout;
