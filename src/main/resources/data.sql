-- Sample product data for the Unmanned Store
-- Each product has a unique ID, name, price, RFID tag, and optional description, category, and image URL

-- Beverages
-- Bottled Water removed as requested

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p002', 'Cola Drink', CAST(2.49 AS DECIMAL(10,2)), 'rfid-cd-002', 'Classic cola flavor, 330ml can', 'Beverages', 'https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fGNvbGF8ZW58MHx8MHx8fDA%3D');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p003', 'Orange Juice', CAST(3.99 AS DECIMAL(10,2)), 'rfid-oj-003', 'Fresh squeezed orange juice, 1L carton', 'Beverages', 'https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8b3JhbmdlJTIwanVpY2V8ZW58MHx8MHx8fDA%3D');

-- Snacks
INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p004', 'Potato Chips', 2.99, 'rfid-pc-004', 'Crispy salted potato chips, 150g bag', 'Snacks', 'https://images.unsplash.com/photo-1566478989037-eec170784d0b?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8cG90YXRvJTIwY2hpcHN8ZW58MHx8MHx8fDA%3D');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p005', 'Chocolate Bar', 1.79, 'rfid-cb-005', 'Milk chocolate bar, 100g', 'Snacks', 'https://images.unsplash.com/photo-1511381939415-e44015466834?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8Y2hvY29sYXRlJTIwYmFyfGVufDB8fDB8fHww');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p006', 'Mixed Nuts', 4.99, 'rfid-mn-006', 'Assorted nuts, 250g pack', 'Snacks', 'https://images.unsplash.com/photo-1599599810769-bcde5a160d32?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8bWl4ZWQlMjBudXRzfGVufDB8fDB8fHww');

-- Dairy
INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p007', 'Milk', 2.29, 'rfid-mk-007', 'Fresh whole milk, 1L bottle', 'Dairy', 'https://images.unsplash.com/photo-1563636619-e9143da7973b?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTJ8fG1pbGt8ZW58MHx8MHx8fDA%3D');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p008', 'Yogurt', 1.49, 'rfid-yg-008', 'Strawberry yogurt, 150g cup', 'Dairy', 'https://images.unsplash.com/photo-1488477181946-6428a0291777?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8eW9ndXJ0fGVufDB8fDB8fHww');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p009', 'Cheese Slices', 3.49, 'rfid-cs-009', 'Cheddar cheese slices, 200g pack', 'Dairy', 'https://images.unsplash.com/photo-1486297678162-eb2a19b0a32d?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Y2hlZXNlfGVufDB8fDB8fHww');

-- Bakery
-- White Bread removed as requested

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p011', 'Croissant', 1.29, 'rfid-cr-011', 'Butter croissant, 60g', 'Bakery', 'https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Y3JvaXNzYW50fGVufDB8fDB8fHww');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p012', 'Muffin', 1.99, 'rfid-mf-012', 'Blueberry muffin, 85g', 'Bakery', 'https://images.unsplash.com/photo-1607958996333-41aef7caefaa?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8bXVmZmlufGVufDB8fDB8fHww');

-- Fruits & Vegetables
INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p013', 'Banana', 0.99, 'rfid-bn-013', 'Fresh banana, price per piece', 'Fruits', 'https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8YmFuYW5hfGVufDB8fDB8fHww');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p014', 'Apple', 1.29, 'rfid-ap-014', 'Red apple, price per piece', 'Fruits', 'https://images.unsplash.com/photo-1570913149827-d2ac84ab3f9a?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YXBwbGV8ZW58MHx8MHx8fDA%3D');

INSERT INTO products (id, name, price, rfidTag, description, category, imageUrl) 
VALUES ('p015', 'Carrot', 1.49, 'rfid-ct-015', 'Fresh carrots, 500g pack', 'Vegetables', 'https://images.unsplash.com/photo-1598170845058-32b9d6a5da37?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8Y2Fycm90fGVufDB8fDB8fHww');
