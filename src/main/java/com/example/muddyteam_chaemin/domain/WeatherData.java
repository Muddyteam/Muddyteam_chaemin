package com.example.muddyteam_chaemin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
//엔티티
@Entity
@Getter
@Setter
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String regId;  // 지역 ID
    private String tmSt;   // 예보 시작 시간
    private String tmEd;   // 예보 종료 시간
    private String regSp;  // 지역 코드
    private String regName;  // 지역 이름
    private LocalDateTime createdAt;  // 데이터가 저장된 시간

    @Version
    private int version;
    public WeatherData() {
        this.createdAt = LocalDateTime.now();  // 생성 시점 자동 설정
    }
}
