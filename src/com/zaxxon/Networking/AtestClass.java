package com.zaxxon.Networking;

public class AtestClass {
	public static void main(String[] args) {
		Client cli = new Client("172.22.106.99", 4444);
		cli.start();
	}
}
