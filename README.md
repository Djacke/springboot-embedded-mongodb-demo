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
```

- PersonRepositoryTest.java

```java
	@Autowired
    private PersonRepository personRepositoty;
    
    @Before
    public void setUp() {
        personRepositoty.save(Person.builder().username("张三").addr("朝阳区小营路").age(32).build())
                .block(Duration.ofSeconds(1));
    }
    
    @Test
    public void testUpdateAndFind() {
       Person person = personRepositoty.findByUsername("张三").block();
       Assertions.assertThat(person).isNotNull();
       Assertions.assertThat(person.getAddr()).isEqualTo("朝阳区小营路");
        personRepositoty
                .updateEmailAndAddrByUsername("张三",
                        Person.builder().addr("海淀花园路").email("xxx@qq.com").build())
                .block(Duration.ofSeconds(1));
        Person person2 = personRepositoty.findByUsername("张三").block();
        Assertions.assertThat(person2).isNotNull();
        Assertions.assertThat(person2.getAddr()).isEqualTo("海淀花园路");
    }
```    