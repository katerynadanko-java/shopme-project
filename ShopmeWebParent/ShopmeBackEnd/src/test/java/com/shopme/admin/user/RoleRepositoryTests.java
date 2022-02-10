package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void createFirstRole() {

        Role roleAdmin = new Role("Admin", "maneged everything");
        Role savedRole = roleRepository.save(roleAdmin);
        Assertions.assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void createRestRoles() {

        Role roleSalesperson = new Role("Salesperson", "maneged " +
                "price, products, customers, shipping, orders and sales report");
        Role roleEditor = new Role("Editor", "maneged " +
                "categories, brands, article and menus");
        Role roleShipper = new Role("Shipper", "view " +
                "products, view orders and update order status");
        Role roleAssistant = new Role("Assistant", "maneged " +
                "questions and reviews");
        roleRepository.saveAll(Arrays.asList(roleAssistant, roleEditor, roleSalesperson, roleShipper));
    }
}
