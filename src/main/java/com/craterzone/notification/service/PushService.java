package com.craterzone.notification.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craterzone.notification.model.PushNotificationRequest;

@Service
public class PushService {

	@Autowired
	private FcmService fcmService;

	public PushService(FcmService fcmService) {
		this.fcmService = fcmService;
	}

	public void sendPushNotification(PushNotificationRequest request) {
		try {
			fcmService.sendMessage(getSamplePayloadData(), request);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	private Map<String, String> getSamplePayloadData() {
		// TODO Auto-generated method stub
		Map<String, String> pushData = new HashMap<>();
		pushData.put("MessageId", "Msgid");
		pushData.put("Text", "Txt");
		pushData.put("User", "Devanshu Bansal");
		return pushData;
	}

	public void sendPushNotificationToToken(PushNotificationRequest request) {
		try {
			fcmService.sendMessageToToken(request);
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
