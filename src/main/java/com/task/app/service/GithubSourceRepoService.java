package com.task.app.service;

import com.task.app.exception.GetRepoDetailsFailedException;
import com.task.app.model.GithubSourceRepoDetails;

public interface GithubSourceRepoService {
    public GithubSourceRepoDetails getRepositoryDetails(String owner, String repoName) throws GetRepoDetailsFailedException;
}
