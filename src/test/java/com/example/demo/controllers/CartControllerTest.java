package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private ItemRepository itemRepository = mock(ItemRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private CartController cartController;

    @Before
    public void setup() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);

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







        when(userRepository.findByUsername("test")).thenReturn(u);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(i));
        when(cartRepository.save(cart)).thenReturn(cart);
    }

    @Test
    public void addTocart(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(2);
        final ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart c = response.getBody();
        assertNotNull(c);
        assertEquals(Long.valueOf(1), c.getId());
        assertEquals("test", c.getUser().getUsername().toString());
    }
}
