package cn.sem.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import cn.sem.model.Person;
import cn.sem.repository.PersonRepositoryCustom;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;
    
    @Override
    public Mono<Person> updateEmailAndAddrByUsername(String username, Person person) {
        return mongoTemplate.findAndModify(new Query(Criteria.where("username").is(username)),
                new Update().set("email", person.getEmail()).set("addr", person.getAddr()),
                FindAndModifyOptions.options().returnNew(true), Person.class);
    }

}
