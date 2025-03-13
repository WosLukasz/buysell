CREATE DATABASE buysell;
CREATE USER buysell WITH ENCRYPTED PASSWORD 'buysell';
GRANT ALL PRIVILEGES ON DATABASE buysell TO buysell;

\c buysell postgres

GRANT ALL ON SCHEMA public TO buysell;

CREATE SEQUENCE IF NOT EXISTS auctions_seq START WITH 1 INCREMENT BY 1;


CREATE DATABASE buysell_admin;
CREATE USER buysell_admin WITH ENCRYPTED PASSWORD 'buysell_admin';
GRANT ALL PRIVILEGES ON DATABASE buysell_admin TO buysell_admin;

\c buysell_admin postgres

GRANT ALL ON SCHEMA public TO buysell_admin;






