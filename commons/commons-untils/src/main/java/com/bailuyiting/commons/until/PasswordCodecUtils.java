package com.bailuyiting.commons.until;

import org.apache.commons.codec.digest.DigestUtils;


public class PasswordCodecUtils {
	/**
	 * 密码加密
	 * @param pwd
	 * @return
	 */
	public static String encode(String pwd) {
		String pwdm=reverse(pwd) + pwd + reverse(pwd);//密码改了更复杂
		String pwd1 = DigestUtils.md5Hex(pwdm).toUpperCase();//复杂密码md5
		return pwd1;
	}
	public static void main(String[] args) {
		 System.out.println(encode("1234567"));
		/*RSAUtils rsaUtils=new RSAUtils();
		String password="lxP7h5ZQztrZ65QtHF3P9Q7ARRDG6QNSzaBWb4+oMLhCGATHYJLl+e5eoDqyPiy1EtBvWc5M79JwEuep+368+wlKxkNLq0VUO6p9ntmA9SbmlxNTA4iUEh0vKr05Tm0/mo+Jb+tFZcRzbuslKq1sTKdrowhbcKbSDrYxh6EpkJw=";
		password=rsaUtils.decrypt(password);
		System.out.println(password);*/
	}
	private static String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}
}
