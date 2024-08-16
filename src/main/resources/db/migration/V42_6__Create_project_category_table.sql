CREATE TABLE project_category (
                        id VARCHAR(255) NOT NULL PRIMARY KEY,
                        creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        last_update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        name VARCHAR(255),
                        description VARCHAR(255)
);
