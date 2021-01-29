package com.example.myapplication.config;

public class EnableEvent {

	public static final String NEW_USER_EVENT = "new_user";

	public static final String PAY_INITIAL_EVENT = "pay_initial";

	public static final String PAY_SUCCESS_EVENT = "pay_success";

	public static final String CANCEL_FREE_TRIAL_EVENT = "cancel_free_trial";

	public static final String CANCEL_SUBSCRIPTION_EVENT = "cancel_subscription";

	public static final String[] ALL_CANCEL_EVENT = {CANCEL_FREE_TRIAL_EVENT, CANCEL_SUBSCRIPTION_EVENT};

	public static final String[] ALL_PAY_EVENT = {PAY_INITIAL_EVENT, PAY_SUCCESS_EVENT};
}
