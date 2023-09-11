package com.aditya.restDemo.controller;

import java.io.IOException;
import java.net.URI;

import com.aditya.restDemo.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.solr.client.solrj.SolrServerException;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(path = "/users}")
public class UserAPIService {
    //Implement a GET Method
//    @GetMapping("{userID}")
    @GetMapping("/users/{userID}")
    public User getUserDetails(@PathVariable Long userID) throws SolrServerException, IOException {
        // call the queryByField Method from the userServiceImpl
        System.out.println("User ID: " + userID);
        UserServiceImpl serviceImpl = new UserServiceImpl();
        User result = serviceImpl.queryByField("userID", userID);
        System.out.println("Result: " + result);
        return result;
    }


    // Create a POST Method
    @PostMapping(path = "/users")
    public void indexDocumentIntoSolr() throws SolrServerException, IOException {
        // call the indexDocument Method from the userService Impl
        UserServiceImpl serviceImpl = new UserServiceImpl();
        serviceImpl.indexDocument();
    }

}
