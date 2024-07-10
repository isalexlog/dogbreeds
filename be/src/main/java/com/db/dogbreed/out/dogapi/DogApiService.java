package com.db.dogbreed.out.dogapi;

import com.db.dogbreed.domain.model.Breed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
@Slf4j
public class DogApiService {

    private final DogApi dogApi;

    public Flux<Breed> getAllBreads() {
        return dogApi.getAllBreads()
                .map(response -> response.getMessage().entrySet())
                .flatMapIterable(set -> set)
                .map(entry -> new Breed(entry.getKey(), entry.getValue()));
    }
}
