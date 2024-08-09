package com.example.muddyteam_chaemin.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class KakaoMapController {

    private final String kakaoApiKey = "e31961d649db20096b9c38a445e3d04d"; // 카카오 REST API 키

    @GetMapping("/mudflat-location")
    public String getMudflatLocation(@RequestParam String mudflatName) {
        String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + mudflatName;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            System.out.println(responseBody); // 응답 확인

            if (responseBody != null && responseBody.startsWith("{")) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode rootNode = objectMapper.readTree(responseBody);
                    JsonNode documents = rootNode.path("documents");
                    if (documents.size() > 0) {
                        JsonNode firstResult = documents.get(0);
                        String addressName = firstResult.path("address_name").asText();
                        String latitude = firstResult.path("y").asText();
                        String longitude = firstResult.path("x").asText();
                        return "Address: " + addressName + ", Latitude: " + latitude + ", Longitude: " + longitude;
                    } else {
                        return "No location found for " + mudflatName;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error processing the response";
                }
            } else {
                return "Invalid response: " + responseBody;
            }
        } else {
            return "Error: " + response.getStatusCode();
        }

    }
}
