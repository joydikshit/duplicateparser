package com.joydikshit.duplicateparser.service.impl;

import com.joydikshit.duplicateparser.businessobjects.Person;
import com.joydikshit.duplicateparser.service.ParseService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParseServiceImpl implements ParseService {
    @Override
    public List<Person> parseDuplicates(String filename) {

        return null;
    }

    @Override
    public List<Person> parseNonDuplicates(String filename) {

        return null;
    }
}
