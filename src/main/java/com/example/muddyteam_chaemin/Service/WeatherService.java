package com.example.muddyteam_chaemin.Service;

import com.example.muddyteam_chaemin.model.WeatherData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<WeatherData> getFilteredWeatherData() throws Exception {
        String url = "https://apihub.kma.go.kr/api/typ01/url/fct_medm_reg.php?authKey=1wLYAOOWTFKC2ADjlnxSVg&otherParams=value";

        // 응답을 바이트 배열로 받음
        byte[] responseBytes = restTemplate.getForObject(url, byte[].class);

        // 바이트 배열을 EUC-KR로 디코딩
        String response = new String(responseBytes, Charset.forName("EUC-KR"));

        // 이후 파싱 및 필터링 로직
        List<WeatherData> weatherDataList = parseWeatherData(response);
        return filterWeatherData(weatherDataList);
    }

    private List<WeatherData> parseWeatherData(String response) {
        String[] lines = response.split("\n");
        List<WeatherData> weatherDataList = new ArrayList<>();

        for (String line : lines) {
            // #으로 시작하는 줄은 무시
            if (line.startsWith("#")) {
                continue;
            }

            // 공백을 기준으로 데이터를 분할
            String[] columns = line.trim().split("\\s+");

            if (columns.length >= 5) {
                WeatherData weatherData = new WeatherData();
                weatherData.setRegId(columns[0]);
                weatherData.setTmSt(columns[1]);
                weatherData.setTmEd(columns[2]);
                weatherData.setRegSp(columns[3]);
                weatherData.setRegName(columns[4]);

                weatherDataList.add(weatherData);
            }
        }
        return weatherDataList;
    }

    private List<WeatherData> filterWeatherData(List<WeatherData> weatherDataList) {
        return weatherDataList.stream()
                .filter(data -> "태안".equals(data.getRegName()) ||
                        "서천".equals(data.getRegName()) ||
                        "보령".equals(data.getRegName()))
                .toList();
    }


}
