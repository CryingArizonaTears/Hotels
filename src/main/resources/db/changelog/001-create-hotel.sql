--changeset 1:test
CREATE SEQUENCE hotel_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE hotel
(
    id           BIGINT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(1000),
    brand        VARCHAR(255),
    house_number INTEGER,
    street       VARCHAR(255),
    city         VARCHAR(255),
    country      VARCHAR(255),
    post_code    VARCHAR(50),
    phone        VARCHAR(50),
    email        VARCHAR(50),
    check_in     VARCHAR(50),
    check_out    VARCHAR(50)
);

CREATE TABLE hotel_amenities
(
    hotel_id BIGINT       NOT NULL,
    amenity  VARCHAR(255) NOT NULL,
    CONSTRAINT fk_hotel_amenities_hotel
        FOREIGN KEY (hotel_id)
            REFERENCES hotel (id),
    CONSTRAINT unique_hotel_amenity
        UNIQUE (hotel_id, amenity)
);

CREATE INDEX hotel_brand_idx
    ON hotel (brand);

CREATE INDEX hotel_city_idx
    ON hotel (city);

CREATE INDEX hotel_country_idx
    ON hotel (country);

CREATE INDEX hotel_amenities_amenity_idx
    ON hotel_amenities (amenity);
