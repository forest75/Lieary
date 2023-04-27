package com.a101.fakediary.deeparteffects.api;

import com.a101.fakediary.deeparteffects.request.DeepArtEffectsUploadRequest;
import com.a101.fakediary.deeparteffects.response.DeepArtEffectsImageUrlResponse;
import com.a101.fakediary.deeparteffects.response.DeepArtEffectsUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeepArtEffectsApi {
    @Value("${fake-diary.deep-art.base-url}")
    private String BASE_URL;

    @Value("${fake-diary.deep-art.api-key}")
    private String API_KEY;

    @Value("${fake-diary.deep-art.access-key}")
    private String ACCESS_KEY;

    @Value("${fake-diary.deep-art.secret-key}")
    private String SECRET_KEY;

    public Mono<String> getDeepArtEffectsStyles() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        headers.set("x-api-access-key", ACCESS_KEY);
        headers.set("x-api-secret-key", SECRET_KEY);

        //  2. api를 통해 style 목록을 얻어온다.
        WebClient client = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(header -> header.addAll(headers))
                .build();

        //  GET request를 보내 style의 목록을 얻어온다.
        Mono<String> response = client.get()
                .uri("/styles")
                .retrieve()
                .bodyToMono(String.class);

        return response;
    }

    /**
     *
     * @param origImageFile : DeepArtEffects에 업로드할 원본 이미지 파일
     * @param styleId : 적용할 스타일 id
     * @return : 업로드 결과로 받아올 submissionId
     * @throws IOException
     */
    public String uploadImageWithStyleId(MultipartFile origImageFile, String styleId) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        headers.set("x-api-access-key", ACCESS_KEY);
        headers.set("x-api-secret-key", SECRET_KEY);

        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(header -> header.addAll(headers))
                .build();

        // MultipartFile을 Base64로 인코딩
        String base64EncodedImage = Base64Utils.encodeToString(origImageFile.getBytes());

        // POST 요청 보낼 body 생성
        DeepArtEffectsUploadRequest request = new DeepArtEffectsUploadRequest(styleId, base64EncodedImage);

        // WebClient로 POST 요청 보내기
        Mono<DeepArtEffectsUploadResponse> response =  webClient.post()
                .uri("/upload")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(DeepArtEffectsUploadResponse.class);

        String submissionId = response.block().getSubmissionId();
        log.info("submissionId = " + submissionId);


        return submissionId;
    }

    /**
     *
     * @param submissionId : 업로드 id
     * @return : 업로드 결과로 생성된 카드 이미지 URL
     */
    public String getCardImageUrl(String submissionId) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        headers.set("x-api-access-key", ACCESS_KEY);
        headers.set("x-api-secret-key", SECRET_KEY);

        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(header -> header.addAll(headers))
                .build();

        Instant start = Instant.now();
        Mono<DeepArtEffectsImageUrlResponse> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/result")
                        .queryParam("submissionId", submissionId)
                        .build())
                .retrieve()
                .bodyToMono(DeepArtEffectsImageUrlResponse.class);


        //  DeepArtEffectsImageUrlResponse res = response.block(Duration.ofMillis(3000));
        Thread.sleep(5000); // 5초간 대기
        DeepArtEffectsImageUrlResponse res = response.block();

        Instant end = Instant.now();
        log.info("status = " + res.getStatus());
        log.info("url = " + res.getUrl());
        log.info("Elapsed time: " + Duration.between(start, end).toMillis() + " ms");

        return res.getUrl();

    }

}