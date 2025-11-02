USE db_products;

CREATE TABLE IF NOT EXISTS product (
    model VARCHAR(50) PRIMARY KEY,
    _type VARCHAR(30),
    brand VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    image LONGBLOB,
    total_rating_sum INT,
    rating_count INT,
    average_rating DECIMAL(3,2)
);

CREATE TABLE IF NOT EXISTS product_rating (
    customer VARCHAR(50) NOT NULL,
    product VARCHAR(50) NOT NULL,
    rating ENUM('ONE', 'TWO', 'THREE', 'FOUR', 'FIVE'),
    PRIMARY KEY (customer, product),
    CONSTRAINT fk_rating_product FOREIGN KEY (product) REFERENCES product(model) ON DELETE CASCADE
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