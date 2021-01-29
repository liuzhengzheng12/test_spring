package com.example.myapplication.controller.utils;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.myapplication.dao.EventRepository;
import com.example.myapplication.domain.Event;

public interface TestEventRepository extends EventRepository {
	List<Event> findByMuidAndBundleIdAndVersionCodeAndTestFlag(String muid, String bundleId, Integer versionCode,
			String testFlag);

	List<Event> findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(String muid, String bundleId,
			Integer versionCode, String testFlag, String position, String event);

	@Modifying
	@Transactional
	@Query(value = "insert into stat_iap_event(muid, bundle_id, version_code, test_flag, event, position, create_time) values (:muid, :bundle_id, :version_code, :test_flag, :event, :position, :create_time)", nativeQuery = true)
	void insert(@Param("muid") String muid, @Param("bundle_id") String bundleId,
			@Param("version_code") Integer versionCode, @Param("test_flag") String testFlag,
			@Param("event") String event, @Param("position") String position, @Param("create_time") String createTime);
}
