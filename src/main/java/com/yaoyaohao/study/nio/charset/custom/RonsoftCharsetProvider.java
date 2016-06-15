package com.yaoyaohao.study.nio.charset.custom;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.HashSet;
import java.util.Iterator;

/**
* A CharsetProvider class which makes available the charsets
* provided by Ronsoft. Currently there is only one, namely the
* X-ROT13 charset. This is not a registered IANA charset, so it's
* name begins with "X-" to avoid name clashes with offical charsets.
*
* To activate this CharsetProvider, it's necessary to add a file to
* the classpath of the JVM runtime at the following location:
* META-INF/services/java.nio.charsets.spi.CharsetProvider
*
* That file must contain a line with the fully qualified name of
* this class on a line by itself:
* com.ronsoft.books.nio.charset.RonsoftCharsetProvider
*
* See the javadoc page for java.nio.charsets.spi.CharsetProvider
* for full details.
*
*/
public class RonsoftCharsetProvider extends CharsetProvider {
	private static final String CHARSET_NAME = "X-ROT13";
	
	private Charset rot13 = null;
	
	public RonsoftCharsetProvider() {
		this.rot13 = new Rot13Charset(CHARSET_NAME, new String[0]);
	}

	/* (non-Javadoc)
	 * @see java.nio.charset.spi.CharsetProvider#charsets()
	 */
	@Override
	public Iterator<Charset> charsets() {
		HashSet<Charset> set = new HashSet<Charset>(1);
		set.add(rot13);
		return set.iterator();
	}

	@Override
	public Charset charsetForName(String charsetName) {
		if(charsetName.equalsIgnoreCase(CHARSET_NAME)) {
			return this.rot13;
		}
		return null;
	}

}
