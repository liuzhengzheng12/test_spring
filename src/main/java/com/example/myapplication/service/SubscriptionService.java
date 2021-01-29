package com.example.myapplication.service;

public interface SubscriptionService {

	public void register(String muid, String bundleId, Integer versionCode, String testFlag);

	public void pay(String muid, String bundleId, Integer versionCode, String testFlag, String event, String position);

	public void cancel(String muid, String bundleId, Integer versionCode, String testFlag, String event);
}
