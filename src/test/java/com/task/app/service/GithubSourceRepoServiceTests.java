package com.task.app.service;

import com.task.app.exception.GetRepoDetailsFailedException;
import com.task.app.model.GithubSourceRepoDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


import java.io.IOException;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GithubSourceRepoServiceTests {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOps;

    @InjectMocks
    private GithubSourceRepoServiceImpl service;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        GithubSourceRepoDetails mockRepo = new GithubSourceRepoDetails();
        mockRepo.setFullName("samliyanage/demo-kotlin-boot");
        mockRepo.setDescription("Demo Application for Kotlin with Spring Boot");
        mockRepo.setCloneUrl("https://github.com/samliyanage/demo-kotlin-boot.git");
        mockRepo.setStars(0);
        mockRepo.setCreatedAt(LocalDateTime.now());

        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(anyString())).thenReturn(mockRepo);
    }

    @Test
    void testGetRepositoryDetailsFromCache() throws GetRepoDetailsFailedException {
        GithubSourceRepoDetails result = service.getRepositoryDetails("samliyanage", "demo-kotlin-boot");
        assertNotNull(result);
        assertEquals("samliyanage/demo-kotlin-boot", result.getFullName());
        assertEquals("Demo Application for Kotlin with Spring Boot", result.getDescription());
        assertEquals("https://github.com/samliyanage/demo-kotlin-boot.git", result.getCloneUrl());
        assertEquals(0, result.getStars());
    }
}
