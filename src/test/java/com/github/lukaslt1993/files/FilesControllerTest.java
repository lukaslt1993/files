package com.github.lukaslt1993.files;

import com.github.lukaslt1993.files.service.ZipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FilesControllerTest {

    public static final String API_FILES = "/api/files";
    public static final String MULTIPART_FILE_NAME = "files";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ZipService zipService;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenCorrectInput_thenMatchAgainstExpectedResults()
            throws Exception {

        Path path1 = Path.of("src/test/resources/input1.txt");
        Path path2 = Path.of("src/test/resources/input2.txt");

        byte[] bytes = mvc.perform(
                multipart(API_FILES)
                        .file(MULTIPART_FILE_NAME, Files.readAllBytes(path1))
                        .file(MULTIPART_FILE_NAME, Files.readAllBytes(path2)))
                .andReturn().getResponse().getContentAsByteArray();

        Map<String, String> result = zipService.unzip(bytes);

        assertEquals(result.get("A-G.txt"), Files.readString(Path.of("src/test/resources/A-G.txt")));
        assertEquals(result.get("H-N.txt"), Files.readString(Path.of("src/test/resources/H-N.txt")));
        assertEquals(result.get("O-U.txt"), Files.readString(Path.of("src/test/resources/O-U.txt")));
        assertEquals(result.get("V-Z.txt"), Files.readString(Path.of("src/test/resources/V-Z.txt")));

    }

    @Test
    public void whenBadInputName_thenReturn400() throws Exception {
        mvc.perform(
                multipart(API_FILES)
                        .file("badName", "hi".getBytes()))
                .andExpect(status().isBadRequest());
    }

}
