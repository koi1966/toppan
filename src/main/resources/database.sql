-- Table: users

CREATE TABLE users1 (

                       id       INT          NOT NULL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- Table: roles
CREATE TABLE roles1 (
                       id   INT          NOT NULL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL
)
;

-- Table for mapping user and roles: user_roles
CREATE TABLE user_roles (
                            user_id INT NOT NULL,
                            role_id INT NOT NULL,

                            FOREIGN KEY (user_id) REFERENCES users1 (id),
                            FOREIGN KEY (role_id) REFERENCES roles1 (id),

                            UNIQUE (user_id, role_id)
);

-- Insert data

INSERT INTO users1 VALUES (1, 'proselyte', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO roles1 VALUES (1, 'ROLE_USER');
INSERT INTO roles1 VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES (1, 2);