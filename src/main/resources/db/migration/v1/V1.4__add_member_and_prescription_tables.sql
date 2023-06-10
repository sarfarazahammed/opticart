CREATE TABLE prescription_type
(
    id  varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    title varchar(255) NOT NULL,
    img_url varchar(255) NOT NULL,
    description TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE power
(
    id  varchar(255) NOT NULL,
    od_cylinder DECIMAL(5,2),
    od_sphere DECIMAL(5,2),
    od_axis INTEGER,
    od_prism_horizontal DECIMAL(5,2),
    od_base_direction_horizontal varchar(20),
    od_prism_vertical DECIMAL(5,2),
    od_base_direction_vertical varchar(20),
    od_add DECIMAL(5,2),
    os_cylinder DECIMAL(5,2),
    os_sphere DECIMAL(5,2),
    os_axis INTEGER,
    os_prism_horizontal DECIMAL(5,2),
    os_base_direction_horizontal varchar(20),
    os_prism_vertical DECIMAL(5,2),
    os_base_direction_vertical varchar(20),
    os_add DECIMAL(5,2),
    pd INTEGER,
    pd_left INTEGER,
    pd_right INTEGER,
    base_curve INTEGER,
    diameter INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id  varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    dob DATE,
    gender varchar(1),
    user_id varchar(255) NOT NULL,
    tag varchar(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES app_user (id)
);

CREATE TABLE prescription
(
    id varchar(255) NOT NULL,
    type_id  varchar(255) NOT NULL,
    power_id varchar(255) NOT NULL,
    member_id varchar(255) NOT NULL,
    created_date       TIMESTAMP             DEFAULT CURRENT_TIMESTAMP(6),
    last_modified_date TIMESTAMP             DEFAULT CURRENT_TIMESTAMP(6),
    created_by         varchar(45)           DEFAULT 'SYSTEM',
    last_modified_by   varchar(45)           DEFAULT 'SYSTEM',
    PRIMARY KEY (id),
    FOREIGN KEY (type_id) REFERENCES prescription_type (id),
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (power_id) REFERENCES power (id)
);
