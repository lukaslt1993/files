package com.github.lukaslt1993.files.service;

import com.github.lukaslt1993.files.service.data.ResultMaps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final WordCountService counter;
    private final ZipService zipper;

    public byte[] getZipFile(MultipartFile[] files) throws IOException, ExecutionException, InterruptedException {
        CompletableFuture<ResultMaps>[] maps = new CompletableFuture[files.length];

        for (int i = 0; i < files.length; i++) {
            maps[i] = counter.countWords(files[i]);
        }

        CompletableFuture.allOf(maps).join();
        ResultMaps combinedMaps = new ResultMaps();

        for (CompletableFuture<ResultMaps> map : maps) {
            merge(combinedMaps.getAgMap(), map.get().getAgMap());
            merge(combinedMaps.getHnMap(), map.get().getHnMap());
            merge(combinedMaps.getOuMap(), map.get().getOuMap());
            merge(combinedMaps.getVzMap(), map.get().getVzMap());
        }

        Map<String, String> nameAndContent = new TreeMap<>();
        nameAndContent.put("A-G.txt", getString(combinedMaps.getAgMap()));
        nameAndContent.put("H-N.txt", getString(combinedMaps.getHnMap()));
        nameAndContent.put("O-U.txt", getString(combinedMaps.getOuMap()));
        nameAndContent.put("V-Z.txt", getString(combinedMaps.getVzMap()));

        return zipper.zip(nameAndContent);
    }

    private void merge (Map<String, Integer> to, Map<String, Integer> from) {
        from.forEach((k, v) -> to.merge(k, v, Integer::sum));
    }

    private String getString(Map<String, Integer> wordAndCount) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Integer> entry : wordAndCount.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" ");
            sb.append(entry.getValue());
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

}
