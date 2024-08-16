package com.example.muddyteam_chaemin.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService {

    private final HuggingFaceService huggingFaceService;

    public ScheduledTaskService(HuggingFaceService huggingFaceService) {
        this.huggingFaceService = huggingFaceService;
    }

    // 매주 일요일 자정 (주기: 일주일 단위로 호출)
    @Scheduled(cron = "0 0 0 * * 0")  // 크론 표현식: 매주 일요일 00:00:00
    public void fetchDataWeekly() {
        huggingFaceService.getChatbotResponse("Hello").subscribe(response -> {
            System.out.println("Response from API: " + response);
        });
    }
}
