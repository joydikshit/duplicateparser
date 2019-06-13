package com.joydikshit.duplicateparser.service.impl;

import com.joydikshit.duplicateparser.businessobjects.Person;
import com.joydikshit.duplicateparser.service.ParseService;
import com.joydikshit.duplicateparser.util.DuplicateParserHelper;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.joydikshit.duplicateparser.util.DuplicateParserConstants.ADVANCEDCSV;
import static com.joydikshit.duplicateparser.util.DuplicateParserConstants.EMPTY_STRING;
import static com.joydikshit.duplicateparser.util.DuplicateParserConstants.NORMALCSV;

@Component
public class ParseServiceImpl implements ParseService {

    private List<Person> normalPersonList = new ArrayList<>();
    private List<Person> advancedPersonList = new ArrayList<>();

    @Override
    public List<Person> parseDuplicates(String filename) {
        //TODO: Use Log4J or other logging framework, Never Syslog, just used here for demo
        System.out.println("Parsing duplicates for file " + filename + ".csv");
        if (filename.equals(NORMALCSV)) {
            return DuplicateParserHelper.extractDuplicates(normalPersonList);
        } else if (filename.equals(ADVANCEDCSV)) {
            return DuplicateParserHelper.extractDuplicates(advancedPersonList);
        }
        return null;
    }

    @Override
    public List<Person> parseNonDuplicates(String filename) {
        //TODO: Use Log4J or other logging framework, Never Syslog, just used here for demo
        System.out.println("Parsing non-duplicates for file " + filename + ".csv");
        if (filename.equals(NORMALCSV)) {
            return DuplicateParserHelper.extractNonDuplicates(normalPersonList);
        } else if (filename.equals(ADVANCEDCSV)) {
            return DuplicateParserHelper.extractNonDuplicates(advancedPersonList);
        }
        return null;
    }

    //TODO: Use caching or less preferably a global object versus using @PostConstruct annotation
    @PostConstruct
    private void readCSV() {
        //read normal.csv
        System.out.println("Reading Normal.csv");
        readSpecifiedCSV(NORMALCSV);

        //read advanced.csv
        System.out.println("Reading Advanced.csv");
        readSpecifiedCSV(ADVANCEDCSV);
    }

    private void readSpecifiedCSV(String filename) {
        List<List<String>> records = new ArrayList<List<String>>();
        try {
            File file = null;
            if (filename.equals(NORMALCSV)) {
                file = ResourceUtils.getFile("classpath:normal.csv");
            } else if (filename.equals(ADVANCEDCSV)) {
                file = ResourceUtils.getFile("classpath:advanced.csv");
            }
            try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
                String[] values;
                while ((values = csvReader.readNext()) != null) {
                    records.add(Arrays.asList(values));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        buildPersonInitialCollection(records, filename);
    }

    private void buildPersonInitialCollection(List<List<String>> records, String fileName) {
        boolean isFirstRecord = true;

        /*
         Couple of things here:
         1. We need not get the column names that is irrelevant
         2. In the CSVs I see there are some blank columns, lets set them as Empty strings and null out the possibility
        of Null Reference Exceptions!
        */
        for (List<String> record : records) {
            if (isFirstRecord) {
                isFirstRecord = false;
            } else {
                Person person = new Person();
                person.setFirstName(record.get(1) != null ? record.get(1).trim() : EMPTY_STRING);//Get rid of nulls and have Empty strings so no need of null-checks
                person.setLastName(record.get(2) != null ? record.get(2).trim() : EMPTY_STRING);
                person.setCompany(record.get(3) != null ? record.get(3).trim() : EMPTY_STRING);
                person.setEmail(record.get(4) != null ? record.get(4).trim() : EMPTY_STRING);
                person.setAddress1(record.get(5) != null ? record.get(5).trim() : EMPTY_STRING);
                person.setAddress2(record.get(6) != null ? record.get(6).trim() : EMPTY_STRING);
                person.setZip(record.get(7) != null ? record.get(7).trim() : EMPTY_STRING);
                person.setCity(record.get(8) != null ? record.get(8).trim() : EMPTY_STRING);
                person.setStateLong(record.get(9) != null ? record.get(9).trim() : EMPTY_STRING);
                person.setStateCode(record.get(10) != null ? record.get(10).trim() : EMPTY_STRING);
                person.setPhoneNumber(record.get(11) != null ? record.get(11).trim() : EMPTY_STRING);
                if (fileName.equals(NORMALCSV)) {
                    normalPersonList.add(person);
                } else if (fileName.equals(ADVANCEDCSV)) {
                    advancedPersonList.add(person);
                }
            }
        }
    }
}
