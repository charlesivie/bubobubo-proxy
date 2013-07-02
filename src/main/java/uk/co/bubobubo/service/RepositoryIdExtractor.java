package uk.co.bubobubo.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepositoryIdExtractor {

	private static final String DEFAULT_REPOSITORY_REGEX = "/repositories/([^/]+)/?";

	public static String extractRepositoryId(final String path) {

		Pattern p = Pattern.compile(DEFAULT_REPOSITORY_REGEX);
		Matcher m = p.matcher(path);

		try {
			m.find();
			return m.group(1);
		} catch(Exception e) {
			return "NOT ALLOWED";
		}
	}
}
