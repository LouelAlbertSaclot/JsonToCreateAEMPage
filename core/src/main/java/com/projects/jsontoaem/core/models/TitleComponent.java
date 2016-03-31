package com.projects.jsontoaem.core.models;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Sample Component for Title. Uses the wcm foundation TITLE component.
 *
 * Created by albertsaclot on 30/03/2016.
 */
public class TitleComponent implements IComponent {

    @Override
    public void setProperties(Node node, String key, JSONObject json, int id)
            throws RepositoryException, JSONException {

        JSONObject content = json.getJSONObject(key);

        Node titleNode = node.addNode("title_" + id);
        titleNode.setProperty("sling:resourceType", "wcm/foundation/components/title");
        titleNode.setProperty("jcr:title", content.getString("text"));
        titleNode.setProperty("type", "h3");
    }
}
