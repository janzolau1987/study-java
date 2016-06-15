package com.yaoyaohao.study.nio.charset;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class EncodeTest {

	public static void main(String[] args) {
		// This is the character sequence to encode
		String input = "\u00bfMa\u00f1ana?";
		
		String[] charsetNames = {"US-ASCII","ISO-8859-1","UTF-8","UTF-16BE","UTF-16LE","UTF-16"};
		
		for(String charsetName : charsetNames) {
			doEncode(Charset.forName(charsetName), input);
		}
	}
	
	private static void doEncode(Charset cs,String input) {
		ByteBuffer bb = cs.encode(input);
		
		System.out.println("Charset : " + cs.name());
		System.out.println("  Input : " + input);
		System.out.println("Encoded : ");
		
		for(int i=0;bb.hasRemaining();i++) {
			int b = bb.get();
			int ival = ((int) b) & 0xff;
			char c = (char) ival;
			
			//
			if(i<10) System.out.print(" ");
			
			System.out.print(" " + i + ": ");
			
			if(ival < 16) System.out.print("0");
			
			System.out.print(Integer.toHexString(ival));
			if(Character.isWhitespace(c) || Character.isISOControl(c)) {
				System.out.println("");
			}
			else{
				System.out.println(" (" + c + ")");
			}
		}
		System.out.println("");
	}

}
