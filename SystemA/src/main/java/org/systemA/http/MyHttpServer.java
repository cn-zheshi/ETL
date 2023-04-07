package org.systemA.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * 根据Java提供的API实现Http服务器
 */
public class MyHttpServer {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // 创建HttpServer服务器
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 10);
        //将 / 请求交给MyHandler处理器处理
        httpServer.createContext("/test", new MyHandler());
        // 提供本院系的课程信息
        httpServer.createContext("/courses", new MyHandler());
        // 提供本院系的学生信息
        httpServer.createContext("/students", new MyHandler());
        // 根据xml文件的信息，选择本院系的课
        // select?courseId=xxx&studentId=xxx
        httpServer.createContext("/select", new MyHandler());
        // 根据xml文件的信息，退选本院系的课
        // unselect?courseId=xxx&studentId=xxx
        httpServer.createContext("/unselect", new MyHandler());
        httpServer.start();
    }
}

class MyHandler implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        String content = "test";
        //设置响应头属性及响应信息的长度
        httpExchange.sendResponseHeaders(200, content.length());
        //获得输出流
        OutputStream os = httpExchange.getResponseBody();
        os.write(content.getBytes());
        os.close();
    }
}
