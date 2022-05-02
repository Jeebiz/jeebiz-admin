package net.jeebiz.admin.shadow.setup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "common-shared")
public class CommonProperteis {

	/**
	 * 固定密钥
	 */
	@Value("${fixed-secret:common2021}")
	private String fixedSecret;

	/**
	 * 白名单，不需要验签,以逗号分割
	 */
	@Value("${whiteList:''}")
	private String whiteList;

	/**
	 * 是否开启签名，true-开启，false-关闭
	 */
	@Value("${signOpen:true}")
	private boolean signOpen;

	@Value("${registerSwitch:true}")
	public boolean registerSwitch;
	
	// 加密方式
	private String algorithmName = "MD5";
    // 加密的次数,可以进行多次的加密操作
    private int hashIterations = 10;
    // 默认密码
 	private String defaultPassword = "123456";
 	
}
