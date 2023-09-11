package com.aditya.restDemo;

import com.aditya.restDemo.controller.UserServiceImpl;
import com.aditya.restDemo.model.User;
import org.apache.solr.client.solrj.SolrServerException;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class UserServiceImplTest {

    @Test
    public void indexAndQueryTest() throws SolrServerException, IOException {
        System.out.println("Junit working");
        UserServiceImpl impl = new UserServiceImpl();
        impl.indexDocument();
        User user = impl.queryByField("userID", 123456L);
        System.out.println("Output:  " + user);
    }
}
