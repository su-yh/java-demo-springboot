package com.suyh0103.nested.simple;

import com.suyh0103.Customer;
import com.suyh0103.CustomerService;
import com.suyh0103.CustomerServiceJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@DisplayName("Test Customer Service")
public class CustomerServiceMethodTest {

    CustomerService customerService;

    @BeforeEach
    void createNewObjectForAll() {
        System.out.println("New CustomerService()");
        customerService = new CustomerServiceJDBC();
    }

    @Test
    void findOne_with_id() {
        customerService.findOneById(2L);
    }

    @Test
    void findOne_with_name() {
        customerService.findOneByName(2L);
    }

    @Test
    void findOne_with_name_regex() {
        customerService.findOneByNameRegex("%s");
    }

    @Test
    void findAll_with_ids() {
        customerService.findAllByIds(Arrays.asList(2, 3, 4));
    }

    @Test
    void findAll_with_name_like() {
        customerService.findAllByName("mkyong");
    }

    @Test
    void update_with_new() {
        customerService.update(new Customer());
    }

    @Test
    void update_with_existing() {
        customerService.update(new Customer());
    }

}
