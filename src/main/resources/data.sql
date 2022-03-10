DROP TABLE IF EXISTS players;
CREATE TABLE players
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    firstname    VARCHAR(255) NOT NULL,
    lastname     VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    last_update TIMESTAMP
);
INSERT INTO players (firstname, lastname, created_date)
VALUES ('Tilly', 'Holland',CURRENT_TIMESTAMP()),
       ('Grace','Atkins', CURRENT_TIMESTAMP()),
       ('Ben','Hughes', CURRENT_TIMESTAMP());

DROP TABLE IF EXISTS wallets;
CREATE TABLE wallets
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    player_id       INT NOT NULL,
    balance         NUMERIC(20, 2) NOT NULL DEFAULT 0,
    last_update    TIMESTAMP
);
INSERT INTO wallets (player_id, balance, last_update)
VALUES (1, 500, CURRENT_TIMESTAMP()),
       (2, 35, CURRENT_TIMESTAMP()),
       (3, 600, CURRENT_TIMESTAMP());

DROP TABLE IF EXISTS transactions_history;
CREATE TABLE transactions_history
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    player_id      INT NOT NULL,
    wallet_id     INT NOT NULL,
    transaction_id VARCHAR(500) NOT NULL,
    amount         NUMERIC(20, 2) NOT NULL DEFAULT 0,
    type           INT NOT NULL,
    created_date   TIMESTAMP
);
INSERT INTO transactions_history (player_id, wallet_id, transaction_id, amount, type, created_date)
VALUES (1,1,'AAAAA11111', 20, 1,CURRENT_TIMESTAMP()),
       (2,2,'BBBBB22222', 5, 2,CURRENT_TIMESTAMP()),
       (3,3,'CCCCC33333', 138, 2,CURRENT_TIMESTAMP());
