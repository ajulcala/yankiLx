package com.yanki.consumer.app.models.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("moneyAccount")
public class MonederoAccount {
    @Id
    private String id;
    @NotEmpty
    private String typeDocument;
    @NotEmpty
    private String nroDocument;
    @NotEmpty
    private String phoneOperator;
    @NotNull
    private String nroPhone;
    @NotEmpty
    private String imeiPhone;
    @NotEmpty
    private String email;
    @NotNull
    private Double balance;
    private LocalDateTime createAt;
}
