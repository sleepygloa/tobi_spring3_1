package com.pojo.sessionScope;

import javax.xml.ws.Provider;

import org.springframework.beans.factory.annotation.Autowired;

public class LoginService {
	
	@Autowired
	Provider<LoginUser> loginUserProvider;
	
	@Autowired 
	LoginUser loginUser;
	
	public void login(Login login) {
		//로그인처리
		//1 provider 를 통한 loginUser 주입.
//		LoginUser loginUser = loginUserProvider.get();
//		loginUser.setLoginId(loginId);
//		loginUser.setName(name);
//		loginUser.setLoginTime(loginTime);
		
		//2 세션 스코프를 통한 LoginUser 주입.
		//autowired를 사용해도 세션에서만 유지되기때문에 다른 유저간 값이 덮어씌여지지 않는다.
		this.loginUser = loginUserProvider.get();
		this.loginUser.setLoginId(loginId);
		this.loginUser.setName(name);
		this.loginUser.setLoginTime(loginTime);
	}
}
