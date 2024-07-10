package com.db.dogbreed.in.rest;

import com.db.dogbreed.domain.model.AnalyticsRecord;
import com.db.dogbreed.domain.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AnalyticsService analyticsService;

    @GetMapping("/report/tracking")
    public Flux<AnalyticsRecord> getAnalytics() {
        return Flux.fromIterable(analyticsService.getAnalytics());
    }
}
