package net.cafecoder.warp.service.impl;

import com.google.gson.Gson;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.cafecoder.warp.service.WarpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WarpServiceImpl implements WarpService {
  private static final Logger LOGGER = LoggerFactory.getLogger(WarpServiceImpl.class);

  private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final String URL = "https://api.cloudflareclient.com/v0a745/reg";
  private final RestTemplate restTemplate = new RestTemplate();
  private static final int maxRetryCount = 3;
  private Random random = new Random();

  @Override
  public ResponseEntity<String> plusOneGBWarp(String referrer) {
    int retryCount = 0;
    return execWarp(referrer, maxRetryCount, retryCount);
  }

  private ResponseEntity<String> execWarp(String referrer, int maxRetryCount, int retryCount) {
    HttpEntity<Object> requestBody = this.buildHttpEntityWarp(referrer);
    try {
      return restTemplate.postForEntity(URL, requestBody, String.class);
    } catch (Exception e) {
      LOGGER.info("ERROR : {}", e.getMessage());
      if (retryCount < maxRetryCount) {
        retryCount++;
        LOGGER.info("RETRY COUNT : {}", retryCount);
        return execWarp(referrer, maxRetryCount, retryCount);
      } else {
        return ResponseEntity.badRequest().body("");
      }
    }
  }

  private HttpEntity<Object> buildHttpEntityWarp(String referrer) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json;charset=UTF-8");
    headers.set("Host", "api.cloudflareclient.com");
    headers.set("Connection", "Keep-Alive");
    headers.set("Accept-Encoding", "gzip");
    headers.set("User-Agent", "okhttp/3.12.1");

    String installId = this.genString(11);
    Map<String, Object> body = new HashMap<>();
    body.put("key", String.format("%s=", this.genString(42)));
    body.put("install_id", installId);
    body.put("fcm_token", String.format("%s:APA91b%s", installId, this.genString(134)));
    body.put("referrer", referrer);
    body.put("warp_enabled", false);
    body.put("tos", OffsetDateTime.now().toString());
    body.put("type", "Android");
    body.put("locale", "zh-CN");

    return new HttpEntity<>(new Gson().toJson(body), headers);
  }

  private String genString(int length) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      builder.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
    }
    return builder.toString();
  }
}
