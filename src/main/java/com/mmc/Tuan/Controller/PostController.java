package com.mmc.Tuan.Controller;

import com.mmc.Tuan.Data.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mmc.Tuan.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/mmc")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping("/post")
    public ResponseEntity<List<Post>> getAllPost(@RequestParam(required = false) String postName){
        List<Post> posts = new ArrayList<>();
        try {
            if(postName == null){
                postRepository.findAll().forEach(posts::add);
            }
                else {
                postRepository.findByPostNameContaining(postName).forEach(posts::add);
            }
            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @GetMapping("/post/{type}")
    public ResponseEntity<List<Post>> getAllProject (@PathVariable("type") String posttype){
        List<Post> posts = postRepository.findByPostType(posttype);
        try{
            if(posts.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(posts,HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    @GetMapping("/post/{type}/{year}")
    public ResponseEntity<List<Post>> getWithYear (@PathVariable("type") String posttype, @PathVariable("year") long postyear){

        List<Post> posts = postRepository.findByPostTypeAndPostYear(posttype,postyear);
        try {

            if(posts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/post/{type}/{year}/{id}")
    public ResponseEntity<Post> getPost (@PathVariable("type") String postType, @PathVariable("year") long postYear, @PathVariable("id") long postId){
        Optional<Post> post = postRepository.findByPostTypeAndPostYearAndPostId(postType,postYear,postId);
        try {
            if(post.isPresent()){
                return new ResponseEntity<>(post.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(new Post(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/post/{type}/{year}/{id}/repair")
    public ResponseEntity<Post> repairPost (@PathVariable("type") String postType, @PathVariable("year") long postYear,
                                            @PathVariable("id") long postId, @RequestBody Post post ) {
        Optional<Post> post1 = postRepository.findByPostTypeAndPostYearAndPostId(postType, postYear, postId);
        try {
            if (post1.isPresent()){
                Post _post = post1.get();
                _post.setPostName(post.getPostName());
                _post.setPostType(post.getPostType());
                _post.setPostDescription(post.getPostDescription());
                _post.setPostContent(post.getPostContent());
                _post.setPostPic(post.getPostPic());
                return new ResponseEntity<>(postRepository.save(_post), HttpStatus.OK);
            }
            return new ResponseEntity<>(new Post(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
