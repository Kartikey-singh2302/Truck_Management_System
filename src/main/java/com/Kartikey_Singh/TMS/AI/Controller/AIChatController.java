package com.Kartikey_Singh.TMS.AI.Controller;

import com.Kartikey_Singh.TMS.AI.RequestDTO.Query;
import com.Kartikey_Singh.TMS.AI.Service.ChatService;
import com.Kartikey_Singh.TMS.entity.enums.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ai")
public class AIChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody Query query, @RequestHeader String conversationId ,
                                       @RequestHeader UUID userId, @RequestHeader UserRoles Role)
    {
        return ResponseEntity.ok(chatService.Chat(query.getMessage(),conversationId,userId,Role));
    }


}
