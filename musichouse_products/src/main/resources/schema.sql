USE db_products;

CREATE TABLE IF NOT EXISTS product (
    model VARCHAR(50) PRIMARY KEY,
    _type VARCHAR(30),
    brand VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    image LONGBLOB
);

CREATE TABLE IF NOT EXISTS electric_guitar (
     model VARCHAR(50) PRIMARY KEY,
     strings INT NOT NULL,
     active_pickup BOOLEAN NOT NULL,
     CONSTRAINT fk_guitar_product FOREIGN KEY (model) REFERENCES product(model)
);

CREATE TABLE IF NOT EXISTS amplifier (
    model VARCHAR(50) PRIMARY KEY,
    watts INT NOT NULL,
    speaker_inch INT NOT NULL,
    CONSTRAINT fk_amplifier_product FOREIGN KEY (model) REFERENCES product(model)
);
