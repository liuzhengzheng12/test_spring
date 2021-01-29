package com.example.myapplication.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "stat_iap_event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "int(11) not null auto_increment")
	private Integer id;

	@Column(nullable = false, length = 150)
	private String muid;

	@Column(name = "bundle_id", nullable = false)
	private String bundleId;

	@Column(name = "version_code", nullable = false)
	private Integer versionCode;

	@Column(name = "test_flag", length = 2000)
	private String testFlag;

	@Column(nullable = false, length = 150)
	private String event;

	@Column(length = 150)
	private String position;

	@CreationTimestamp
	@Column(name = "create_time", columnDefinition = "datetime not null")
	private LocalDateTime createTime;

	private Event() {
	}

	public Event(String muid, String bundleId, Integer versionCode, String testFlag, String event, String position,
			LocalDateTime createTime) {
		this.muid = muid;
		this.bundleId = bundleId;
		this.versionCode = versionCode;
		this.testFlag = testFlag;
		this.event = event;
		this.position = position;
		this.createTime = createTime;
	}
}
