/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Constants {
	
	public static Marker authzLogMarker = MarkerFactory.getMarker("Authz-Log");
	public static Marker bizLogMarker = MarkerFactory.getMarker("Biz-Log");
	public static Marker bizExcpMarker = MarkerFactory.getMarker("Biz-Excp");
	public static final String EXTRAS_MYAPP = "Extras-MyApp";
	
}
