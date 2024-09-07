package com.task.app.util;

import com.task.app.TestConstants;
import com.task.app.exception.GithubApiFetchFailedException;
import com.task.app.model.GithubSourceRepoDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GithubApiUtilTest {

    @InjectMocks
    private GithubApiUtil githubApiUtil;

    @BeforeEach
    void setUp() {
        org.springframework.test.util.ReflectionTestUtils.setField(githubApiUtil, "githubApiBaseUri", "https://api.github.com");
    }

    @Test
    void testFetchGithubRepoDetailsSuccess() throws Exception {
        GithubSourceRepoDetails repoDetails = new GithubSourceRepoDetails();
        repoDetails.setFullName(TestConstants.GITHUB_REPO_FULL_NAME);
        repoDetails.setDescription(TestConstants.GITHUB_REPO_DESC);
        repoDetails.setCloneUrl(TestConstants.GITHUB_REPO_CLONE_URL);
        repoDetails.setStars(0);



        GithubSourceRepoDetails result = githubApiUtil.fetchGithubRepoDetails("samliyanage", "demo-kotlin-boot");

        assertNotNull(result);
        assertEquals("samliyanage/demo-kotlin-boot", result.getFullName());
        assertEquals("Demo Application for Kotlin with Spring Boot", result.getDescription());
        assertEquals("https://github.com/samliyanage/demo-kotlin-boot.git", result.getCloneUrl());
        assertEquals(0, result.getStars());
    }

    @Test
    void testFetchGithubRepoDetailsFailure() throws Exception {

        GithubApiFetchFailedException exception = assertThrows(GithubApiFetchFailedException.class, () -> {
            githubApiUtil.fetchGithubRepoDetails("test", "repo");
        });
        assertEquals("Failed to fetch repository details from Github API", exception.getMessage());
    }

    @Test
    void testParseGitHubResponse() throws Exception {

        GithubSourceRepoDetails result = githubApiUtil.parseGitHubResponse(TestConstants.GITHUB_API_REPO_DETAIL_JSON);

        assertNotNull(result);
        assertEquals("samliyanage/demo-kotlin-boot", result.getFullName());
        assertEquals("Demo Application for Kotlin with Spring Boot", result.getDescription());
        assertEquals("https://github.com/samliyanage/demo-kotlin-boot.git", result.getCloneUrl());
        assertEquals(0, result.getStars());
    }
}
