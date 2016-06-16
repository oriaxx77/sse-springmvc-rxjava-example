package com.oriaxx77.play.coinminer.domain;

import java.util.Random;

public class Coin {	
	
	private Integer value;
	
	public Coin(){
		 value = new Random().nextInt(100);
	}
	
	public Integer getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Coin [value=" + value + "]";
	}
	
	
}
