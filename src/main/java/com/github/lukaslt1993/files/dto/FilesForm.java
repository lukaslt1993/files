package com.github.lukaslt1993.files.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FilesForm {

    private MultipartFile[] files;

}
