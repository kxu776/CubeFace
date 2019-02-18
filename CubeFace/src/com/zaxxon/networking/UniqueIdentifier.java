package com.zaxxon.networking;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

public class UniqueIdentifier {
	
	private static ArrayList<Integer> ids = new ArrayList<Integer>();
	private static final int RANGE = 100;
	private static int index = 0;
	static {
		for (int i= 0; i < RANGE; i++) {
			ids.add(i);
			Collections.shuffle(ids);
		}
	}
	
	private UniqueIdentifier() {
		
	}
	
	public static int getIdentifier() {
		if (index>ids.size()-1) index = 0;
		return ids.get(index++);
	}

}
