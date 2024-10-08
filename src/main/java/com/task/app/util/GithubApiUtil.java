package com.task.app.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.app.exception.GithubApiFetchFailedException;
import com.task.app.model.GithubSourceRepoDetails;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class GithubApiUtil {

    @Value("${github.api.basUri}")
    private String githubApiBaseUri;

    public GithubSourceRepoDetails fetchGithubRepoDetails(String owner, String repoName) throws GithubApiFetchFailedException{
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
            .url(githubApiBaseUri+"/repos/" + owner + "/" + repoName)
            .build();

        log.info("Executing Endpoint {}", request.url());

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String jsonResponse = "";
            if(response.body() != null){
                jsonResponse = response.body().string();
            }

            GithubSourceRepoDetails repoDetails = parseGitHubResponse(jsonResponse);
            repoDetails.setCreatedAt(LocalDateTime.now());
            log.info("Fetched repo details {}", repoDetails);
            return repoDetails;
        }catch (Exception e){
            log.error("Error Response from Github API {}", e.getMessage());
            throw new GithubApiFetchFailedException("Failed to fetch repository details from Github API");
        }
    }

    protected GithubSourceRepoDetails parseGitHubResponse(String jsonResponse) throws IOException {
        JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);

        GithubSourceRepoDetails repo = new GithubSourceRepoDetails();
        repo.setFullName(jsonNode.get("full_name").asText());
        repo.setDescription(jsonNode.get("description").asText());
        repo.setCloneUrl(jsonNode.get("clone_url").asText());
        repo.setStars(jsonNode.get("stargazers_count").asInt());

        return repo;
    }
}
