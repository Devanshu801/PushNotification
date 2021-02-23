package com.craterzone.notification.service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.craterzone.notification.model.PushNotificationRequest;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class FcmService {

	public void sendMessage(Map<String, String> data, PushNotificationRequest request)
			throws InterruptedException, ExecutionException {
		Message message = getPreconfiguredMessageWithData(data, request);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(message);
		String response = sendAndGetResponse(message);

	}

	public void sendMessageWithoutData(PushNotificationRequest request)
			throws InterruptedException, ExecutionException {
		Message message = getPreconfiguredMessageWithoutData(request);
		String response = sendAndGetResponse(message);
	}

	public void sendMessageToToken(PushNotificationRequest request) throws InterruptedException, ExecutionException {
		Message message = getPreconfiguredMessageToToken(request);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(message);
		String respoonse = sendAndGetResponse(message);

	}

	private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
		// TODO Auto-generated method stub
		return getPreconfiguredMessageBuilder(request).setToken(request.getToken()).build();
	}

	private AndroidConfig getAndroidConfig(String topic) {
		return AndroidConfig.builder().setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
				.setPriority(AndroidConfig.Priority.HIGH)
				.setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
						.setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build())
				.build();

	}

	private ApnsConfig getApnsConfig(String topic) {
		return ApnsConfig.builder().setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
	}

	private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request) {
		// TODO Auto-generated method stub
		return getPreconfiguredMessageBuilder(request).setTopic(request.getTopic()).build();
	}

	private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return FirebaseMessaging.getInstance().sendAsync(message).get();
	}

	private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequest request) {
		// TODO Auto-generated method stub
		return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken()).build();
	}

	private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
		AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
		ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
		return Message.builder().setApnsConfig(apnsConfig).setAndroidConfig(androidConfig)
				.setNotification(new Notification(request.getTitle(), request.getMessage()));
	}

}
