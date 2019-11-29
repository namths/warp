package net.cafecoder.warp.web.controller;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import net.cafecoder.warp.service.WarpService;
import net.cafecoder.warp.web.dto.WarpDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WarpController {

  private static final Logger LOGGER = LoggerFactory.getLogger(WarpController.class);
  private final WarpService warpService;

  public WarpController(WarpService warpService) {
    this.warpService = warpService;
  }

  @PostMapping("/plus")
  public ResponseEntity<String> plusWarp(@RequestBody WarpDTO warpDTO, HttpServletRequest request) {
    if (StringUtils.isEmpty(warpDTO.getReferrer())
        || Objects.isNull(warpDTO.getNumber()) || warpDTO.getNumber() > 1000) {
      return ResponseEntity.badRequest().body("referrer and number not empty, number < 1000GB");
    }
    new Thread(() -> {
      try {
        for (int i = 0; i < warpDTO.getNumber(); i++) {
          LOGGER.info("====cafecoder.net plusWarp = [{}]GB====", i + 1);
          new Thread(() -> warpService.plusOneGBWarp(warpDTO.getReferrer())).start();
          Thread.sleep(5 * 1000);
        }
      } catch (Exception ignored) {}
    }).start();
    return ResponseEntity.badRequest().body("Success ~ contact: namth.dev@gmail.com | +84703.113.789");
  }
}