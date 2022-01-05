## Description
This is a test project for trying out Spring Boot's multithreading and features like retrieving zipped content.
To see how the program works, please check [this](https://github.com/lukaslt1993/files/blob/master/src/test/java/com/github/lukaslt1993/files/FilesControllerTest.java) integration
test or have a look into **Workflow** and **Result of the processing** chapters

## Workflow
1. Controller accepts a multifile input
2. Each file is then processed in a multithread fashion (one thread for each file) and the result of the processing is saved as a single zip file
3. The zip file is then returned in the response of the controller

## Result of the processing
We take a Latin alphabet, split it into intervals of letters and group into files - one file for each interval. Then in the each file we list the words falling into the interval.
The word falls into the interval if it starts with a letter that falls into the interval. We also print a number next to the word denoting how many times does the word appear in the input
