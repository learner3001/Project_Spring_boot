package com.cts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loggingClient", url = "http://localhost:9096")
public interface LoggingClient {
    @PostMapping("/logging/post")
    void logMessage(@RequestParam String level,@RequestBody String logMessage);
}

