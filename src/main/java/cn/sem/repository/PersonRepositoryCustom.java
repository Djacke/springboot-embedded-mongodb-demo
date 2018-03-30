package cn.sem.repository;

import cn.sem.model.Person;
import reactor.core.publisher.Mono;

public interface PersonRepositoryCustom {
    public Mono<Person> updateEmailAndAddrByUsername(String username, Person person);
}
