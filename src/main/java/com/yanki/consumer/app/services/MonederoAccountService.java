package com.yanki.consumer.app.services;

import com.yanki.consumer.app.models.documents.MonederoAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MonederoAccountService {
    Mono<MonederoAccount> create(MonederoAccount m);
    Flux<MonederoAccount> findAll();
    Mono<MonederoAccount> findById(String id);
    Flux<MonederoAccount> findByNroPhone(String nroPhone);
    Mono<MonederoAccount> update(MonederoAccount m);
    Mono<Boolean> delete(String id);
}
