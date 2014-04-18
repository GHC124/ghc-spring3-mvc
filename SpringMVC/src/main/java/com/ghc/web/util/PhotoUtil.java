/**
 * PhotoUtil.java
 *
 *	
 */
package com.ghc.web.util;

import static com.ghc.web.Constants.PHOTO_TYPE;

/**
 * 
 */
public class PhotoUtil {
	public static boolean isValidType(String type) {
		boolean valid = false;
		if (PHOTO_TYPE.contains(type)) {
			valid = true;
		}
		return valid;
	}
}
