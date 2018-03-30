package cn.sem.model;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document(collection = "person")
@CompoundIndexes({
    @CompoundIndex(name = "username_1", background = true, sparse = true, def = "{'username': 1}")
})
public class Person {
    private String username;
    private int age;
    private String email;
    private String addr;
}
