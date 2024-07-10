package com.db.dogbreed.out.dogapi;

import com.db.dogbreed.out.dogapi.dto.BreedResponse;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange()
public interface DogApi {

    @GetExchange("/breeds/list/all")
    Mono<BreedResponse> getAllBreads();

}
