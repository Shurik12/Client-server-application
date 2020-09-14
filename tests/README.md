## Testing client-server application
3 tests for methods: append, get, set, run sequentially. Result writing to log files with timing of executing.
### Description
* create_key_value_1000000.py, create_random_commands.py - create files with testing data.;
* TestAppend.java - test for inserting data to db;
* TestGet.java - test for getting data wich were inserted into db;
* TestSet.java - check changing data in db;
* TestLpop.java - check deleting data from db;
* TestBadCommands.java - check reaction of server on incorrect commands;
* Into logs are stored information about results of tests and time execution.
### Running
1) for running correct-tests: 
```bash
bash testing.sh
```
2) for running load-testыЖ:
```bash
python3 create_key_value_1000000.py
python3 create_random_commands.py
javac TestRandomCommands.java
java TestRandomCommands

javac TestAppend1000000.java
java TestAppend1000000
```