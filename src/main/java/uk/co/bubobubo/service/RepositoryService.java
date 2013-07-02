package uk.co.bubobubo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.bubobubo.domain.Level;
import uk.co.bubobubo.domain.RepoInfo;

import javax.sql.DataSource;

@Service
public class RepositoryService {

	@Autowired
	private DataSource dataSource;

	public RepoInfo getRepositoryInfo(final String repositoryId) {

		RepoInfo repoInfo = new RepoInfo();
		repoInfo.setId(repositoryId);
		Level level = Level.FREE;

		repoInfo.setLevel(level);

		return repoInfo;

	}
}
