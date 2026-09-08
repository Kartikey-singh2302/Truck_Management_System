package com.Kartikey_Singh.TMS.AI.Service;

import com.Kartikey_Singh.TMS.AI.Entity.AIContextKeys;
import com.Kartikey_Singh.TMS.AI.Service.RAGService.RagService;
import com.Kartikey_Singh.TMS.AI.Tools.BidTools;
import com.Kartikey_Singh.TMS.AI.Tools.BookingTools;
import com.Kartikey_Singh.TMS.AI.Tools.LoadTools;
import com.Kartikey_Singh.TMS.entity.enums.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;
    private final LoadTools loadTools;
    private final BidTools bidTools;
    private final BookingTools bookingTools;
    private final RagService ragService;
    private final ChatMemory chatMemory;

    @Override
    public String Chat(String Message, String conversationId, UUID userId, UserRoles role) {

        String context = ragService.search(Message)
                .stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        return chatClient.prompt()
                .system("behave as a designated copilot and the dedicated designed AI for this Truck management System")
                .advisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user("""
                          You are an AI assistant for a Truck Management System.
                        
                          Use the following TMS knowledge when relevant.
                          If no relevant knowledge is provided, do not invent TMS policies when knowledge does not contain 
                          the required information.
                        
                        TMS Knowledge:
                        %s
                        
                        User Question:
                        %s
                        """.formatted(context, Message))
                .tools(loadTools, bidTools, bookingTools)
                .toolContext(Map.of(AIContextKeys.USER_ID,userId,AIContextKeys.ROLE,role))
                .call()
                .content();

    }
}
