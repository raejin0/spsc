package net.miraeit.cmm.util;

import java.io.UnsupportedEncodingException;

public class Utils {
	public static String decodeString(String targetString, String inputCode, String outputCode) throws UnsupportedEncodingException {
		return new String(targetString.getBytes(inputCode), outputCode);
	}
}
