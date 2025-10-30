### Description:

<p>Online store for musical instruments with a microservices architecture (product, sales, payment).</p>

<p>There is a gateway service as an access point, more than one instance per service, telemetry to monitor system behavior, and integration tests.</p>

### System features:

- Full Text Search
- Shopping Cart
- Purchase and Payment Simulation
- Asynchronous Payment Verification via Message
- Asynchronous Inventory Controll via Message that is updated according to sales

### Technologies/Frameworks:

Java, Spring Cloud, RabbitMQ, JPA Specifications, Open Telemetry, Eureka,
React, Open Feign, Docker, MySQL, MongoDB, Junit, MockMVC e Testcontainers.

### Instructions for use:

- You need to have docker installed on your machine.
- The unique project file needed is the **docker-compose.yml**
- Open the folder where you placed the file in the terminal
- Type `docker compose up` (all services will be downloaded from Docker Hub)
- Wait the services to load, then open the browser and type `localhost:3000` as url

### Screenshoots:

<img src="./mh-front/src/images/home-featured-products.png" width="50%" alt="Home page with featured prodcuts">

<img src="./mh-front/src/images/home-amp-category.png" width="50%" alt="Home page listing products of a category">

<img src="./mh-front/src/images/search-guitar.png" width="50%" alt="Guitars found through a text field">

<img src="./mh-front/src/images/search-amp.png" width="50%" alt="Amplifiers found through a text field">

<img src="./mh-front/src/images/search-amp-by-speaker-inch.png" width="50%" alt="Amplifiers found by speaker inch through a text field">

<img src="./mh-front/src/images/search-guitar-by-strings.png" width="50%" alt="Guitars found by number of strings through a text field">

<img src="./mh-front/src/images/pdp.png" width="50%" alt="Product Detail Page">
<img src="./mh-front/src/images/pdp-specifications.png" width="50%" alt="PDP Specifications">
