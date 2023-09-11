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
//        JSONArray docs = new JSONArray();

//        for(Map singleDoc : docList)
//        {
//            docs.put(new JSONObject(singleDoc));
////            solrDocMap.put(counter, new JSONObject(singleDoc));
////            counter++;
//        }

        Map map = docList.get(0);
        User userResult = new User();

        userResult.setUserID((Long)map.get("userID"));
        userResult.setFirstName(map.get("firstName").toString());
        userResult.setLastName(map.get("lastName").toString());
        userResult.setPhoneNumber(map.get("phoneNumber").toString());


//        System.out.println("docs: " + docs);

//        userResult.setUserID(123456L);
//        userResult.setFirstName("Amrit");
//        userResult.setLastName("Madugundu");
//        userResult.setPhoneNumber("9000669247");
//        System.out.println("Docs: " + docs);
//        returnResults.put("docs", docs);

//        ObjectMapper mapper = new ObjectMapper();
////        String json = mapper.writeValueAsString(returnResults);
////        JsonNode jsonNode = mapper.readTree(json);
//        JsonNode jsonNode = mapper.convertValue(docs, JsonNode.class);
        return userResult;
    }

    public void index() throws SolrServerException, IOException {

        SolrInputDocument document = new SolrInputDocument();
        document.addField("userID", "123456");
        document.addField("firstName", "Aditya");
        document.addField("lastName", "Madugundu");
        document.addField("phoneNumber", "9000772508");
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

