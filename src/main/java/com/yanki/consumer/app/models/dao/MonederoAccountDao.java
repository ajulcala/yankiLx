package com.yanki.consumer.app.models.dao;

import com.yanki.consumer.app.models.documents.MonederoAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MonederoAccountDao extends ReactiveMongoRepository<MonederoAccount,String> {
    Flux<MonederoAccount> findByNroPhone(String nroPhone);
}
