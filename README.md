# Word frequency in files

The program runs sucessfully on Java 8+

To run the program execute the below command:
```
	java -jar WordCount.jar <file1 absolute path> <file2 absolute path>
```
Example: 
```
java -jar WordCount.jar file1.txt file2.txt
```

The prgram runs on 2 threads for each of the file to create in-memory cache of word and its count.
Then 3rd thread executes on completion of previous 2 threads to print in console the desired output.

Output format - <word> <total occurences> = <1st file occurence> + <2nd file occurence>


Scenarios Handled:
1. only 2 input files allowed.
2. file if exist is checked.
3. only text file with format .txt allowed.
4. UTF-8 encoding is validated while using BufferReader.
5. ConcurrentHashMap is used to store word frequency in-memory and ensure it to be thread-safe.
6. Each line of the file is processed one by one instead of loading entire file in-mmemory.
7. Each word has special characters removed and made lowercase.
8. Word Model class is used to create object with word name and its frequency as its members. Which is stored in a list to sort it
   decreasing order of frequency.
