package com.oriaxx77.play.coinminer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.oriaxx77.play.coinminer.domain.AsyncCoinMiner;
import com.oriaxx77.play.coinminer.domain.CoinMiner;


@SpringBootApplication
public class CoinMinerApplication {

		
	@Bean CoinMiner coinMiner( ExecutorService coinMinerExecutorService ) {
		return new AsyncCoinMiner( coinMinerExecutorService ) ;
	}
	
	@Bean ExecutorService coinMinerExecutorService() {
		return Executors.newFixedThreadPool(2);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CoinMinerApplication.class, args);
	}
}
