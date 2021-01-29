package com.example.myapplication.dto.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class Metrics {

	private Map<String, BundleInfo> bundleInfoMap = new HashMap<>();

	@JsonAnyGetter
	public Map<String, BundleInfo> getBundleInfoMap() {
		return bundleInfoMap;
	}

	@JsonAnySetter
	public void add(String bundleId, BundleInfo bundleInfo) {
		bundleInfoMap.put(bundleId, bundleInfo);
	}
}
