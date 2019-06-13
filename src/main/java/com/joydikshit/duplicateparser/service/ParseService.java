package com.joydikshit.duplicateparser.service;

import com.joydikshit.duplicateparser.businessobjects.Person;

import java.util.List;

public interface ParseService {
    List<Person> parseDuplicates(String filename);

    List<Person> parseNonDuplicates(String filename);
}
