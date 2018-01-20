package com.neo.tomcat.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: GaoJinjin
 * Data: 2018/1/18 9:10
 * Comment:GRequest
 */
public class GRequest {
    private String url;
    private String method;

    public GRequest(InputStream inputStream) {
        byte[] bytes = new byte[1024];
        int len;
        String content;
        try {
            if ((len = inputStream.read(bytes))>0){
                content = new String(bytes, 0, len);
                String line = content.split("\\n")[0];
                String[] arr = line.split("\\s");

                this.method = arr[0];
                this.url = arr[1];
//                System.out.println(url+":"+method);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return this.url;
    }

    public String getMethod() {
        return this.method;
    }
}
