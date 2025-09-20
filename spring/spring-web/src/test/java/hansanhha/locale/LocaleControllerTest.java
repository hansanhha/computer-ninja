package hansanhha.locale;

import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LocalController.class)
public class LocaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void greet_ko_with_acceptHeader() throws Exception {
        mockMvc.perform(get("/locale/greet")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, Locale.KOREA.getLanguage()))
        .andExpect(status().isOk())
        .andExpect(content().string("안녕"));
    }

    @Test
    void greet_en_with_acceptHeader() throws Exception {
        mockMvc.perform(get("/locale/greet")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, Locale.ENGLISH.getLanguage()))
        .andExpect(status().isOk())
        .andExpect(content().string("hello"));
    }

    @Test
    void greet_non_header() throws Exception {
        mockMvc.perform(get("/locale/greet"))
        .andExpect(status().isOk())
        .andExpect(content().string("hello"));
    }
    
}
