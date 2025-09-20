package hansanhha.file;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileSystemUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FileDownloadController.class)
class FileDownloadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Path uploadDir = Paths.get("uploads");

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(uploadDir);

        Path testFile = uploadDir.resolve("test.txt");
        Files.writeString(testFile, "test file");
    }

    @AfterAll
    static void cleanUp() throws IOException {
        FileSystemUtils.deleteRecursively(uploadDir);
    }

    @Test
    void downloadFile_success() throws Exception {
        mockMvc.perform(get("/files/download/test.txt"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test.txt\""))
                .andExpect(content().string("test file"));
    }

    @Test
    void downloadFile_notFound() throws Exception {
        mockMvc.perform(get("/files/download/zzz.txt"))
                .andExpect(status().isNotFound());
    }

}