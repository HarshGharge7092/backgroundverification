CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       department VARCHAR(255),
                       role VARCHAR(50)
);

CREATE TABLE candidates (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            user_id INT,
                            added_by INT,
                            verification_status VARCHAR(50),
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (added_by) REFERENCES users(id)
);
CREATE TABLE verification_requests (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       candidate_id INT,
                                       requested_by INT,
                                       request_date TIMESTAMP,
                                       status VARCHAR(50),
                                       FOREIGN KEY (candidate_id) REFERENCES candidates(id),
                                       FOREIGN KEY (requested_by) REFERENCES users(id)
);

CREATE TABLE verification_statuses (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       candidate_id INT,
                                       address_verification VARCHAR(50),
                                       id_verification VARCHAR(50),
                                       education_verification VARCHAR(50),
                                       drugs_test VARCHAR(50),
                                       last_updated TIMESTAMP,
                                       FOREIGN KEY (candidate_id) REFERENCES candidates(id)
);

CREATE TABLE reports (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255),
                         type VARCHAR(50),
                         upload_date DATE,
                         file_size BIGINT,
                         extracted_text TEXT
);

CREATE TABLE hrs (
                     id INT AUTO_INCREMENT PRIMARY KEY,0
                     first_name VARCHAR(255) NOT NULL,
                     last_name VARCHAR(255) NOT NULL,
                     email VARCHAR(255) NOT NULL UNIQUE,
                     password VARCHAR(255) NOT NULL,
                     company_name VARCHAR(255) NOT NULL,
                     job_title VARCHAR(255) NOT NULL,
                     phone_number VARCHAR(20) NOT NULL
);