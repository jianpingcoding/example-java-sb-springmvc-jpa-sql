package org.ganjp.example.cm.repository;

import org.ganjp.example.cm.entity.Comment;
import org.ganjp.example.cm.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Transactional
    Page<Post> findAll(Pageable pageable);
}