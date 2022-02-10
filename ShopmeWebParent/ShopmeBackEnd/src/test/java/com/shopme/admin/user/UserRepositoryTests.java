package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userKate = new User("kate@mail.com", "kate", "Kate", "Danko");
        userKate.addRole(roleAdmin);

        User savedUser = userRepository.save(userKate);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles() {
        User userAnna = new User("anna@mail.com", "anna", "Anna", "Black");
        Role roleAssistant = new Role(2);
        Role roleEditor = new Role(3);
        userAnna.addRole(roleEditor);
        userAnna.addRole(roleAssistant);

        User savedUser = userRepository.save(userAnna);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> users = userRepository.findAll();
        users.forEach(u -> System.out.println(u));
    }
    @Test
    public void testGetUserById(){
        User userKate = userRepository.findById(1).get();
        System.out.println(userKate);
        assertThat(userKate).isNotNull();
    }
    @Test
    public void testUpdateUserDetails(){
        User userKate = userRepository.findById(1).get();
        userKate.setEnabled(true);
        userKate.setEmail("kateryna@mail.com");

        userRepository.save(userKate);
    }

    @Test
    public void testUpdateUserRoles(){
        User userAnna = userRepository.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(4);
//        userKate2.addRole(roleEditor);
//        userKate2.getRoles().remove(roleEditor);
        userAnna.addRole(roleSalesperson);

        userRepository.save(userAnna);
    }

    @Test
    public void testDeleteUser(){
        Integer userId = 3;
        userRepository.deleteById(userId);
    }
    @Test
    public void testGetUserFromEmail(){
        String email = "dane@mail.com";
        User userByEmail = userRepository.getUserByEmail(email);
        assertThat(userByEmail).isNotNull();
    }
    @Test
    public void testCountById(){
        Integer id = 10;
        Long count = userRepository.countById(id);
        assertThat(count).isNotNull().isGreaterThan(0);
    }
    @Test
    public void testDisableUser(){
        Integer id = 11;
        userRepository.updateEnabledStatus(id, false);
    }
    @Test
    public void testEnableUser(){
        Integer id = 4;
        userRepository.updateEnabledStatus(id, true);
    }
    @Test
    public  void testListFirstPage(){
        int pageNumber = 1;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);
        List<User> listUsers = page.getContent();
        listUsers.forEach(u -> System.out.println(u));
        assertThat(listUsers.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchUsers(){
        String keyword = "Kateryna";

        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(keyword, pageable);
        List<User> listUsers = page.getContent();
        listUsers.forEach(u -> System.out.println(u));
        assertThat(listUsers.size()).isGreaterThan(0);
    }
}
