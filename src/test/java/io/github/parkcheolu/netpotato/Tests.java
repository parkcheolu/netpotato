package io.github.parkcheolu.netpotato;

import io.github.parkcheolu.netpotato.client.NettyTestClient;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Tests {

    @Test
    public void clientTest() {
        new NettyTestClient(9999).run();
    }


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
