package com.yanki.consumer.app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonederoTransaction {
    private String id;
    private String originNumber;
    private String destinationNumber;
    private Double amount;
    private Status status;
    private int condition;
    private LocalDateTime createAt;
    public enum Status {
        PENDING,
        REJECTED,
        SUCCESSFUL
    }
}
