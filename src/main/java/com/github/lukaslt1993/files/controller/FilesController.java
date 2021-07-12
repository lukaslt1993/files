package com.github.lukaslt1993.files.controller;

import com.github.lukaslt1993.files.dto.FilesForm;
import com.github.lukaslt1993.files.exception.FileReadException;
import com.github.lukaslt1993.files.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FilesController {

    private final ResultService service;

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = "application/zip")
    @ResponseBody
    public byte[] getFiles(HttpServletResponse response, @ModelAttribute FilesForm form)
            throws IOException, ExecutionException, InterruptedException {

        if (form.getFiles() == null) {
            throw new FileReadException("Input files are null", HttpStatus.BAD_REQUEST);
        }

        response.setHeader("Content-Disposition", "attachment; filename=output.zip");
        return service.getZipFile(form.getFiles());

    }

}
