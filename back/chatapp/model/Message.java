package com.singhsoft.chatapp.model;



import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String content;
	
	@Enumerated(EnumType.STRING)
	private MessageType messageType;
	
	@Column
	private String room;
	
	@Column
	private String sender;
	
	@Column
	private String recipient;
	
	@Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdDateTime;
	
	


}
