package com.joydikshit.duplicateparser.service;

public interface ParseService {
    void parseDuplicates(String filename);

    void parseNonDuplicates(String filename);
}
