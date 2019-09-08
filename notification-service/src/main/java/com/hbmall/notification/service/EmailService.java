package com.hbmall.notification.service;

import com.hbmall.notification.domain.NotificationType;
import com.hbmall.notification.domain.Recipient;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

	void send(NotificationType type, Recipient recipient, String attachment) throws MessagingException, IOException;

}
