import org.apache.shiro.crypto.hash.SimpleHash;

/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */

public class Test1 {

	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = "123456";//密码原值
        Object salt = "MTIzNDU2";//盐值
        int hashIterations = 10;//加密1024次
        String result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations).toBase64();
        System.out.println(result);
	}
	
	// 9Ku3tuMdrZoKuuRc/EoSHA==
	// 9Ku3tuMdrZoKuuRc/EoSHA==
	
}
