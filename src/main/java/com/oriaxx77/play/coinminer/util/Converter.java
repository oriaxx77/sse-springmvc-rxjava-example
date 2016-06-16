package com.oriaxx77.play.coinminer.util;

@FunctionalInterface
public interface Converter<TOriginal,TNew> {
	TNew convert( TOriginal objectToConvert);
}
