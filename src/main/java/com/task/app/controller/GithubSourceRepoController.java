package com.task.app.controller;

import com.task.app.exception.GetRepoDetailsFailedException;
import com.task.app.model.GithubSourceRepoDetails;
import com.task.app.service.GithubSourceRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo/api/v1/repositories")
public class GithubSourceRepoController {
    @Autowired
    private GithubSourceRepoService service;

    @GetMapping("/{owner}/{repositoryName}")
    public ResponseEntity<GithubSourceRepoDetails> getRepositoryDetails(
            @PathVariable("owner") String owner,
            @PathVariable("repositoryName") String repositoryName) {

        try {
            GithubSourceRepoDetails repoDetails = service.getRepositoryDetails(owner, repositoryName);
            return ResponseEntity.ok(repoDetails);
        } catch (GetRepoDetailsFailedException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
