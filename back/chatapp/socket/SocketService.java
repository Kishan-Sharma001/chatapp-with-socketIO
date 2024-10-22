package com.singhsoft.chatapp.socket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.singhsoft.chatapp.model.Message;
import com.singhsoft.chatapp.model.MessageType;
import com.singhsoft.chatapp.service.MessageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocketService {
	

	@Autowired
    private  MessageService messageService;


    public void sendSocketMessage(SocketIOClient senderClient, Message message, String room) {
        for (
                SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent("read_message",
                        message);
              
                
            }
        }
    }
    

    
    public void sendPrivateSocketMessage(SocketIOClient senderClient, Message message, String room,Map<String, SocketIOClient> clients) {
    	
    	
    	String recipient=message.getRecipient();
    	String username=message.getSender();
    	String roomName=message.getRoom();
    	
    	SocketIOClient receipientCLient=clients.get(recipient);
    	
    	if (receipientCLient != null) {
    		receipientCLient.sendEvent("read_private_message",
                     message);
    		
    	}

    }
    
    public void savePrivateMessage(SocketIOClient senderClient, Message message, Map<String, SocketIOClient> clients) {
    	Message msg = new Message();
    	
    	
    	
        Message storedMessage = messageService.saveMessage(Message.builder()
                .messageType(MessageType.CLIENT)
                .content(message.getContent())
                .room(message.getRoom())
                .sender(message.getSender())
                .recipient(message.getRecipient())
                .build());
        sendPrivateSocketMessage(senderClient, storedMessage, message.getRoom(),clients);
    }

    public void saveMessage(SocketIOClient senderClient, Message message) {
    	Message msg = new Message();
    	
    	
    	
        Message storedMessage = messageService.saveMessage(Message.builder()
                .messageType(MessageType.CLIENT)
                .content(message.getContent())
                .room(message.getRoom())
                .sender(message.getSender())
                .recipient(message.getRecipient())
                .build());
        sendSocketMessage(senderClient, storedMessage, message.getRoom());
    }

    public void saveInfoMessage(SocketIOClient senderClient, String message, String room) {
        Message storedMessage = messageService.saveMessage(Message.builder()
                .messageType(MessageType.SERVER)
                .content(message)
                .room(room)
                .build());
        sendSocketMessage(senderClient, storedMessage, room);
    }

}
