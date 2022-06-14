/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.config.setup;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Constants {
	
	public static Marker authzLogMarker = MarkerFactory.getMarker("Authz-Log");
	public static Marker bizLogMarker = MarkerFactory.getMarker("Biz-Log");
	public static Marker bizExcpMarker = MarkerFactory.getMarker("Biz-Excp");
	public static final String EXTRAS_MYAPP = "Extras-MyApp";

	public static class Normal {
		public static final Integer IS_DELETE_0 = 0; //未删除
		public static final Integer IS_DELETE_1 = 1; //已删除

		public static final Integer IS_DEFAULT_NO = 0; //非默认
		public static final Integer IS_DEFAULT_YES = 1; //是默认
	}

}
