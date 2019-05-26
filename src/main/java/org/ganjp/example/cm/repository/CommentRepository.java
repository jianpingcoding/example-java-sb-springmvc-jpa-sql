package org.ganjp.example.cm.repository;

import org.ganjp.example.cm.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    Page<Comment> findByPostId(Long postId, Pageable pageable);

    @Transactional
    Optional<Comment> findByIdAndPostId(Long id, Long postId);
}