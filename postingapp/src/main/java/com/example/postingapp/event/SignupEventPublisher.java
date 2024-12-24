package com.example.postingapp.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.postingapp.entity.User;

@Component
//Publisherクラスはイベントを発生させたい処理の中で呼び出して使う。
//コンポーネントつけてDIに登録
public class SignupEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;
	//DI
	public SignupEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	//イベントを起こしたいときにこれを呼ぶ
	public void publishSignupEvent(User user, String requestUrl){
		applicationEventPublisher.publishEvent(new SignupEvent(this,user,requestUrl));
	}
}
