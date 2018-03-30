## spring boot test use embedded mongodb

### 主要配置
- build.gradle

```
dependencies {
	compile('org.springframework.cloud:spring-cloud-starter-config')
	compile('org.springframework.boot:spring-boot-starter-data-mongodb-reactive')

	compileOnly('org.projectlombok:lombok')
	
	testCompile('org.springframework.boot:spring-boot-starter-test')
	testCompile('io.projectreactor:reactor-test')
	testCompile("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
}
```
- bootstrap.yml（可选）

```yml
spring.mongodb.embedded.version: 3.4.10
```

### java
- Person.java

```java
public class Person {
    private String username;
    private int age;
    private String email;
    private String addr;
}
```
- PersonRepositoryCustom.java

```
public Mono<Person> updateEmailAndAddrByUsername(String username, Person person);
```

- PersonRepository.java

```java
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;
    
    @Override
    public Mono<Person> updateEmailAndAddrByUsername(String username, Person person) {
        return mongoTemplate.findAndModify(new Query(Criteria.where("username").is(username)),
                new Update().set("email", person.getEmail()).set("addr", person.getAddr()),
                FindAndModifyOptions.options().returnNew(true), Person.class);
    }
```