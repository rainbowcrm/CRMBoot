package com.rainbow;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationManager {

    @Value("${RadsResourcePath}")
    private  String radsResourcePath ;

    @Value("${Country_State_JSON_Path}")
    private String  countryStateJSONPath;


    @Value("${doc_server}")
    private String docServer;

    @Value("${doc_server_host}")
    private String docServerHost;

    @Value("${doc_server_user}")
    private String docServerUser;


    @Value("${doc_server_password}")
    private String docServerPassword;


    @Value("${doc_server_port}")
    private String docServerPort;

    @Value("${Thread_Interval}")
    private String threadInterval;

    @Value("${googlekey}")
    private String googleKey;

    public String getThreadInterval() {
        return threadInterval;
    }

    public void setThreadInterval(String threadInterval) {
        this.threadInterval = threadInterval;
    }

    public String getRadsResourcePath() {
        return radsResourcePath;
    }

    public void setRadsResourcePath(String radsResourcePath) {
        this.radsResourcePath = radsResourcePath;
    }

    public String getCountryStateJSONPath() {
        return countryStateJSONPath;
    }

    public void setCountryStateJSONPath(String countryStateJSONPath) {
        this.countryStateJSONPath = countryStateJSONPath;
    }


    public String getDocServer() {
        return docServer;
    }

    public void setDocServer(String docServer) {
        this.docServer = docServer;
    }

    public String getDocServerHost() {
        return docServerHost;
    }

    public void setDocServerHost(String docServerHost) {
        this.docServerHost = docServerHost;
    }

    public String getDocServerUser() {
        return docServerUser;
    }

    public void setDocServerUser(String docServerUser) {
        this.docServerUser = docServerUser;
    }

    public String getDocServerPassword() {
        return docServerPassword;
    }

    public void setDocServerPassword(String docServerPassword) {
        this.docServerPassword = docServerPassword;
    }

    public String getDocServerPort() {
        return docServerPort;
    }

    public void setDocServerPort(String docServerPort) {
        this.docServerPort = docServerPort;
    }


    public String getGoogleKey() {
        return googleKey;
    }

    public void setGoogleKey(String googleKey) {
        this.googleKey = googleKey;
    }
}
