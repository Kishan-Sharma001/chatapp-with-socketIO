package com.singhsoft.chatapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SocketIOConfig {
	

    @Value("${socket-server.host}")
    private String host;

    @Value("${socket-server.port}")
    private Integer port;
    
    private SocketIOServer server;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        server = new SocketIOServer(config);
        server.start();
        return server;
    }
    
    @PreDestroy
    public void stopSocketServer() {
        this.server.stop();
    }

}
