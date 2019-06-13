package com.joydikshit.duplicateparser.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParserController {

    @GetMapping("/duplicates/{filename}")
    public ResponseEntity parseDuplicates(@PathVariable String filename) {
        return new ResponseEntity("Hello", HttpStatus.OK);
    }
}
