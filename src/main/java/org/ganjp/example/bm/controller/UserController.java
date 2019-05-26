package org.ganjp.example.bm.controller;

import org.ganjp.example.bm.Service.UserService;
import org.ganjp.example.bm.entity.User;
import org.ganjp.example.bm.repository.UserRepository;
import org.ganjp.example.cm.entity.Post;
import org.ganjp.example.common.controller.SuccessResponse;
import org.ganjp.example.common.exception.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.ganjp.example.common.Util.getNullPropertyNames;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

//    @GetMapping(params = { "page", "size", "sortBy", "sortOrder"})
//    @GetMapping
//    public SuccessResponse findPaginatedUsers(@RequestParam(value="page", defaultValue = "0") int page,
//                                              @RequestParam(value="size", defaultValue = "30") int size,
//                                              @RequestParam(value="sortBy", defaultValue = "name") String sortBy,
//                                              @RequestParam(value="sortOrder", defaultValue = "asc") String sortOrder) {
//        Sort sort = Sort.by(sortBy);
//        if ("desc".equalsIgnoreCase(sortOrder)) {
//            sort = sort.descending();
//        }
//
//        Pageable pageableRequest = PageRequest.of(page, size, sort);
//        Page<User> userPage = userRepository.findAll(pageableRequest);
//
//        List<User> users = userPage.getContent();
//        Map<String, Integer> pageMap = new HashMap<String, Integer>();
//        pageMap.put("current", page);
//        pageMap.put("size", size);
//        pageMap.put("total", userPage.getTotalPages());
//
//        String message = "No data";
//        if (users.size() > 1) {
//            message = "Get " + users.size() + " users successfully";
//        }
//
//        return new SuccessResponse(message, "users", users, "page", pageMap);
//    }

    @GetMapping
    public SuccessResponse findPaginatedUsers(Pageable pageable, @RequestParam(value="include", defaultValue = "profile") String include) {
        Page<User> userPage = userService.findAll(pageable, include);
        return new SuccessResponse("Get users successfully.", userPage);
    }

    @GetMapping("/{id}")
    public SuccessResponse getUser(@PathVariable String id) {
        User user = userRepository.findById(id).get();
        return new SuccessResponse("Get user " + user.getName() + " successfully",  "user", user);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse createUser(@Valid @RequestBody User user) {
        userRepository.save(user);
        return new SuccessResponse("Create user " + user.getName() + " successfully","user", user);
    }

    @PutMapping("/{userId}")
    public SuccessResponse updateUser(@PathVariable String userId, @Valid @RequestBody User user) {
        return userRepository.findById(userId).map(dbUser -> {
            String[] nullPropertyNames = getNullPropertyNames(user);
            BeanUtils.copyProperties(user, dbUser, nullPropertyNames);
            userRepository.save(dbUser);
            return new SuccessResponse("Update user " + user.getName() + " successfully","user", dbUser);
        }).orElseThrow(() -> new ResourceNotFoundException("userId " + userId + " not found"));
    }

    @DeleteMapping("/{userId}")
    public SuccessResponse deleteUser(@PathVariable String userId) {
        return userRepository.findById(userId).map(dbUser -> {
            userRepository.delete(dbUser);
            return new SuccessResponse("Delete user " + dbUser.getName() + " successfully.");
        }).orElseThrow(() -> new ResourceNotFoundException("userId " + userId + " not found"));
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
}
