package com.singhsoft.chatapp.socket;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.singhsoft.chatapp.model.Message;

@Configuration
public class SocketModule {

    private final SocketIOServer server;
    private final SocketService socketService;
    
    private final Map<String,SocketIOClient> clients = new HashMap<>();
    
//    private final Map<String, SocketIOClient> clients = new ConcurrentHashMap<>();

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("register", String.class, (client, userId, ackRequest) -> onRegister(client, userId));
      
        server.addEventListener("send_message", Message.class, onChatReceived());
        
//        server.addEventListener("send_private_message",  Message.class, onPrivateChatReceived());
        
      server.addEventListener("send_private_message",  Message.class, onChatReceived());

    }


    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            socketService.saveMessage(senderClient, data);
        };
    }
    
//    
//    private DataListener<Message> onPrivateChatReceived() {
//        return (senderClient, data, ackSender) -> {
//        	
//             
//            socketService.savePrivateMessage(senderClient, data,clients);
////            socketService.saveMessage(senderClient, data);
//        };
//    }


    private ConnectListener onConnected() {
        return (client) -> {

            var params = client.getHandshakeData().getUrlParams();
            String room = params.get("room").stream().collect(Collectors.joining());
            String username = params.get("username").stream().collect(Collectors.joining());
            client.joinRoom(room);
            clients.put(username, client); 
            socketService.saveInfoMessage(client, String.format( "%s joined to chat", username), room);
            

        };

    }

    private DisconnectListener onDisconnected() {
        return client -> {
            var params = client.getHandshakeData().getUrlParams();
            String room = params.get("room").stream().collect(Collectors.joining());
            String username = params.get("username").stream().collect(Collectors.joining());
//            socketService.saveInfoMessage(client, String.format("%s left to chat", username), room);

        };
    }
    
   
  private void onRegister(SocketIOClient client, String userId) {
	  if (clients.get(userId) != null) {
  clients.put(userId, client);
    }
}
    
}

