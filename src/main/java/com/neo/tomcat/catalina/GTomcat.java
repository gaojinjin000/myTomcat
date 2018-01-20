package com.neo.tomcat.catalina;

import com.neo.tomcat.http.GRequest;
import com.neo.tomcat.http.GResponse;
import com.neo.tomcat.http.GServlet;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;

/**
 * User: GaoJinjin
 * Data: 2018/1/18 10:30
 * Comment:GTomcat
 */
public class GTomcat {

    private ServerSocket serverSocket = null;

    private static HashMap<String, GServlet> hashMap = new HashMap<String, GServlet>();
    //初始化,加载解析web.xml文件
    private void init() throws Exception {
        Properties properties = new Properties();

        String basePath = this.getClass().getResource("/").getPath();

        InputStream inStream = new FileInputStream(basePath + "web.properties");
        properties.load(inStream);
        for (Object o : properties.keySet()) {
            String key = o.toString();
            if (key.endsWith(".url")) {
                String servletName = key.replaceAll("\\.url$", "");
                String servletClass = properties.getProperty(servletName + ".className");
                GServlet gServlet = (GServlet) Class.forName(servletClass).newInstance();
                String url = properties.getProperty(key);
//                System.out.println(url);
                hashMap.put(url, gServlet);
            }

        }

    }

    private void process(Socket socket) {

        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            GRequest request = new GRequest(inputStream);

            GResponse response = new GResponse(outputStream);
            System.out.println("response::::::::" + response);
            //根据url调用相应的类
            String url = request.getUrl();
            System.out.println("url:::" + url);
            System.out.println("hashMap:" + hashMap.toString());
            if (hashMap.containsKey(url)) {
                System.out.println(hashMap.get(url));
                hashMap.get(url).service(request, response);
            } else {
                response.write("HTTP/1.1 404 not found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>错误</h1>");
            }

            outputStream.flush();
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void start() throws Exception {
        init();
        serverSocket = new ServerSocket(11101, 10, InetAddress.getByName("127.0.0.1"));

        while (true) {
            Socket socket = serverSocket.accept();
//            process(socket);
            new Thread(new task(socket)).start();
//            invoke(socket);
        }
    }
//
//    private void invoke(final Socket socket) {
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("curentThread:" + Thread.currentThread().getName());
//                        process(socket);
//                    }
//                }
//        );
//    }


    static class task implements Runnable {
        Socket socket;

        public task(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("curentThread:" + Thread.currentThread().getName());
            GTomcat gTomcat = new GTomcat();
            gTomcat.process(socket);
        }
    }


    public static void main(String[] args) throws Exception {

        GTomcat gTomcat = new GTomcat();
        gTomcat.start();
//        new GTomcat().start();

    }

}
