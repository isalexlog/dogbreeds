package com.db.dogbreed.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsRecord {
    private String breedName;
    private Long numberOfRequests;
}
