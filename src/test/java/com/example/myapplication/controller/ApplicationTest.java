package com.example.myapplication.controller;

import static com.example.myapplication.config.EnableEvent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.myapplication.controller.utils.TestEventRepository;
import com.example.myapplication.controller.utils.TestUserRepository;
import com.example.myapplication.domain.Event;
import com.example.myapplication.domain.User;
import com.example.myapplication.domain.UserPK;
import com.example.myapplication.dto.request.CancelRequest;
import com.example.myapplication.dto.request.MetricGetRequest;
import com.example.myapplication.dto.request.PayRequest;
import com.example.myapplication.dto.request.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestUserRepository userRepository;

	@Autowired
	private TestEventRepository eventRepository;

	@AfterEach
	public void tearDown() {
		this.userRepository.deleteAllInBatch();
		this.eventRepository.deleteAllInBatch();
	}

	@Test
	public void errorRegisterTest() throws Exception {
		// parameter error
		String muid = "error_muid";
		String bundleId = "error_bundle_id";
		Integer versionCode = 0;
		String testFlag = "error_register";
		ObjectMapper mapper = new ObjectMapper();
		String url = "/api/subscription/register";

		// muid not exist
		RegisterRequest request = new RegisterRequest(null, bundleId, versionCode, testFlag);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The muid must not be null nor empty!\",\"data\":null}"));
		// muid is empty
		request = new RegisterRequest("", bundleId, versionCode, testFlag);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The muid must not be null nor empty!\",\"data\":null}"));
		// bundle_id not exist
		request = new RegisterRequest(muid, null, versionCode, testFlag);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The bundle_id must not be null nor empty!\",\"data\":null}"));
		// bundle_id is empty
		request = new RegisterRequest(muid, "", versionCode, testFlag);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The bundle_id must not be null nor empty!\",\"data\":null}"));
		// version_code not exist
		request = new RegisterRequest(muid, bundleId, null, testFlag);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The version_code must not be null!\",\"data\":null}"));
	}

	@Test
	public void errorPayTest() throws Exception {
		// parameter error
		String muid = "error_muid";
		String bundleId = "error_bundle_id";
		Integer versionCode = 0;
		String testFlag = "error_pay";
		String position = "error_position";
		ObjectMapper mapper = new ObjectMapper();
		String url = "/api/subscription/pay";
		String[] eventList = {PAY_INITIAL_EVENT, PAY_SUCCESS_EVENT};
		for (String event : eventList) {
			// muid not exist
			PayRequest request = new PayRequest(null, bundleId, versionCode, testFlag, event, position);
			this.mockMvc
					.perform(post(url).contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(content().json(
							"{\"code\":400,\"message\":\"ParameterValidException! The muid must not be null nor empty!\",\"data\":null}"));
			// muid is empty
			request = new PayRequest("", bundleId, versionCode, testFlag, event, position);
			this.mockMvc
					.perform(post(url).contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(content().json(
							"{\"code\":400,\"message\":\"ParameterValidException! The muid must not be null nor empty!\",\"data\":null}"));
			// bundle_id not exist
			request = new PayRequest(muid, null, versionCode, testFlag, event, position);
			this.mockMvc
					.perform(post(url).contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(content().json(
							"{\"code\":400,\"message\":\"ParameterValidException! The bundle_id must not be null nor empty!\",\"data\":null}"));
			// bundle_id is empty
			request = new PayRequest(muid, "", versionCode, testFlag, event, position);
			this.mockMvc
					.perform(post(url).contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(content().json(
							"{\"code\":400,\"message\":\"ParameterValidException! The bundle_id must not be null nor empty!\",\"data\":null}"));
			// version_code not exist
			request = new PayRequest(muid, bundleId, null, testFlag, event, position);
			this.mockMvc
					.perform(post(url).contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(content().json(
							"{\"code\":400,\"message\":\"ParameterValidException! The version_code must not be null!\",\"data\":null}"));
		}
		// event not exist
		PayRequest request = new PayRequest(muid, bundleId, versionCode, testFlag, null, position);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The event must be pay_initial or pay_success!\",\"data\":null}"));
		// event is empty
		request = new PayRequest(muid, bundleId, versionCode, testFlag, "", position);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The event must be pay_initial or pay_success!\",\"data\":null}"));
		// event is error
		request = new PayRequest(muid, bundleId, versionCode, testFlag, "error_event", position);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The event must be pay_initial or pay_success!\",\"data\":null}"));
	}

	@Test
	public void errorCancelTest() throws Exception {
		// parameter error
		String muid = "error_muid";
		String bundleId = "error_bundle_id";
		Integer versionCode = 0;
		String testFlag = "error_cancel";
		ObjectMapper mapper = new ObjectMapper();
		String url = "/api/subscription/cancel";
		String[] eventList = {CANCEL_FREE_TRIAL_EVENT, CANCEL_SUBSCRIPTION_EVENT};
		for (String event : eventList) {
			// muid not exist
			CancelRequest request = new CancelRequest(null, bundleId, versionCode, testFlag, event);
			this.mockMvc
					.perform(post(url).contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(content().json(
							"{\"code\":400,\"message\":\"ParameterValidException! The muid must not be null nor empty!\",\"data\":null}"));
			// muid is empty
			request = new CancelRequest("", bundleId, versionCode, testFlag, event);
			this.mockMvc
					.perform(post(url).contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(content().json(
							"{\"code\":400,\"message\":\"ParameterValidException! The muid must not be null nor empty!\",\"data\":null}"));
			// bundle_id not exist
			request = new CancelRequest(muid, null, versionCode, testFlag, event);
			this.mockMvc
					.perform(post(url).contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(content().json(
							"{\"code\":400,\"message\":\"ParameterValidException! The bundle_id must not be null nor empty!\",\"data\":null}"));
			// bundle_id is empty
			request = new CancelRequest(muid, "", versionCode, testFlag, event);
			this.mockMvc
					.perform(post(url).contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(content().json(
							"{\"code\":400,\"message\":\"ParameterValidException! The bundle_id must not be null nor empty!\",\"data\":null}"));
			// version_code not exist
			request = new CancelRequest(muid, bundleId, null, testFlag, event);
			this.mockMvc
					.perform(post(url).contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(content().json(
							"{\"code\":400,\"message\":\"ParameterValidException! The version_code must not be null!\",\"data\":null}"));
		}
		// event not exist
		CancelRequest request = new CancelRequest(muid, bundleId, versionCode, testFlag, null);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The event must be cancel_free_trial or cancel_subscription!\",\"data\":null}"));
		// event is empty
		request = new CancelRequest(muid, bundleId, versionCode, testFlag, "");
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The event must be cancel_free_trial or cancel_subscription!\",\"data\":null}"));
		// event is error
		request = new CancelRequest(muid, bundleId, versionCode, testFlag, "error_event");
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The event must be cancel_free_trial or cancel_subscription!\",\"data\":null}"));
	}

	@Test
	public void errorGetMetricTest() throws Exception {
		String bundleId = "bundle_id_0";
		Integer versionCode = 0;
		String testFlag = "norm_register";
		String fromDate = "2018-10-19";
		String toDate = "2018-10-20";
		String url = "/api/iap/metric/get";
		ObjectMapper mapper = new ObjectMapper();
		// bundle_id not exist
		MetricGetRequest request = new MetricGetRequest(null, versionCode, testFlag, fromDate, toDate);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The bundle_id must not be null nor empty!\",\"data\":null}"));
		// bundle_id is empty
		request = new MetricGetRequest("", versionCode, testFlag, fromDate, toDate);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The bundle_id must not be null nor empty!\",\"data\":null}"));
		// version_code not exist
		request = new MetricGetRequest(bundleId, null, testFlag, fromDate, toDate);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The version_code must not be null!\",\"data\":null}"));
		// test_flag not exist
		request = new MetricGetRequest(bundleId, versionCode, null, fromDate, toDate);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The test_flag must not be null nor empty!\",\"data\":null}"));
		// test_flag is empty
		request = new MetricGetRequest(bundleId, versionCode, "", fromDate, toDate);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The test_flag must not be null nor empty!\",\"data\":null}"));
		// from date error
		request = new MetricGetRequest(bundleId, versionCode, testFlag, "2021-01", toDate);
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The date string must be format of yyyy-MM-dd!\",\"data\":null}"));
		// to date error
		request = new MetricGetRequest(bundleId, versionCode, testFlag, fromDate, "1234");
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":400,\"message\":\"ParameterValidException! The date string must be format of yyyy-MM-dd!\",\"data\":null}"));
	}

	@Test
	public void normalRegisterTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String url = "/api/subscription/register";
		for (int i = 0; i < 10; i++) {
			String muid = String.format("muid_%s", i);
			for (int j = 0; j < 10; j++) {
				String bundleId = String.format("bundle_id_%s", j);
				// test_flag param exist
				for (int k = 0; k < 10; k++) {
					Integer versionCode = k;
					String testFlag = "norm_register";
					RegisterRequest request = new RegisterRequest(muid, bundleId, versionCode, testFlag);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(request)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					Optional<User> user = userRepository.findById(new UserPK(muid, bundleId, versionCode));
					List<Event> eventList = eventRepository.findByMuidAndBundleIdAndVersionCodeAndTestFlag(muid,
							bundleId, versionCode, testFlag);
					assertThat(user.isPresent()).isTrue();
					assertThat(user.get().getTestFlag()).isEqualTo(testFlag);
					assertThat(eventList.size()).isEqualTo(1);
					assertThat(eventList.get(0).getEvent()).isEqualTo(NEW_USER_EVENT);
					// repeat reigster, ignore
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(request)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					Optional<User> repeatUser = userRepository.findById(new UserPK(muid, bundleId, versionCode));
					List<Event> repeatEventList = eventRepository.findByMuidAndBundleIdAndVersionCodeAndTestFlag(muid,
							bundleId, versionCode, testFlag);
					assertThat(repeatUser.isPresent()).isTrue();
					assertThat(repeatUser.get().getTestFlag()).isEqualTo(testFlag);
					assertThat(repeatEventList.size()).isEqualTo(1);
					assertThat(repeatEventList.get(0).getEvent()).isEqualTo(NEW_USER_EVENT);
				}
				// test_flag param nonexist
				for (int k = 11; k < 20; k++) {
					Integer versionCode = k;
					RegisterRequest request = new RegisterRequest(muid, bundleId, versionCode, null);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(request)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					Optional<User> user = userRepository.findById(new UserPK(muid, bundleId, versionCode));
					List<Event> eventList = eventRepository.findByMuidAndBundleIdAndVersionCodeAndTestFlag(muid,
							bundleId, versionCode, null);
					assertThat(user.isPresent()).isTrue();
					assertThat(user.get().getTestFlag()).isNull();
					assertThat(eventList.size()).isEqualTo(1);
					assertThat(eventList.get(0).getEvent()).isEqualTo(NEW_USER_EVENT);
				}
			}
		}
	}

	@Test
	public void normalPayTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String url = "/api/subscription/pay";
		for (int i = 0; i < 10; i++) {
			String muid = String.format("muid_%s", i);
			for (int j = 0; j < 10; j++) {
				String bundleId = String.format("bundle_id_%s", j);
				// test_flag and position param both exist
				String testFlag = "norm_pay_initial";
				String position = "a";
				for (int k = 0; k < 5; k++) {
					Integer versionCode = k;
					// pay_initial event
					PayRequest payInitialRequest = new PayRequest(muid, bundleId, versionCode, testFlag,
							PAY_INITIAL_EVENT, position);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(payInitialRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> payInitialEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, testFlag, position, PAY_INITIAL_EVENT);
					assertThat(payInitialEventList.size()).isEqualTo(1);
					// pay_success event
					PayRequest paySuccessRequest = new PayRequest(muid, bundleId, versionCode, testFlag,
							PAY_SUCCESS_EVENT, position);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(paySuccessRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> paySuccessEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, testFlag, position, PAY_SUCCESS_EVENT);
					assertThat(paySuccessEventList.size()).isEqualTo(1);
				}
				// test_flag param exist and position parm not exist
				for (int k = 5; k < 10; k++) {
					Integer versionCode = k;
					// pay_initial event
					PayRequest payInitialRequest = new PayRequest(muid, bundleId, versionCode, testFlag,
							PAY_INITIAL_EVENT, null);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(payInitialRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> payInitialEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, testFlag, null, PAY_INITIAL_EVENT);
					assertThat(payInitialEventList.size()).isEqualTo(1);
					// pay_success event
					PayRequest paySuccessRequest = new PayRequest(muid, bundleId, versionCode, testFlag,
							PAY_SUCCESS_EVENT, null);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(paySuccessRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> paySuccessEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, testFlag, null, PAY_SUCCESS_EVENT);
					assertThat(paySuccessEventList.size()).isEqualTo(1);
				}
				// test_flag param not exist and position parm exist
				for (int k = 10; k < 15; k++) {
					Integer versionCode = k;
					// pay_initial event
					PayRequest payInitialRequest = new PayRequest(muid, bundleId, versionCode, null, PAY_INITIAL_EVENT,
							position);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(payInitialRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> payInitialEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, null, position, PAY_INITIAL_EVENT);
					assertThat(payInitialEventList.size()).isEqualTo(1);
					// pay_success event
					PayRequest paySuccessRequest = new PayRequest(muid, bundleId, versionCode, null, PAY_SUCCESS_EVENT,
							position);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(paySuccessRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> paySuccessEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, null, position, PAY_SUCCESS_EVENT);
					assertThat(paySuccessEventList.size()).isEqualTo(1);
				}
				// test_flag param not exist and position parm not exist
				for (int k = 15; k < 20; k++) {
					Integer versionCode = k;
					// pay_initial event
					PayRequest payInitialRequest = new PayRequest(muid, bundleId, versionCode, null, PAY_INITIAL_EVENT,
							null);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(payInitialRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> payInitialEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, null, null, PAY_INITIAL_EVENT);
					assertThat(payInitialEventList.size()).isEqualTo(1);
					// pay_success event
					PayRequest paySuccessRequest = new PayRequest(muid, bundleId, versionCode, null, PAY_SUCCESS_EVENT,
							null);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(paySuccessRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> paySuccessEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, null, null, PAY_SUCCESS_EVENT);
					assertThat(paySuccessEventList.size()).isEqualTo(1);
				}
			}
		}
	}

	@Test
	public void normalCancelTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String url = "/api/subscription/cancel";
		for (int i = 0; i < 10; i++) {
			String muid = String.format("muid_%s", i);
			for (int j = 0; j < 10; j++) {
				String bundleId = String.format("bundle_id_%s", j);
				// test_flag param exist
				String testFlag = "norm_cancel";
				for (int k = 0; k < 10; k++) {
					Integer versionCode = k;
					// register
					userRepository.save(new User(new UserPK(muid, bundleId, versionCode), testFlag, null, null));
					// cancel_free_trial event
					CancelRequest cancelFreeTrialRequest = new CancelRequest(muid, bundleId, versionCode, testFlag,
							CANCEL_FREE_TRIAL_EVENT);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(cancelFreeTrialRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> cancelFreeTrialEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, testFlag, null, CANCEL_FREE_TRIAL_EVENT);
					assertThat(cancelFreeTrialEventList.size()).isEqualTo(1);
					// cancel_subscription event
					CancelRequest cancelSubscriptionRequest = new CancelRequest(muid, bundleId, versionCode, testFlag,
							CANCEL_SUBSCRIPTION_EVENT);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(cancelSubscriptionRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> cancelSubscriptionEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, testFlag, null, CANCEL_SUBSCRIPTION_EVENT);
					assertThat(cancelSubscriptionEventList.size()).isEqualTo(1);
				}
				// test_flag param not exist
				for (int k = 10; k < 20; k++) {
					Integer versionCode = k;
					// register
					userRepository.save(new User(new UserPK(muid, bundleId, versionCode), null, null, null));
					// cancel_free_trial event
					CancelRequest cancelFreeTrialRequest = new CancelRequest(muid, bundleId, versionCode, null,
							CANCEL_FREE_TRIAL_EVENT);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(cancelFreeTrialRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> cancelFreeTrialEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, null, null, CANCEL_FREE_TRIAL_EVENT);
					assertThat(cancelFreeTrialEventList.size()).isEqualTo(1);
					// cancel_subscription event
					CancelRequest cancelSubscriptionRequest = new CancelRequest(muid, bundleId, versionCode, null,
							CANCEL_SUBSCRIPTION_EVENT);
					this.mockMvc
							.perform(post(url).contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(cancelSubscriptionRequest)))
							.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json("{\"code\":200,\"message\":\"操作成功\",\"data\":null}"));
					List<Event> cancelSubscriptionEventList = eventRepository
							.findByMuidAndBundleIdAndVersionCodeAndTestFlagAndPositionAndEvent(muid, bundleId,
									versionCode, null, null, CANCEL_SUBSCRIPTION_EVENT);
					assertThat(cancelSubscriptionEventList.size()).isEqualTo(1);
				}
			}
		}
	}

	@Test
	public void normalListMetricTest() throws Exception {
		// init test data
		String insertTime1 = "2021-01-27 16:34:33";
		String insertTime2 = "2021-01-28 15:20:15";
		String bundleId = "bundle_id_1";
		Integer versionCode = 1;
		String testFlag = "test_flag_1";
		String position = "abc";

		for (int i = 1; i <= 3; i++) {
			String muid = String.format("muid_%s", i);
			userRepository.insert(muid, bundleId, versionCode, testFlag, insertTime1, insertTime1);
			eventRepository.insert(muid, bundleId, versionCode, testFlag, NEW_USER_EVENT, null, insertTime1);
		}
		for (int i = 4; i <= 5; i++) {
			String muid = String.format("muid_%s", i);
			userRepository.insert(muid, bundleId, versionCode, testFlag, insertTime2, insertTime2);
			eventRepository.insert(muid, bundleId, versionCode, testFlag, NEW_USER_EVENT, null, insertTime2);
		}
		eventRepository.insert("muid_1", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime1);
		eventRepository.insert("muid_1", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime2);
		eventRepository.insert("muid_2", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime1);
		eventRepository.insert("muid_3", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime1);
		eventRepository.insert("muid_4", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime2);
		eventRepository.insert("muid_5", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime2);
		// test
		String url = "/api/iap/metric/list";
		this.mockMvc.perform(get(url)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json(
						"{\"code\":200,\"message\":\"操作成功\", \"data\": {\"metrics\": {\"bundle_id_1\": {\"all\": [\"test_flag_1\"], \"1\": [\"test_flag_1\"]}}}}"));
	}

	@Test
	public void normalGetMetricTest() throws Exception {
		// init test data
		String insertTime1 = "2021-01-27 16:34:33";
		String insertTime2 = "2021-01-28 15:20:15";
		String bundleId = "bundle_id_1";
		Integer versionCode = 1;
		String testFlag = "test_flag_1";
		String position = "abc";

		for (int i = 1; i <= 3; i++) {
			String muid = String.format("muid_%s", i);
			userRepository.insert(muid, bundleId, versionCode, testFlag, insertTime1, insertTime1);
			eventRepository.insert(muid, bundleId, versionCode, testFlag, NEW_USER_EVENT, null, insertTime1);
		}
		for (int i = 4; i <= 5; i++) {
			String muid = String.format("muid_%s", i);
			userRepository.insert(muid, bundleId, versionCode, testFlag, insertTime2, insertTime2);
			eventRepository.insert(muid, bundleId, versionCode, testFlag, NEW_USER_EVENT, null, insertTime2);
		}
		eventRepository.insert("muid_1", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime1);
		eventRepository.insert("muid_1", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime2);
		eventRepository.insert("muid_2", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime1);
		eventRepository.insert("muid_3", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime1);
		eventRepository.insert("muid_4", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime2);
		eventRepository.insert("muid_5", bundleId, versionCode, testFlag, PAY_SUCCESS_EVENT, position, insertTime2);
		// test
		ObjectMapper mapper = new ObjectMapper();
		String url = "/api/iap/metric/get";
		MetricGetRequest request = new MetricGetRequest(bundleId, versionCode, testFlag, "2021-01-27", "2021-01-28");
		this.mockMvc
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"{\"code\":200,\"message\":\"操作成功\",\"data\":{\"metric\":[[\"2021-01-28\",2,2],[\"2021-01-27\",3,3,1]],\"summary\":[5,5,1],\"pay_ratio\":1.2,\"pay_ratio_first_day\":1.0}}"));
	}
}
