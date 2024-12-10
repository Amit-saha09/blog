README - BlogNest App

Project Overview
This project is a Spring Boot application, which can be run either through IntelliJ IDEA (IDE) or via the command prompt using Maven. 
The project uses application.yml and application-dev.yml for configuration. The application.yml holds the common configurations, while application-dev.yml is used for development-specific settings.

Prerequisites
Before running the application, ensure that you have the following tools installed:

Java: Java Development Kit (JDK) 11 or later (Java 22 in this case).
Maven: Apache Maven for managing dependencies and running the application.

Follow these steps to set up Java and Maven:
Setting up Java:
Download JDK: Download JDK 11 or later from Oracle JDK or OpenJDK.
Set JAVA_HOME Environment Variable:
Open System Properties (right-click This PC > Properties > Advanced system settings).
Go to Environment Variables and create a new System variable with:
Variable name: JAVA_HOME
Variable value: Path to the JDK directory (e.g., C:\Program Files\Java\jdk-22).
Add the JDK’s bin folder to the PATH environment variable, so Java commands work from any directory:
Edit PATH and add: %JAVA_HOME%\bin.

Setting up Maven:
Download Maven: Download Maven from Apache Maven.
Set MAVEN_HOME Environment Variable:
Create a new System variable with:
Variable name: MAVEN_HOME
Variable value: Path to the Maven directory (e.g., C:\Program Files\Apache Maven\apache-maven-3.9.9).
Add Maven’s bin folder to the PATH environment variable:
Edit PATH and add: %MAVEN_HOME%\bin.

Verify Installation:
Open a Command Prompt and run:
		java -version
mvn -version
These commands should return the installed Java and Maven versions.

Configuring the Application
The project uses YAML configuration files for environment-specific settings:
application.yml: Contains common configurations, including the active profile.
application-dev.yml: Contains development-specific configurations, such as database connections and logging settings.
Example Configuration in application.yml:
spring:
  profiles:
    active: dev  # Specifies the active profile (development)

Example Configuration in application-dev.yml:
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_database
    username: your_username
    password: your_password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.PostgreSQLDialect

Here, the active profile is set to dev, which means application-dev.yml will be used.


Running the Application
Option 1: Using IntelliJ IDEA
Import the Project:


Open IntelliJ IDEA.
Select Open and choose the project directory.
IntelliJ should automatically detect it as a Maven project and resolve dependencies.

Run the Application:
In the Maven tool window, locate the Lifecycle section.
Click clean and then install to build the project.
Right-click the main class (BlogApplication.java) and select Run to start the Spring Boot application.

Access the Application:
Once the application is running, open your browser and go to:

	<<http://localhost:8201/api/home>>
 
Option 2: Using Command Line (Maven)
Navigate to the Project Folder:
Open a Command Prompt or PowerShell.

Navigate to the root directory of the project where the pom.xml file is located:
	cd path\to\project
 
Run the Application:
Use Maven to run the application with the following command:
		mvn spring-boot:run
  
Access the Application:
Once the application is running, open your browser and go to:
	<<http://localhost:8201/api/home>>
 
Stop the Application:
Press Ctrl + C to stop the application in the terminal.

Project Structure
src/main/java/com/example/blog: Contains Java source code.

Main application class: BlogApplication.java, which contains the public static void main(String[] args) method to run the Spring Boot application.

src/main/resources: Contains resources like configuration files.
application.yml: Main configuration file.
application-dev.yml: Configuration for the development profile.

This folder also contains the static and templates folders.
static folder - css and jpg files (static folder) 
templates folder - html files

Conclusion
This README provides a guide to setting up and running the Spring Boot project using both IntelliJ IDEA and Maven. Ensure that Java and Maven are correctly set up on your system, and then follow the instructions to run the application either from the IDE or the command line.
Feel free to modify configurations in application.yml and application-dev.yml based on your environment, and remember to set the correct database credentials and other settings.

