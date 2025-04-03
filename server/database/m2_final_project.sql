-- database m2_final_project
 --rollback;
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS data_entry, symbol, exchange CASCADE;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE exchange (
	exchange_id bigserial,
	name varchar(128) NOT NULL UNIQUE,
	CONSTRAINT PK_exchange PRIMARY KEY (exchange_id)
);

CREATE TABLE symbol (
	symbol_id bigserial,
	exchange_id bigint NOT NULL,
	name varchar(128) NOT NULL UNIQUE,
	ticker_symbol varchar(128) NOT NULL UNIQUE,
	priority boolean,
	CONSTRAINT PK_symbol PRIMARY KEY (symbol_id),
    CONSTRAINT FK_symbol_exchange FOREIGN KEY(exchange_id) REFERENCES exchange(exchange_id)
	ON DELETE CASCADE
);

CREATE TABLE data_entry (
	data_entry_id bigserial,
	symbol_id bigint NOT NULL,
	trading_date date NOT NULL,
	open_price decimal,
	close_price decimal,
	high_price decimal,
	low_price decimal,
	CONSTRAINT PK_data_entry PRIMARY KEY (data_entry_id),
    CONSTRAINT FK_data_entry_symbol FOREIGN KEY(symbol_id) REFERENCES symbol(symbol_id)
	ON DELETE CASCADE
);

-- *************************************************************************************************
-- Insert some sample starting data
-- *************************************************************************************************

-- Users
-- Password for all users is password
INSERT INTO
    users (username, password_hash, role)
VALUES
    ('user', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('admin','$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN');
	
-- data:	
	
INSERT INTO exchange(exchange_id, name) VALUES
	(1, 'New York Stock Exchange'),
	(2, 'Nasdaq'),
	(3, 'Hong Kong Stock Exchange');
	
INSERT INTO symbol(exchange_id, name, ticker_symbol, priority) VALUES
	(1, 'JPMorgan Chase & Co.', 'JPM', TRUE),
	(2, 'Apple Inc.', 'AAPL', TRUE),
	(2, 'NVIDIA Corporation', 'NVDA', TRUE),
	(3,'HSBC Holdings plc', 'HSBC', FALSE);
	
INSERT INTO data_entry(data_entry_id, symbol_id, trading_date, open_price, close_price, high_price, low_price) VALUES
	(1, 1, '2024-07-23', 211.02, 210.33, 211.48, 209.35),
	(2, 2, '2024-07-23', 224.37, 225.01, 226.94, 222.68),
	(3, 3, '2024-07-23', 122.78, 122.59, 124.69, 122.10),
	(4, 4, '2024-07-23', 43.06, 43.23, 43.29, 43.03),
	(5, 1, '2024-07-24', 209.55, 208.59, 212.03, 208.07),
	(6, 2, '2024-07-24', 224.00, 218.54, 224.80, 217.13),
	(7, 3, '2024-07-24', 119.17, 114.25, 119.95, 113.44),
	(8, 4, '2024-07-24', 43.12, 42.87, 43.24, 42.86),
	(9, 1, '2024-07-25', 208.65, 208.89, 209.80, 208.19),
	(10, 2, '2024-07-25', 218.87, 217.38, 219.85, 214.62),
	(11, 3, '2024-07-25', 113.08, 114.84, 114.84, 106.30),
	(12, 4, '2024-07-25', 42.79, 42.84, 42.87, 42.63);


COMMIT TRANSACTION;
