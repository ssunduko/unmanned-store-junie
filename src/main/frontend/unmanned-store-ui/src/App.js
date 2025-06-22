import React from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import { Navbar, Container, Nav } from 'react-bootstrap';
import ProductList from './components/ProductList';
import ShoppingBasket from './components/ShoppingBasket';
import Checkout from './components/Checkout';
import './App.css';

function App() {
  return (
    <div className="App">
      <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
          <Navbar.Brand as={Link} to="/">Unmanned Store</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link as={Link} to="/">Products</Nav.Link>
              <Nav.Link as={Link} to="/basket">Basket</Nav.Link>
              <Nav.Link as={Link} to="/checkout">Checkout</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <Container className="mt-4">
        <Routes>
          <Route path="/" element={<ProductList />} />
          <Route path="/basket" element={<ShoppingBasket />} />
          <Route path="/checkout" element={<Checkout />} />
        </Routes>
      </Container>
    </div>
  );
}

export default App;