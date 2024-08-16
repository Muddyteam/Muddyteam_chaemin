package com.example.muddyteam_chaemin.Controller;

import com.example.muddyteam_chaemin.Service.HuggingFaceService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")  // 모든 출처에서 요청 허용
public class ChatbotController {

    private final HuggingFaceService huggingFaceService;

    public ChatbotController(HuggingFaceService huggingFaceService) {
        this.huggingFaceService = huggingFaceService;
    }

    // 질문을 JSON으로 받기 위한 DTO 클래스 사용
    public static class ChatbotRequest {
        private String question;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }
    }

    @PostMapping("/ask")
    public Mono<String> askQuestion(@RequestBody ChatbotRequest request) {
        // 입력 받은 question에 대해 간단한 검증 추가
        if (request.getQuestion() == null || request.getQuestion().isEmpty()) {
            return Mono.just("Please enter a valid question.");
        }
        // 입력 받은 question을 서비스로 전달
        return huggingFaceService.getChatbotResponse(request.getQuestion());
    }
}
