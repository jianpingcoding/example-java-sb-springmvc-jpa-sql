package org.ganjp.example.bm.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ganjp.example.bm.entity.User;
import org.ganjp.example.bm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void newUser() throws IOException {
        User user = new User();
        user.setName("Jacky Francisco");


        // something like this will read the avatar from disk in a byte[]
        File file = new File("jacky.jpg");
        byte[] avatar = Files.readAllBytes(file.toPath());

//        user.setAvatar(avatar);

        userRepository.save(user);
    }

    @Transactional(readOnly=true)
    public Page<User> findAll(Pageable pageable, String include) {
        Page<User> userPage = userRepository.findAll(pageable);
        userPage.getContent().forEach((user) -> {
            if (include.indexOf("addresses")!=-1) {
                user.getAddresses();
            } else {
                user.setAddresses(null);
            }
            if (include.indexOf("roles")!=-1) {
                user.getRoles();
            } else {
                user.setRoles(null);
            }
            if (include.indexOf("avatar")!=-1) {
                user.getProfile().getAvatar();
            } else {
                user.getProfile().setAvatar(new byte[1]);
            }
        });
        return userPage;
    }

    @Transactional(readOnly=true)
    public User fetchUserWithoutAvatar(String id) {
        return userRepository.findById(id).get();
    }

    @Transactional(readOnly=true)
    public User fetchUserWithAvatar(String id) {

        Optional<User> user = userRepository.findById(id);

        // this is loaded LAZY
//        user.get().getAvatar();

        return user.get();
    }
}
