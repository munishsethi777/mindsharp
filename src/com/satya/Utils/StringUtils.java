package com.satya.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StringUtils {
	public static String ConvertStreamToString(java.io.InputStream is) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
		} catch (Exception e) {
		}
		return sb.toString();
	}
}
