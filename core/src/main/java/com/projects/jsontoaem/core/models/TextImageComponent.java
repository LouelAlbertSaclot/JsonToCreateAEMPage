package com.projects.jsontoaem.core.models;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Sample Component for TextImage. Uses the wcm foundation TEXTIMAGE component.
 *
 * Created by albertsaclot on 30/03/2016.
 */
public class TextImageComponent implements IComponent {

    @Override
    public void setProperties(Node node, String key, JSONObject json, int id)
            throws RepositoryException, JSONException {

        JSONObject content = json.getJSONObject(key);

        Node textImageNode = node.addNode("textImage_" + id);
        textImageNode.setProperty("sling:resourceType", "wcm/foundation/components/textimage");
        textImageNode.setProperty("textIsRich", "true");
        textImageNode.setProperty("text", content.getString("text"));
        textImageNode.setProperty("alignment", content.getString("alignment"));

        Node imageNode = textImageNode.addNode("image");
        imageNode.setProperty("sling:resourceType", "wcm/foundation/components/image");
        imageNode.setProperty("jcr:title", content.getString("title"));
        imageNode.setProperty("fileReference", content.getString("fileReference"));
        imageNode.setProperty("linkURL", content.getString("linkUrl"));
        imageNode.setProperty("alt", content.getString("alt"));
        // imageNode.setProperty("jcr:description", content.getString("description"));
        // imageNode.setProperty("fileName", "");
        // imageNode.setProperty("width", "");
        // imageNode.setProperty("height", "");
    }
}
