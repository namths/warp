package net.cafecoder.warp.service;

import org.springframework.http.ResponseEntity;

public interface WarpService {
  ResponseEntity<String> plusOneGBWarp(String referrer);
}
