package com.projects.jsontoaem.core.models;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Interface that all model components need to implement.
 *
 * Created by albertsaclot on 30/03/2016.
 */
public interface IComponent {

    void setProperties(Node node, String key, JSONObject json, int id)
            throws RepositoryException, JSONException;
}
