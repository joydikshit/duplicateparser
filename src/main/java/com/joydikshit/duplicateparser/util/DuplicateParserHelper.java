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

        emailMap.clear();
        phoneMap.clear();

        return duplicates;
    }

    public static List<Person> extractNonDuplicates(List<Person> allRecords) {
        List<Person> duplicates = new ArrayList<>();

        buildDuplicateSet(allRecords);

        //email map is populated, from therein extract the duplicates
        for (String email : emailMap.keySet()) {
            if (emailMap.get(email).size() == 1) {
                duplicates.addAll(emailMap.get(email));
            }
        }

        emailMap.clear();
        phoneMap.clear();

        return duplicates;
    }

    private static void buildDuplicateSet(List<Person> allRecords) {
        for (Person person : allRecords) {
            String email = person.getEmail();
            String phone = person.getPhoneNumber();
            if (emailMap.containsKey(email)) {
                //make sure that the other few fields also closely match
                Person existingPerson = emailMap.get(email).stream().findFirst().get();
                if (isAlmostMatchAfterEmailCheck(existingPerson, person)) {
                    //also lets add it to the phoneMap
                    if (phoneMap.containsKey(existingPerson.getPhoneNumber())) {
                        phoneMap.get(existingPerson.getPhoneNumber()).add(person);
                    } else if (existingPerson.getPhoneNumber().length() > 0) {
                        //phone number is different and is non null let's add it to phone Map, emails taken care of
                        List<Person> personList = new ArrayList<>();
                        personList.add(person);
                        phoneMap.put(existingPerson.getPhoneNumber(), personList);
                    }
                    emailMap.get(email).add(person);
                }
            } else {
                //lets check the phone number from the phone Map
                if (phoneMap.containsKey(phone)) {
                    Person existingPerson = phoneMap.get(phone).stream().findFirst().get();
                    if (isAlmostMatchAfterPhoneCheck(existingPerson, person)) {
                        //here the phone number exists in phone Map, email does not, but existing person email must exist
                        //so let's update the email map as well
                        emailMap.get(existingPerson.getEmail()).add(person);
                        phoneMap.get(phone).add(person);
                    }
                } else {
                    if (phone.length() > 0) {
                        //email has not matched and phone also not matched, let's add it to phoneMap
                        List<Person> personList = new ArrayList<>();
                        personList.add(person);
                        phoneMap.put(phone, personList);
                    }
                }
                if (email.length() > 0) {
                    List<Person> personList = new ArrayList<>();
                    personList.add(person);
                    emailMap.put(email, personList);
                }

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

    private static boolean isAlmostMatchAfterPhoneCheck(Person firstPerson, Person secondPerson) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        boolean emailMatched = levenshteinDistance.apply(firstPerson.getEmail(), secondPerson.getEmail()) < 1;
        boolean firstNameMatched = levenshteinDistance.apply(firstPerson.getFirstName(), secondPerson.getFirstName()) < 3;
        boolean lastNameMatched = levenshteinDistance.apply(firstPerson.getLastName(), secondPerson.getLastName()) < 2;
        boolean addressMatched = levenshteinDistance.apply(firstPerson.getAddress1(), secondPerson.getAddress1()) < 3;
        boolean zipMatched = levenshteinDistance.apply(firstPerson.getZip(), secondPerson.getZip()) < 1;
        boolean stateCodeMatched = levenshteinDistance.apply(firstPerson.getStateCode(), secondPerson.getStateCode()) < 1;

        //The email matches too, let's just check either of first name and last name and return true
        if (emailMatched && (firstNameMatched || lastNameMatched)) {
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
