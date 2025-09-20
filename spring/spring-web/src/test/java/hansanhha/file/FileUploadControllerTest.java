package hansanhha.file;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FileUploadController.class)
public class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void uploadMultiFiles_success() throws Exception {
        var file1 = new MockMultipartFile(
                "files",
                "test.txt",
                "text/plain",
                "Hello MultipartFile".getBytes(StandardCharsets.UTF_8));

        var file2 = new MockMultipartFile(
                "files",
                "test2.txt",
                "text/plain",
                "Hello MultipartFile2".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/files/multi-upload")
                        .file(file1)
                        .file(file2))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully uploaded: test.txt")))
                .andExpect(content().string(containsString("successfully uploaded: test2.txt")));
    }

    @Test
    void uploadMultipartFiles_empty() throws Exception {
        mockMvc.perform(multipart("/files/multi-upload"))
                .andExpect(status().isBadRequest());
    }
    
}
