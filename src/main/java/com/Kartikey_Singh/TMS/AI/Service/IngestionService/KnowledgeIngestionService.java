package com.Kartikey_Singh.TMS.AI.Service.IngestionService;

import com.Kartikey_Singh.TMS.AI.TMSKnowledge;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KnowledgeIngestionService {

    private final VectorStore vectorStore;

    public void ingest(String content, String type, String domain) {

        // Check whether this knowledge already exists
        List<Document> existingDocuments = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(content)
                        .topK(1)
                        .similarityThreshold(0.95)
                        .build()
        );

        if (!existingDocuments.isEmpty()) {
            System.out.println("Knowledge already exists. Skipping ingestion.");
            return;
        }

        Document document = new Document(
                content,
                Map.of(
                        "type", type,
                        "domain", domain
                )
        );

        vectorStore.add(List.of(document));

        System.out.println("Knowledge successfully added to PGVector.");
    }

    public void ingestTmsKnowledge() {

        ingest( TMSKnowledge.LOAD_CANCELLATION_POLICY, "LOAD_POLICY", "LOAD");

        ingest( TMSKnowledge.BID_POLICY, "BID_POLICY", "BID");

        ingest( TMSKnowledge.BOOKING_POLICY, "BOOKING_POLICY", "BOOKING");
    }
}
