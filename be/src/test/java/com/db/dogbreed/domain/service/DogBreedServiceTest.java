package com.db.dogbreed.domain.service;

import com.db.dogbreed.domain.model.Breed;
import com.db.dogbreed.out.dogapi.DogApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class DogBreedServiceTest {

    @Mock
    private DogApiService dogApiService;

    @Mock
    private CacheService cacheService;

    @Mock
    private AnalyticsService analyticsService;

    private DogBreedService dogBreedService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dogBreedService = new DogBreedService(dogApiService, cacheService, analyticsService);
    }

    @Test
    void getAllBreeds_CacheHit() {
        List<Breed> cachedBreeds = Arrays.asList(
                new Breed("Labrador", Arrays.asList("Yellow", "Chocolate", "Black")),
                new Breed("Poodle", Arrays.asList("Toy", "Miniature", "Standard"))
        );

        when(cacheService.getAll()).thenReturn(cachedBreeds);

        StepVerifier.create(dogBreedService.getAllBreeds())
                .expectNext(cachedBreeds)
                .verifyComplete();

        verify(cacheService).getAll();
        verifyNoInteractions(dogApiService);
    }

    @Test
    void getAllBreeds_CacheMiss() {
        List<Breed> apiBreeds = Arrays.asList(
                new Breed("Bulldog", Arrays.asList("English", "French")),
                new Breed("Shepherd", Arrays.asList("German", "Australian"))
        );

        when(cacheService.getAll()).thenReturn(Collections.emptyList());
        when(dogApiService.getAllBreads()).thenReturn(Flux.fromIterable(apiBreeds));

        StepVerifier.create(dogBreedService.getAllBreeds())
                .expectNext(apiBreeds)
                .verifyComplete();

        verify(cacheService).getAll();
        verify(dogApiService).getAllBreads();
        verify(cacheService, times(apiBreeds.size())).put(any(Breed.class));
    }

    @Test
    void getBreed_Success() {
        String breedId = "Labrador";
        Breed expectedBreed = new Breed("Labrador", Arrays.asList("Yellow", "Chocolate", "Black"));

        when(cacheService.get(breedId)).thenReturn(expectedBreed);

        StepVerifier.create(dogBreedService.getBreed(breedId))
                .expectNext(expectedBreed)
                .verifyComplete();

        verify(analyticsService).log(breedId);
        verify(cacheService).get(breedId);
    }
}
