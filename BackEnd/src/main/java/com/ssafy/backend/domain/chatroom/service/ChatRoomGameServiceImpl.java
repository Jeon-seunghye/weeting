package com.ssafy.backend.domain.chatroom.service;

import com.ssafy.backend.domain.chatroom.dto.ChatRoomDto;
import com.ssafy.backend.domain.chatroom.dto.ChatRoomGameResultDto;
import com.ssafy.backend.domain.chatroom.dto.ChatRoomUserInfo;
import com.ssafy.backend.domain.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomGameServiceImpl implements ChatRoomGameService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public LocalTime roomStatusModify(String chatRoomId) {
        String key = "chatRoom:" + chatRoomId;

        ChatRoomDto roomInfo = (ChatRoomDto) redisTemplate.opsForValue().get(key);
        System.out.println(roomInfo);
        if (roomInfo == null) {
            throw new IllegalStateException("채팅방 정보를 불러올 수 없습니다 ㅠㅠ");
        }

        ChatRoomDto.RoomStatus currentStatus = roomInfo.getRoomStatus();
        LocalTime currentTime = LocalTime.now();
        LocalTime futureTime = null;

        switch (currentStatus) {
            case waiting:
                roomInfo.setRoomStatus(ChatRoomDto.RoomStatus.allready);
                break;
            case allready:
                roomInfo.setRoomStatus(ChatRoomDto.RoomStatus.wordsetting);
                futureTime = currentTime.plusSeconds(30);
                break;
            case wordsetting:
                roomInfo.setRoomStatus(ChatRoomDto.RoomStatus.wordfinish);
                break;
            case wordfinish:
                roomInfo.setRoomStatus(ChatRoomDto.RoomStatus.start);
                futureTime = currentTime.plusSeconds(240);
                break;
            case start:
                roomInfo.setRoomStatus(ChatRoomDto.RoomStatus.end);
                break;
            case end:
                roomInfo.setRoomStatus(ChatRoomDto.RoomStatus.waiting);
                break;
            default:
                throw new IllegalStateException("방 상태 변경 중 에러 발생 !");
        }

        redisTemplate.opsForValue().set(key, roomInfo);
        if (currentStatus == ChatRoomDto.RoomStatus.allready || currentStatus == ChatRoomDto.RoomStatus.wordfinish) {
            return futureTime;
        } else {
            return currentTime;
        }
    }


    @Override
    public void readyStatusTrans(String chatRoomId,
                                 User user) {
        String key = "chatRoom:" + chatRoomId;

        ChatRoomDto roomInfo = (ChatRoomDto) redisTemplate.opsForValue().get(key);
        if (roomInfo == null) {
            throw new IllegalStateException("채팅방 정보를 불러올 수 없습니다 ㅠㅠ");
        }

        for (ChatRoomUserInfo userInfo : roomInfo.getRoomUsers()) {
            if (userInfo.getId().equals(user.getId())) {
                Boolean userReadyStatus = userInfo.getReady();

                userInfo.setReady(!userReadyStatus);

                redisTemplate.opsForValue().set(key, roomInfo);

                break;
            }
        }
    }

    @Override
    public List<ChatRoomGameResultDto> gameResult(String chatRoomId) {
        String key = "chatRoom:" + chatRoomId;

        ChatRoomDto roomInfo = (ChatRoomDto) redisTemplate.opsForValue().get(key);

        if (roomInfo == null) {
            throw new IllegalStateException("채팅방 정보를 불러올 수 없습니다.");
        }

        List<ChatRoomGameResultDto> results = new ArrayList<>();

        for (ChatRoomUserInfo userInfo : roomInfo.getRoomUsers()) {
            redisTemplate.delete("similar:" + userInfo.getNickname());
            redisTemplate.delete("forbidden:" + userInfo.getNickname());

            ChatRoomGameResultDto resultDto = ChatRoomGameResultDto.builder()
                    .id(userInfo.getId())
                    .nickname(userInfo.getNickname())
                    .score(userInfo.getScore())
                    .isAlive(userInfo.getIsAlive())
                    .build();
            results.add(resultDto);
        }

        results.sort(Comparator.comparing(ChatRoomGameResultDto::getIsAlive).reversed()
                .thenComparing(Comparator.comparing(ChatRoomGameResultDto::getScore).reversed()));

        return results;
    }


    @Override
    public void gameInitialize(String chatRoomId) {
        String key = "chatRoom:" + chatRoomId;

        ChatRoomDto roomInfo = (ChatRoomDto) redisTemplate.opsForValue().get(key);

        if (roomInfo == null) {
            throw new IllegalStateException("채팅방 정보를 불러올 수 없습니다.");
        }

        for (ChatRoomUserInfo userInfo : roomInfo.getRoomUsers()) {
            userInfo.setReady(false);
            userInfo.setWord("");
            userInfo.setScore(0.0F);
            userInfo.setIsAlive(true);
        }

        redisTemplate.opsForValue().set(key, roomInfo);
    }


    public void forbiddenWordSetting(String chatRoomId, User user) {

    }




}
