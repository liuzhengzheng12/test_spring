package com.example.myapplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.myapplication.domain.User;
import com.example.myapplication.domain.UserPK;

@Repository
public interface UserRepository extends JpaRepository<User, UserPK> {

	@Query(value = "select distinct bundle_id from stat_iap_user", nativeQuery = true)
	List<String> findDistinctBundleId();

	@Query(value = "select distinct version_code from stat_iap_user where bundle_id = :bundle_id", nativeQuery = true)
	List<Integer> findDistinctVersionCode(@Param("bundle_id") String bundleId);

	@Query(value = "select distinct test_flag from stat_iap_user where bundle_id = :bundle_id and version_code = :version_code", nativeQuery = true)
	List<String> findDistinctTestFlagByBundleIdAndVersionCode(@Param("bundle_id") String bundleId,
			@Param("version_code") Integer version_code);

	@Query(value = "select distinct test_flag from stat_iap_user where bundle_id = :bundle_id", nativeQuery = true)
	List<String> findDistinctTestFlagByBundleId(@Param("bundle_id") String bundleId);
}
