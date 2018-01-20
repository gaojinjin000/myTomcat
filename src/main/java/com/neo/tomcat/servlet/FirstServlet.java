package com.neo.tomcat.servlet;

import com.neo.tomcat.http.GRequest;
import com.neo.tomcat.http.GResponse;
import com.neo.tomcat.http.GServlet;

/**
 * User: GaoJinjin
 * Data: 2018/1/18 9:15
 * Comment:FirstServlet
 */
public class FirstServlet extends GServlet {

    @Override
    public void doGet(GRequest request, GResponse response) throws Exception {
        doPost(request, response);
    }

    @Override
    public void doPost(GRequest request, GResponse response) throws Exception {
        response.write("第一个servlet");
    }
}
