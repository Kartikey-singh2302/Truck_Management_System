package com.Kartikey_Singh.TMS.AI.Config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AIConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder)
    {
        return builder
                .defaultSystem("""
                        You are an AI assistant for a
                        Transportation Management System.

                        Help users understand and operate the TMS.
                        Do not invent TMS data.
                        """)
                .build();
    }

    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository chatMemoryRepository)
    {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(20)
                .build();
    }

//        @Bean
//        public SafeGuardAdvisor safeGuardAdvisor() {
//
//            return SafeGuardAdvisor.builder()
//                    .sensitiveWords(List.of(
//                            "hack",
//                            "malware",
//                            "ransomware",
//                            "chutiya"
//                    ))
//                    .failureResponse(
//                            "I can't assist with that request."
//                    )
//                    .build();
//        }



}
