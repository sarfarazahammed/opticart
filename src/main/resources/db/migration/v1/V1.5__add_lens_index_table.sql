CREATE TABLE lens_index
(
    id                 varchar(255)  NOT NULL,
    index              DECIMAL(3, 2) NOT NULL,
    sph_min_neg        DECIMAL(4, 2) NOT NULL,
    sph_min_pos        DECIMAL(4, 2) NOT NULL,
    sph_max_neg        DECIMAL(4, 2) NOT NULL,
    sph_max_pos        DECIMAL(4, 2) NOT NULL,
    cyl_min_neg        DECIMAL(4, 2) NOT NULL,
    cyl_min_pos        DECIMAL(4, 2) NOT NULL,
    cyl_max_neg        DECIMAL(4, 2) NOT NULL,
    cyl_max_pos        DECIMAL(4, 2) NOT NULL,
    features           TEXT,
    img_url            varchar(255),
    created_date       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP(6),
    last_modified_date TIMESTAMP   DEFAULT CURRENT_TIMESTAMP(6),
    created_by         varchar(45) DEFAULT 'SYSTEM',
    last_modified_by   varchar(45) DEFAULT 'SYSTEM',
    PRIMARY KEY (id)
)