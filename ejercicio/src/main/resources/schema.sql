CREATE SCHEMA IF NOT EXISTS evaluacion;
SET SCHEMA evaluacion;
CREATE TABLE users (id UUID default random_uuid() PRIMARY KEY, 
					name VARCHAR(200), 
					email VARCHAR(200) NOT NULL, 
					password VARCHAR(100) NOT NULL, 
					token VARCHAR(256),
					created DATE NOT NULL,
					last_login DATE,
					is_active BOOLEAN NOT NULL
				);
				
CREATE TABLE phones (id UUID default random_uuid() PRIMARY KEY, 
					user_id UUID NOT NULL,
					number BIGINT NOT NULL, 
					citycode INTEGER NOT NULL, 
					contrycode VARCHAR(20) NOT NULL
				);
ALTER TABLE phones ADD FOREIGN KEY ( user_id ) REFERENCES users( id ) ;