package com.example.myapplication.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "stat_iap_user")
public class User {

	@EmbeddedId
	private UserPK userPK;

	@Column(name = "test_flag", length = 2000)
	private String testFlag;

	@CreationTimestamp
	@Column(name = "create_time", columnDefinition = "datetime not null")
	private LocalDateTime createTime;

	@UpdateTimestamp
	@Column(name = "update_time", columnDefinition = "datetime not null")
	private LocalDateTime updateTime;

	private User() {
	}
}
