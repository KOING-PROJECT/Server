package com.koing.server.koing_server.controller.chat;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.MessageType;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.domain.Greeting;
import com.koing.server.koing_server.domain.HelloMessage;
import com.koing.server.koing_server.service.chat.ChatMessageService;
import com.koing.server.koing_server.service.chat.dto.ChatMessageSendDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

//@Api(tags = "ChatMessage")
@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final Logger LOGGER = LoggerFactory.getLogger(ChatMessageController.class);
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping(value = "/chat/enter")
//    @MessageMapping(value = "/hello")
    @SendTo("/sub/greetings")
    public SuperResponse enterChatRoom(ChatMessageSendDto chatMessageSendDto) {
        LOGGER.info("[ChatMessageController] 채팅방 입장 시도");
        chatMessageSendDto.setMessage(chatMessageSendDto.getWriterName() + " 님이 채팅방에 입장하셨습니다.");
        sendMessage(chatMessageSendDto);

        SuperResponse chatMessageSendResponse = saveMessage(chatMessageSendDto, MessageType.ENTER);
        LOGGER.info("[ChatMessageController] 채팅방 입장 및 입장 저장 성공");
        return chatMessageSendResponse;
    }

//    @MessageMapping(value = "/chat/message")
////    @MessageMapping(value = "/hello")
//    @SendTo("/sub/greetings")
//    public SuperResponse sendMessageChatRoom(ChatMessageSendDto chatMessageSendDto) {
//        LOGGER.info("[ChatMessageController] 채팅방에 채팅 시도");
//        sendMessage(chatMessageSendDto);
//
//        SuperResponse chatMessageSendResponse = saveMessage(chatMessageSendDto, MessageType.TALK);
//        LOGGER.info("[ChatMessageController] 채팅방에 채팅 및 채팅 저장 성공");
//        return chatMessageSendResponse;
//    }

    ///////
    @MessageMapping(value = "/chat/message")
//    @MessageMapping(value = "/hello")
    @SendTo("/sub/greetings")
    public Greeting sendMessageChatRoom(HelloMessage helloMessage) {
        LOGGER.info("[ChatMessageController] 채팅방에 채팅 시도");
//        sendMessage(chatMessageSendDto);

//        SuperResponse chatMessageSendResponse = saveMessage(chatMessageSendDto, MessageType.TALK);
        LOGGER.info("[ChatMessageController] 채팅방에 채팅 및 채팅 저장 성공");
//        return chatMessageSendResponse;
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(helloMessage.getName()) + "!");
    }
    ////////////

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    private void sendMessage(ChatMessageSendDto chatMessageSendDto) {
        simpMessagingTemplate.convertAndSend(
//                "sub/chat/room/" + chatMessageSendDto.getRoomId(),
                "/app/hello",
                chatMessageSendDto.getMessage()
        );
    }

    private SuperResponse saveMessage(ChatMessageSendDto chatMessageSendDto, MessageType messageType) {
        SuperResponse chatMessageSendResponse;
        try {
            chatMessageSendResponse = chatMessageService.sendMessage(chatMessageSendDto, messageType);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        return chatMessageSendResponse;
    }

}
