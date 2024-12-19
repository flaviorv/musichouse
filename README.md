## Application with microservices architecture
- For running, open the dir of the application in the cli and type docker-compose up.
- The frontend needs to be itialized separatily. Install npm packages and type npm start and access by http://localhost:3000.
- The front is only to list the instruments e to buy. Until this moment.
- To add new products is only by a api tester as Insomnia or Postman.
- The url is http://localhost:9999/electricguitar or http://localhost:9999/amplifier with Post method.
- Examples of products below:
#### Electric guitars:
{
		"model": "RG121",
		"brand": "Ibanez",
		"price": 200.0,
		"quantity": 700,
		"strings": 6,
		"activePickup": false
	},
	{
		"model": "Soloist",
		"brand": "Jackson",
		"price": 300.0,
		"quantity": 200,
		"watts": 0,
		"speakerInch": 0
	},
	{
		"model": "T500",
		"brand": "Tagima",
		"price": 400.0,
		"quantity": 500,
		"strings": 6,
		"activePickup": false
	}
  #### Amplifier:
  {
		"model": "MG1000",
		"brand": "Meteoro",
		"price": 70.0,
		"quantity": 900,
		"watts": 200,
		"speakerInch": 0
	}
