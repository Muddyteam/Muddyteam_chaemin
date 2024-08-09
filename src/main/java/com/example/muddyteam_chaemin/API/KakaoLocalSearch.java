package com.example.muddyteam_chaemin.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class KakaoLocalSearch {
    private static final String API_KEY = "e31961d649db20096b9c38a445e3d04d"; // 카카오 REST API 키
    private static final String SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";

    public static void main(String[] args) {
        String query = "갯벌 충청";
        try {
            String response = searchKeyword(query);
            parseAndPrintResults(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String searchKeyword(String query) throws Exception {
        String apiUrl = SEARCH_URL + "?query=" + query + "&size=10";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "KakaoAK " + API_KEY);

        int responseCode = conn.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        return response.toString();
    }

    private static void parseAndPrintResults(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray documents = jsonObject.getJSONArray("documents");

        for (int i = 0; i < documents.length(); i++) {
            JSONObject document = documents.getJSONObject(i);
            String placeName = document.getString("place_name");
            String address = document.getString("address_name");
            double latitude = document.getDouble("y");
            double longitude = document.getDouble("x");

            System.out.println("Place: " + placeName);
            System.out.println("Address: " + address);
            System.out.println("Latitude: " + latitude);
            System.out.println("Longitude: " + longitude);
            System.out.println("---------------------------");
        }
    }
}
