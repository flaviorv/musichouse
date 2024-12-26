## Application with microservices architecture
### Instructions for running:
	You need to have docker installed on your machine
 	With the musichouse folder open in terminal, type docker compose up
	Open the browser and type localhost:3000
### The app:
	This is an musical instruments web store, where is possible add items to order
<img src="./mh-front/src/images/screenshot_home.png" width="50%" title="Home screen"><img src="./mh-front/src/images/screenshot_products.png" width="50%" title="Products screen">

	After click in the close order button, 
 	there will be a processing payment simulation and a progress bar to see the order status
<img src="./mh-front/src/images/screenshot_adding_to_order.png" width="50%" title="Open order screen"><img src="./mh-front/src/images/screenshot_open-order.png" width="50%" title="Open order screen">
<img src="./mh-front/src/images/screenshot_processing_payment.png" width="50%" title="Open order screen"><img src="./mh-front/src/images/screenshot_payment_error.png" width="50%" title="Open order screen">
 	
  	The order status and the products stock change asynchronously over time via messaging
<img src="./mh-front/src/images/screenshot_payment_result.png" width="50%" title="Open order screen"><img src="./mh-front/src/images/screenshot_payment_accepted_status.png" width="50%" title="Open order screen">

	At this moment, the status reach only the processing payment result,
 	but will be change until the delivered status in the near future
<img src="./mh-front/src/images/screenshot_all_orders.png" width="50%" title="Open order screen"><img src="./mh-front/src/images/screenshot_payment_refused_status.png" width="50%" title="Open order screen">
