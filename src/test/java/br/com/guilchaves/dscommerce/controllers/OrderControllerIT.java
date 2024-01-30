package br.com.guilchaves.dscommerce.controllers;

import br.com.guilchaves.dscommerce.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    String adminUsername, adminPassword;
    String clientUsername, clientPassword;
    String adminToken, clientToken;
    Long nonExistingId, existinId, notOwnedId;
    Long clientId, adminId;


    @BeforeEach
    void setUp() throws Exception {
        clientUsername = "maria@gmail.com";
        clientPassword = "123456";
        clientId = 1L;
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";
        nonExistingId = 0L;
        existinId = 1L;
        notOwnedId = 2L;

        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
    }

    @Test
    public void findByIdShouldReturnOrderWhenIdExistsAndAdminLogged() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/orders/{id}", existinId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existinId));
    }

    @Test
    public void findByIdShouldReturnOrderWhenOrderSelfOwnedAndSelfClientLogged() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/orders/{id}", existinId)
                        .header("Authorization", "Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existinId));
        result.andExpect(jsonPath("$.client.id").value(clientId));
        result.andExpect(jsonPath("$.items").isArray());
    }

    @Test
    public void findByIdShouldReturnForbiddenWhenOrderNotSelfOwnedAndClientLogged() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/orders/{id}", notOwnedId)
                        .header("Authorization", "Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExistsAndAdminLogged() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/orders/{id}", nonExistingId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExistsAndClientLogged() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/orders/{id}", nonExistingId)
                        .header("Authorization", "Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnUnauthorizedWhenLoggedOff() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/orders/{id}", existinId)
                        .header("Authorization", "Bearer ")
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());
    }
}