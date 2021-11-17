package com.yanki.consumer.app.controllers;

import com.yanki.consumer.app.models.documents.MonederoAccount;
import com.yanki.consumer.app.models.dto.Maestros;
import com.yanki.consumer.app.models.dto.MonederoTransaction;
import com.yanki.consumer.app.redis.MonederoRedisService;
import com.yanki.consumer.app.services.MonederoAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@RestController
@EnableBinding(Sink.class)
@RequestMapping("/yanki")
public class MonederoAccountController {
    @Autowired
    MonederoAccountService service;

    @Autowired
    private MonederoRedisService serviceRedis;

    @StreamListener("input")
    public void consumeMessage(MonederoTransaction monederoTransaction){
        log.info("Consume payload : " + monederoTransaction);
        service.findByNroPhone(monederoTransaction.getDestinationNumber())
                .next()
                .flatMap(ac -> {
                    if(monederoTransaction.getCondition() == 1){
                        ac.setBalance(monederoTransaction.getAmount()+ac.getBalance());
                    }else{
                        ac.setBalance(ac.getBalance() - monederoTransaction.getAmount());
                    }
                    log.info("Actualizando saldo: " + ac.toString());
                    return service.update(ac);
                }).subscribe();
    }

    @GetMapping("/list")
    public Flux<MonederoAccount> findAll(){
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<MonederoAccount> findById(@PathVariable String id){
        return service.findById(id);
    }

    @GetMapping("/find/phone/{number}")
    public Mono<MonederoAccount> findByNroPhone(@PathVariable String number){
        return service.findByNroPhone(number).next();
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<MonederoAccount>> create(@RequestBody MonederoAccount monederoAccount){
        log.info("Buscando ....");
        return service.findByNroPhone(monederoAccount.getNroPhone()).collectList().flatMap(c -> {
                    if(c.isEmpty()){
                        log.info("Enviando nueva cuenta");
                        monederoAccount.setCreateAt(LocalDateTime.now());
                        return service.create(monederoAccount);
                    }else{
                        log.info("ya existe un nuemro con esa cuenta cuenta");
                        return Mono.empty();
                    }
                })
                .map(savedCustomer -> new ResponseEntity<>(savedCustomer , HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<MonederoAccount>> update(@RequestBody MonederoAccount monederoAccount) {
        log.info("buscando tarjeta de credito");
        return service.findById(monederoAccount.getId())
                .flatMap(cc -> {
                    cc.setBalance(monederoAccount.getBalance());
                    cc.setEmail(monederoAccount.getEmail());
                    cc.setImeiPhone(monederoAccount.getImeiPhone());
                    cc.setNroDocument(monederoAccount.getNroDocument());
                    cc.setNroPhone(monederoAccount.getNroPhone());
                    cc.setPhoneOperator(monederoAccount.getPhoneOperator());
                    cc.setTypeDocument(monederoAccount.getTypeDocument());
                    return service.update(cc);
                })
                .map(cc->new ResponseEntity<>(cc , HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable String id) {
        log.info("eliminando ....");
        return service.delete(id)
                .filter(da -> da)
                .map(da -> new ResponseEntity<>("Account Deleted", HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/redis")
    public Mono<ResponseEntity<Long>> putRedis(Maestros maestros){
        log.info("Agregando a RedisDB");
        return serviceRedis.put(maestros)
                .map(m -> new ResponseEntity<>(m , HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/redis")
    public Flux<ResponseEntity<Maestros>> getRedisAll(Maestros maestros){
        log.info("Listar RedisDB");
        return serviceRedis.getAll()
                .map(m -> new ResponseEntity<>(m , HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
