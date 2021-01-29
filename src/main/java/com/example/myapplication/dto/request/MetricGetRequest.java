package com.example.myapplication.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.myapplication.validator.DateTimeString;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetricGetRequest {

	@NotEmpty(message = "The bundle_id must not be null nor empty!")
	@JsonProperty(value = "bundle_id")
	private String bundleId;

	@NotNull(message = "The version_code must not be null!")
	@JsonProperty(value = "version_code")
	private Integer versionCode;

	@NotEmpty(message = "The test_flag must not be null nor empty!")
	@JsonProperty(value = "test_flag")
	private String testFlag;

	@DateTimeString
	private String from;

	@DateTimeString
	private String to;
}
