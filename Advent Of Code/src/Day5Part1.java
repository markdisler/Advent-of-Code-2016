import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day5Part1 {

	public static void main (String [] args) throws IOException {
		String s = "wtnhxymk";
		String code = "";
		int x = 0;
		
		for (int i = 0; i < 8; i++) {
			String hashed = MD5(s + x);
			while (!hashed.startsWith("00000")) {
				x++;
				hashed = MD5(s + x);
			}
			code += hashed.charAt(5);		
			x++;
		}
		System.out.println(code);
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
