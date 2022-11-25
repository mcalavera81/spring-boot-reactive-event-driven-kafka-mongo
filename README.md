## Description
Stock prices processing components:

* `service.ApiStockService`: Stateful service. On each call, we get a batch of pseudo-random-generated stock
prices. Each call also moves forward the current time.

* `message.StockTickSupplierConfig`: Using a mock API `service.ApiStockService`, it generates an infinite sequence of
stock prices with a `stock-update.frequency.seconds` frequency and pushes these prices into the *testtock* Kafka 
topic.

* `message.StockTickConsumerConfig`: Consumes the stock prices from the *testtock* Kafka topic and stores them 
in a raw format in the `stockTick` Mongodb collection using `service.StockTickService`

* `message.CandleStickConsumerConfig`: Consumes the stock prices from the *testtock* Kafka topic and calls 
`service.CandleStickService` to update the day-based and hour-based *CandleSticks*

API endpoints:
* `Company` endpoints:
  * GET `/api/v1/companies`: list all companies with stock prices. It is defined statically in 
  `initializer.MasterDataLoader`, instead of being defined by the incoming stock prices. It is a coroutine-based
  reactive endpoint.
* `StockTick` endpoints:
  * GET `/api/v1/ticks/changelog`: Server pushes new stock prices being stored in MongoDB using Server-sent events. Only for debugging purposes.
  * GET `/api/v1/ticks`: retrieves all stock prices. Just for debugging in non-prod envs. due to the scale of data.
  * POST `/api/v1/ticks/delete-all`: deletes all stock prices from database. Only for debugging purposes.
* `CandleStick` endpoints:
  * GET `/api/v1/candles/changelog`: Server pushes candle updates being stored in MongoDB using Server-sent events. Only for debugging purposes.
  * GET `/api/v1/candles`: retrieves all candles, optionally filtered by query params *ticker* and *type*. 
  Valid values for ticker: AMZN,AAPL,GOOG,META. Valid values for type: DAY,ONE_HOUR.
  * POST `/api/v1/candles/delete-all`: deletes all candles from database. Only for debugging purposes.

## Run
### Requirements
* Install docker and docker-compose
* JVM

### Steps
1. Run `docker-compose up`
2.  `mvn spring-boot:run`
3. curl http://localhost:8080/api/v1/candles/?ticker=META&type=ONE_HOUR
```
[
  {
    "id": "63849bcad4c41217e8e109e5",
    "type": "ONE_HOUR",
    "ticker": "META",
    "timestamp": "2022-11-28T15:00:00",
    "high": 180,
    "low": 105
  },
  ...
]
```

## TODO

* Introduce tests. Refactor app code as needed for testability.
* Split code into modules: API, Stock API Consumer, Prices Processor(s)
* Implement Weekly Candlesticks.
* Better input handling and validation.
* Add Open and Close prices for Candlesticks.
