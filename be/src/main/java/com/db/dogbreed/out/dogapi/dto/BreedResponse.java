package com.db.dogbreed.out.dogapi.dto;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BreedResponse {
    private Map<String, List<String>> message;
    private String status;
}
