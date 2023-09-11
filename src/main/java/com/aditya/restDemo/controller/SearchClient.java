package com.aditya.restDemo.controller;

import com.aditya.restDemo.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.solr.client.solrj.SolrServerException;
import org.json.JSONArray;

import java.io.IOException;

public interface SearchClient {
    public User query(String field, Long value) throws SolrServerException, IOException;
    //public User query(String field, Integer value) throws SolrServerException, IOException;

}
