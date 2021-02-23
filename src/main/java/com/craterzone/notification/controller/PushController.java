package com.craterzone.notification.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.craterzone.notification.model.PushNotificationRequest;
import com.craterzone.notification.model.PushNotificationResponse;
import com.craterzone.notification.service.PushService;

@RestController
@RequestMapping("/api/v1/notification")
public class PushController {

	@Autowired
	private PushService pushService;
	
	private PushController(PushService pushService) {
		this.pushService = pushService;
	}
	
	@PostMapping("/topic")
	public ResponseEntity sendNotification(@RequestBody PushNotificationRequest request) {
		pushService.sendPushNotification(request);
		return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
	}
	@PostMapping("/token")
	public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) {
        pushService.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
	@PostMapping("/data")
	public ResponseEntity sendDataNotification(@RequestBody PushNotificationRequest request) {
        pushService.sendPushNotification(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }   
}
