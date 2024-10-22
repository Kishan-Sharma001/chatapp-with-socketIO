package com.singhsoft.chatapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.singhsoft.chatapp.model.Message;
import com.singhsoft.chatapp.model.Member;
import com.singhsoft.chatapp.service.MessageService;
import com.singhsoft.chatapp.service.MemberService;

@CrossOrigin("*")

@RestController
@RequestMapping("/")

public class ChatController {
	
	@Autowired
	private MemberService userService;
	
	@Autowired
	private MessageService messageService;
	
	@GetMapping("/recipient")
	public List<Member> getAllReceipient(){
		return  userService.getAllUsers();
	}
//	@GetMapping("/messages")
//	public List<Message> getMessages(){
//		return  messageService.getMessage();
//	}
	
	@PostMapping("/register")
	public String registerUser(@RequestBody Member user){
		  userService.saveUser(user);
		  return "user Saved Successfully" ;
	}
	

    @CrossOrigin
    @GetMapping("/{room}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String room) {
        return ResponseEntity.ok(messageService.getMessages(room));
    }
    
    @GetMapping("/private/{recipient}")
    public ResponseEntity<List<Message>> getPrivateMessage(@PathVariable String recipient) {
        return ResponseEntity.ok(messageService.getMessagesByReceiver(recipient));
    }
	
    @GetMapping("/between/{username}/{recipient}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@PathVariable String username, @PathVariable String recipient) {
        List<Message> messages = messageService.getMessagesBetweenUsers(username, recipient);
        return ResponseEntity.ok(messages);
    }
	

	
}
