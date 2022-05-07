/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.shadow.service;

import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import io.hiwepy.boot.api.exception.BizCheckedException;

public interface IRemoteService {

	List<User> process() throws BizCheckedException;
	
}
