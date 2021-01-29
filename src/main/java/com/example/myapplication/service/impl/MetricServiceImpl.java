package com.example.myapplication.service.impl;

import static com.example.myapplication.config.EnableEvent.NEW_USER_EVENT;
import static com.example.myapplication.config.EnableEvent.PAY_SUCCESS_EVENT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapplication.dao.EventRepository;
import com.example.myapplication.dao.UserRepository;
import com.example.myapplication.domain.Event;
import com.example.myapplication.dto.response.BundleInfo;
import com.example.myapplication.dto.response.MetricGetDto;
import com.example.myapplication.dto.response.MetricListDto;
import com.example.myapplication.dto.response.Metrics;
import com.example.myapplication.service.MetricService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MetricServiceImpl implements MetricService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public MetricListDto list_metric() {
		List<String> bundleIdList = userRepository.findDistinctBundleId();
		Metrics metrics = new Metrics();
		for (String bundleId : bundleIdList) {
			List<Integer> versionCodeList = userRepository.findDistinctVersionCode(bundleId);
			BundleInfo bundleInfo = new BundleInfo();
			for (Integer versionCode : versionCodeList) {
				List<String> testFlagList = userRepository.findDistinctTestFlagByBundleIdAndVersionCode(bundleId,
						versionCode);
				bundleInfo.addVersionInfo(versionCode, testFlagList);
			}
			List<String> allTestFlagList = userRepository.findDistinctTestFlagByBundleId(bundleId);
			bundleInfo.addVersionInfo("all", allTestFlagList);
			metrics.add(bundleId, bundleInfo);
		}

		return new MetricListDto(metrics);
	}

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public MetricGetDto get_metric(String bundleId, Integer versionCode, String testFlag, String from, String to) {
		Map<String, List<Integer>> metricsMap = new HashMap<>();
		List<Integer> summaryList = new ArrayList<>();
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dateTimePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime fromDate = LocalDateTime.of(LocalDate.parse(from, datePattern), LocalTime.of(0, 0, 0));
		LocalDateTime toDate = LocalDateTime.of(LocalDate.parse(to, datePattern), LocalTime.of(23, 59, 59));
		List<Event> eventList = eventRepository.findEvent(bundleId, versionCode, testFlag,
				fromDate.format(dateTimePattern), toDate.format(dateTimePattern));
		while (fromDate.compareTo(toDate) <= 0) {
			List<Integer> metricList = new ArrayList<>();
			List<String> muidList = new ArrayList<>();
			for (Event event : eventList) {
				if (event.getEvent().equals(NEW_USER_EVENT) && event.getCreateTime().compareTo(fromDate) >= 0
						&& event.getCreateTime().compareTo(fromDate.plusDays(1)) < 0) {
					muidList.add(event.getMuid());
				}
			}
			metricList.add(muidList.size());
			LocalDateTime startDate = fromDate;
			while (startDate.compareTo(toDate) <= 0) {
				Integer payCount = 0;
				for (String muid : muidList) {
					for (Event event : eventList) {
						if (event.getMuid().equals(muid) && event.getEvent().equals(PAY_SUCCESS_EVENT)
								&& event.getCreateTime().compareTo(startDate) >= 0
								&& event.getCreateTime().compareTo(startDate.plusDays(1)) < 0) {
							payCount++;
						}
					}
				}
				metricList.add(payCount);
				startDate = startDate.plusDays(1);
			}
			metricsMap.put(fromDate.format(datePattern), metricList);
			fromDate = fromDate.plusDays(1);
			int summaryLen = summaryList.size();
			int metricLen = metricList.size();

			for (int i = 0; i < Math.max(summaryLen, metricLen); i++) {
				if (i < summaryLen && i < metricLen)
					summaryList.set(i, summaryList.get(i) + metricList.get(i));
				if (i >= summaryLen)
					summaryList.add(metricList.get(i));
			}
		}
		List<List<Object>> metric = new ArrayList<>();
		for (String date : metricsMap.keySet()) {
			List<Object> dayMetric = new ArrayList<>();
			dayMetric.add(date);
			dayMetric.addAll(metricsMap.get(date));
			metric.add(dayMetric);
		}

		Integer paySum = 0;
		for (int i = 1; i < summaryList.size(); i++) {
			paySum += summaryList.get(i);
		}

		return new MetricGetDto(metric, summaryList, paySum / (double) summaryList.get(0),
				summaryList.get(1) / (double) summaryList.get(0));
	}
}
