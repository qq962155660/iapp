package com.cdf.iapp.util;

import java.util.Collection;
import java.util.List;

public class StringUtils {

	public static String join(Collection collection, String separator){
	    Object[] array =  collection.toArray();
		if (array == null) {  
            return null;
		}
		int arraySize = array.length;
		System.out.println(arraySize);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(separator);
			}
			buf.append(array[i]);

		}
		return buf.toString();
	}
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	public static boolean isBlank(String str){
		return str == null || str.equals("");
	}
}
