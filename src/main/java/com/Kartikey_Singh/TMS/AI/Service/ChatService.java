package com.Kartikey_Singh.TMS.AI.Service;

import com.Kartikey_Singh.TMS.AI.RequestDTO.Query;
import com.Kartikey_Singh.TMS.entity.enums.UserRoles;

import java.util.UUID;

public interface ChatService {

    String Chat(String message, String conversationId, UUID userId, UserRoles Role);
}
