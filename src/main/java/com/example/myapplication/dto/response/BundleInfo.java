package com.example.myapplication.dto.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class BundleInfo {

	private Map<Object, List<String>> versionInfoMap = new HashMap<>();

	@JsonAnyGetter
	public Map<Object, List<String>> getVersionInfoMap() {
		return versionInfoMap;
	}

	@JsonAnySetter
	public void addVersionInfo(Object versionCode, List<String> testFlagList) {
		versionInfoMap.put(versionCode, testFlagList);
	}
}
