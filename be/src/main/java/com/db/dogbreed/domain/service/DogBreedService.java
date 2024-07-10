package com.db.dogbreed.domain.service;

import com.db.dogbreed.domain.model.Breed;
import com.db.dogbreed.out.dogapi.DogApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class DogBreedService {

    private final DogApiService dogApiService;
    private final CacheService cacheService;
    private final AnalyticsService analyticsService;

    public Mono<List<Breed>> getAllBreeds() {
        var cachedValues = cacheService.getAll();
        if (!cachedValues.isEmpty()) {
            return Mono.just(cachedValues);
        }

        return dogApiService.getAllBreads()
                .doOnNext(cacheService::put)
                .collectList();
    }

    public Mono<Breed> getBreed(String id) {
        analyticsService.log(id);
        var breed = cacheService.get(id);
        return breed == null? Mono.empty() : Mono.just(breed);
    }
}
