package com.oriaxx77.play.coinminer.util;

import java.util.concurrent.CompletableFuture;

import rx.Observable;

public class CompletableFutureToRxObservableConverter<T>
	implements Converter<CompletableFuture<T>,Observable<T>> {

	@Override
	public Observable<T> convert(CompletableFuture<T> future ) {
		
		return Observable
				.create( subscriber -> future.whenComplete( (result,error) -> {
																if ( error != null ){
																	subscriber.onError(error);
																} else {
																	subscriber.onNext(result);
																	subscriber.onCompleted();
																}
															}));
	}

}
