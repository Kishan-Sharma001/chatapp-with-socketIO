package com.singhsoft.chatapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singhsoft.chatapp.model.Member;
import com.singhsoft.chatapp.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository userRepo;
		
	public List<Member> getAllUsers() {
		
		return userRepo.findAll();
	}

	public void saveUser(Member user) {
		Member saveduser = Member.builder().username(user.getUsername()).build();
		
		userRepo.save(saveduser);
		
	}

}
