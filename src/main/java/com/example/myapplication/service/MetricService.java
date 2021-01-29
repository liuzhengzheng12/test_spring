package com.example.myapplication.service;

import com.example.myapplication.dto.response.MetricGetDto;
import com.example.myapplication.dto.response.MetricListDto;

public interface MetricService {

	public MetricListDto list_metric();

	public MetricGetDto get_metric(String bundleId, Integer versionCode, String testFlag, String from, String to);
}
