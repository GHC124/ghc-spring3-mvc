/**
 * Lists.java
 *
 *	
 */
package com.ghc.util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 */
public class Lists {
	public static <T> ArrayList<T> newArrayList(Iterable<T> iterable) {
		ArrayList<T> arrayList = new ArrayList<>();

		Iterator<T> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			arrayList.add(iterator.next());
		}

		return arrayList;
	}
}
