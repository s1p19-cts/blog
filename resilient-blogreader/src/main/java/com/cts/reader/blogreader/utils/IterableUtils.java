package com.cts.reader.blogreader.utils;

import java.util.Iterator;

public class IterableUtils {

	// Function to get the Spliterator
	public static <T> Iterable<T> getIterableFromIterator(Iterator<T> iterator) {
		return () -> iterator;
	}

}
