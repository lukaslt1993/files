package com.github.lukaslt1993.files.service;

import com.github.lukaslt1993.files.service.data.ResultMaps;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface WordCountService {

    @Async
    CompletableFuture<ResultMaps> countWords(MultipartFile file) throws IOException;

}
