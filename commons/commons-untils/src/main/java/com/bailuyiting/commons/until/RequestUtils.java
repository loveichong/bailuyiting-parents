package com.bailuyiting.commons.until;



import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

public class RequestUtils {

	public static String getSort(String sort){
		if(StringUtils.isEmpty(sort)){
			return "o.create_time desc";
		}
		StringBuilder buf = new StringBuilder( sort.replace(':', ' ') );
		for (int i=1; i<buf.length()-1; i++) {
			if (
				Character.isLowerCase( buf.charAt(i-1) ) &&
				Character.isUpperCase( buf.charAt(i) ) &&
				Character.isLowerCase( buf.charAt(i+1) )
			) {
				buf.insert(i++, '_');
			}
			
		}
		return "o."+buf.toString().toLowerCase();
	}
	
	public static String URLDecode(String str) {
		return URLDecode(str, null);
	}

	public static String URLDecode(byte[] bytes, String enc) {
		return URLDecode(bytes, enc, false);
	}
	
	 public static String URLDecode(String str, String enc) {
	        return URLDecode(str, enc, false);
	    }
	 
	 public static String URLDecode(String str, String enc, boolean isQuery) {
	        if (str == null)
	            return (null);

	        // use the specified encoding to extract bytes out of the
	        // given string so that the encoding is not lost. If an
	        // encoding is not specified, let it use platform default
	        byte[] bytes = null;
	        try {
	            if (enc == null) {
	                bytes = str.getBytes();
	            } else {
	                bytes = str.getBytes(enc);
	            }
	        } catch (UnsupportedEncodingException uee) {}

	        return URLDecode(bytes, enc, isQuery);

	    }


	public static String URLDecode(byte[] bytes, String enc, boolean isQuery) {

		if (bytes == null)
			return (null);

		int len = bytes.length;
		int ix = 0;
		int ox = 0;
		while (ix < len) {
			byte b = bytes[ix++]; // Get byte to test
			if (b == '+' && isQuery) {
				b = (byte) ' ';
			} else if (b == '%') {
				b = (byte) ((convertHexDigit(bytes[ix++]) << 4) + convertHexDigit(bytes[ix++]));
			}
			bytes[ox++] = b;
		}
		if (enc != null) {
			try {
				return new String(bytes, 0, ox, enc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new String(bytes, 0, ox);

	}

	private static byte convertHexDigit(byte b) {
		if ((b >= '0') && (b <= '9'))
			return (byte) (b - '0');
		if ((b >= 'a') && (b <= 'f'))
			return (byte) (b - 'a' + 10);
		if ((b >= 'A') && (b <= 'F'))
			return (byte) (b - 'A' + 10);
		return 0;
	}

}
