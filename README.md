"# sse-springmvc-rxjava-example" 

Server Sent Event example with Spring MVC and Rxjava
-----------------------------------------------------------------------
----------------------------------------------------------------------
Example application for Server Sent Events using Spring MVC and RxJava


1. Functionality
-------------------
-------------------
The app is a webapp that enables the clients to ask the app to mine 
certain amount of coin with random values.


2. Architecture
--------------------
The app provides the `/coinminer/mine/{coinCoun}` endpoint with the `CoinMinerController`
Spring MVC Restcontroller. The `CoinMinerController#mine( coinCount )` method returns an SseEmitter that 
is used to send CoinMined events back to the client.

```Java
@RequestMapping(value="/mine/{coinCount}", method=RequestMethod.GET)
public SseEmitter mine( @PathVariable int coinCount ) {
	final SseEmitter sseEmitter = new SseEmitter();
	coinMiner.mine( coinCount ).subscribe( 
						value -> sendCoinMinedEvent(sseEmitter,value),
						sseEmitter::completeWithError,
						sseEmitter::complete );		
	return sseEmitter;
}
```

The controller uses a CoinMiner domain object to mine coins. The `CoinMiner#mine( coinCount )`
returns an RxJava `Observable<Coin>`. The implementation uses an ExecutorService to mine coins parallel.
Each parallel execution returns an Observable<Coin> that are merged to one <Observable<Coin>> stream.

```Java
@Override
public Observable<Coin> mine( int coinCount ) {
	List<Observable<Coin>> observables = 
			IntStream.range(0,	coinCount )
					 .mapToObj( i -> mineOne() )
					 .collect( Collectors.toList() );
	return Observable.merge( observables );		
}
```


3. Building the app
-------------------
Run the following command to create an eclipse project: gradle clean build cleanEclipse eclipse


4. Running the app:
--------------------
1. Run the CoinMinerApplication spring boot application.
2. Open your browser with the http://localhost:8080/sse_consumer.html or 
http://localhost:8080/coinminer/mine/5

5. Original example:
--------------------- 
http://www.nurkiewicz.com/2015/07/server-sent-events-with-rxjava-and.html
--------------------

6. Room for improvement:
----------------------
--------------------

6.1 REST
--------------------
The /coinminer/mine/{coinCount} is service oriented approach. It reflects how 
the server provides coins.

It would be better to use a client based, resource centric approach. E.g. :
/coinminer/coins?limit=11. This way the client only wants 11 coins from the coinminer
resource. It doesn't care about the how, but cares about the what.

6.2 Domain Driven Design
-----------------------
The CoinMiner#mine() method returns an Observable<Coin>. I'm not sure if 
I like it or not since it ties the interface to RxJava.

