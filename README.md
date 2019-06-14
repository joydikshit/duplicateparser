# duplicateparser

This is a simple Spring Boot application (Maven project) that exposes Restful endpoints that return an array of JSON elements that are parsed
from two files 'normal.csv' and 'advanced.csv' which can be viewed under the duplicateparser/src/main/resources directory.

To use this application and view the duplicates (and the non-duplicates), all you need to do is run the maven command to package
and then run the Spring Boot application startups a Tomcat server on port 8080, and then you can access the 
localhost:8080/duplicates/normal (for normal.csv)
and 
localhost:8080/duplicates/advanced (for advanced.csv)

Similarly, non-duplicates can be accessed via the endpoints 
localhost:8080/nonduplicates/normal (for normal.csv)
and 
localhost:8080/nonduplicates/advanced (for advanced.csv)

Steps

1. Clone the repo.
2. Make sure Maven is installed on your work station. If you are using Mac and have homebrew installed then simply type
   in brew install maven on your Terminal program. For Windows please look at the following link (https://www.mkyong.com/maven/how-to-install-maven-in-windows/)  
   NOTE: Homebrew can be installed by pasting the following in your Terminal
   /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
3. After Maven is installed, check that it is installed correctly by typing: mvn --version
4. After that at the root of the project type: mvn package
5. After that simply type: mvn spring-boot:run
6. This should bring up the Spring Boot application and should start listening on localhost:8080 assuming the port is available
7. After that go to your browser http://localhost:8080/duplicates/normal and http://localhost:8080/duplicates/advanced to view the duplicates
   in both files. If you need to view the JSON in a pretty format you can install the JSONView for Chrome or similar tools for
   the browser you are using. JSONView can be accessed at https://chrome.google.com/webstore/detail/jsonview/chklaanhfefbnpoihckbnefhakgolnmc/related?hl=en
8. After that you can access non-duplicates by the endpoints http://localhost:8080/nonduplicates/normal and 
   http://localhost:8080/nonduplicates/advanced
   
