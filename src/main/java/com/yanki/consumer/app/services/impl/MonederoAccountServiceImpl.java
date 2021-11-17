package com.yanki.consumer.app.services.impl;

import com.yanki.consumer.app.models.dao.MonederoAccountDao;
import com.yanki.consumer.app.models.documents.MonederoAccount;
import com.yanki.consumer.app.services.MonederoAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@Service
public class MonederoAccountServiceImpl implements MonederoAccountService {
    @Autowired
    MonederoAccountDao dao;
    @Override
    public Mono<MonederoAccount> create(MonederoAccount m) {
        log.info("Agregando cuenta");
        return dao.save(m);
    }

    @Override
    public Flux<MonederoAccount> findAll() {
        log.info("Listar cuentas");
        return dao.findAll();
    }

    @Override
    public Mono<MonederoAccount> findById(String id) {
        log.info("Buscar cuenta por Id");
        return dao.findById(id);
    }

    @Override
    public Flux<MonederoAccount> findByNroPhone(String nroPhone) {
        log.info("Buscar cuenta por numero de celular");
        return dao.findByNroPhone(nroPhone);
    }

    @Override
    public Mono<MonederoAccount> update(MonederoAccount m) {
        return dao.save(m);
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return dao.findById(id)
                .flatMap(money -> dao.delete(money).then(Mono.just(Boolean.TRUE)))
                .defaultIfEmpty(Boolean.FALSE);
    }
}
