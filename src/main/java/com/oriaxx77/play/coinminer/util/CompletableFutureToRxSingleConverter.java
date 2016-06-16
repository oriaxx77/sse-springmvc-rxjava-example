package com.oriaxx77.play.coinminer.util;

import java.util.concurrent.CompletableFuture;
import rx.Single;

public class CompletableFutureToRxSingleConverter<T>
	implements Converter<CompletableFuture<T>,Single<T>> {

	@Override
	public Single<T> convert(CompletableFuture<T> future ) {
		
		return Single
				.create( subscriber -> 
						   future.whenComplete((result,error) -> {
							if (error != null){
								subscriber.onError( error );
							} else {
								subscriber.onSuccess( result );
							}}));
	}

}
