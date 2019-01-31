package com.zaxxon.Networking;

public class AtestClass {
	public static void main(String[] args) {
		Client cli = new Client("localhost", 4444);
		cli.start();
	}
}
