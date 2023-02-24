 # Overview

AWS Lambda function triggered weekly by CloudWatch Events to update a text file containing the global top 5 songs on spotify for the week. This function utilizes Java, JGit, and the Spotify API

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

[Josh Valentino](https://valentinojosh.github.io/)  

## Version History

* 0.1
    * Initial Release
    
## Security Note

Currently the necesary secrets are hardcoded. Since this function is low impact with limited permissions, security was not an aspect designed for. AWS Secrets Manager is a paid service that would be a great way to impliment secure storage of secrets.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details
