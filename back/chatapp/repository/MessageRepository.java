package com.singhsoft.chatapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.singhsoft.chatapp.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long>{
	 List<Message> findAllByRoom(String room);

	List<Message> findByRecipient(String recipient);
	
	@Query("SELECT m FROM Message m WHERE (m.sender = :username AND m.recipient = :recipient) OR (m.sender = :recipient AND m.recipient = :username) ORDER BY m.createdDateTime")
	List<Message> findMessagesBetweenUsers(String username, String recipient);
}
