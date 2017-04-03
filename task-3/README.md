INSTRUCTIONS TO TASK 3:

Run Mongo with Docker:
docker pull mongo
docker run -p 27017:27017 -t mongo

Run ApacheMQ with Docker:
docker pull webcenter/activemq
docker run -p 61616:61616 -p 8161:8161 -t webcenter/activemq

Run Aplications:
on folder wms application run: mvn spring-boot:run
on folder wms-storage application run: mvn spring-boot:run

now submit to http://localhost:8080/products, json products with: 

[{
  "sku": "SPD-33412ee",
  "price": "1.99",
  "name": "Spidernet refill set",
  "description": "To refill your net capabilities",
  "size": "single",
  "brand": "Peter Parker",
  "categories": ["Super Heroes", "Spiderman", "Accessories"],
  "product_image_url": "http://cdn.gfg.com.br/spiderman/spidernet.jpg",
  "special_price": "1"
}]

now stop the application wms-storage, and submit one more time to http://localhost:8080/products, json products with: 

[{
  "sku": "SPD-33412ee",
  "price": "1.99",
  "name": "Spidernet refill set",
  "description": "To refill your net capabilities",
  "size": "single",
  "brand": "Peter Parker",
  "categories": ["Super Heroes", "Spiderman", "Accessories"],
  "product_image_url": "http://cdn.gfg.com.br/spiderman/spidernet.jpg",
  "special_price": "1"
}]

The application will not fail, instead of this the request will be on the queue waiting the "application storage" back to working.

now run wms-storage and the item of queue will be accomplished.

