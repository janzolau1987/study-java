package com.yaoyaohao.study.nio.charset;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetDecode {

	public static void main(String[] args) throws IOException{
		String charsetName = "ISO-8859-1";
		
		if(args.length > 0)
			charsetName = args[0];
		
		decodeChannel(Channels.newChannel(System.in), 
				new OutputStreamWriter(System.out), 
				Charset.forName(charsetName));
	}
	
	public static void decodeChannel(ReadableByteChannel source,Writer writer,Charset charset)
		throws UnsupportedCharsetException,IOException {
		CharsetDecoder decoder = charset.newDecoder();
		decoder.onMalformedInput(CodingErrorAction.REPLACE);
		decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
		
		ByteBuffer bb = ByteBuffer.allocateDirect(16 * 1024);
		CharBuffer cb = CharBuffer.allocate(57);
		
		//Buffer starts empty; indicate input is needed
		CoderResult result = CoderResult.UNDERFLOW;
		boolean eof = false;
		while(!eof) {
			//input buffer underflow; decoder wants more input
			if(result == CoderResult.UNDERFLOW) {
				bb.clear();
				
				//Fill the input buffer; watch for EOF
				eof = (source.read(bb) == -1);
				
				bb.flip();
			}
			
			//Decode input bytes to output chars; pass EOF flag
			result = decoder.decode(bb, cb, eof);
			
			//If output buffer is null,drain output
			if(result == CoderResult.OVERFLOW) {
				drainCharBuf(cb, writer);
			}
		}
		
		//Flush any remaining state from the decoder,being careful to detect output
		//buffer overflows
		while(decoder.flush(cb) == CoderResult.OVERFLOW) {
			drainCharBuf(cb, writer);
		}
		
		//Drain any chars remaining in the output buffer
		drainCharBuf(cb, writer);
		
		//close the channel;push out any buffered data to stdout
		source.close();
		writer.flush();
		
	}
	
	/**
	 * Helper method to drain the char buffer and write its content to
	 * the given Writer object. Upon return, the buffer is empty and
	 * ready to be refilled.
	  * 
	  * @param cb
	  * @param writer
	  * @throws IOException
	 */
	static void drainCharBuf(CharBuffer cb,Writer writer) throws IOException {
		cb.flip();
		
		if(cb.hasRemaining()) {
			writer.write(cb.toString());
		}
		
		cb.clear();
	}

}
