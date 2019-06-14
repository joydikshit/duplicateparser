package com.joydikshit.duplicateparser.util;

import com.joydikshit.duplicateparser.businessobjects.Person;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DuplicateParserHelper {

    private static HashMap<String, List<Person>> emailMap = new HashMap<>();
    private static HashMap<String, List<Person>> phoneMap = new HashMap<>();

    public static List<Person> extractDuplicates(List<Person> allrecords) {
        List<Person> duplicates = new ArrayList<>();

        buildDuplicateSet(allrecords);

        //email map is populated, from therein extract the duplicates
        for (String email : emailMap.keySet()) {
            if (emailMap.get(email).size() > 1) {
                duplicates.addAll(emailMap.get(email));
            }
        }

        return duplicates;
    }

    public static List<Person> extractNonDuplicates(List<Person> allRecords) {
        List<Person> nonDuplicates = new ArrayList<>();

        return nonDuplicates;
    }

    private static void buildDuplicateSet(List<Person> allrecords) {
        for (Person person : allrecords) {
            if (emailMap.containsKey(person.getEmail())) {
                //make sure that the other few fields also closely match
                Person existingPerson = emailMap.get(person.getEmail()).stream().findFirst().get();
                if (isAlmostMatchAfterEmailCheck(existingPerson, person)) {
                    emailMap.get(person.getEmail()).add(person);
                }
            } else {
                List<Person> personList = new ArrayList<>();
                personList.add(person);
                emailMap.put(person.getEmail(), personList);
            }
        }
    }

    private static boolean isAlmostMatchAfterEmailCheck(Person firstPerson, Person secondPerson) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        boolean phoneNoMatched = levenshteinDistance.apply(firstPerson.getPhoneNumber(), secondPerson.getPhoneNumber()) < 1;
        boolean firstNameMatched = levenshteinDistance.apply(firstPerson.getFirstName(), secondPerson.getFirstName()) < 3;
        boolean lastNameMatched = levenshteinDistance.apply(firstPerson.getLastName(), secondPerson.getLastName()) < 2;
        boolean addressMatched = levenshteinDistance.apply(firstPerson.getAddress1(), secondPerson.getAddress1()) < 3;
        boolean zipMatched = levenshteinDistance.apply(firstPerson.getZip(), secondPerson.getZip()) < 1;
        boolean stateCodeMatched = levenshteinDistance.apply(firstPerson.getStateCode(), secondPerson.getStateCode()) < 1;

        //The phone number matches too, let's just check either of first name and last name and return true
        if (phoneNoMatched && (firstNameMatched || lastNameMatched)) {
            return true;
        }

        //If both first name and last name matches (along with the exact match on email), then lets return true
        if (firstNameMatched && lastNameMatched) {
            return true;
        }

        //If the address and zip and state matches lets return true
        if (addressMatched && zipMatched && stateCodeMatched) {
            return true;
        }

        return false;
    }
}
