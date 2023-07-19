package com.icbc.digitalhuman.Controller;

import com.icbc.digitalhuman.Evaluator.ConversationEvaluator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConversationController {

    ConversationEvaluator conversationEvaluator;

    @PostMapping("/{conversationId}/evaluate")
    public ResponseEntity<String> evaluateConversation(@PathVariable String conversationId, @RequestParam("evaluation") String evaluation) {
        if (evaluation.equalsIgnoreCase("thumbsUp")) {
            conversationEvaluator.thumbsUp(conversationId);
            return ResponseEntity.ok("Thumbs up received for conversation: " + conversationId);
        } else if (evaluation.equalsIgnoreCase("thumbsDown")) {
            conversationEvaluator.thumbsDown(conversationId);
            return ResponseEntity.ok("Thumbs down received for conversation: " + conversationId);
        } else {
            return ResponseEntity.badRequest().body("Invalid evaluation value");
        }
    }
}
