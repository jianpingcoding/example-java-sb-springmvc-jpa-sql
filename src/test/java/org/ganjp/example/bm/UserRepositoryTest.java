package org.ganjp.example.bm;

import org.ganjp.example.bm.entity.User;
import org.ganjp.example.bm.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void getUser() {
       List<User> users = userRepository.findAll();
       System.out.println(users);
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setName("admin");
        user.setPassword("123456");
        userRepository.save(user);
    }
}
