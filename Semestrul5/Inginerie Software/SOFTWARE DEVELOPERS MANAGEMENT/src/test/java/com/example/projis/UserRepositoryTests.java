package com.example.projis;

import com.example.projis.model.User;
import com.example.projis.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNew(){
        User user = new User();
        user.setEmail("hitu.octavian@gmail.com");
        user.setPassword("octavian123");
        user.setFirstName("Octavian");
        user.setLastName("Hitu");
        User savedUser = repo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAll(){
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSize(2);
        for (User user: users ) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate(){
        Iterable<User> users = repo.findAll();
        String email = "hitu.octavian@gmail.com";
        int id = -1;
        for (User user: users ) {
            if (user.getEmail().equals(email)) id = user.getId();
        }
        if (id != -1){
            Optional <User> optionalUser = repo.findById(id);
            User user = optionalUser.get();
            user.setPassword("octavian234");
            repo.save(user);
            User updateUser = repo.findById(id).get();
            Assertions.assertThat(updateUser.getPassword()).isEqualTo("octavian234");
        }
    }

    @Test
    public void testDelete(){
        int id = 2;
        repo.deleteById(id);
        Optional <User> optionalUser = repo.findById(2);
        Assertions.assertThat(optionalUser).isNotPresent();
    }


}
