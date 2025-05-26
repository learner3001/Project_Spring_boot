package com.cts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.cts.entity.Email;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name="NOTIFICATION-SERVICE")
public interface NotificationClient  {
	@PostMapping("/email/emailService")
    ResponseEntity<String> email(@RequestBody Email email);
}
