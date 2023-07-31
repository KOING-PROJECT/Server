package com.koing.server.koing_server.controller.chat;

import com.koing.server.koing_server.service.chat.ChatRoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ChatRoom", description = "ChatRoom API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-room")
public class ChatRoomController {

    private final Logger LOGGER = LoggerFactory.getLogger(ChatRoomController.class);
    private final ChatRoomService chatRoomService;

//    @ApiOperation("ChatRoom : 채팅방을 개설합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "ChatRoom : 채팅방 개설 성공"),
//            @ApiResponse(code = 402, message = "채팅방 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
//            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
//            @ApiResponse(code = 404, message = "해당 투어 신청서를 찾을 수 없습니다."),
//            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
//    })
//    @PostMapping("/create")
//    public SuperResponse createChatRoom(@RequestBody ChatRoomCreateDto chatRoomCreateDto) {
//        LOGGER.info("[ChatRoomController] 채팅방 개설 시도");
//
//        SuperResponse createChatRoomResponse;
//        try {
//            createChatRoomResponse = chatRoomService.createChatRoom(chatRoomCreateDto);
//        } catch (BoilerplateException boilerplateException) {
//            return ErrorResponse.error(boilerplateException.getErrorCode());
//        } catch (Exception exception) {
//            System.out.println(exception);
//            System.out.println(exception.getMessage());
//            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
//        }
//        LOGGER.info("[ChatRoomController] 채팅방 개설 성공");
//
//        return createChatRoomResponse;
//    }


//    @ApiOperation("ChatRoom : 해당 채팅방을 가져옵니다.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "ChatRoom : 해당 채팅방 조회 성공"),
//            @ApiResponse(code = 404, message = "해당 채팅방을 찾을 수 없습니다."),
//            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
//    })
//    @GetMapping("/{roomId}")
//    public SuperResponse getChatRoom(@PathVariable("roomId") String roomId) {
//        LOGGER.info("[ChatRoomController] 채팅방 조회 시도");
//
//        SuperResponse getChatRoomResponse;
//        try {
//            getChatRoomResponse = chatRoomService.getChatRoom(roomId);
//        } catch (BoilerplateException boilerplateException) {
//            return ErrorResponse.error(boilerplateException.getErrorCode());
//        } catch (Exception exception) {
//            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
//        }
//
//        LOGGER.info("[ChatRoomController] 채팅방 조회 성공");
//
//        return getChatRoomResponse;
//    }

}
