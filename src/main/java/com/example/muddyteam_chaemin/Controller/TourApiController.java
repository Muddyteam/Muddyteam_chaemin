package com.example.muddyteam_chaemin.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TourApiController {

    @Value("${service.key:NOT_FOUND}")
    private String serviceKey;

    private final RestTemplate restTemplate;

    @Autowired
    public TourApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/usemuds")
    public List<String> getUsemuds() {
        List<String> mudflatNames = new ArrayList<>();

        try {
            if ("NOT_FOUND".equals(serviceKey)) {
                throw new RuntimeException("Service key not found in environment variables!");
            }

            System.out.println("Using API Service Key: " + serviceKey);

            String keyword = "갯벌";
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);

            String url = "https://apis.data.go.kr/B551011/KorService1/searchKeyword1"
                    + "?numOfRows=20&MobileOS=AND&MobileApp=Muddy"
                    + "&keyword=" + encodedKeyword
                    + "&serviceKey=" + serviceKey;

            System.out.println("Request URL: " + url);

            URI uri = new URI(url);

            RequestEntity<Void> requestEntity = RequestEntity
                    .get(uri)
                    .accept(MediaType.APPLICATION_XML)
                    .header("Content-Type", "application/xml; charset=UTF-8")
                    .build();

            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

            String responseBody = responseEntity.getBody();

            if (responseBody != null && responseBody.contains("<items>")) {
                XmlMapper xmlMapper = new XmlMapper();
                try {
                    JsonNode rootNode = xmlMapper.readTree(responseBody.getBytes(StandardCharsets.UTF_8));
                    JsonNode items = rootNode.path("body").path("items").path("item");

                    if (items.isArray()) {
                        for (JsonNode item : items) {
                            String title = item.path("title").asText();
                            mudflatNames.add(title);
                        }
                    } else {
                        System.out.println("No items found.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid response: " + responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mudflatNames;
    }
}
