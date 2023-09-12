package com.example.anination05.repo;

import com.example.anination05.models.Post;
import com.example.anination05.models.Post_request;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Post_requestRepository extends JpaRepository<Post_request, Long> {

    List<Post_request> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Post_request> findAllByOrderByCreatedAtDesc();

}
