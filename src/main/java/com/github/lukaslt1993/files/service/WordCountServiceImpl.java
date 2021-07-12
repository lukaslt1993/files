package com.github.lukaslt1993.files.service;

import com.github.lukaslt1993.files.service.data.ResultMaps;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class WordCountServiceImpl implements WordCountService {

    @Override
    public CompletableFuture<ResultMaps> countWords(MultipartFile file) throws IOException {
        ResultMaps maps = new ResultMaps();
        String[] words = new String(file.getBytes()).split("[\\s,.;:!?]");

        for (String word : words) {
            if (word.matches("[a-gA-G][a-zA-Z]*")) {
                update(maps.getAgMap(), word);
            } else if (word.matches("[h-nH-N][a-zA-Z]*")) {
                update(maps.getHnMap(), word);
            } else if (word.matches("[o-uO-U][a-zA-Z]*")) {
                update(maps.getOuMap(), word);
            } else if (word.matches("[v-zV-Z][a-zA-Z]*")) {
                update(maps.getVzMap(), word);
            }
        }

        return CompletableFuture.completedFuture(maps);

    }

    private void update(Map<String, Integer> map, String word) {
        map.compute(word, (key, value) -> value == null ? 1 : value + 1);
    }

}
