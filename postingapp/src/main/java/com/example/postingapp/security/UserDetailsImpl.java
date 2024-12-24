package com.example.postingapp.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.postingapp.entity.User;

public class UserDetailsImpl implements UserDetails {
	//Userテーブルとロールを持ってくる
	private final User user;
	private final Collection<GrantedAuthority> authorities;
	
	//初期化する
	public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}
	
	//ログイン中のユーザー情報を取得する。
	public User getUser() {
		return user;
	}
	
	//ハッシュ済みパスワードを返す
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	//ユーザーネームを渡す
	@Override
	public String getUsername() {
		return user.getEmail();
	}
	
	//ロールのリスト渡す
	@Override
	//<>の中身はGrantedAuthorityまたはそのサブタイプすべて
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return authorities;
	}
	
	//ユーザーが期限切れでなければtrue
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//ユーザーがロックされていなければtrue
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//ユーザーのパスワードが期限切れていなければtrue
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	//ユーザーが有効ならtrue
	@Override
	public boolean isEnabled() {
		return user.getEnabled();
	}
	
	

}
