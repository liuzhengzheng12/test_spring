package com.example.myapplication.controller.utils;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.myapplication.dao.UserRepository;

public interface TestUserRepository extends UserRepository {

	@Modifying
	@Transactional
	@Query(value = "insert into stat_iap_user(muid, bundle_id, version_code, test_flag, create_time, update_time) values (:muid, :bundle_id, :version_code, :test_flag, :create_time, :update_time)", nativeQuery = true)
	void insert(@Param("muid") String muid, @Param("bundle_id") String bundleId,
			@Param("version_code") Integer versionCode, @Param("test_flag") String testFlag,
			@Param("create_time") String createTime, @Param("update_time") String updateTime);
}
