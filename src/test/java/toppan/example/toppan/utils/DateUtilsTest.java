package toppan.example.toppan.utils;

import org.hibernate.query.criteria.internal.ParameterContainer;
import org.hibernate.query.criteria.internal.path.AbstractPathImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.nio.file.FileSystems;
import java.nio.file.Path;



@SpringBootTest
public class DateUtilsTest {

    @Test
    void getMonthTest() {
        String nameMonth = DateUtils.MonthNamUA.getNameMonth(3);
        Assertions.assertEquals("березень", nameMonth);
        Path path = FileSystems.getDefault().getPath("C:\\rsc1840\\", "Temp_rubin_Mounth_1841.docx");
        System.out.println(path);

    }
}
