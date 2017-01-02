package cn.lyj.common.encode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * md5 encode
 * @author Administrator
 *
 */
public class Md5PwdImpl implements Md5Pwd {
	
	//加密，托管给Spring 所以不写成静态的
	public String encode(String password){
		String algorithm = "MD5";
		//TODO 加盐
		MessageDigest instance = null;
		try {
			instance = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] digest = instance.digest(password.getBytes());
		//十六进制加密
		char[] encodeHex = Hex.encodeHex(digest);
		
		return new String(encodeHex);
		
	}

}
