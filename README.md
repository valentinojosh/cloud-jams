 # Overview

AWS Lambda function triggered weekly via CloudWatch Events to update a text file containing the global top five songs on Spotify for the week. This function utilizes Java, JGit, and the Spotify API

## Description

This function was created as a demonstration of a simple AWS Lambda function that clones a repository into the temporary files, reads the data, and updates a file based upon the current data and the Spotify data. The Spotify data is obtained by handing over developer credentials to the Spotify API in a query for the current Top Global Charts data. This data is parsed through and the top five songs are extracted.

## Getting Started

### Dependencies

* Maven 4.0.0 (or suitable build automation tool of your choice)
* Java 11 Corretto
* JGit 5.1.3
* Spotify Web API Java 7.3.0
* Spotify Developer Credentials

### Installing

* Fork or clone this repository
* Create a new Java Project in your desired IDE with the depedencies above
* Copy over the Java code file
* Input the necesarry credientials/links into the areas with values of Foo Bar
* Update the project structure to include a JAR artificat for the entire project
* Build the artifact
* Upload the JAR file into an AWS Lambda function running Java 11 Corretto
* Run via AWS Lambda test with an empty string parameter or schedule a trigger via CloudWatch Events using a cron statement

## Author

[Josh Valentino](https://joshvalentino.com)  

## Version History

* 0.1
    * Initial Release
* 0.2
    * Changed weekly commit from Friday to Monday
    
## Security Note

Currently, the necessary secrets are hardcoded. Because this function has low impact and limited permissions, security was not a consideration during its design. Alternative solutions:

* AWS Secrets Manager
* AWS Key Management Service
* Environment variables

## License

This project is licensed under the MIT License - see the LICENSE.md file for details
