-- Sample product data for the Unmanned Store
-- Each product has a unique ID, name, price, RFID tag, and optional description, category, and image URL

-- Beverages
INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p001', 'Bottled Water', CAST(1.99 AS DECIMAL(10,2)), 'rfid-bw-001', 'Refreshing spring water, 500ml bottle', 'Beverages', 'https://example.com/images/bottled-water.jpg');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p002', 'Cola Drink', CAST(2.49 AS DECIMAL(10,2)), 'rfid-cd-002', 'Classic cola flavor, 330ml can', 'Beverages', 'https://example.com/images/cola.jpg');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p003', 'Orange Juice', CAST(3.99 AS DECIMAL(10,2)), 'rfid-oj-003', 'Fresh squeezed orange juice, 1L carton', 'Beverages', 'https://example.com/images/orange-juice.jpg');

-- Snacks
INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p004', 'Potato Chips', 2.99, 'rfid-pc-004', 'Crispy salted potato chips, 150g bag', 'Snacks', 'https://example.com/images/potato-chips.jpg');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p005', 'Chocolate Bar', 1.79, 'rfid-cb-005', 'Milk chocolate bar, 100g', 'Snacks', 'https://example.com/images/chocolate-bar.jpg');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p006', 'Mixed Nuts', 4.99, 'rfid-mn-006', 'Assorted nuts, 250g pack', 'Snacks', 'https://example.com/images/mixed-nuts.jpg');

-- Dairy
INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p007', 'Milk', 2.29, 'rfid-mk-007', 'Fresh whole milk, 1L bottle', 'Dairy', 'https://example.com/images/milk.jpg');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p008', 'Yogurt', 1.49, 'rfid-yg-008', 'Strawberry yogurt, 150g cup', 'Dairy', 'https://example.com/images/yogurt.jpg');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p009', 'Cheese Slices', 3.49, 'rfid-cs-009', 'Cheddar cheese slices, 200g pack', 'Dairy', 'https://example.com/images/cheese.jpg');

-- Bakery
INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p010', 'White Bread', 2.19, 'rfid-wb-010', 'Sliced white bread, 400g loaf', 'Bakery', 'https://example.com/images/white-bread.jpg');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p011', 'Croissant', 1.29, 'rfid-cr-011', 'Butter croissant, 60g', 'Bakery', 'https://example.com/images/croissant.jpg');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p012', 'Muffin', 1.99, 'rfid-mf-012', 'Blueberry muffin, 85g', 'Bakery', 'https://example.com/images/muffin.jpg');

-- Fruits & Vegetables
INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p013', 'Banana', 0.99, 'rfid-bn-013', 'Fresh banana, price per piece', 'Fruits', 'https://example.com/images/banana.jpg');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p014', 'Apple', 1.29, 'rfid-ap-014', 'Red apple, price per piece', 'Fruits', 'https://example.com/images/apple.jpg');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p015', 'Carrot', 1.49, 'rfid-ct-015', 'Fresh carrots, 500g pack', 'Vegetables', 'https://example.com/images/carrot.jpg');
