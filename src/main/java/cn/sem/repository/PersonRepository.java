package cn.sem.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import cn.sem.model.Person;
import reactor.core.publisher.Mono;

public interface PersonRepository extends ReactiveMongoRepository<Person, String>, PersonRepositoryCustom {

    public Mono<Person> findByUsername(String username);
}
