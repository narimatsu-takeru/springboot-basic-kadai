package com.example.postingapp.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.postingapp.entity.User;
import com.example.postingapp.service.VerificationTokenService;

@Component
public class SignupEventListener {
	private  VerificationTokenService verificationTokenService;
	private JavaMailSender javaMailSender;
	
	public SignupEventListener(VerificationTokenService verificationTokenService,JavaMailSender javaMailSender){
		this.verificationTokenService = verificationTokenService;
		this.javaMailSender = javaMailSender;
	}
	
	@EventListener
	//イベント発生を受け取るために、引数にイベントクラス
	private void onSignupEvent(SignupEvent signupEvent) {
		User user= signupEvent.getUser();
		String token = UUID.randomUUID().toString();
		//トークンはユーザーのIDと主にDBに保存
		verificationTokenService.create(user, token);
		
		String senderAddress = "springboot.postingapp@example.com";
        String recipientAddress = user.getEmail();
        String subject = "メール認証";
        //トークンをパラメータとして埋め込み、メールのメッセージとして記載する。
        String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token;
        String message = "以下のリンクをクリックして会員登録を完了してください。";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //送信元をセット
        mailMessage.setFrom(senderAddress);
        //送信先をセット
        mailMessage.setTo(recipientAddress);
        //件名をセット
        mailMessage.setSubject(subject);
        //本文をセット
        mailMessage.setText(message + "\n" + confirmationUrl);
        javaMailSender.send(mailMessage);
	}

}
