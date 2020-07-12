package by.minsk.softeq.kunitski.servise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParserTest {

    @InjectMocks
    private Parser parser;

    @Mock
    private Statistic statistic = new Statistic();

    @BeforeEach
    void setUp() {
     parser = new Parser();
    }

    @Test
    void parseBlankUrl() {
        parser.parse("", 0);
        verify(statistic, times(0)).collectStats("", "");
    }

    @Test
    void parseNotValidDepth(){
        parser.parse("", 9);
        verify(statistic, times(0)).collectStats("", "");
    }

    @Test
    void parseNotValidUrlStartWith()  {
        parser.parse("https:/www.com", 0);
        verify(statistic, times(0)).collectStats("", "");
    }

    @Test
    void parseNotValidUrlHasExclusion() {
        parser.parse("https://www.baeldung.pdf", 0);
        verify(statistic, times(0)).collectStats("", "");
    }

    @Test
    void parseOnceWorking() throws NoSuchFieldException, IllegalAccessException {

        Field field = Parser.class.getDeclaredField("pageCounter");
        field.setAccessible(true);
        field.set(parser, 9999);

        doNothing().when(statistic).collectStats(anyString(), anyString());
        statistic.collectStats("text", "url");

        parser.parse("https://www.baeldung.com", 0);
        verify(statistic).collectStats("text", "url");
    }

}