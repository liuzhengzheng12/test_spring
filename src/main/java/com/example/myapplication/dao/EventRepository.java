package com.example.myapplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.myapplication.domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	@Query(value = "select * from stat_iap_event where (bundle_id = :bundle_id and version_code = :version_code and test_flag = :test_flag and create_time >= :from_date and create_time <= :to_date and (event = 'new_user' or event = 'pay_success'))", nativeQuery = true)
	List<Event> findEvent(@Param("bundle_id") String bundleId, @Param("version_code") Integer versionCode,
			@Param("test_flag") String testFlag, @Param("from_date") String fromDate, @Param("to_date") String toDate);

}
