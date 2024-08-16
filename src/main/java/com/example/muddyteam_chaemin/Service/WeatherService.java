package com.example.muddyteam_chaemin.Service;

import com.example.muddyteam_chaemin.Repository.WeatherDataRepository;
import com.example.muddyteam_chaemin.domain.WeatherData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherDataRepository weatherDataRepository;

    public WeatherService(RestTemplate restTemplate, WeatherDataRepository weatherDataRepository) {
        this.restTemplate = restTemplate;
        this.weatherDataRepository = weatherDataRepository;
    }

    // API로부터 데이터를 가져오고 DB에 저장하는 메서드
    public void fetchAndSaveWeatherData() throws Exception {
        String url = "https://apihub.kma.go.kr/api/typ01/url/fct_medm_reg.php?authKey=1wLYAOOWTFKC2ADjlnxSVg&otherParams=value";

        // 응답을 바이트 배열로 받음
        byte[] responseBytes = restTemplate.getForObject(url, byte[].class);

        // 바이트 배열을 EUC-KR로 디코딩
        String response = new String(responseBytes, Charset.forName("EUC-KR"));

        // 데이터 파싱 및 필터링 후 저장
        List<WeatherData> weatherDataList = filterWeatherData(parseWeatherData(response));

        // 데이터 DB에 저장
        weatherDataRepository.saveAll(weatherDataList);
    }

    // 데이터를 파싱하는 메서드
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

    // 특정 지역 데이터를 필터링하는 메서드
    private List<WeatherData> filterWeatherData(List<WeatherData> weatherDataList) {
        return weatherDataList.stream()
                .filter(data -> "태안".equals(data.getRegName()) ||
                        "서천".equals(data.getRegName()) ||
                        "보령".equals(data.getRegName()))
                .toList();
    }
}
