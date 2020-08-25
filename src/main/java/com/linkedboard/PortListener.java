package com.linkedboard;

import org.apache.catalina.Host;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.linkedboard.utils.GetPropertyUtils;

@Component
public class PortListener implements ApplicationListener<ServletWebServerInitializedEvent> {
    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
    	String docbase = GetPropertyUtils.getProperty("tomcat.docbase.dir");
    	String hostName = GetPropertyUtils.getProperty("tomcat.host.name");
    	
        ServletWebServerApplicationContext applicationContext = event.getApplicationContext();
        
        TomcatWebServer tomcatWebServer = (TomcatWebServer) applicationContext.getWebServer();
        Tomcat tomcat = tomcatWebServer.getTomcat();
        Host host = tomcat.getHost(); 
        host.setName(hostName);
        
        tomcat.addWebapp(host, "/upload", docbase);
    }
}