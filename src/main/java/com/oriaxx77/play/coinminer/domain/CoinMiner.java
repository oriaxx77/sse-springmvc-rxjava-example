package com.oriaxx77.play.coinminer.domain;

import rx.Observable;

/**
 * A virtual coin miner. 
 * Call {link {@link #mine()} to mine a coin with blocking or 
 * {@link #mineAsync()} to mine a coin asynchronously.
 */
public interface CoinMiner {
	/**
	 * Mines coinCount amount {@code Coin}
	 * @param coinCount number of {@code Coin} to mine.
	 * @return {@code Coin} stream.
	 */
	Observable<Coin> mine( int coinCount );
	
}
