DROP TABLE IF EXISTS order_shipping;
DROP TABLE IF EXISTS address;
CREATE TABLE order_shipping (id bigint, user_id int, order_id VARCHAR(255), instant timestamp);
-- AS SELECT * FROM CSVREAD('classpath:order_shipping.csv');
CREATE TABLE address AS SELECT * FROM CSVREAD('classpath:address.csv');