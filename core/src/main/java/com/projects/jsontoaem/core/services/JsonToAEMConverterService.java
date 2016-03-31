package com.projects.jsontoaem.core.services;

import com.day.cq.wcm.api.WCMException;
import com.projects.jsontoaem.core.models.ComponentTypeEnum;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * The OSGi Service Interface for performing convertion of the JSON objects into AEM pages or components.
 *
 * Created by albertsaclot on 22/03/2016.
 */
public interface JsonToAEMConverterService {

    void initialize() throws LoginException;
    void saveSession() throws RepositoryException;
    void saveSessionAndLogout() throws RepositoryException;
    void sessionLogout();

    Node createPageNode(final String path, final String pageName, final String pageTitle, final String template, final String referenceType)
            throws RepositoryException, WCMException;

    void addComponent(Node pageNode, String key, JSONObject json, ComponentTypeEnum type, int counter)
            throws JSONException, RepositoryException;
}
