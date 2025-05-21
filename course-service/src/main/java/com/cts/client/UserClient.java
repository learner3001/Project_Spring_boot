package com.cts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.entity.User;

@FeignClient(name = "user-client", url = "http://localhost:9001")
public interface UserClient {
	@GetMapping("/user/userId/{id}")
	User getUserById(@PathVariable int id);
}
