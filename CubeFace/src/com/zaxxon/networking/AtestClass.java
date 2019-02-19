package com.zaxxon.networking;

public class AtestClass {
	public static void main(String[] args) {
		Client cli = new Client("localhost", 4444,"player1");
		cli.start();
	}
}
