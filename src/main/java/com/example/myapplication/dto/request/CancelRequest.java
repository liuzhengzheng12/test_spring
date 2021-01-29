package com.example.myapplication.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.myapplication.validator.CancelEvent;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CancelRequest {

	@NotEmpty(message = "The muid must not be null nor empty!")
	private String muid;

	@NotEmpty(message = "The bundle_id must not be null nor empty!")
	@JsonProperty(value = "bundle_id")
	private String bundleId;

	@NotNull(message = "The version_code must not be null!")
	@JsonProperty(value = "version_code")
	private Integer versionCode;

	@JsonProperty(value = "test_flag")
	private String testFlag;

	@CancelEvent
	private String event;
}
