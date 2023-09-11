package com.aditya.restDemo.controller;

import com.aditya.restDemo.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines the business logic for user service
 */
public class UserServiceImpl {

    SolrClient solrClient = new SolrClient().getInstance();

    public void indexDocument() throws SolrServerException, IOException {
        solrClient.index();
    }

    public User queryByField(String field, Long value) throws SolrServerException, IOException {
        return solrClient.query(field, value);
    }
}

