# Simple Application that would use JSON to create an AEM page with components

This is a project is a proof of concept (POC) to create an AEM page from  a JSON endpoint programatically. This is only the initial phase as the main goal is to create the whole structure and pages from a json-formatted sitemap. The codes are targeted to be flexible and only needs a few codes/classes changes to customise for own purpose.

A simple servlet was also created as an entry point of the service which takes 2 inputs:
* app - The target AEM application/site to add the page (Default: jsontoaem)
* endPoint - The json endpoint that provides the json array to use. A separate project was created that uses MongoDB/ExpressJS/NodeJS as the provider (See: https://github.com/LouelAlbertSaclot/MongoDBAsJsonEndpointForAEM)

## Modules
The main parts of the application are:
* core: Java bundle containing the OSGi Service, Models and Servlet for this application
* ui.apps: contains the /apps (and /etc) parts of the project
* ui.content: contains content using the components from the ui.apps (currently empty)

## TODOs for Future
* Extend code to create entire structure from a sitemap.

## How to build
To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with  

    mvn clean install -PautoInstallPackage
    
Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish
    
Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle


## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html
