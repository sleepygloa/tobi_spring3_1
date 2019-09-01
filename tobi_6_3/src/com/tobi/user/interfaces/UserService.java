package com.tobi.user.interfaces;

import com.tobi.user.domain.User;

public interface UserService {
	void add(User user);
	void upgradeLevels();
}
