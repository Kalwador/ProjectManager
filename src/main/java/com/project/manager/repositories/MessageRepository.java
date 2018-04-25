package com.project.manager.repositories;

import com.project.manager.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySender(String email);

    List<Message> findByReceiver(String email);
}