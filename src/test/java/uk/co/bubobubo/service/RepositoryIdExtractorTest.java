package uk.co.bubobubo.service;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RepositoryIdExtractorTest {

	@Test
	public void extractRepositoryId_should_find_a_repositoryID() {

		String input = "/repositories/test-repo-1/contexts";
		String actual = RepositoryIdExtractor.extractRepositoryId(input);

		String expected = "test-repo-1";

		assertThat(actual, is(expected));
	}
}
