CREATE TABLE supplier (
                          id SERIAL PRIMARY KEY,
                          supplier_name VARCHAR(255) NOT NULL,
                          contact VARCHAR(255)
);

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        buyer_name VARCHAR(255) NOT NULL,
                        supplier_id INTEGER REFERENCES supplier(id)
);

CREATE TABLE product (
                         id SERIAL PRIMARY KEY,
                         product_name VARCHAR(255) NOT NULL,
                         quantity INTEGER NOT NULL,
                         unit_price DECIMAL(10, 2) NOT NULL,
                         order_id INTEGER REFERENCES orders(id)
);

-- Insert sample data

INSERT INTO supplier (supplier_name, contact) VALUES ('Supplier 1', 'contact1@example.com');
INSERT INTO supplier (supplier_name, contact) VALUES ('Supplier 2', 'contact2@example.com');
INSERT INTO supplier (supplier_name, contact) VALUES ('Supplier 3', 'contact3@example.com');
INSERT INTO supplier (supplier_name, contact) VALUES ('Supplier 4', 'contact4@example.com');
INSERT INTO supplier (supplier_name, contact) VALUES ('Supplier 5', 'contact5@example.com');

INSERT INTO orders (buyer_name, supplier_id) VALUES ('Buyer 1', 1);
INSERT INTO orders (buyer_name, supplier_id) VALUES ('Buyer 2', 2);
INSERT INTO orders (buyer_name, supplier_id) VALUES ('Buyer 3', 3);
INSERT INTO orders (buyer_name, supplier_id) VALUES ('Buyer 4', 4);
INSERT INTO orders (buyer_name, supplier_id) VALUES ('Buyer 5', 5);

INSERT INTO product (product_name, quantity, unit_price, order_id) VALUES ('Product 1', 100, 10.0, 1);
INSERT INTO product (product_name, quantity, unit_price, order_id) VALUES ('Product 2', 200, 15.0, 1);
INSERT INTO product (product_name, quantity, unit_price, order_id) VALUES ('Product 3', 150, 20.0, 2);
INSERT INTO product (product_name, quantity, unit_price, order_id) VALUES ('Product 4', 300, 25.0, 2);
INSERT INTO product (product_name, quantity, unit_price, order_id) VALUES ('Product 5', 50, 30.0, 3);
INSERT INTO product (product_name, quantity, unit_price, order_id) VALUES ('Product 6', 80, 35.0, 3);
INSERT INTO product (product_name, quantity, unit_price, order_id) VALUES ('Product 7', 60, 40.0, 4);
INSERT INTO product (product_name, quantity, unit_price, order_id) VALUES ('Product 8', 120, 45.0, 4);
INSERT INTO product (product_name, quantity, unit_price, order_id) VALUES ('Product 9', 90, 50.0, 5);
INSERT INTO product (product_name, quantity, unit_price, order_id) VALUES ('Product 10', 110, 55.0, 5);
