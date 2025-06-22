-- Sample product data for testing
-- Each product has a unique ID, name, price, RFID tag, and optional description, category, and image URL

-- Beverages
-- Bottled Water removed as requested

INSERT INTO products (id, name, price, RFIDTAG, description, category, IMAGEURL) VALUES
('p002', 'Cola Drink', 2.49, 'rfid-cd-002', 'Classic cola flavor, 330ml can', 'Beverages', 'https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fGNvbGF8ZW58MHx8MHx8fDA%3D');

-- Snacks
INSERT INTO products (id, name, price, RFIDTAG, description, category, IMAGEURL) VALUES
('p004', 'Potato Chips', 2.99, 'rfid-pc-004', 'Crispy salted potato chips, 150g bag', 'Snacks', 'https://images.unsplash.com/photo-1566478989037-eec170784d0b?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8cG90YXRvJTIwY2hpcHN8ZW58MHx8MHx8fDA%3D');

INSERT INTO products (id, name, price, RFIDTAG, description, category, IMAGEURL) VALUES
('p005', 'Chocolate Bar', 1.79, 'rfid-cb-005', 'Milk chocolate bar, 100g', 'Snacks', 'https://images.unsplash.com/photo-1511381939415-e44015466834?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8Y2hvY29sYXRlJTIwYmFyfGVufDB8fDB8fHww');
