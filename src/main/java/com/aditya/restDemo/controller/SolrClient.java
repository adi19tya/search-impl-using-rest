package com.aditya.restDemo.controller;

import com.aditya.restDemo.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SolrClient implements SearchClient{
    private HttpSolrClient solr;

    private static SolrClient obj;

    SolrClient() {}

    public static SolrClient getInstance()
    {
        if (obj==null) {
            obj = new SolrClient();
            obj.initializeClient();
        }
        return obj;
    }

    private void initializeClient() {
        String urlString = "http://localhost:8983/solr/testCollection";
        solr = new HttpSolrClient.Builder(urlString).build();
        solr.setParser(new XMLResponseParser());
    }

    public User query(String field, Long value) throws SolrServerException, IOException {

        String fieldValue = field + ":" + value;
        SolrQuery query = new SolrQuery();
        query.set("q", fieldValue);
        QueryResponse response = solr.query(query);

        SolrDocumentList docList = response.getResults();
        JSONObject returnResults = new JSONObject();

        Map map = docList.get(0);
        User userResult = new User();

        userResult.setUserID((Long)map.get("userID"));
        userResult.setFirstName(map.get("firstName").toString());
        userResult.setLastName(map.get("lastName").toString());
        userResult.setPhoneNumber((long)map.get("phoneNumber"));

        return userResult;
    }

    public void index(User doc) throws SolrServerException, IOException {

        SolrInputDocument document = new SolrInputDocument();
        Long userId = doc.getUserID();
        String firstName = doc.getFirstName();
        String lastName = doc.getLastName();
        Long phoneNumber = doc.getPhoneNumber();


        document.addField("userID", userId);
        document.addField("firstName", firstName);
        document.addField("lastName", lastName);
        document.addField("phoneNumber", phoneNumber);
        try {
            solr.add(document);
        } catch (SolrServerException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        solr.commit();
    }
}

