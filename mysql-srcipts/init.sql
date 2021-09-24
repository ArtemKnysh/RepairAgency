-- -----------------------------------------------------
-- Create Schema repair_agency
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS repair_agency;
CREATE SCHEMA repair_agency DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE repair_agency;

-- -----------------------------------------------------
-- Create Table role
-- -----------------------------------------------------
CREATE TABLE role
(
    id   INT         NOT NULL,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------------------------
-- Create Table user
-- -----------------------------------------------------
CREATE TABLE user
(
    id           INT          NOT NULL AUTO_INCREMENT,
    first_name   VARCHAR(16)  NOT NULL,
    last_name    VARCHAR(16)  NOT NULL,
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15)  NULL     DEFAULT NULL,
    password     VARCHAR(32)  NOT NULL,
    create_time  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    role_id      INT          NOT NULL DEFAULT '1',
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
    UNIQUE INDEX email_UNIQUE (email ASC) VISIBLE,
    INDEX fk_user_role_idx (role_id ASC) VISIBLE,
    CONSTRAINT fk_user_role
        FOREIGN KEY (role_id)
            REFERENCES role (id)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------------------------
-- Create Table account_transaction
-- -----------------------------------------------------
CREATE TABLE account_transaction
(
    id          INT            NOT NULL AUTO_INCREMENT,
    user_id     INT            NOT NULL,
    amount      DECIMAL(10, 2) NOT NULL,
    create_time TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
    INDEX fk_account_transaction_user_idx (user_id ASC) VISIBLE,
    CONSTRAINT fk_account_transaction_user
        FOREIGN KEY (user_id)
            REFERENCES user (id)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------------------------
-- Create Table feedback
-- -----------------------------------------------------
CREATE TABLE feedback
(
    id          INT          NOT NULL AUTO_INCREMENT,
    text        VARCHAR(512) NOT NULL,
    is_hidden   TINYINT(1)   NOT NULL DEFAULT '0',
    customer_id INT          NOT NULL,
    master_id   INT          NOT NULL,
    create_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
    INDEX fk_feedback_master_idx (master_id ASC) VISIBLE,
    INDEX fk_feedback_customer_idx (customer_id ASC) VISIBLE,
    CONSTRAINT fk_feedback_user_customer
        FOREIGN KEY (customer_id)
            REFERENCES user (id),
    CONSTRAINT fk_feedback_user_master
        FOREIGN KEY (master_id)
            REFERENCES user (id)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------------------------
-- Create Table repair_request_status
-- -----------------------------------------------------
CREATE TABLE repair_request_status
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------------------------
-- Create Table repair_request
-- -----------------------------------------------------
CREATE TABLE repair_request
(
    id          INT            NOT NULL AUTO_INCREMENT,
    description VARCHAR(512)   NOT NULL,
    create_time TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cost        DECIMAL(10, 2) NULL     DEFAULT '0.00',
    customer_id INT            NOT NULL,
    master_id   INT            NULL     DEFAULT NULL,
    status_id   INT            NOT NULL DEFAULT '1',
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
    INDEX fk_repair_request_master_idx (master_id ASC) VISIBLE,
    INDEX fk_repair_request_status_idx (status_id ASC) VISIBLE,
    INDEX fk_repair_request_customer_idx (customer_id ASC) VISIBLE,
    CONSTRAINT fk_repair_request_customer
        FOREIGN KEY (customer_id)
            REFERENCES user (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_repair_request_master
        FOREIGN KEY (master_id)
            REFERENCES user (id),
    CONSTRAINT fk_repair_request_status
        FOREIGN KEY (status_id)
            REFERENCES repair_request_status (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;

SET SQL_SAFE_UPDATES = 0;

-- -----------------------------------------------------
-- Populate Table role
-- -----------------------------------------------------
INSERT INTO role (id, name)
VALUES (1, 'CUSTOMER'),
       (2, 'MANGER'),
       (3, 'MASTER'),
       (4, 'ADMIN');

-- -----------------------------------------------------
-- Populate Table user
-- -----------------------------------------------------
INSERT INTO user (first_name, last_name, email, password, role_id)
VALUES ('ADMIN', 'ADMIN', 'admin@mail.com', 'admin', 4);

-- -----------------------------------------------------
-- Populate Table repair_request_status
-- -----------------------------------------------------
INSERT INTO repair_request_status (id, name)
VALUES (1, 'CREATED'),
       (2, 'WAIT_FOR_PAYMENT'),
       (3, 'CANCELLED'),
       (4, 'PAID'),
       (5, 'GIVEN_TO_MASTER'),
       (6, 'IN_WORK'),
       (7, 'COMPLETED');
