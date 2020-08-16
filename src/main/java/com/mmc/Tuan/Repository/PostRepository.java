package com.mmc.Tuan.Repository;

import com.mmc.Tuan.Data.Post;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findByPostNameContaining (String postName);
    List<Post> findByPostType (String postType);
    Optional<Post> findByPostId (long postId);
    List<Post> findByPostTypeAndPostYear (String postType, long postYear);
    Optional<Post> findByPostTypeAndPostYearAndPostId (String postType, long postYear, long postId);
}
