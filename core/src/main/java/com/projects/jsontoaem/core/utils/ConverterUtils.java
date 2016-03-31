package com.projects.jsontoaem.core.utils;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.projects.jsontoaem.core.models.ComponentTypeEnum;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Utility Class to segregate helper codess.
 */
public final class ConverterUtils {

    /**
     * Retrieves the expected JSONArray from the provided endpoint of the content page to be converted.
     *
     * @param endPoint Endpoint of the content page to convert
     * @return JSONArray to generate AEM page
     * @throws IOException
     * @throws JSONException
     */
    public static JSONArray getJSONEndPoint(final String endPoint) throws IOException, JSONException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(endPoint);
        JSONArray jsonArray = null;

        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json");
        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() == 200) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            jsonArray = new JSONArray(result.toString());
        }

        return jsonArray;
    }

    /**
     * @return A set of keys required to get the AEM properties from a JSON object.
     */
    public static HashSet<String> expectedKeys() {

        HashSet<String> keySet = new HashSet<String>();
        for (ComponentTypeEnum comp : ComponentTypeEnum.values()) {
            keySet.add(comp.name());
        }

        return keySet;
    }
}
