package com.projects.jsontoaem.core.models;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Sample Component for Image. Uses the wcm foundation IMAGE component.
 *
 * Created by albertsaclot on 30/03/2016.
 */
public class ImageComponent implements IComponent {

    @Override
    public void setProperties(Node node, String key, JSONObject json, int id)
            throws RepositoryException, JSONException {

        JSONObject content = json.getJSONObject(key);

        Node imageNode = node.addNode("image_" + id);
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
