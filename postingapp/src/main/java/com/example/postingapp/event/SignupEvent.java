package com.example.postingapp.event;

import org.springframework.context.ApplicationEvent;

import com.example.postingapp.entity.User;

import lombok.Getter;

@Getter
//ApplicationEventクラスを継承。イベントのsourceを持つ。
public class SignupEvent extends ApplicationEvent{
	private User user;
	private String requestUrl;
	//会員の情報とリクエストを受けたURLを保持している。
	public SignupEvent (Object source, User user,String requestUrl) {
		super(source);
		this.user = user;
		this.requestUrl = requestUrl;
	}

}
