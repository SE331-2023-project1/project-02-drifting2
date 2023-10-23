package com.drifting2.projectbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentHistoryMessageDTO {
    Long id;
    String message;
    String timeSent;
    Boolean sentFromAdvisor;
    
}
