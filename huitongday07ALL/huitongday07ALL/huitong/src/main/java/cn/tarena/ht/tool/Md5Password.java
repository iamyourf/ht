package cn.tarena.ht.tool;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Md5Password {
	public static String getMd5HashPassword(String password,String username){
		Md5Hash md5Hash = new Md5Hash(password,username,3);
		return md5Hash.toString();
	}
	
	public static void main(String[] args) {
		String str = getMd5HashPassword("123123123","123132123");
		System.out.println(str.length());
	}
}
