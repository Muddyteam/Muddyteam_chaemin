package com.example.muddyteam_chaemin.Service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class HuggingFaceService {

    private final WebClient webClient;

    public HuggingFaceService(WebClient.Builder webClientBuilder) {
        // WebClient 인스턴스 생성 (기본 URL과 관련 설정 추가 가능)
        this.webClient = webClientBuilder.baseUrl("https://api-inference.huggingface.co").build();
    }

    public Mono<String> getChatbotResponse(String question) {
        // JSON 구조로 inputs 및 parameters 설정 (문자열 처리 오류 수정)
        String requestBody = "{ \"inputs\": \"" + question.replace("\"", "\\\"") + "\", " +
                "\"parameters\": { \"max_length\": 20, \"num_return_sequences\": 1, " +
                "\"temperature\": 0.5, \"top_k\": 50, \"top_p\": 0.9, \"repetition_penalty\": 1.2 } }";


        return webClient.post()
                .uri("/models/gpt2") // 실제 Hugging Face API 엔드포인트
                .header("Authorization", "Bearer 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")  // 실제 API 키 사용
                .contentType(MediaType.APPLICATION_JSON)  // Content-Type 헤더 설정
                .bodyValue(requestBody)  // JSON 포맷으로 요청 본문 설정
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))  // 타임아웃 설정
                .onErrorResume(e -> Mono.just("Error occurred: " + e.getMessage()));  // 예외 처리
    }


}
