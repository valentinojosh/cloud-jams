 # Overview

AWS Lambda function triggered weekly by CloudWatch Events to update a text file containing the global top 5 songs on spotify for this week. This function utilizes Java, JGit, and the Spotify API

## Description

This function was created as a demonstration of a simple AWS Lambda function that clones a repository into the temporary files, reads the data, and updates a file based upon the current data and the Spotify data. The Spotify data is obtained by handing over developer credentials to the Spotify API in a query for the current Top Global Charts data. This data is parsed through and the top five songs are extracted.

## Getting Started

### Dependencies

*maven or gradle (term) 
java 11 corretto
JGIT (v?)
Spotify api
se.spotify (v?+name?)

* Describe any prerequisites, libraries, OS version, etc., needed before installing program.
* ex. Windows 10

### Installing

* Fork or clone this repository
* Create a new Java Project in your desired IDE with the depedencies above
* Copy over the Java code file
* Input the necesarry credientials/links into the areas with values of Foo Bar
* Update the project structure to include a JAR artificat for the entire project
* Build the artifact
* Upload the JAR file into an AWS Lambda function running Java 11 Corretto
* Run via AWS Lambda test with an empty string parameter or schedule a trigger via CloudWatch Events using a cron statement

### Executing program

* Simply run the program through your IDE
* Step-by-step bullets
```
code blocks for commands
```

## Help

Any advise for common problems or issues.
```
command to run if program contains helper info
```

## Authors

Contributors names and contact info

ex. Josh Valentino  
ex. [@DomPizzie](https://twitter.com/dompizzie)

## Version History

* 0.1
    * Initial Release

## License

This project is licensed under the [NAME HERE] License - see the LICENSE.md file for details
