package com.example.myapplication.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.myapplication.common.CommonResult;
import com.example.myapplication.dto.request.MetricGetRequest;
import com.example.myapplication.dto.response.MetricGetDto;
import com.example.myapplication.dto.response.MetricListDto;
import com.example.myapplication.service.MetricService;

@RestController
@RequestMapping("/api/iap/metric")
public class MetricController {

	@Autowired
	private MetricService metricService;

	@GetMapping("/list")
	public CommonResult<MetricListDto> list_metric() {
		MetricListDto metricListDto = metricService.list_metric();

		return CommonResult.success(metricListDto);
	}

	@PostMapping("/get")
	public CommonResult<MetricGetDto> get_metric(@Valid @RequestBody MetricGetRequest request) {
		MetricGetDto metricGetDto = metricService.get_metric(request.getBundleId(), request.getVersionCode(),
				request.getTestFlag(), request.getFrom(), request.getTo());

		return CommonResult.success(metricGetDto);
	}
}
