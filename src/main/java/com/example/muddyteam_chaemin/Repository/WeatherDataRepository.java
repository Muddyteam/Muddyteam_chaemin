package com.example.muddyteam_chaemin.Repository;

import com.example.muddyteam_chaemin.domain.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
}
