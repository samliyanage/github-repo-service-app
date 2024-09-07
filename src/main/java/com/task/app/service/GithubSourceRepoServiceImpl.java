package com.task.app.service;

import com.task.app.exception.GetRepoDetailsFailedException;
import com.task.app.model.GithubSourceRepoDetails;
import com.task.app.util.GithubApiUtil;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;

@Service
public class GithubSourceRepoServiceImpl implements GithubSourceRepoService{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private GithubApiUtil githubApiUtil;

    @Override
    public GithubSourceRepoDetails getRepositoryDetails(String owner, String repoName) throws GetRepoDetailsFailedException {
        try {
            String cacheKey = owner + "/" + repoName;

            // Check cache first
            GithubSourceRepoDetails cachedRepo = (GithubSourceRepoDetails) redisTemplate.opsForValue().get(cacheKey);
            if (cachedRepo != null) {
                return cachedRepo;
            }

            // Else Fetch from GitHub API
            GithubSourceRepoDetails newRepo = githubApiUtil.fetchGithubRepoDetails(owner, repoName);

            // Save to Redis cache
            redisTemplate.opsForValue().set(cacheKey, newRepo, 10, TimeUnit.MINUTES);

            return newRepo;
        }catch (Exception e){
            throw new GetRepoDetailsFailedException(e.getMessage());
        }
    }


}



