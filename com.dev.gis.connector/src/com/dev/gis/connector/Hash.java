package com.dev.gis.connector;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;


public class Hash {

	private static Logger logger = Logger.getLogger(Hash.class);
	

	public static String calculateMD5Hash (String stringToHash) throws UnsupportedEncodingException {
		logger.debug("during hash, input: " + stringToHash);
		
		MessageDigest md = getMessageDigest();
		md.reset();
		md.update(stringToHash.getBytes("UTF8"));
		final byte[] resultByte = md.digest();
		char[] encodedBytes = Hex.encodeHex(resultByte);
		final String result = new String (encodedBytes);
		logger.debug("during hash: result: " + result);
		return result;
	}

	public static String calculateSHA256Hash (String stringToHash) throws UnsupportedEncodingException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update(stringToHash.getBytes("UTF8"));
			final byte[] resultByte = md.digest();
			char[] encodedBytes = Hex.encodeHex(resultByte);
			final String result = new String (encodedBytes);
			logger.debug("during hash: result: " + result);
			return result;
		}
		catch(Exception err) {
			logger.error(err.getMessage());
		}
		return null;
	}

	private static MessageDigest getMessageDigest () {
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("MD5");
		} catch(NoSuchAlgorithmException e) {
			logger.debug("md5 not available, no hash available !!!");
			md = new MessageDigest("default") {

				@Override
				protected void engineUpdate(byte input) {
				}

				@Override
				protected void engineUpdate(byte[] input, int offset, int len) {
				}

				@Override
				protected byte[] engineDigest() {
					return null;
				}

				@Override
				protected void engineReset() {
				}

				@Override
				public byte[] digest(byte[] input) {
					return input;
				}
			};
		}
		return md;
	}
}