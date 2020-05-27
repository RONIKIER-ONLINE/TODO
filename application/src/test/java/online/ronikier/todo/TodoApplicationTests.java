package online.ronikier.todo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TodoApplicationTests {

    @Test
    void contextLoads() {
        String expected = "Implement tests schedule";
        String actual = "Implement tests schedule";
        assertEquals(expected, actual);
    }

}
