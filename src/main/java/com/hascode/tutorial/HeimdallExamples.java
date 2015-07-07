package com.hascode.tutorial;

import de.qaware.heimdall.Password;
import de.qaware.heimdall.PasswordException;
import de.qaware.heimdall.PasswordFactory;
import de.qaware.heimdall.SecureCharArray;

public class HeimdallExamples {

	public static void main(final String[] args) throws PasswordException {
		char[] plaintextPassword = "soosecret".toCharArray();

		String hashedPassword = hashPassword(plaintextPassword);
		System.out.println("the hashed password is: " + hashedPassword);

		System.out.println("verifying a wrong password succeeds: " + verifyPassword("wrongpassword".toCharArray(), hashedPassword));
		System.out.println("verifying the right password succeeds: " + verifyPassword("soosecret".toCharArray(), hashedPassword));
	}

	private static String hashPassword(final char[] plaintextPassword) throws PasswordException {
		Password password = PasswordFactory.create();
		try (SecureCharArray cleartextPassword = new SecureCharArray(plaintextPassword)) {
			return password.hash(cleartextPassword);
		}
	}

	private static boolean verifyPassword(final char[] plaintextPassword, final String hashedPassword) throws PasswordException {
		Password password = PasswordFactory.create();

		try (SecureCharArray cleartext = new SecureCharArray(plaintextPassword)) {
			if (password.verify(cleartext, hashedPassword)) {
				if (password.needsRehash(hashedPassword)) {
					String renewedHashedPassword = password.hash(cleartext);
					System.out.println("password needed rehash: new hash is " + renewedHashedPassword);
					// store new hash etc ...
				}
				return true;
			}
			return false;
		}
	}
}
