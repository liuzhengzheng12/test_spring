package com.example.myapplication.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class UserPK implements Serializable {

	@Column(nullable = false, length = 150)
	private String muid;

	@Column(name = "bundle_id", nullable = false, length = 150)
	private String bundleId;

	@Column(name = "version_code", nullable = false)
	private Integer versionCode;

	private UserPK() {
	}
}
