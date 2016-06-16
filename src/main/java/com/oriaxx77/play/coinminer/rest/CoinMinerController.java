package com.oriaxx77.play.coinminer.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.oriaxx77.play.coinminer.domain.Coin;
import com.oriaxx77.play.coinminer.domain.CoinMiner;

/**
 * Coin miner service to show how to use SSE.
 * It provides /coinminer/mine/{cointCount} endpoint to get a Server Sent Event stream.
 * For this see {@link #mine(int)}
 */
@RestController()
@RequestMapping("/coinminer")
public class CoinMinerController {
	
	private CoinMiner coinMiner;
	
	@Autowired
	public CoinMinerController( CoinMiner coinMiner ) {
		this.coinMiner = coinMiner;
	}
	
	
	@RequestMapping(value="/health", method=RequestMethod.GET, produces="application/json" )
	public String healthCheck() {
		return "CoinMinerController is up";
	}
		
	
	@RequestMapping(value="/mine/{coinCount}", method=RequestMethod.GET)
	public SseEmitter mine( @PathVariable int coinCount ) {
		final SseEmitter sseEmitter = new SseEmitter();
		coinMiner.mine( coinCount ).subscribe( 
							value -> sendCoinMinedEvent(sseEmitter,value),
							sseEmitter::completeWithError,
							sseEmitter::complete );		
		return sseEmitter;
	}
	
	private void sendCoinMinedEvent( SseEmitter sseEmitter, Coin coin ){
		try{
			SseEventBuilder coinMinedEventBuilder = SseEmitter.event().data( coin, MediaType.APPLICATION_JSON ).name( "CoinMined" );
			sseEmitter.send( coinMinedEventBuilder ) ;
		}catch( IOException ioEx){ 
			throw new RuntimeException(ioEx);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
