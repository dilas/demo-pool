package com.example.demo;

import java.util.Random;

public class Connection {
	public boolean isValid() {
		// return true;
		return new Random().nextBoolean();
	}
	
	public void close() {
		
	}
}
