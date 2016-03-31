package com.projects.jsontoaem.core.models;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Sample Component for Text. Uses the wcm foundation TEXT component.
 *
 * Created by albertsaclot on 30/03/2016.
 */
public class TextComponent implements IComponent {

    @Override
    public void setProperties(Node node, String key, JSONObject json, int id)
            throws RepositoryException, JSONException {

        JSONObject content = json.getJSONObject(key);

        Node textNode = node.addNode("text_" + id);
        textNode.setProperty("sling:resourceType", "wcm/foundation/components/text");
        textNode.setProperty("textIsRich", "true");
        textNode.setProperty("text", content.getString("text"));
    }
}
