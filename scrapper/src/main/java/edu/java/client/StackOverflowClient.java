package edu.java.client;

import edu.java.dto.stackoverflow.QuestionResponse;

public interface StackOverflowClient {
    QuestionResponse fetchQuestion(Long question);
}
