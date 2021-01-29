package com.example.myapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapplication.common.CommonResult;
import com.example.myapplication.dto.request.CancelRequest;
import com.example.myapplication.dto.request.PayRequest;
import com.example.myapplication.dto.request.RegisterRequest;
import com.example.myapplication.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	@PostMapping("/register")
	public CommonResult register(@Validated @RequestBody RegisterRequest request) {
		subscriptionService.register(request.getMuid(), request.getBundleId(), request.getVersionCode(),
				request.getTestFlag());

		return CommonResult.success();
	}

	@PostMapping("/pay")
	public CommonResult pay(@Validated @RequestBody PayRequest request) {
		subscriptionService.pay(request.getMuid(), request.getBundleId(), request.getVersionCode(),
				request.getTestFlag(), request.getEvent(), request.getPosition());

		return CommonResult.success();
	}

	@PostMapping("/cancel")
	public CommonResult cancel(@Validated @RequestBody CancelRequest request) {
		subscriptionService.cancel(request.getMuid(), request.getBundleId(), request.getVersionCode(),
				request.getTestFlag(), request.getEvent());

		return CommonResult.success();
	}
}
