DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS bike;
DROP TABLE IF EXISTS usr;
DROP SEQUENCE IF EXISTS usr_id_seq;
DROP SEQUENCE IF EXISTS cart_id_seq;
DROP SEQUENCE IF EXISTS cart_item_id_seq;
DROP SEQUENCE IF EXISTS account_id_seq;

CREATE SEQUENCE usr_id_seq START WITH 1;
CREATE SEQUENCE cart_id_seq START WITH 1;
CREATE SEQUENCE cart_id_id_seq START WITH 1;
CREATE SEQUENCE account_id_seq START WITH 1;

CREATE TABLE usr
(
  id               BIGINT PRIMARY KEY DEFAULT nextval('usr_id_seq'),
  username         VARCHAR(255)                 NOT NULL,
  email            VARCHAR(255)                 NOT NULL,
  password         VARCHAR(255)                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  active           BOOL DEFAULT TRUE       NOT NULL
);
CREATE UNIQUE INDEX user_unique_email_idx ON usr (email);

ALTER TABLE usr ADD CONSTRAINT U_unique UNIQUE (username);

CREATE TABLE user_role
(
  user_id BIGINT NOT NULL,
  role    VARCHAR,
--   CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES usr (id) ON UPDATE CASCADE
);

CREATE TABLE car (
  id          INTEGER PRIMARY KEY DEFAULT nextval('car_id_seq'),
  date_receiving   TIMESTAMP NOT NULL,
  brand            VARCHAR   NOT NULL,
  model            VARCHAR   NOT NULL,
  engine_type      VARCHAR   NOT NULL,
  engine_volume    DECIMAL   NOT NULL,
  fuel_consumption DECIMAL   NOT NULL,
  price             DECIMAL   NOT NULL,
  horse_power      INT       NOT NULL,
  cargo_space      INT       NOT NULL,
  seats            INT       NOT NULL,
  length           INT       NOT NULL,
  width            INT       NOT NULL,
  height           INT       NOT NULL,
  clearance        INT       NOT NULL
);


CREATE TABLE status (
  id           INTEGER PRIMARY KEY DEFAULT nextval('status_id_seq'),
  status_index INTEGER   NOT NULL,
  staus_time   TIMESTAMP NOT NULL
);

CREATE TABLE orders (
  id          INTEGER PRIMARY KEY DEFAULT nextval('order_id_seq'),
  amount      INT       NOT NULL,
  user_id     INTEGER   NOT NULL,
  car_id      INTEGER   NOT NULL,
  status_id   INTEGER   NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (car_id) REFERENCES car (id) ON DELETE CASCADE,
  FOREIGN KEY (status_id) REFERENCES status (id) ON DELETE CASCADE
);
