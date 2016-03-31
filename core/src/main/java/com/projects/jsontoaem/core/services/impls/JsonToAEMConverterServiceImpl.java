package com.projects.jsontoaem.core.services.impls;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.projects.jsontoaem.core.models.ComponentTypeEnum;
import com.projects.jsontoaem.core.models.IComponent;
import com.projects.jsontoaem.core.services.JsonToAEMConverterService;
import com.projects.jsontoaem.core.utils.ComponentFactory;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation Class for the {@link JsonToAEMConverterService}.
 *
 * Created by albertsaclot on 22/03/2016.
 */
@Component(immediate = true)
@Service(value = JsonToAEMConverterService.class)
public class JsonToAEMConverterServiceImpl implements JsonToAEMConverterService {

    private final Logger logger = LoggerFactory.getLogger(JsonToAEMConverterServiceImpl.class);

    @Reference
    private ResourceResolverFactory resolverFactory;
    private ResourceResolver resourceResolver;
    private PageManager pageManager;
    private Session session;

    private String username;
    private String password;

    @Override
    public void initialize() throws LoginException {
        Map<String, Object> param = new HashMap<String, Object>();
        // param.put(ResourceResolverFactory.SUBSERVICE, "createPages"); // CORRECT Way but needs a SystemUser
        param.put(ResourceResolverFactory.USER, "admin");
        // this.resourceResolver = this.resolverFactory.getServiceResourceResolver(param); // CORRECT Way
        param.put(ResourceResolverFactory.PASSWORD, "admin".toCharArray());
        this.resourceResolver = this.resolverFactory.getResourceResolver(param);

        session = this.resourceResolver.adaptTo(Session.class);
        pageManager = this.resourceResolver.adaptTo(PageManager.class);
    }

    @Override
    public void sessionLogout() {
        session.logout();
    }

    @Override
    public void saveSession() throws RepositoryException {
        session.save();
    }

    @Override
    public void saveSessionAndLogout() throws RepositoryException {
        saveSession();
        session.logout();
    }

    @Override
    public Node createPageNode(final String path, final String pageName, final String pageTitle, final String template,
                               final String referenceType) throws RepositoryException, WCMException {

        Page prodPage = pageManager.create(path, pageName, template, pageTitle, true);
        Node pageNode = prodPage.adaptTo(Node.class);
        Node jcrNode;
        if (prodPage.hasContent()) {
            jcrNode = prodPage.getContentResource().adaptTo(Node.class);
        } else {
            jcrNode = pageNode.addNode("jcr:content", "cq:PageContent");
        }
        jcrNode.setProperty("sling:resourceType", referenceType);

        return jcrNode;
    }

    @Override
    public void addComponent(final Node pageNode, final String key, final JSONObject json, final ComponentTypeEnum type,
                             int counter) throws JSONException, RepositoryException {

        IComponent component = ComponentFactory.createComponent(type);
        if(component != null) {
            component.setProperties(pageNode, key, json, counter);
        }
    }
}
