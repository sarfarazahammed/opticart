INSERT INTO permission (name)
VALUES ('PERM_RW_ALL'),
       ('PERM_RW_USER'),
       ('PERM_RO_USER'),
       ('PERM_RW_ROLE'),
       ('PERM_RO_ROLE'),
       ('PERM_RW_PERMISSION'),
       ('PERM_RO_PERMISSION'),
       ('PERM_RW_PRODUCT'),
       ('PERM_RO_PRODUCT'),
       ('PERM_RW_STOCK'),
       ('PERM_RO_STOCK'),
       ('PERM_RW_ORDER'),
       ('PERM_RO_ORDER'),
       ('PERM_RW_PRESCRIPTION'),
       ('PERM_RO_PRESCRIPTION'),
       ('PERM_RO_ANALYTICS'),
       ('PERM_RW_WISHLIST'),
       ('PERM_RO_WISHLIST'),
       ('PERM_RW_CART'),
       ('PERM_RO_CART');

INSERT INTO role (name)
VALUES ('ROLE_SUPER_ADMIN'),
       ('ROLE_ADMIN'),
       ('ROLE_CUSTOMER_ADMIN'),
       ('ROLE_GUEST'),
       ('ROLE_CUSTOMER'),
       ('ROLE_TECH_SUPPORT');

INSERT INTO role_permission_map (role_name, permission_name)
VALUES ('ROLE_SUPER_ADMIN', 'PERM_RW_ALL');

INSERT INTO role_permission_map (role_name, permission_name)
VALUES ('ROLE_ADMIN', 'PERM_RW_STOCK'),
       ('ROLE_ADMIN', 'PERM_RW_PRODUCT'),
       ('ROLE_ADMIN', 'PERM_RO_ANALYTICS'),
       ('ROLE_ADMIN', 'PERM_RW_ORDER');

INSERT INTO role_permission_map (role_name, permission_name)
VALUES ('ROLE_CUSTOMER_ADMIN', 'PERM_RW_USER'),
       ('ROLE_CUSTOMER_ADMIN', 'PERM_RW_ROLE'),
       ('ROLE_CUSTOMER_ADMIN', 'PERM_RW_PERMISSION');

INSERT INTO role_permission_map (role_name, permission_name)
VALUES ('ROLE_GUEST', 'PERM_RO_PRODUCT'),
       ('ROLE_GUEST', 'PERM_RW_WISHLIST'),
       ('ROLE_GUEST', 'PERM_RW_CART');

INSERT INTO role_permission_map (role_name, permission_name)
VALUES ('ROLE_CUSTOMER', 'PERM_RO_PRODUCT'),
       ('ROLE_CUSTOMER', 'PERM_RW_WISHLIST'),
       ('ROLE_CUSTOMER', 'PERM_RW_CART'),
       ('ROLE_CUSTOMER', 'PERM_RW_ORDER'),
       ('ROLE_CUSTOMER', 'PERM_RW_PRESCRIPTION');

INSERT INTO role_permission_map (role_name, permission_name)
VALUES ('ROLE_TECH_SUPPORT', 'PERM_RO_PRODUCT'),
       ('ROLE_TECH_SUPPORT', 'PERM_RW_WISHLIST'),
       ('ROLE_TECH_SUPPORT', 'PERM_RW_CART'),
       ('ROLE_TECH_SUPPORT', 'PERM_RW_ORDER'),
       ('ROLE_TECH_SUPPORT', 'PERM_RW_PRESCRIPTION');