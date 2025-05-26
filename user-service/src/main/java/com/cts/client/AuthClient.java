package com.cts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthClient {
   
	@PostMapping("/auth/verify/{email}")
	 String verifyUsercredentials(@PathVariable String email);
}
