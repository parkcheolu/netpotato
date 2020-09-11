package io.github.parkcheolu.netpotato.config;

import io.github.parkcheolu.netpotato.server.NetpotatoChannelInitilizer;
import io.github.parkcheolu.netpotato.server.NetpotatoServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class ServerConfiguration {

    @Autowired
    private ApplicationProperties properties;

    @Bean
    public ChannelGroup clientChannelGroup() {
        return new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    }

    @Bean
    public ChannelInitializer<Channel> clientChannelInitializer() {
        return new NetpotatoChannelInitilizer(clientChannelGroup());
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public EventLoopGroup boosGroup() {
        return new NioEventLoopGroup(properties.getConnectionThreadCount());
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public EventLoopGroup workerGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public NetpotatoServer netpotatoServer() {
        NetpotatoServer netpotatoServer = new NetpotatoServer(properties.getServerPort(),
                clientChannelInitializer());
        netpotatoServer.setBossGroup(boosGroup());
        netpotatoServer.setWorkerGroup(workerGroup());
        return netpotatoServer;
    }
}
