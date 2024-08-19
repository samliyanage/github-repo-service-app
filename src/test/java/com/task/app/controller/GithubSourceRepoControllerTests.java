package com.task.app.controller;

import com.task.app.exception.GetRepoDetailsFailedException;
import com.task.app.model.GithubSourceRepoDetails;
import com.task.app.service.GithubSourceRepoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class GithubSourceRepoControllerTests {
    private MockMvc mockMvc;

    @Mock
    private GithubSourceRepoService service;

    @InjectMocks
    private GithubSourceRepoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetRepositoryDetailsSuccess() throws Exception {

        GithubSourceRepoDetails repo = new GithubSourceRepoDetails();
        repo.setFullName("samliyanage/demo-kotlin-boot");
        repo.setDescription("Demo Application for Kotlin with Spring Boot");
        repo.setCloneUrl("https://github.com/samliyanage/demo-kotlin-boot.git");
        repo.setStars(0);
        repo.setCreatedAt(LocalDateTime.now());

        when(service.getRepositoryDetails("samliyanage", "demo-kotlin-boot")).thenReturn(repo);


        mockMvc.perform(get("/repositories/samliyanage/demo-kotlin-boot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is("samliyanage/demo-kotlin-boot")))
                .andExpect(jsonPath("$.description", is("Demo Application for Kotlin with Spring Boot")))
                .andExpect(jsonPath("$.cloneUrl", is("https://github.com/samliyanage/demo-kotlin-boot.git")))
                .andExpect(jsonPath("$.stars", is(0)))
                .andExpect(jsonPath("$.createdAt", notNullValue()));
    }

    @Test
    void testGetRepositoryDetailsServiceException() throws Exception {
        when(service.getRepositoryDetails(anyString(), anyString())).thenThrow(new GetRepoDetailsFailedException("throw exception"));

        mockMvc.perform(get("/repositories/samliyanage/demo-kotlin-boot"))
                .andExpect(status().isInternalServerError());
    }

}
