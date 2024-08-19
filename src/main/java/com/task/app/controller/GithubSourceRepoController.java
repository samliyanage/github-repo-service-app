package com.task.app.controller;

import com.task.app.controller.response.ErrorResponse;
import com.task.app.exception.GetRepoDetailsFailedException;
import com.task.app.model.GithubSourceRepoDetails;
import com.task.app.service.GithubSourceRepoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repositories")
@Slf4j
public class GithubSourceRepoController {
    @Autowired
    private GithubSourceRepoService service;

    @GetMapping("/{owner}/{repositoryName}")
    public ResponseEntity<Object> getRepositoryDetails(
            @PathVariable("owner") String owner,
            @PathVariable("repositoryName") String repositoryName) {

            log.info("Fetching Github Repository Details Started!");

        try {
            GithubSourceRepoDetails repoDetails = service.getRepositoryDetails(owner, repositoryName);
            log.info("Fetched Github Repository Details Successfully!");
            return ResponseEntity.ok(repoDetails);
        } catch (GetRepoDetailsFailedException e) {
            log.error("Failed to fetch Github repository details Reason: {}",e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("Unable to fetch repository details from Github");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
