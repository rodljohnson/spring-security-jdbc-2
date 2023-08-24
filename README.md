CREATE TABLE users (
username VARCHAR(50) NOT NULL,
password VARCHAR(255) NOT NULL,
enabled BOOLEAN NOT NULL,
CONSTRAINT pk_users PRIMARY KEY (username),
CONSTRAINT uq_username UNIQUE (username)
);
CREATE TABLE authorities (
username VARCHAR(50) NOT NULL,
authority VARCHAR(50) NOT NULL,
CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);