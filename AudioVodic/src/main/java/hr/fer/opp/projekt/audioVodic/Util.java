package hr.fer.opp.projekt.audioVodic;

import java.security.MessageDigest;
import java.util.Objects;

public class Util {

	public static String sha(String givenDigest) {
		MessageDigest messageDigest;
		String digest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");

			byte[] buff = givenDigest.getBytes();
			messageDigest.update(buff, 0, buff.length);

			byte[] dig = messageDigest.digest();
			digest = bytetohex(dig);

		} catch (Exception e) {
			System.out.println("Error.");
			System.exit(1);
		}
		return digest;
	}

	public static String bytetohex(byte[] array) {
		Objects.requireNonNull(array);
		StringBuilder sb = new StringBuilder();
		for (byte b : array) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public static boolean checkIsAlphabetic(String name) {
		for (int i = 0; i < name.length(); ++i) {
			if (!Character.isAlphabetic(name.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkUsername(String username) {
		for (int i = 0; i < username.length(); ++i) {
			if (!Character.isAlphabetic(username.charAt(i)) && !Character.isDigit(username.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkTitle(String title) {
		for (int i = 0; i < title.length(); ++i) {
			if (!Character.isAlphabetic(title.charAt(i)) && !Character.isDigit(title.charAt(i))
					&& !Character.isSpaceChar(title.charAt(i)) && !(title.charAt(i) == '.')) {
				return false;
			}
		}
		return true;
	}

	public static String checkDescription(String description) {
		if (description.length() > 1500) {
			return "Unijeli ste prevelik tekst";
		}
		for (int i = 0; i < description.length(); ++i) {
			if (!Character.isAlphabetic(description.charAt(i)) && !Character.isDigit(description.charAt(i))
					&& !Character.isSpaceChar(description.charAt(i)) && !(description.charAt(i) == '.')
					&& !(description.charAt(i) == ',') && !(description.charAt(i) == '(')
					&& !(description.charAt(i) == ')') && !(description.charAt(i) == '!')
					&& !(description.charAt(i) == '-') && !(description.charAt(i) == ';')
					&& !(description.charAt(i) == ':')) {
				System.out.println(description.charAt(i));
				return ("Nedozvoljeni znak: " + description.charAt(i));
			}
		}
		return null;
	}

	public static boolean checkMail(String email) {
		int atCounter = 0;
		for (int i = 0; i < email.length(); ++i) {
			if (email.charAt(i) == '@') {
				atCounter++;
			}
			if (!Character.isAlphabetic(email.charAt(i)) && !Character.isDigit(email.charAt(i))
					&& email.charAt(i) != '.' && email.charAt(i) != '@') {
				return false;
			}
		}
		if (atCounter != 1) {
			return false;
		}
		return true;
	}

	public static boolean checkPassword(String password) {
		boolean lower = false;
		boolean upper = false;
		if (password.length() < 7) {
			return false;
		}
		for (int i = 0; i < password.length(); ++i) {
			if (Character.isLowerCase(password.charAt(i))) {
				lower = true;
			}
			if (Character.isUpperCase(password.charAt(i))) {
				upper = true;
			}
		}
		return (lower && upper);
	}

}
