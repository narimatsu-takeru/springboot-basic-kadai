package com.example.postingapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.postingapp.entity.User;
import com.example.postingapp.entity.VerificationToken;
import com.example.postingapp.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService {
	private VerificationTokenRepository verificationTokenRepository;
	//repositoryをDIする
	public VerificationTokenService (VerificationTokenRepository verificationTokenRepository) {
		this.verificationTokenRepository=verificationTokenRepository;
	}
	
	@Transactional
	//メソッドをトランザクション化シ、処理によってデータベースの変更が保存か破棄される
	public void create(User user, String token) {
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setUser(user);
		verificationToken.setToken(token);
		verificationTokenRepository.save(verificationToken);
	}
	//トークンの文字列で検索した結果を返す
	public VerificationToken getVerificationToken(String token) {
		return verificationTokenRepository.findByToken(token);
	}
}
