package com.koing.server.koing_server.domain.chat.repository;

import com.koing.server.koing_server.domain.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
