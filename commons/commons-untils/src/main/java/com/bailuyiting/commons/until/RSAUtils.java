package com.bailuyiting.commons.until;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.HashMap;

public class RSAUtils {
	
	private  String privateKeyString="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKwdDxuq9wrePHS1KEjSOr5OKEZNskXzygAZohHBmM6cNXlSP6EKSjA8tV4+YswaDbbSLWE/spY9EqOwKPQX8Qns4BE7I1Q9vUvZipSr7VSjQUA9wCnlArsvuDl7Ve+S5j2iuvoF7+JcTYb5GzWTJUcNOri4QDbv0vPdee1yv7WJAgMBAAECgYEAkzrwd5ySY8ukL7ngUhr0gWLedPV18P0Q6XEEAOh5TRS3cab4I0xoFkd4Zrw7S5ll7eRxSWVx4a8wyLGi9tucJpgoE+ec4QHZVp7Vi1bTiyk1dkb7+qblCRRG+Kd9veZKSSIgZ1mCxLDIacY9sb8CAIdBLcuoX448NQuqA7ni83kCQQDXnMS3evz9FPxGUbjw9MKlTe/XFLccVKEb9QKn5jehHPSsT+ZzFVxFwx7+UuwJJCQOJv2q/x48jEK+aweU9NM7AkEAzFplM5ZUBn+hpnkiQHEou9FAU0xJbUWYkaE8cMFZjFtuCiWpLXPC3leBv84257LMUiZdpbmGQ1imb8TAfuHGCwJBAKHK16q/NXxL9QpnZJobcLxOgX0p5EX0E3lBH58SGAhkDA4JXupYywbmZil92/T4E74y8AMSsYPJ1IPDGMtj8+UCQHyXXH5gWCiCJneOAUES/QfN448lwtrZhNS9XlFMwSEfGW8cWRI6MwRGi/f6bWVEkOhBNfV1sJNc+FTKu6ZgVtMCQQCPazMjGb1iE51WDIOcIJSbhsLkqY6fVasOXd3CNYWIH1hEEO7U63F5Wchv1M6WO98rQMlptXMYPa+5lSv4KwNZ";
	private  String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsHQ8bqvcK3jx0tShI0jq+TihGTbJF88oAGaIRwZjOnDV5Uj+hCkowPLVePmLMGg220i1hP7KWPRKjsCj0F/EJ7OAROyNUPb1L2YqUq+1Uo0FAPcAp5QK7L7g5e1XvkuY9orr6Be/iXE2G+Rs1kyVHDTq4uEA279Lz3Xntcr+1iQIDAQAB";
	private KeyFactory keyFactory;
	/** 
     * 字节数据转字符串专用集合 
     */  
    private  final char[] HEX_CHAR= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    Logger logger = LoggerFactory.getLogger(getClass());
	public RSAUtils(){
		try {
			keyFactory = KeyFactory.getInstance("RSA");


        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** 
     * 生成公钥和私钥 
     * @throws NoSuchAlgorithmException  
     * 
     */  
    public  HashMap<String, Object> getKeys() throws NoSuchAlgorithmException{  
        HashMap<String, Object> map = new HashMap<String, Object>();  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        keyPairGen.initialize(1024,new SecureRandom());  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        
        try {
			System.out.println(getKeyString(privateKey));
			System.out.println(getKeyString(publicKey));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        map.put("public", publicKey);  
        map.put("private", privateKey);  
        return map;  
    }  
    /** 
     * 使用模和指数生成RSA公钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public  RSAPublicKey getPublicKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 使用模和指数生成RSA私钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public  RSAPrivateKey getPrivateKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    
    /**
     * 得到密钥字符串（经过base64编码）
     * @return
     */
    public  String getKeyString(Key key) throws Exception {
          byte[] keyBytes = key.getEncoded();
          String s = Base64.encode(keyBytes);
          return s;
    }
  
    /** 
     * 公钥加密 
     *  
     * @param data 
     * @param publicKey 
     * @return 
     * @throws Exception 
     */  
    public  String encryptByPublicKey(String data, RSAPublicKey publicKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        // 模长  
        int key_len = publicKey.getModulus().bitLength() / 8;  
        // 加密数据长度 <= 模长-11  
        String[] datas = splitString(data, key_len - 11);  
        String mi = "";  
        //如果明文长度大于模长-11则要分组加密  
        for (String s : datas) {  
            mi += bcd2Str(cipher.doFinal(s.getBytes()));  
        }  
        return mi;  
    }  
    
    /** 
     * 从字符串中加载公钥 
     * @param publicKeyStr 公钥数据字符串 
     * @throws Exception 加载公钥时产生的异常 
     */  
    public  RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception{  
        try {  
            byte[] buffer=Base64.decode(publicKeyStr); 
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);  
           return  (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("公钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥数据为空");  
        }  
    }  
    public  RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception{  
        try {  
            
            byte[] buffer= Base64.decode(privateKeyStr);  
            PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("私钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥数据为空");  
        }  
    }  
  
    /** 
     * 私钥解密 
     *  
     * @param data 
     * @param privateKey 
     * @return 
     * @throws Exception 
     */  
    public  String decryptByPrivateKey(String data, RSAPrivateKey privateKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        //模长  
        int key_len = privateKey.getModulus().bitLength() / 8;  
        byte[] bytes = data.getBytes();  
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);  
        System.err.println(bcd.length);  
        //如果密文长度大于模长则要分组解密  
        String ming = "";  
        byte[][] arrays = splitArray(bcd, key_len);  
        for(byte[] arr : arrays){  
            ming += new String(cipher.doFinal(arr));  
        }  
        return ming;  
    }  
    /** 
     * ASCII码转BCD码 
     *  
     */  
    public  byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {  
        byte[] bcd = new byte[asc_len / 2];  
        int j = 0;  
        for (int i = 0; i < (asc_len + 1) / 2; i++) {  
            bcd[i] = asc_to_bcd(ascii[j++]);  
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));  
        }  
        return bcd;  
    }  
    public  byte asc_to_bcd(byte asc) {  
        byte bcd;  
  
        if ((asc >= '0') && (asc <= '9'))  
            bcd = (byte) (asc - '0');  
        else if ((asc >= 'A') && (asc <= 'F'))  
            bcd = (byte) (asc - 'A' + 10);  
        else if ((asc >= 'a') && (asc <= 'f'))  
            bcd = (byte) (asc - 'a' + 10);  
        else  
            bcd = (byte) (asc - 48);  
        return bcd;  
    }  
    /** 
     * BCD转字符串 
     */  
    public  String bcd2Str(byte[] bytes) {  
        char temp[] = new char[bytes.length * 2], val;  
  
        for (int i = 0; i < bytes.length; i++) {  
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);  
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
  
            val = (char) (bytes[i] & 0x0f);  
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
        }  
        return new String(temp);  
    }  
    /** 
     * 拆分字符串 
     */  
    public  String[] splitString(String string, int len) {  
        int x = string.length() / len;  
        int y = string.length() % len;  
        int z = 0;  
        if (y != 0) {  
            z = 1;  
        }  
        String[] strings = new String[x + z];  
        String str = "";  
        for (int i=0; i<x+z; i++) {  
            if (i==x+z-1 && y!=0) {  
                str = string.substring(i*len, i*len+y);  
            }else{  
                str = string.substring(i*len, i*len+len);  
            }  
            strings[i] = str;  
        }  
        return strings;  
    }  
    /** 
     *拆分数组  
     */  
    public  byte[][] splitArray(byte[] data,int len){  
        int x = data.length / len;  
        int y = data.length % len;  
        int z = 0;  
        if(y!=0){  
            z = 1;  
        }  
        byte[][] arrays = new byte[x+z][];  
        byte[] arr;  
        for(int i=0; i<x+z; i++){  
            arr = new byte[len];  
            if(i==x+z-1 && y!=0){  
                System.arraycopy(data, i*len, arr, 0, y);  
            }else{  
                System.arraycopy(data, i*len, arr, 0, len);  
            }  
            arrays[i] = arr;  
        }  
        return arrays;  
    }  
    
    
    /** 
     * 加密过程 
     * @param publicKey 公钥 
     * @param plainTextData 明文数据 
     * @return 
     * @throws Exception 加密过程中的异常信息 
     */  
    public  byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{  
        if(publicKey== null){  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher= Cipher.getInstance("RSA/ECB/NOPADDING");
        try {  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output= cipher.doFinal(plainTextData);  
            return output;  
        
        }catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  
    }  
  
    /** 
     * 解密过程 
     * @param privateKey 私钥 
     * @param cipherData 密文数据 
     * @return 明文 
     * @throws Exception 解密过程中的异常信息 
     */  
    public  byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception{  
        if (privateKey== null){  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher= Cipher.getInstance("RSA/ECB/NOPADDING");
        try {  
            
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output= cipher.doFinal(cipherData);  
            return output;  
        }catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }         
    }  
    /** 
     * 字节数据转十六进制字符串 
     * @param data 输入数据 
     * @return 十六进制内容 
     */  
    public  String byteArrayToString(byte[] data){  
        StringBuilder stringBuilder= new StringBuilder();  
        for (int i=0; i<data.length; i++){  
            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移  
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0)>>> 4]);  
            //取出字节的低四位 作为索引得到相应的十六进制标识符  
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);  
            if (i<data.length-1){  
                stringBuilder.append(' ');  
            }  
        }  
        return stringBuilder.toString();  
    }  
    
    public String encrypt(String content) {
		
		byte[] toBeEncrypted = content.getBytes();
		
		try {
			RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(publicKeyString)));
			Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			c.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] data = c.doFinal(toBeEncrypted);
			return Base64.encode(data);
		} catch (Exception e) {
			//LOG.error("加密密码失败", e);
			//throw new YktException("加密密码失败");
		}
		return null;
	}
    public String decrypt(String encryptedString) {
		try {
			RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(privateKeyString)));
			Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			c.init(Cipher.DECRYPT_MODE, privKey);
			byte[] data = c.doFinal(Base64.decode(encryptedString));
			
			String plainText = new String(data);
			return plainText;
		} catch (Exception e) {
		    e.printStackTrace();
            logger.error("解密密码失败", e);
			//throw new YktException("解密密码失败");
		}
		return null;
	}
    
	public static void main(String[] args) {
		
		RSAUtils rsaUtils=new RSAUtils();
  
        //测试字符串  
        String encryptStr= "123456";
  
        try {  
        	String entryPwd = rsaUtils.encrypt(encryptStr);
    		
    		System.out.println(entryPwd);
    		System.out.println(rsaUtils.decrypt("Znjs81G0IjtEYBWxtjYQpZT+Roll8dS+Dl/mlJdUlWK4Ug8DqQEaJTdzqpYAzLRe1fQJIcVpDzWVn9FnIJUlmXc+GaWR40JHv8Sek72l25YDZO/W4Rf6oB6GtnsyAtAIhYIrNl5TQUxEA+l+7uYljfs3TjWX3dptTiTVZD+KLzg="));
            
        } catch (Exception e) {  
            System.err.println(e.getMessage());  
        }  
		
		
		
		
	}
	
}
