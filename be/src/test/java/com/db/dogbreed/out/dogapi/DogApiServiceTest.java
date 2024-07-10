package com.db.dogbreed.out.dogapi;

import com.db.dogbreed.domain.model.Breed;
import com.db.dogbreed.out.dogapi.dto.BreedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

class DogApiServiceTest {

    @Mock
    private DogApi dogApi;

    private DogApiService dogApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dogApiService = new DogApiService(dogApi);
    }

    @Test
    void getAllBreeds_shouldReturnCorrectBreeds() {
        // Arrange
        Map<String, List<String>> breedMap = new HashMap<>();
        breedMap.put("retriever", Arrays.asList("golden", "labrador"));
        breedMap.put("bulldog", Arrays.asList("french", "english"));

        BreedResponse breedResponse = new BreedResponse();
        breedResponse.setMessage(breedMap);
        breedResponse.setStatus("success");

        when(dogApi.getAllBreads()).thenReturn(Mono.just(breedResponse));

        // Act & Assert
        StepVerifier.create(dogApiService.getAllBreads())
                .expectNext(new Breed("retriever", Arrays.asList("golden", "labrador")))
                .expectNext(new Breed("bulldog", Arrays.asList("french", "english")))
                .verifyComplete();
    }

    @Test
    void getAllBreeds_withEmptyResponse_shouldReturnEmptyFlux() {
        // Arrange
        BreedResponse breedResponse = new BreedResponse();
        breedResponse.setMessage(new HashMap<>());
        breedResponse.setStatus("success");

        when(dogApi.getAllBreads()).thenReturn(Mono.just(breedResponse));

        // Act & Assert
        StepVerifier.create(dogApiService.getAllBreads())
                .verifyComplete();
    }

    @Test
    void getAllBreeds_withErrorResponse_shouldPropagateError() {
        // Arrange
        RuntimeException exception = new RuntimeException("API Error");
        when(dogApi.getAllBreads()).thenReturn(Mono.error(exception));

        // Act & Assert
        StepVerifier.create(dogApiService.getAllBreads())
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && throwable.getMessage().equals("API Error"))
                .verify();
    }
}
