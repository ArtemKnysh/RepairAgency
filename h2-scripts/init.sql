CREATE TABLE IF NOT EXISTS role
(
    id   INT         NOT NULL,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS user
(
    id           INT          NOT NULL AUTO_INCREMENT,
    first_name   VARCHAR(16)  NOT NULL,
    last_name    VARCHAR(16)  NOT NULL,
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15)  NULL     DEFAULT NULL,
    password     VARCHAR(32)  NOT NULL,
    create_time  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    role_id      INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES role (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS user_id_UNIQUE ON user (id);
CREATE UNIQUE INDEX IF NOT EXISTS user_email_UNIQUE ON user (email);
CREATE INDEX IF NOT EXISTS fk_user_role_idx ON user (role_id);
CREATE TABLE IF NOT EXISTS account_transaction
(
    id          INT            NOT NULL AUTO_INCREMENT,
    user_id     INT            NOT NULL,
    amount      DECIMAL(10, 2) NOT NULL,
    create_time TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_account_transaction_user FOREIGN KEY (user_id) REFERENCES user (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS account_transaction_id_UNIQUE ON account_transaction (id);
CREATE INDEX IF NOT EXISTS fk_account_transaction_user_idx ON account_transaction (user_id);
CREATE TABLE IF NOT EXISTS feedback
(
    id          INT          NOT NULL AUTO_INCREMENT,
    text        VARCHAR(512) NOT NULL,
    is_hidden   TINYINT(1)   NOT NULL DEFAULT '0',
    customer_id INT          NOT NULL,
    master_id   INT          NOT NULL,
    create_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_feedback_user_customer FOREIGN KEY (customer_id) REFERENCES user (id),
    CONSTRAINT fk_feedback_user_master FOREIGN KEY (master_id) REFERENCES user (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS feedback_id_UNIQUE ON feedback (id);
CREATE INDEX IF NOT EXISTS fk_feedback_master_idx ON feedback (master_id);
CREATE INDEX IF NOT EXISTS fk_feedback_customer_idx ON feedback (customer_id);
CREATE TABLE IF NOT EXISTS repair_request_status
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS repair_request_status_id_UNIQUE ON repair_request_status (id);
CREATE TABLE IF NOT EXISTS repair_request
(
    id          INT            NOT NULL AUTO_INCREMENT,
    description VARCHAR(512)   NOT NULL,
    create_time TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cost        DECIMAL(10, 2) NULL     DEFAULT '0.00',
    customer_id INT            NOT NULL,
    master_id   INT            NULL     DEFAULT NULL,
    status_id   INT            NOT NULL DEFAULT '1',
    PRIMARY KEY (id),
    CONSTRAINT fk_repair_request_customer FOREIGN KEY (customer_id) REFERENCES user (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_repair_request_master FOREIGN KEY (master_id) REFERENCES user (id),
    CONSTRAINT fk_repair_request_status FOREIGN KEY (status_id) REFERENCES repair_request_status (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX IF NOT EXISTS repair_request_id_UNIQUE ON repair_request (id);
CREATE INDEX IF NOT EXISTS fk_repair_request_master_idx ON repair_request (master_id);
CREATE INDEX IF NOT EXISTS fk_repair_request_status_idx ON repair_request (status_id);
CREATE INDEX IF NOT EXISTS fk_repair_request_customer_idx ON repair_request (customer_id);
INSERT INTO role (id, name)
VALUES (1, 'CUSTOMER'),
       (2, 'MANGER'),
       (3, 'MASTER'),
       (4, 'ADMIN');
INSERT INTO repair_request_status (id, name)
VALUES (1, 'CREATED'),
       (2, 'WAIT_FOR_PAYMENT'),
       (3, 'CANCELLED'),
       (4, 'PAID'),
       (5, 'GIVEN_TO_MASTER'),
       (6, 'IN_WORK'),
       (7, 'COMPLETED');
