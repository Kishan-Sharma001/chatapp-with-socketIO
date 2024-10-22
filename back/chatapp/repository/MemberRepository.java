package com.singhsoft.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.singhsoft.chatapp.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer>{
	
}