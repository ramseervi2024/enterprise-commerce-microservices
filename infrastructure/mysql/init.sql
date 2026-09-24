-- Initialization script for MySQL
-- This script runs automatically when the MySQL Docker container starts for the first time.

-- Create databases for each microservice
CREATE DATABASE IF NOT EXISTS auth_db;
CREATE DATABASE IF NOT EXISTS user_db;
CREATE DATABASE IF NOT EXISTS product_db;
CREATE DATABASE IF NOT EXISTS cart_db;
CREATE DATABASE IF NOT EXISTS order_db;
CREATE DATABASE IF NOT EXISTS payment_db;
CREATE DATABASE IF NOT EXISTS inventory_db;
CREATE DATABASE IF NOT EXISTS analytics_db;

-- Grant privileges (assuming default root user, but good practice to ensure access)
GRANT ALL PRIVILEGES ON auth_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON user_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON product_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON cart_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON order_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON payment_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON inventory_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON analytics_db.* TO 'root'@'%';

FLUSH PRIVILEGES;
