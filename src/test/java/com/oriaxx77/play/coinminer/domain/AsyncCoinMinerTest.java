package com.oriaxx77.play.coinminer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.junit.Test;
import static org.junit.Assert.*;

public class AsyncCoinMinerTest {

	private CoinMiner coinMiner = new AsyncCoinMiner(Executors.newSingleThreadExecutor());

	@Test
	public void testMineSuccessful() {
		int noOfCoinsToMine = 11;
		coinMiner.mine(noOfCoinsToMine);
		List<Coin> coins = new ArrayList<Coin>();
		coinMiner.mine(11).toBlocking().forEach(coins::add);
		assertEquals(noOfCoinsToMine, coins.size());
	}

}
