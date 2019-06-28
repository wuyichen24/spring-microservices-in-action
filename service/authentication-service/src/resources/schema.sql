---------------------------------------
-- For Store Credentials in database
---------------------------------------
DROP TABLE IF EXISTS oauth_client_details;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;

-- client table
CREATE TABLE oauth_client_details (
  client_id                VARCHAR(256)  NOT NULL  PRIMARY KEY,
  resource_ids             VARCHAR(256),
  client_secret            VARCHAR(256),
  scope                    VARCHAR(256),
  authorized_grant_types   VARCHAR(256),
  web_server_redirect_uri  VARCHAR(256),
  authorities              VARCHAR(256),
  access_token_validity    INTEGER,
  refresh_token_validity   INTEGER,
  additional_information   VARCHAR(4096),
  autoapprove              VARCHAR(256)
);

-- user table
CREATE TABLE users (
  username  VARCHAR(256)  NOT NULL  PRIMARY KEY,
  password  VARCHAR(256)  NOT NULL,
  enabled   BOOLEAN       NOT NULL
);
  
CREATE TABLE authorities (
  id         SERIAL         NOT NULL  PRIMARY KEY,
  username   VARCHAR(256) NOT NULL,
  authority  VARCHAR(256) NOT NULL
);

-- insert clients
INSERT INTO oauth_client_details
    (client_id, client_secret, scope, authorized_grant_types,
    web_server_redirect_uri, authorities, 
    access_token_validity, refresh_token_validity, 
    additional_information, autoapprove)
VALUES
    ('eagleeye', 'thisissecret', 'webclient,mobileclient', 'refresh_token,password,client_credentials', 
    null, null, 
    36000, 36000, 
    null, true);

-- insert users
INSERT INTO users(username,password,enabled) VALUES ('john.carnell',    '$2a$04$N.D.ZTXhH9F2i9Gf1q6CTuvXRRTJWMHlKhftTu9OKMj.xTPCtZNGi', true);
INSERT INTO users(username,password,enabled) VALUES ('william.woodward','$2a$04$Xn/P6VTrkr88jugCJ4IhHuO6ClYCJmjO/nPAcbaI1EQMEvn3CnnWK', true);

-- insert authorities
INSERT INTO authorities(username,authority) VALUES ('john.carnell',    'ADMIN');
INSERT INTO authorities(username,authority) VALUES ('john.carnell',    'USER');
INSERT INTO authorities(username,authority) VALUES ('william.woodward','ADMIN');
---------------------------------------
-- For Application's tables
---------------------------------------

DROP TABLE IF EXISTS user_orgs;

CREATE TABLE user_orgs (
  organization_id  VARCHAR(100)  NOT NULL  PRIMARY KEY,
  username         VARCHAR(100)  NOT NULL
);

INSERT INTO user_orgs (organization_id, username) VALUES ('d1859f1f-4bd7-4593-8654-ea6d9a6a626e', 'john.carnell');
INSERT INTO user_orgs (organization_id, username) VALUES ('42d3d4f5-9f33-42f4-8aca-b7519d6af1bb', 'william.woodward');
