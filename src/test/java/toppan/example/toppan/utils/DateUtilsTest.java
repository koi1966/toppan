package toppan.example.toppan.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DateUtilsTest {

    @Test
    void getMonthTest() {
        String nameMonth = DateUtils.MonthNamUA.getNameMonth(3);
        Assertions.assertEquals("березень", nameMonth);
    }
}
