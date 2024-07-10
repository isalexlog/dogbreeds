package com.db.dogbreed.in.rest;

import com.db.dogbreed.configuration.SecurityConfig;
import com.db.dogbreed.in.dto.AuthStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@WebFluxTest(AuthController.class)
@Import(SecurityConfig.class)
@DisabledInAotMode
class AuthControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getAuthStatus_whenNotAuthenticated_shouldReturnUnauthenticatedStatus() {
        webTestClient.get().uri("/auth/status")
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthStatus.class)
                .value(authStatus -> {
                    assertThat(authStatus.getLoggedIn()).isFalse();
                    assertThat(authStatus.getAuthorities()).isNull();
                });
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void getAuthStatus_whenAuthenticated_shouldReturnAuthenticatedStatusWithAuthorities() {
        List<String> expectedAuthorities = List.of("ROLE_USER", "ROLE_ADMIN");

        webTestClient.get().uri("/auth/status")
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthStatus.class)
                .value(authStatus -> {
                    assertThat(authStatus.getLoggedIn()).isTrue();
                    assertThat(authStatus.getAuthorities()).containsExactlyInAnyOrderElementsOf(expectedAuthorities);
                });
    }
}
