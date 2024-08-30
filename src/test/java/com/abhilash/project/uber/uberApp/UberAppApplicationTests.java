package com.abhilash.project.uber.uberApp;

import com.abhilash.project.uber.uberApp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberAppApplicationTests {

	@Autowired
	EmailSenderService emailSenderService;

	@Test
	void contextLoads() {
		String emails[]={"v.s.n.m.abhilash@gmail.com"};
		emailSenderService.sendEmail(emails,"Hello from Uber clone","Whats up!!!!!!!!!!");
	}

}
