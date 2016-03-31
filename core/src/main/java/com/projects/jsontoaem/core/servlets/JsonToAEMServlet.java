
package com.projects.jsontoaem.core.servlets;

import com.day.cq.wcm.api.WCMException;
import com.projects.jsontoaem.core.models.ComponentTypeEnum;
import com.projects.jsontoaem.core.services.JsonToAEMConverterService;
import com.projects.jsontoaem.core.utils.ConverterUtils;
import com.projects.jsontoaem.core.utils.PagePropBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Simple Servlet code that migrates RedDot pages to AEM through a SITEMAP json endpoint.
 */
@SuppressWarnings("serial")
@SlingServlet(paths = {"/bin/jsontoaem"}, extensions = {"html"}, methods = "GET", metatype = true)
public class JsonToAEMServlet extends SlingSafeMethodsServlet {

    private final Logger logger = LoggerFactory.getLogger(JsonToAEMServlet.class);

    @Reference
    private JsonToAEMConverterService service;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {

        //StringBuilder hardCodeJSONSample = getHardCodedJSONSample();

        try {
            service.initialize();

            String app = req.getParameter("app");
            String endPoint = req.getParameter("endPoint");
            if (StringUtils.isNotEmpty(app) && StringUtils.isNotEmpty(endPoint)) {

                PagePropBuilder pageProp = new PagePropBuilder.Builder(app, "My Page").setPageName("myPage").build();
                Node pageNode = service.createPageNode(pageProp.getPath(), pageProp.getPageName(),
                        pageProp.getPageTitle(), pageProp.getTemplate(), pageProp.getReferenceType());

                Node parNode;
                if (pageNode.getNodes("par").hasNext()) {
                    parNode = pageNode.getNode("par");
                } else {
                    parNode = pageNode.addNode("par");
                }

                // JSONArray jsonPage = new JSONArray(hardCodeJSONSample.toString());
                JSONArray jsonPage = ConverterUtils.getJSONEndPoint(endPoint);
                for (int i = 0; i < jsonPage.length(); i++) {

                    JSONObject jObj = jsonPage.getJSONObject(i);
                    Iterator<String> iter = jObj.keys();
                    int counter = 0;
                    while (iter.hasNext()) {
                        String key = iter.next();
                        if (ConverterUtils.expectedKeys().contains(key)) {
                            service.addComponent(parNode, key, jObj, ComponentTypeEnum.valueOf(key), counter);
                            counter++;
                        }
                    }
                }

                // Session is complete
                service.saveSessionAndLogout();
                resp.getOutputStream().println("SUCCESS -- Converting JSON to AEM content Completed without errors!");

            } else {
                logger.error("Missing target Application Site!");
                resp.sendError(400, "Missing target Application Site!");
            }

        } catch (LoginException le) {
            logger.error("Login Failed : ", le);
            resp.sendError(401, "Unable to use credentials provided!");
        } catch (RepositoryException re) {
            logger.error("Unable to find target repository : ", re);
            resp.sendError(400, "Unable to find target repository!");
        } catch (JSONException je) {
            logger.error("JSON translation failed : ", je);
            resp.sendError(400, "JSON translation failed!");
        } catch (WCMException we) {
            logger.error("Getting Node failed : ", we);
            resp.sendError(400, "Getting Node failed!");
        } catch (Exception ex) {
            logger.error("Unexpected Exception : ", ex);
            resp.sendError(400, "Unexpected Error! Please review logs.");
        } finally {
            service.sessionLogout();
        }
    }

//    private StringBuilder getHardCodedJSONSample() {
//        StringBuilder hardCodeJSONSample = new StringBuilder();
//        hardCodeJSONSample.append("[");
//        hardCodeJSONSample.append("{title: { text: 'Get extra peace of mind with Delivery Confirmation and Extra Cover when sending your item overseas.' }}");
//        hardCodeJSONSample.append(",");
//        hardCodeJSONSample.append("{text: { text: '<p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>' }}");
//        hardCodeJSONSample.append(",");
//        hardCodeJSONSample.append("{image: { title: 'My Image', fileReference: '/content/dam/geometrixx/travel/train_man_working.jpg', linkUrl: 'http://www.adobe.com/au/marketing-cloud/enterprise-content-management.html', alt:'alternate' }}");
//        hardCodeJSONSample.append(",");
//        hardCodeJSONSample.append("{textimage: { text: '<p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut</p>', alignment: 'right', title: 'My TextImage', fileReference: '/content/dam/projects/media/cover', linkUrl: 'https://www.hackerrank.com/', alt:'alternate' }}");
//        hardCodeJSONSample.append("]");
//        return hardCodeJSONSample;
//    }

}
