/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformRecordRoute {

	/**
	 * 路由名称
	 */
	private String name;
	/**
	 * 路由文字（用于替换为可点击连接）
	 */
	private String word;
	/**
	 * 路由跳转地址（H5地址或路由地址）
	 */
	private String link;

}
