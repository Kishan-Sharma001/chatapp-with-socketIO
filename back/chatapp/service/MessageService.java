package com.singhsoft.chatapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singhsoft.chatapp.model.Message;
import com.singhsoft.chatapp.repository.MessageRepository;

@Service
public class MessageService {


	@Autowired
    private MessageRepository messageRepository;


    public List<Message> getMessages(String room) {
        return messageRepository.findAllByRoom(room);
    }
    

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

	public List<Message> getMessagesByReceiver(String recipient) {
		// TODO Auto-generated method stub
		return messageRepository.findByRecipient(recipient);
	}


	public List<Message> getMessagesBetweenUsers(String username, String recipient) {
		// TODO Auto-generated method stub
		 return messageRepository.findMessagesBetweenUsers(username, recipient);
	}

}
