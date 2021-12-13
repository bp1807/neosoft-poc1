package com.neosoft.poc1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.poc1.model.User;
import com.neosoft.poc1.service.UserService;
import com.neosoft.poc1.utils.PagingHeaders;
import com.neosoft.poc1.utils.PagingResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(value=UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    private UserController userController = new UserController();

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
    public void getUserTest() throws Exception {
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
    public void editUserTest() throws Exception {
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
    public void deleteUserTest() throws Exception {
        Mockito.when(userService.deleteUser(123)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/123").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void purgeUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/purge/123").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void retrunHttpHeadersTest() {

        PagingResponse pagingResponse = new PagingResponse((long) 49, (long) 2, (long) 5, (long) 10, Collections.<User>emptyList());

        HttpHeaders httpHeaders = userController.returnHttpHeaders(pagingResponse);

        assertThat(Objects.requireNonNull(httpHeaders.get(PagingHeaders.COUNT.getName())).get(0)).isEqualTo("49");
        assertThat(Objects.requireNonNull(httpHeaders.get(PagingHeaders.PAGE_NUMBER.getName())).get(0)).isEqualTo("2");
        assertThat(Objects.requireNonNull(httpHeaders.get(PagingHeaders.PAGE_SIZE.getName())).get(0)).isEqualTo("5");
        assertThat(Objects.requireNonNull(httpHeaders.get(PagingHeaders.PAGE_TOTAL.getName())).get(0)).isEqualTo("10");


    }

}