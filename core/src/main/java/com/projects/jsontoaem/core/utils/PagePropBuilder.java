package com.projects.jsontoaem.core.utils;

/**
 * Simple builder class to have a more configurable page properties for creating a Page Node.
 *
 * Created by albertsaclot on 30/03/2016.
 */
public class PagePropBuilder {

    private String path;
    private String app;
    private String pageTitle;
    private String pageName;
    private String template;
    private String referenceType;

    private PagePropBuilder(Builder builder) {
        this.path = builder.path;
        this.app  = builder.app;
        this.pageTitle = builder.pageTitle;
        this.pageName  = builder.pageName;
        this.template  = builder.template;
        this.referenceType = builder.referenceType;
    }

    /**
     * Inner Class for creating the properties for the Page Node.
     */
    public static class Builder {
        private String path;
        private String app;
        private String pageTitle;
        private String pageName;
        private String template;
        private String referenceType;

        public Builder(final String app, final String pageTitle) {
            this.app = app;
            this.path = "/content/" + app;
            this.pageTitle = pageTitle;
            this.template = "/apps/" + app + "/templates/page-content";
            this.referenceType = app + "/components/structure/page";
        }

        /**
         * @param pageName The page name
         * @return the inner class for chaining.
         */
        public Builder setPageName(final String pageName) {
            this.pageName = pageName;
            return this;
        }

        /**
         * Option to modify the target application site to add the page.
         *
         * @param app The new target App
         * @return the inner class for chaining.
         */
        public Builder changeTargetApp(final String app) {
            this.app = app;
            return this;
        }

        /**
         * Option to modify the template to use for the new page.
         *
         * @param template The new template to use
         * @return the inner class for chaining.
         */
        public Builder changeTemplate(final String template) {
            this.template = template;
            return this;
        }

        /**
         * Option to modify the referenceType to use for the new page.
         *
         * @param referenceType The new referenceType to use
         * @return the inner class for chaining.
         */
        public Builder changeReferenceType(final String referenceType) {
            this.referenceType = referenceType;
            return this;
        }

        /**
         * @return The completed PageProperty object.
         */
        public PagePropBuilder build() {
            PagePropBuilder pageProp = new PagePropBuilder(this);
            return pageProp;
        }
    }

    public String getPath() {
        return path;
    }

    public String getApp() {
        return app;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getPageName() {
        return pageName;
    }

    public String getTemplate() {
        return template;
    }

    public String getReferenceType() {
        return referenceType;
    }
}
