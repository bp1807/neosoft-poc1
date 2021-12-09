package com.neosoft.poc1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.poc1.model.User;
import com.neosoft.poc1.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(value=UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void addUserTest() throws Exception {
        User user = new User();
        user.setName("Bhavik");
        user.setSurname("Parmar");
        user.setPincode("400070");
        user.setDateOfBirth(LocalDate.of(1997, 1, 1));
        user.setDateOfJoining(LocalDate.of(2021, 1, 1));


        User createdUser = new User(123, "Bhavik", "Parmar", "400070", LocalDate.of(1997, 1, 1), LocalDate.of(2021, 1, 1), false);


        Mockito.when(userService.addUser(user)).thenReturn(createdUser);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
        //.andExpect(jsonPath("$", notNullValue()));
        //.andExpect(jsonPath("$.name", is("Bhavik")))
        //.andExpect(jsonPath("$.surname", is("Parmar")));


    }

    @Test
    public void getUserByIdTest() throws Exception {
        User user = new User(123, "Bhavik", "Parmar", "400070", LocalDate.of(1997, 1, 1), LocalDate.of(2021, 1, 1), false);
        Mockito.when(userService.getUser(123)).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/user/123").contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(mockRequest).andReturn();

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Bhavik")))
                .andExpect(jsonPath("$.surname", is("Parmar")));

    }

    @Test
    public void editUser() throws Exception {
        User user = new User();
        user.setName("Yash");

        User updatedUser = new User(123, "Bhavik", "Parmar", "400070", LocalDate.of(1997, 1, 1), LocalDate.of(2021, 1, 1), false);

        Mockito.when(userService.editUser(123, user)).thenReturn(updatedUser);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/user/123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
        //.andExpect(jsonPath("$", notNullValue()));
        //.andExpect(jsonPath("$.name", is("Bhavik")))
        //.andExpect(jsonPath("$.surname", is("Parmar")));

    }


    @Test
    public void deleteUser() throws Exception {
        Mockito.when(userService.deleteUser(123)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/123").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void purgeUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/purge/123").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}