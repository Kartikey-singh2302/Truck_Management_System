package com.Kartikey_Singh.TMS.AI.Service.RAGService;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RagService {
    private final VectorStore vectorStore;

    public List<Document> search(String query)
    {

         return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .similarityThreshold(0.75)
                        .build()
        );
    }

}
