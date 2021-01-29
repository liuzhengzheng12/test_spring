package com.example.myapplication.service.impl;

import static com.example.myapplication.config.EnableEvent.NEW_USER_EVENT;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapplication.dao.EventRepository;
import com.example.myapplication.dao.UserRepository;
import com.example.myapplication.domain.Event;
import com.example.myapplication.domain.User;
import com.example.myapplication.domain.UserPK;
import com.example.myapplication.service.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	public Boolean existUser(String muid, String bundleId, Integer versionCode) {
		Optional<User> user = userRepository.findById(new UserPK(muid, bundleId, versionCode));

		return user.isPresent();
	}

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public void register(String muid, String bundleId, Integer versionCode, String testFlag) {
		if (!existUser(muid, bundleId, versionCode)) {
			userRepository.save(new User(new UserPK(muid, bundleId, versionCode), testFlag, null, null));
			eventRepository.save(new Event(muid, bundleId, versionCode, testFlag, NEW_USER_EVENT, null, null));
		}
	}

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public void pay(String muid, String bundleId, Integer versionCode, String testFlag, String event, String position) {
		eventRepository.save(new Event(muid, bundleId, versionCode, testFlag, event, position, null));
	}

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public void cancel(String muid, String bundleId, Integer versionCode, String testFlag, String event) {
		if (existUser(muid, bundleId, versionCode)) {
			eventRepository.save(new Event(muid, bundleId, versionCode, testFlag, event, null, null));
		}
	}
}
