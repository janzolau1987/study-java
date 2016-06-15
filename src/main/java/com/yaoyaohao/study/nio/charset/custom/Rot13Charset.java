package com.yaoyaohao.study.nio.charset.custom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * 简单自定义字符集
 * 
 * @author liujianzhu
 * @date 2016年6月15日 下午4:33:09
 *
 */
public class Rot13Charset extends Charset {
	private static final String BASE_CHARSET_NAME = "UTF-8";
	
	Charset baseCharset;

	protected Rot13Charset(String canonicalName, String[] aliases) {
		super(canonicalName, aliases);
		//
		baseCharset = Charset.forName(BASE_CHARSET_NAME);
	}

	@Override
	public boolean contains(Charset cs) {
		return false;
	}

	@Override
	public CharsetDecoder newDecoder() {
		return new Rot13Decoder(this, baseCharset.newDecoder());
	}

	@Override
	public CharsetEncoder newEncoder() {
		return new Rot13Encoder(this, baseCharset.newEncoder());
	}

	private void rot13(CharBuffer cb) {
		for(int pos = cb.position();pos < cb.limit();pos++) {
			char c = cb.get(pos);
			char a = '\u0000';
			
			if((c >= 'a') && (c <= 'z')) {
				a = 'a';
			}
			
			if((c >= 'A') && (c <= 'Z')) {
				a = 'A';
			}
			
			if(a != '\u0000') {
				c = (char)(((c - a) + 13) % 26 + a);
				cb.put(pos,c);
			}
		}
	}
	
	private  class Rot13Encoder extends CharsetEncoder {
		private CharsetEncoder baseEncoder;
		
		Rot13Encoder(Charset cs,CharsetEncoder baseEncoder) {
			super(cs,baseEncoder.averageBytesPerChar(),baseEncoder.maxBytesPerChar());
			this.baseEncoder = baseEncoder;
		}

		@Override
		protected CoderResult encodeLoop(CharBuffer cb, ByteBuffer bb) {
			CharBuffer tmpcb = CharBuffer.allocate(cb.remaining());
			while(cb.hasRemaining()) {
				tmpcb.put(cb.get());
			}
			tmpcb.rewind();
			rot13(tmpcb);
			baseEncoder.reset();
			CoderResult cr = baseEncoder.encode(tmpcb, bb, true);
			cb.position(cb.position() - tmpcb.remaining());
			return cr;
		}

	}
	
	private class Rot13Decoder extends CharsetDecoder {
		private CharsetDecoder baseDecoder;
		
		Rot13Decoder(Charset cs,CharsetDecoder baseDecoder) {
			super(cs,baseDecoder.averageCharsPerByte(),baseDecoder.maxCharsPerByte());
			this.baseDecoder = baseDecoder;
		}

		@Override
		protected CoderResult decodeLoop(ByteBuffer bb, CharBuffer cb) {
			baseDecoder.reset();
			CoderResult result = baseDecoder.decode(bb, cb, true);
			rot13(cb);
			return result;
		}

	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader in;
		if(args.length > 0) {
			in = new BufferedReader(new FileReader(args[0]));
		}
		else {
			in = new BufferedReader(new InputStreamReader(System.in));
		}
		//
		PrintStream out = new PrintStream(System.out,false,"X-ROT13");
		
		String s = null;
		while((s = in.readLine()) != null) 
			out.println(s);
		out.flush();
	}
}
