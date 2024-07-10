package com.db.dogbreed.domain.service;

import com.db.dogbreed.domain.model.AnalyticsRecord;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AnalyticsService {

    private final ConcurrentHashMap<String, Long> repository = new ConcurrentHashMap<>();

    public void log(String breedName) {
        repository.compute(breedName, (k, v) -> v == null ? 1L : ++v);
    }

    public List<AnalyticsRecord> getAnalytics() {
        return repository.entrySet().stream()
                .map(entry -> new AnalyticsRecord(entry.getKey(), entry.getValue()))
                .toList();
    }
}
