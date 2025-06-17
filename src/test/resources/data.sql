-- Sample product data for testing
-- Each product has a unique ID, name, price, RFID tag, and optional description, category, and image URL

-- Beverages
INSERT INTO products (id, name, price, RFIDTAG, description, category, IMAGEURL) VALUES
('p001', 'Bottled Water', 1.99, 'rfid-bw-001', 'Refreshing spring water, 500ml bottle', 'Beverages', 'https://example.com/images/bottled-water.jpg');

INSERT INTO products (id, name, price, RFIDTAG, description, category, IMAGEURL) VALUES
('p002', 'Cola Drink', 2.49, 'rfid-cd-002', 'Classic cola flavor, 330ml can', 'Beverages', 'https://example.com/images/cola.jpg');

-- Snacks
INSERT INTO products (id, name, price, RFIDTAG, description, category, IMAGEURL) VALUES
('p004', 'Potato Chips', 2.99, 'rfid-pc-004', 'Crispy salted potato chips, 150g bag', 'Snacks', 'https://example.com/images/potato-chips.jpg');

INSERT INTO products (id, name, price, RFIDTAG, description, category, IMAGEURL) VALUES
('p005', 'Chocolate Bar', 1.79, 'rfid-cb-005', 'Milk chocolate bar, 100g', 'Snacks', 'https://example.com/images/chocolate-bar.jpg');
