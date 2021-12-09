package com.neosoft.poc1.repo;

import com.neosoft.poc1.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    @Sql("classpath:data-test.sql")
    public void saveTest() {
        User createdUser = new User(null, "Bhavik", "Parmar", "400070", LocalDate.of(1997,1,1), LocalDate.of(2021,1,1),false);

        User savedUser = userRepo.save(createdUser);

        System.out.println(savedUser.toString());
        assertThat(savedUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(createdUser);
    }

    @Test
    @Sql("classpath:data-test.sql")
    public void findByIdTest() {
        User expectedUser = new User(22, "Bhavik", "Parmar", "400070", LocalDate.of(1997,1,1), LocalDate.of(2021,1,1),false);

        Optional<User> retrievedUser = userRepo.findById(22);
        assertThat(retrievedUser.get()).usingRecursiveComparison().isEqualTo(expectedUser);

    }

    @Test
    @Sql("classpath:data-test.sql")
    public void existsUserById(){
        Boolean isPresent = userRepo.existsById(22);
        assertThat(isPresent).isTrue();
    }

    @Test
    @Sql("classpath:data-test.sql")
    public void deleteByIdTest() {
        userRepo.deleteById(22);
    }

    @Test
    @Sql("classpath:data-test.sql")
    public void purgeUserByIdTest() {
        userRepo.purgeUserById(22);
    }

}
