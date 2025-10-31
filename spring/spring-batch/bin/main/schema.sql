CREATE TABLE person (
    id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);

CREATE TABLE processed_person (
    id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100)
);