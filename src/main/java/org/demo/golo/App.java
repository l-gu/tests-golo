package org.demo.golo;

import java.util.LinkedList;
import java.util.List;

public class App {

	public static void main(String... args) {
		System.out.println("Hello world!");
		System.out.println("Java version : " + System.getProperty("java.version") );
		
		List<String> list = new LinkedList<>();
		list.add("AA") ;
		
		String s = "B" ;
		switch (s) {
		case "A" :
			break;
		case "B" :
			break;
		}
	}

}
