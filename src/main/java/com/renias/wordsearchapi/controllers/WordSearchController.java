package com.renias.wordsearchapi.controllers;

import com.renias.wordsearchapi.services.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class WordSearchController {

    @Autowired
    private WordGridService wordGridService;

    @GetMapping("/wordgrid")
    public String createWordGrid(@RequestParam int gridSize, @RequestParam String words){
        List<String> wordsToFullfillGrid = Arrays.asList(words.split(","));
        char[][] grid = wordGridService.generateGrid(gridSize, wordsToFullfillGrid);
        String gridToString = "";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gridToString += grid[i][j] + " ";
            }
            gridToString += "\r\n";
        }
        return gridToString;
    }
}
