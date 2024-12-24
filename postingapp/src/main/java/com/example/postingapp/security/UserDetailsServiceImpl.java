package com.example.postingapp.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.postingapp.entity.User;
import com.example.postingapp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	//DI注入、usertableを使うためのレポジトリの使用のため
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	 @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			//送信されたユーザー名と一致するユーザー情報をテーブルから取得
		try {
			User user = userRepository.findByEmail(email);
			String userRoleName = user.getRole().getName();
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(userRoleName));
			return new UserDetailsImpl(user,authorities);
		}catch(Exception e) {
			throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
		}
	}
}

