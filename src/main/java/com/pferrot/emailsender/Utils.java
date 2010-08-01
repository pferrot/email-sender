package com.pferrot.emailsender;

public class Utils {
	
	private final static String INLINE_RESOURCE_SEPARATOR = ":::::";
	
	public static String encodeInlineResource(final String pId, final String pPath) {
		return pId + INLINE_RESOURCE_SEPARATOR + pPath;
	}
	
	public static String[] decodeInlineResource(final String code) {
		final String[] result = new String[2];
		final int indexOfSeparator = code.indexOf(INLINE_RESOURCE_SEPARATOR);
		result[0] = code.substring(0, indexOfSeparator);
		result[1] = code.substring(indexOfSeparator + INLINE_RESOURCE_SEPARATOR.length());
		return result;		
	}
}
