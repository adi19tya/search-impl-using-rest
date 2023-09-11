package com.aditya.restDemo;

import com.aditya.restDemo.controller.UserServiceImpl;
import com.aditya.restDemo.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.solr.client.solrj.SolrServerException;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class UserServiceImplTest {

    @Test
    public void indexAndQueryTest() throws SolrServerException, IOException {
        System.out.println("Junit working");

        // create input doc to be indexed
        User doc = new User(123456, "Roger", "Fedrer", 9000728162);

        UserServiceImpl impl = new UserServiceImpl();
        impl.indexDocument(doc);
        User user = impl.queryByField("userID", 123456L);
        System.out.println("Output:  " + user);
    }
}
