package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private OrderController orderController;

    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);

        Item i = new Item();
        i.setId(1l);
        i.setName("test");
        i.setDescription("test description");
        i.setPrice(new BigDecimal(12));

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotal(new BigDecimal(6));
        cart.setItems(Lists.newArrayList(i));


        User u = new User();
        u.setId(0);
        u.setUsername("test");
        u.setPassword("testPassword");
        u.setCart(cart);

        cart.setUser(u);



        UserOrder order = new UserOrder();
        order.setId(1L);
        order.setUser(u);
        order.setTotal(new BigDecimal(5));
        order.setItems(Lists.newArrayList(i));




        when(orderRepository.save(order)).thenReturn(order);
        when(userRepository.findByUsername("test")).thenReturn(u);
    }

    @Test
    public void submit(){
        final ResponseEntity<UserOrder> response = orderController.submit("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        UserOrder order = response.getBody();
        assertNotNull(order);
        assertEquals(new BigDecimal(6), order.getTotal());

    }
}
