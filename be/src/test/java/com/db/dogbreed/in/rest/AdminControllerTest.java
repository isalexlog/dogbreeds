package com.db.dogbreed.in.rest;

import com.db.dogbreed.configuration.SecurityConfig;
import com.db.dogbreed.domain.model.AnalyticsRecord;
import com.db.dogbreed.domain.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(AdminController.class)
@Import(SecurityConfig.class)
@DisabledInAotMode
class AdminControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AnalyticsService analyticsService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAnalytics() {
        // Arrange
        List<AnalyticsRecord> mockRecords = Arrays.asList(
                new AnalyticsRecord("Labrador", 2L),
                new AnalyticsRecord("Pudle", 1L)
        );
        when(analyticsService.getAnalytics()).thenReturn(mockRecords);

        // Act & Assert
        webTestClient.get()
                .uri("/admin/report/tracking")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AnalyticsRecord.class)
                .hasSize(2)
                .isEqualTo(mockRecords);
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetAnalytics_Unauthorized() {
        webTestClient.get()
                .uri("/admin/report/tracking")
                .exchange()
                .expectStatus().isForbidden();
    }
}
