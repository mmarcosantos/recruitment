CREATE TABLE clients (
                                         id BIGINT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
                                         name VARCHAR(255) NOT NULL,
                                         last_name VARCHAR(255) NOT NULL,
                                         email VARCHAR(255) NOT NULL,
                                         phone VARCHAR(255) NOT NULL
);
