package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;
    private User userHaveOrders;
    private User userDontHaveOrders;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        //Юзер без заказов
        userDontHaveOrders = new User();
        userDontHaveOrders.setId(userId);
        userDontHaveOrders.setName("Саня");
        userDontHaveOrders.setEmail("sanya.ne@email.com");


        Order order = new Order();
        order.setOrderId(UUID.randomUUID());
        order.setProductName("Стол");
        order.setAmount(BigDecimal.valueOf(15000.00));
        order.setStatus(OrderStatus.NEW);
        order.setUser(userHaveOrders);


        //Юзер с заказами
        userHaveOrders = new User();
        userHaveOrders.setId(userId);
        userHaveOrders.setName("Димон");
        userHaveOrders.setEmail("dimonpokemon@email.com");
        userHaveOrders.setOrderList(List.of(order));
    }

    @Test
    void getAllUsersMustReturnSummaryViews() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(userDontHaveOrders));
        MvcResult mvcResult = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List<User> users = objectMapper.readValue(json,
                new TypeReference<List<User>>() {});
        //Должны получить одного пользователя
        assertEquals(1, users.size());
        User returnUser = users.get(0);
        //Айди должны совпадать
        assertEquals(userId, returnUser.getId());
        //Имя должно совпадать
        assertEquals("Саня", returnUser.getName());
        //Почта должна совпасть
        assertEquals("sanya.ne@email.com", returnUser.getEmail());

        //Заказы должны быть пустыми, так как UserSummary
        assertTrue(returnUser.getOrderList() == null || returnUser.getOrderList().isEmpty());
    }

    @Test
    void getSingleUserMustReturnDetailsViews() throws Exception {
        when(userService.getByUserId(userId)).thenReturn(userHaveOrders);
        MvcResult mvcResult = mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        User singleUser = objectMapper.readValue(json, new TypeReference<User>() {});
        Order orderFromResponse = singleUser.getOrderList().get(0);
        assertEquals(1, singleUser.getOrderList().size());
        assertEquals(userId, singleUser.getId());
        assertEquals("Димон", singleUser.getName());
        assertEquals("dimonpokemon@email.com", singleUser.getEmail());
        assertEquals("Стол", orderFromResponse.getProductName());
        assertEquals(0, BigDecimal.valueOf(15000.00).compareTo(orderFromResponse.getAmount()));

        assertFalse(singleUser.getOrderList().isEmpty());
    }

    @Test
    void createUserMustReturnNewCreatedUser() throws Exception {
        User newUser = new User();
        newUser.setName("Артем");
        newUser.setEmail("artem@email.com");

        User savedUser = new User();
        savedUser.setId(UUID.randomUUID());
        savedUser.setName("Артем");
        savedUser.setEmail("artem@email.com");

        when(userService.createUser(any(User.class))).thenReturn(savedUser);


        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        User createdUser = objectMapper.readValue(json, User.class);
        assertNotNull(createdUser.getId());
        assertEquals("Артем", createdUser.getName());
        assertEquals("artem@email.com", createdUser.getEmail());
        assertTrue(createdUser.getOrderList() == null || createdUser.getOrderList().isEmpty());
    }

    @Test
    void createUser_withInvalidEmail_shouldReturnBadRequest() throws Exception {
        User invalidUser = new User();
        invalidUser.setName("Тест");
        invalidUser.setEmail("ошибка в почтее");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }
}
