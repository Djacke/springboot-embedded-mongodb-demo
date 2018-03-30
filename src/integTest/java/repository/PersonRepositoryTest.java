package repository;

import java.time.Duration;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import cn.sem.model.Person;
import cn.sem.repository.PersonRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("integTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = cn.sem.SemApplication.class)
public class PersonRepositoryTest {

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
}
