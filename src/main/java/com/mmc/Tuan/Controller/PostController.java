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

// @CrossOrigin(origins = "https://mmc-website-e135b.web.app")
// @CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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



//    @GetMapping("/post/search/{name}")
//    public  ResponseEntity<List<Post>> searchPost (@PathVariable("name") String postName){
//        List<Post> posts = postRepository.findByPostNameContaining(postName);
//        try{
//            if(posts.isEmpty()){
//                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(posts,HttpStatus.OK);
//        } catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
//        }
//    }

//    @DeleteMapping("post/delete/id")

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
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/post/detail/{id}")
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



    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

}
