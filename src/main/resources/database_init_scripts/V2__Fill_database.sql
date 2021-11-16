INSERT INTO product (internal_code, name)
VALUES (111, 'fork'), (222, 'spoon'),
       (333, 'plate'), (444, 'table');

INSERT INTO organization (itn, name, payment_account)
VALUES (1111111, 'ItemsProvider', '111222333'),
       (2222222, 'Provider2', '222333444'),
       (3333333, 'Provider3', '333444555');

INSERT INTO invoice (id, date, sender_org_itn)
VALUES (1, 'January 8, 1999', 1111111),
       (2, 'March 20, 1999', 1111111),
       (3, 'March 20, 1999', 2222222),
       (4, 'April 4, 2000', 3333333);

INSERT INTO invoice_item (id, invoice_id, product_code, price, quantity)
VALUES (1, 1, 111, 120, 200),
       (2, 1, 222, 90, 400),
       (3, 2, 111, 100, 100),
       (4, 3, 111, 50, 200),
       (5, 3, 222, 60, 500),
       (6, 3, 333, 300, 250),
       (7, 4, 111, 150, 300),
       (8, 4, 222, 110, 200),
       (9, 4, 333, 400, 120),
       (10, 4, 444, 1200, 90);