package com.joydikshit.duplicateparser.controllers;

import com.joydikshit.duplicateparser.businessobjects.Person;
import com.joydikshit.duplicateparser.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParserController {

    @Autowired
    private ParseService parseService;

    @GetMapping("/duplicates/{filename}")
    public ResponseEntity<List<Person>> parseDuplicates(@PathVariable String filename) {
        return new ResponseEntity<>(parseService.parseDuplicates(filename), HttpStatus.OK);
    }

    @GetMapping("/nonduplicates/{filename}")
    public ResponseEntity<List<Person>> parseNonDuplicates(@PathVariable String filename) {
        return new ResponseEntity<>(parseService.parseNonDuplicates(filename), HttpStatus.OK);
    }
}
