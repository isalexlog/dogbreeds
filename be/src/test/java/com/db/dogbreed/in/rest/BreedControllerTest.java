package com.db.dogbreed.in.rest;

import com.db.dogbreed.configuration.SecurityConfig;
import com.db.dogbreed.domain.model.Breed;
import com.db.dogbreed.domain.service.DogBreedService;
import com.db.dogbreed.in.dto.Page;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebFluxTest(value = BreedController.class)
@Import(SecurityConfig.class)
@DisabledInAotMode
class BreedControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DogBreedService dogBreedService;

    @MockBean
    private PaginatorService paginatorService;

    @Test
    void getDogBreeds_ShouldReturnPagedResult() {
        // Arrange
        List<Breed> breeds = Arrays.asList(
                new Breed("Labrador", Arrays.asList("Yellow", "Chocolate")),
                new Breed("Poodle", Arrays.asList("Toy", "Miniature", "Standard"))
        );
        Page<Breed> expectedPage = new Page<>(breeds, 0, 10, 2, 2);

        when(dogBreedService.getAllBreeds()).thenReturn(Mono.just(breeds));
        when(paginatorService.createPage(ArgumentMatchers.<List<Breed>>any(), anyInt(), anyInt())).thenReturn(expectedPage);

        // Act & Assert
        webTestClient.get().uri("/breeds/list/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content[0].breed").isEqualTo("Labrador")
                .jsonPath("$.content[1].breed").isEqualTo("Poodle")
                .jsonPath("$.pageNumber").isEqualTo(0)
                .jsonPath("$.pageSize").isEqualTo(10)
                .jsonPath("$.totalElements").isEqualTo(2);
    }

    @Test
    void getDogBreeds_WithCustomParams_ShouldReturnCustomPagedResult() {
        // Arrange
        List<Breed> breeds = Arrays.asList(
                new Breed("Labrador", Arrays.asList("Yellow", "Chocolate")),
                new Breed("Poodle", Arrays.asList("Toy", "Miniature", "Standard"))
        );
        Page<Breed> expectedPage = new Page<>(breeds, 1, 5, 2, 2);

        when(dogBreedService.getAllBreeds()).thenReturn(Mono.just(breeds));
        when(paginatorService.createPage(ArgumentMatchers.<List<Breed>>any(), anyInt(), anyInt())).thenReturn(expectedPage);

        // Act & Assert
        webTestClient.get().uri("/breeds/list/all?page=1&size=5")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content[0].breed").isEqualTo("Labrador")
                .jsonPath("$.content[1].breed").isEqualTo("Poodle")
                .jsonPath("$.pageNumber").isEqualTo(1)
                .jsonPath("$.pageSize").isEqualTo(5)
                .jsonPath("$.totalElements").isEqualTo(2);
    }

    @Test
    void getBreed_WithValidId_ShouldReturnBreed() {
        // Arrange
        String breedId = "labrador";
        Breed expectedBreed = new Breed("Labrador", Arrays.asList("Yellow", "Chocolate"));

        when(dogBreedService.getBreed(breedId)).thenReturn(Mono.just(expectedBreed));

        // Act & Assert
        webTestClient.get().uri("/breeds/detail/{id}", breedId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.breed").isEqualTo("Labrador")
                .jsonPath("$.subBreeds[0]").isEqualTo("Yellow")
                .jsonPath("$.subBreeds[1]").isEqualTo("Chocolate");
    }

    @Test
    void getBreed_WithInvalidId_ShouldReturnNotFound() {
        // Arrange
        String breedId = "invalid";

        when(dogBreedService.getBreed(breedId)).thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.get().uri("/breeds/detail/{id}", breedId)
                .exchange()
                .expectStatus().isNotFound();
    }
}


