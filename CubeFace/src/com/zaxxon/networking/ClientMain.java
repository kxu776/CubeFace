package com.zaxxon.networking;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class ClientMain extends Client {

	public ClientMain(String host, int port, String player,Game game) {
		super(host, port, player);
	}
	
	
	//Takes ArrayList of Sprites, returns ArrayList of attribute strings of sprites
	public static ArrayList<String> spritesToString(LinkedList<Sprite> sprites){
			ArrayList<String> attributeStrings = new ArrayList<>();
			for(Sprite sprite: sprites){
				attributeStrings.add(mapToString(sprite.getAttributes()));
			}
			return attributeStrings;
		}

		//Parses hashmap of sprite attributes to string. Can then be converted to packet via byte array.
	private static String mapToString(LinkedHashMap<String, Object> attributes){
		String outputString = "";
		for(Map.Entry<String, Object> value: attributes.entrySet()){
			outputString += String.valueOf(value.getValue());
		}
		return outputString;
	}
	
	private void sendSprites() {
		
	}

		//Test method
		public static void main(String[] args){
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put("test", new Integer(17));
			map.put("test1", new String("test1"));
			map.put("test2", new Character('c'));
			System.out.println(mapToString(map));
		}

}
