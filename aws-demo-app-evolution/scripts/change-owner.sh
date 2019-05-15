#!/bin/bash
mv /opt/tomcat/latest/webapps/monolith-web-app.war /opt/tomcat/latest/webapps/ROOT.war
chown tomcat:tomcat /opt/tomcat/latest/webapps/ROOT.war