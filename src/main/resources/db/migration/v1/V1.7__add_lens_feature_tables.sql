CREATE TABLE lens
(
    id varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    description text NOT NULL,
    icon_url text NOT NULL,
    parent_id varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE lens_variant
(
    id varchar(255) NOT NULL,
    parent_id varchar(255),
    name varchar(255) NOT NULL,
    description text NOT NULL,
    icon_url text NOT NULL,
    color_code varchar(255) NOT NULL,
    quantity_in_stock INTEGER,
    min_order_quantity INTEGER,
    price_id varchar(255),
    status varchar(255) NOT NULL,
    brand_code varchar(255) NOT NULL,
    tags text[],
    features JSONB,
    media_id varchar(255) NOT NULL,
    index_id varchar(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (price_id) REFERENCES price(id),
    FOREIGN KEY (color_code) REFERENCES color(code),
    FOREIGN KEY (brand_code) REFERENCES brand(code),
    FOREIGN KEY (media_id) REFERENCES media(id),
    FOREIGN KEY (index_id) REFERENCES lens_index(id)
);

ALTER TABLE lens_index
    ADD COLUMN price_id varchar(255) NOT NULL;

ALTER TABLE lens_index
    ADD CONSTRAINT fk_lens_index_price
    FOREIGN KEY (price_id) REFERENCES price (id);