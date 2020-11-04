/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.service;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

public interface IRemoteService {

	List<User> process() throws TimeoutException, InterruptedException;
	
}
