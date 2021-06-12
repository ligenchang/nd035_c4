package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private ItemController itemController;

    @Before
    public void setup() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
        Item i = new Item();
        i.setId(1l);
        i.setName("test");
        i.setDescription("test description");
        i.setPrice(new BigDecimal(12));

        when(itemRepository.findById(1l)).thenReturn(Optional.of(i));
        when(itemRepository.findAll()).thenReturn(Lists.newArrayList(i));
        when(itemRepository.findByName("test")).thenReturn(Lists.newArrayList(i));
    }

    @Test
    public void getItemById(){
        final ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Item i = response.getBody();
        assertNotNull(i);
        assertEquals(Long.valueOf(1), i.getId());
        assertEquals("test", i.getName());
        assertEquals("test description", i.getDescription());
    }

    @Test
    public void getItems(){
        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> items = response.getBody();
        assertNotNull(items);
        assertEquals(Long.valueOf(1), items.get(0).getId());
        assertEquals("test", items.get(0).getName());
        assertEquals("test description", items.get(0).getDescription());
    }

    @Test
    public void getItemsByName(){
        final ResponseEntity<List<Item>> response = itemController.getItemsByName("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> items = response.getBody();
        assertNotNull(items);
        assertEquals(Long.valueOf(1), items.get(0).getId());
        assertEquals("test", items.get(0).getName());
        assertEquals("test description", items.get(0).getDescription());
    }
}
