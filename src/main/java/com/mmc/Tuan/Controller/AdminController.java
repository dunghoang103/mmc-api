package com.mmc.Tuan.Controller;

import com.mmc.Tuan.Data.Post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import com.mmc.Tuan.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/mmc")
public class AdminController{

    @Autowired
    PostRepository postRepository;

    @PostMapping("/post/create")
    public ResponseEntity<Post> setPost (@RequestBody Post post){
        try {
            Post post1 = postRepository.save(new Post(post.getPostName()
                    , post.getPostDescription()
                    , post.getPostContent()
                    , post.getPostType()
                    , post.getPostYear()
                    , post.getPostPic()));
            return new ResponseEntity<>(post1,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Post> repairPost (@PathVariable("id") long postId, @RequestBody Post post ) {
        Optional<Post> post1 = postRepository.findByPostId(postId);
        try {
            System.out.println(post);
            if (post1.isPresent()){
                Post _post = post1.get();
                if (post.getPostName() != null) {
                    _post.setPostName(post.getPostName());
                }
                if (post.getPostType() != null) {
                    _post.setPostType(post.getPostType());
                }
                if (post.getPostDescription() != null) {
                    _post.setPostDescription(post.getPostDescription());
                }
                if (post.getPostContent() != null) {
                    _post.setPostContent(post.getPostContent());
                }
                if (post.getPostPic() != null) {
                    _post.setPostPic(post.getPostPic());
                }
                if (post.getPostYear() != 0) {
                    _post.setPostYear(post.getPostYear());
                }
                return new ResponseEntity<>(postRepository.save(_post), HttpStatus.OK);
            }
            return new ResponseEntity<>(new Post(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    // @PutMapping("/post/{id}")
    // public ResponseEntity<Post> updatePost (@PathVariable("id") long postId) {
    //     Optional<Post> post = postRepository.findByPostId(postId);
    //     try {
    //         if (post1.isPresent()){
    //                         Post _post = post1.get();
    //                         _post.setPostName(post.getPostName());
    //                         _post.setPostType(post.getPostType());
    //                         _post.setPostDescription(post.getPostDescription());
    //                         _post.setPostContent(post.getPostContent());
    //                         _post.setPostPic(post.getPostPic());
    //                         return new ResponseEntity<>(postRepository.save(_post), HttpStatus.OK);
    //                     }
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @GetMapping("/admin/post")
    public ResponseEntity<List<Post>> getPost (@RequestParam(required = false) String postName){
        List<Post> posts = new ArrayList<>();
        try {
            if(postName == null){
                postRepository.findAll().forEach(posts::add);
            }
            else {
                postRepository.findByPostNameContaining(postName).forEach(posts::add);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admin/post/detail/{id}")
    public ResponseEntity<Post> getPostById (@PathVariable("id") long postId){
        Optional<Post> post = postRepository.findByPostId(postId);
        try {
            if(post.isPresent()){
                return new ResponseEntity<>(post.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(new Post(), HttpStatus.NOT_FOUND);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    @DeleteMapping("/admin/post/{id}")
    public ResponseEntity<Post> deleteById (@PathVariable("id") long postId){
        Optional<Post> post = postRepository.findByPostId(postId);
        try {
            if(post.isPresent()) {
                postRepository.deleteById(postId);
             return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
