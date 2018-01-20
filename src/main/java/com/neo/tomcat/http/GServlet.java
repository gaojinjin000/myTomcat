package com.neo.tomcat.http;

/**
 * User: GaoJinjin
 * Data: 2018/1/18 9:10
 * Comment:GServlet
 */
public abstract class GServlet {

    public void service(GRequest request,GResponse response) throws Exception {

        if ("GET".equalsIgnoreCase(request.getUrl())) {
            doGet(request,response);
        } else {
            doPost(request,response);
        }
    }

    public abstract void doGet(GRequest request, GResponse response) throws Exception;

    public abstract void doPost(GRequest request, GResponse response) throws Exception;

}
