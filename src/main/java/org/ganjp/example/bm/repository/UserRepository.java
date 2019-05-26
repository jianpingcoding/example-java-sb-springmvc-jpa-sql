package org.ganjp.example.bm.repository;


import org.ganjp.example.bm.entity.User;
import org.ganjp.example.cm.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends JpaRepository<User, String> {
    @Override
    @Transactional(timeout = 8)
    List<User> findAll();
}