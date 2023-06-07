CREATE TABLE IF NOT EXISTS "role"
(
    name               varchar(255) NOT NULL,
    version            BIGINT       NOT NULL DEFAULT 1,
    created_date       TIMESTAMP             DEFAULT CURRENT_TIMESTAMP(6),
    last_modified_date TIMESTAMP             DEFAULT CURRENT_TIMESTAMP(6),
    created_by         varchar(45)           DEFAULT 'SYSTEM',
    last_modified_by   varchar(45)           DEFAULT 'SYSTEM',
    PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS permission
(
    name               varchar(255) NOT NULL,
    version            BIGINT       NOT NULL DEFAULT 1,
    created_date       TIMESTAMP             DEFAULT CURRENT_TIMESTAMP(6),
    last_modified_date TIMESTAMP             DEFAULT CURRENT_TIMESTAMP(6),
    created_by         varchar(45)           DEFAULT 'SYSTEM',
    last_modified_by   varchar(45)           DEFAULT 'SYSTEM',
    PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS role_permission_map
(
    role_name       VARCHAR(255) NOT NULL,
    permission_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (role_name, permission_name),
    FOREIGN KEY (role_name) REFERENCES ROLE (name),
    FOREIGN KEY (permission_name) REFERENCES permission (name)
);

CREATE TABLE IF NOT EXISTS "app_user"
(
    id                 varchar(255) NOT NULL,
    first_name         varchar(255)          DEFAULT NULL,
    last_name          varchar(255)          DEFAULT NULL,
    profile_img_url    VARCHAR(255)          DEFAULT NULL,
    password           VARCHAR(255)          DEFAULT NULL,
    email              varchar(255) NOT NULL,
    phone_number       varchar(15)  NOT NULL,
    state              varchar(255) NOT NULL DEFAULT 'PENDING',
    version            BIGINT       NOT NULL DEFAULT 1,
    created_date       TIMESTAMP             DEFAULT CURRENT_TIMESTAMP(6),
    last_modified_date TIMESTAMP             DEFAULT CURRENT_TIMESTAMP(6),
    created_by         varchar(45)           DEFAULT 'SYSTEM',
    last_modified_by   varchar(45)           DEFAULT 'SYSTEM',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_role_map
(
    user_id   varchar(255),
    role_name varchar(255),
    PRIMARY KEY (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES "app_user" (id),
    FOREIGN KEY (role_name) REFERENCES "role" (name)
);

CREATE TABLE IF NOT EXISTS address
(
    id                 varchar(255) NOT NULL,
    address            varchar(255) NOT NULL,
    city               varchar(255) NOT NULL,
    state              varchar(255),
    country            varchar(255),
    zipcode            varchar(100),
    user_id            varchar(255),
    tag                varchar(255),
    is_default         boolean               DEFAULT false,
    version            BIGINT       NOT NULL DEFAULT 1,
    created_date       TIMESTAMP             DEFAULT CURRENT_TIMESTAMP(6),
    last_modified_date TIMESTAMP             DEFAULT CURRENT_TIMESTAMP(6),
    created_by         varchar(45)           DEFAULT 'SYSTEM',
    last_modified_by   varchar(45)           DEFAULT 'SYSTEM',
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES "app_user" (id)
);

CREATE TABLE IF NOT EXISTS "user_token"
(
    id                      varchar(255) NOT NULL,
    access_token            TEXT         NOT NULL,
    refresh_token           TEXT         NOT NULL,
    token_type              varchar(255) NOT NULL,
    access_expiration_time  TIMESTAMP    NOT NULL,
    refresh_expiration_time TIMESTAMP    NOT NULL,
    user_id                 varchar(255),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES "app_user" (id)
);
CREATE TABLE IF NOT EXISTS "password_recovery"
(
    id          varchar(255) NOT NULL,
    user_id     VARCHAR(255) NOT NULL,
    code        VARCHAR(6)   NOT NULL,
    expiry_time TIMESTAMP    NOT NULL,
    used_time   TIMESTAMP DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES "app_user" (id)
);

