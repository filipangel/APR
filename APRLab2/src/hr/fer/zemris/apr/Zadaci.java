package hr.fer.zemris.apr;

import java.io.BufferedReader;
import java.io.FileReader;

public class Zadaci {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new FileReader("config.txt"));
		
		zadatak1();
		zadatak2();
		zadatak3();
		zadatak4();
		zadatak5();
	}
}
