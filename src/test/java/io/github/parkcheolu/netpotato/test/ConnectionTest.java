package io.github.parkcheolu.netpotato.test;

import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionTest {

    @Test
    public void connectionTest() throws Exception {
        URL url = new URL("http://127.0.0.1:9999");
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStream out = connection.getOutputStream();
        out.write("Hello!".getBytes());
        out.flush();
        InputStream in = connection.getInputStream();
        System.out.println(new String(in.readAllBytes()));
        out.close();
        in.close();
    }
}
