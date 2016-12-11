import java.io.IOException;

public class Day05Part2 {

	public static void main (String [] args) throws IOException {
		String s = "wtnhxymk";
		int x = 0;
		char [] code = new char[8];
		
		for (int i = 0; i < 8; i++) {
			String hashed = MD5(s + x); //initial hash
			while (!hashed.startsWith("00000")) {
				x++;
				hashed = MD5(s + x);
			}
			char position = hashed.charAt(5);
			if ('0' <= position && position <= '7' && code[Integer.parseInt(""+position)] == '\u0000') {
				code[Integer.parseInt(""+position)] = hashed.charAt(6);
			} else {
				i--;  // step the loop back b/c no character was filled
			}
			x++;
		}

		System.out.print(code);
	}

	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {	}
		return null;
	}
}
