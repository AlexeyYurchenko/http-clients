package com.example.service.integrationapp.controller;

import com.example.service.integrationapp.AbstractTest;
import com.example.service.integrationapp.entity.DataBaseEntity;
import com.example.service.integrationapp.model.UpsertEntityRequest;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class EntityClientControllerTest extends AbstractTest {

    @Test
    public void wheGetAllEntities_thenReturnEntitiesList() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());

        String actualResponse = mockMvc.perform(get("/api/v1/client/entity"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(dataBaseEntityService.findAll());

        assertFalse(redisTemplate.keys("*").isEmpty());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenGetEntityByName_thenReturnOneEntity() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());

        String actualResponse = mockMvc.perform(get("/api/v1/client/entity/testName_1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(dataBaseEntityService.findByName("testName_1"));
        assertFalse(redisTemplate.keys("*").isEmpty());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateEntity_thenCreateEntityAndEvictCache() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());
        assertEquals(3, dataBaseEntityRepository.count());

        mockMvc.perform(get("/api/v1/client/entity"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(redisTemplate.keys("*").isEmpty());
        UpsertEntityRequest request = new UpsertEntityRequest();
        request.setName("newEntity");
        String actualResponse = mockMvc.perform(post("/api/v1/client/entity")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(new DataBaseEntity(UUID.randomUUID(), "newEntity", ENTITY_DATE));
        assertTrue(redisTemplate.keys("*").isEmpty());
        assertEquals(4, dataBaseEntityRepository.count());

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse, JsonAssert.whenIgnoringPaths("id"));

    }

    @Test
    public void whenUpdateEntity_thenUpdateEntityAndEvictCache() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());

        mockMvc.perform(get("/api/v1/client/entity"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(redisTemplate.keys("*").isEmpty());
        UpsertEntityRequest request = new UpsertEntityRequest();
        request.setName("updateName");
        String actualResponse = mockMvc.perform(put("/api/v1/client/entity/{id}", UPDATE_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(new DataBaseEntity(UUID.randomUUID(), "updateName", ENTITY_DATE));
        assertTrue(redisTemplate.keys("*").isEmpty());

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse, JsonAssert.whenIgnoringPaths("id"));
    }

    @Test
    public void whenDeleteEntityById_thenDeleteEntityByIdAndEvictCache() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());
        assertEquals(3, dataBaseEntityRepository.count());
        mockMvc.perform(get("/api/v1/client/entity"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(redisTemplate.keys("*").isEmpty());
        mockMvc.perform(delete("/api/v1/client/entity/" + UPDATE_ID))
                .andExpect(status().isNoContent());

        assertTrue(redisTemplate.keys("*").isEmpty());
        assertEquals(2, dataBaseEntityRepository.count());

    }
}