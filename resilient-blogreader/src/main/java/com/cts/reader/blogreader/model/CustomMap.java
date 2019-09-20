package com.cts.reader.blogreader.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CustomMap extends HashMap implements Iterable {

	private Map amap = new HashMap();

	public CustomMap(Map map) {
		this.amap = map;
	}

	public Iterator<Map> iterator() {
		Iterator<Map> imap = null;
		if (amap != null && amap.entrySet() != null) {
			imap = this.amap.entrySet().iterator();
		}
		return imap;
	}
}
