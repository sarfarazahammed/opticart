CREATE TABLE price
(
    id varchar(255) NOT NULL,
    amount DECIMAL(4,2) NOT NULL,
    currency varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE brand
(
    code varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    description TEXT,
    PRIMARY KEY (code)
);

CREATE TABLE media
(
    id varchar(255) NOT NULL,
    celebrity text[],
    material text[],
    product text[],
    video text[],
    PRIMARY KEY (id)
);

CREATE TABLE color
(
    code varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    hex_code varchar(6) NOT NULL,
    PRIMARY KEY (code)
);

CREATE TABLE frame_sizes
(
  size_code varchar(255) NOT NULL,
  description TEXT NOT NULL,
  PRIMARY KEY (size_code)
);

CREATE TABLE frame
(
    model_id varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    description TEXT NOT NULL,
    gender CHAR(1) NOT NULL,
    age_group varchar(255) NOT NULL,
    shape varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    material varchar(255) NOT NULL,
    temple_material varchar(255) NOT NULL,
    price_id varchar(255) NOT NULL,
    brand_code varchar(255) NOT NULL,
    PRIMARY KEY (model_id),
    FOREIGN KEY (price_id) REFERENCES price(id),
    FOREIGN KEY (brand_code) REFERENCES brand(code)
);

CREATE TABLE frame_variant
(
    id varchar(255) NOT NULL,
    color_code varchar(255) NOT NULL,
    frame_size varchar(255) NOT NULL,
    temple_color varchar(255) NOT NULL,
    weight_in_gms DECIMAL(5,2) NOT NULL,
    frame_id varchar(255) NOT NULL,
    PRIMARY KEY (id, frame_id),
    FOREIGN KEY (color_code) REFERENCES color(code),
    FOREIGN KEY (frame_size) REFERENCES frame_sizes(size_code),
    FOREIGN KEY (frame_id) REFERENCES frame(model_id)
);