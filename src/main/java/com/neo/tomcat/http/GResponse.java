package com.neo.tomcat.http;

import java.io.OutputStream;

/**
 * User: GaoJinjin
 * Data: 2018/1/18 9:10
 * Comment:GResponse
 */
public class GResponse {
    private OutputStream outputStream;

    public GResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(String message) throws Exception{
        outputStream.write(message.getBytes());
    }
}
