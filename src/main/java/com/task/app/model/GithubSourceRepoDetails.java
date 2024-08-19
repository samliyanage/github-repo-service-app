package com.task.app.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class GithubSourceRepoDetails {
    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private LocalDateTime createdAt;
}
