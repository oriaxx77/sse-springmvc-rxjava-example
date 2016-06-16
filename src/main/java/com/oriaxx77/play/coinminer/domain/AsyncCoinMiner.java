package com.oriaxx77.play.coinminer.domain;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.oriaxx77.play.coinminer.util.CompletableFutureToRxObservableConverter;

import rx.Observable;

/**
 * Mines coins with random values. It uses {@link CompletableFuture} objects to
 * mine coins asynchronously with an {@link ExecutorService}. Each threads wait a random 0-100 millis
 * before supplying a {@link Coin} 
 */
public class AsyncCoinMiner implements CoinMiner {

	
	/** Executor to mine asynchrhonously. */
	private ExecutorService executorService;
	
	/**
	 * Constructor
	 * @param executorService Executor to mine asynchrhonously
	 */
	public AsyncCoinMiner( ExecutorService executorService ) {
		this.executorService = executorService;
	}
	
	@Override
	public Observable<Coin> mine( int coinCount ) {
		List<Observable<Coin>> observables = 
				IntStream.range(0,	coinCount )
						 .mapToObj( i -> mineOne() )
						 .collect( Collectors.toList() );
		return Observable.merge( observables );		
	}
	
	
	private Observable<Coin> mineOne() {
		CompletableFuture<Coin> future = CompletableFuture.supplyAsync( this::mineOneBlocking , executorService );
		return new CompletableFutureToRxObservableConverter<Coin>().convert( future );
	}
	
	/**
	 * It blocks for 0-100 millis before returning a coin.
	 * @return The mined {@code Coin}.
	 */
	private Coin mineOneBlocking() {
		try {
			Thread.sleep( new Random().nextInt(100));
			return new Coin();
		} catch ( InterruptedException ex ) {
			Thread.currentThread().interrupt();
			throw new RuntimeException( ex );
		}
	}
	
}
