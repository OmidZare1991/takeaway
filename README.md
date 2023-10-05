# **Employee Service**

The Employee Service is a Spring Boot project that allows you to manage employee data. It provides the following functionality:

* Adding a new employee.
* Retrieving an employee by ID.
* Removing an employee by ID.

# Prerequisites

Before you get started, make sure you have the following installed on your machine:

* Docker
* Docker Compose


# Project Details

## GitHub Repository: 

[Employee Service GitHub Repo]: 

(https://github.com/OmidZare1991/takeaway/tree/master)https://github.com/OmidZare1991/takeaway/tree/master

# Installation

To run this project on your local machine, follow these steps:

1. Clone the GitHub repository to your local machine:


        git clone https://github.com/OmidZare1991/takeaway.git


2. Navigate to the project directory:


        cd takeaway


3. Build the project using Maven:


        mvn clean package


4. Build the Docker Image


        In the root directory of the project, you will find a Dockerfile. Use the following command to build a Docker image for the Spring Boot application:


        docker build -t employeeservice .

        This command will create a Docker image named takeaway-app


5. Load the Docker image:


        docker save -o takeaway-app.tar takeaway-app


        docker load -i takeaway-app.tar


6. Run the Application with Docker Compose



         Now, you can run the application along with other services using Docker Compose. In the root directory, you will find a **app.yaml** file.

         Before running Docker Compose, make sure that the names of the Docker images in the **app.yaml** file match the name of the image you built earlier (takeaway-app).


7. Start the Services:



        docker-compose -f compose.yaml up.


        If every thing goes well stop it and run docker-compose -f compose.yaml up -d
        This command will start all the necessary services defined in the compose.yaml file in detached mode.

# Additional Details

        This project uses the Axon Framework to implement the CQRS (Command Query Responsibility Segregation) pattern.


        MongoDB is used as the event store, and Kafka is used to send events instead of an internal bus.


        MySQL is used to store employee entities.


**Security:** 


        Before calling the APIs, users need to sign up or sign in to get authenticated. JWT (JSON Web Tokens) are used for authentication and authorization.



# API Documentation

        You can access the API documentation via Swagger. Once the application is running, visit the following URL in your web browser:


        **http://localhost:8080/swagger-ui.html**

# Alternative Approach To Get Application Docker Tar File  Using Jib

Another approach to generate the Docker TAR file of the application is as follows:


        **Using Maven Package: You can generate the TAR file containing your application files and dependencies by running the following Maven command:**

        mvn clean package


This command utilizes the Jib plugin to create the TAR file.


TAR File Name: The TAR file generated using this approach will have the name "employee-service-management.1.0.0.tar."


Line Break Considerations: Occasionally, when using this approach, you might encounter issues related to line breaks (CRLF, LF, CR) based on your system's configuration.


**Resolving Line Break Issues:**

To resolve line break issues, consider the following steps:


* Configure IDE: Configure your Integrated Development Environment (IDE) to save the entrypoint.sh file located in the src/main/jib directory with LF (Unix-style) line breaks.


* Rebuild: Once you've made the line break configuration changes in your IDE, run mvn clean package again to regenerate the TAR file with the corrected line breaks.




**POM File Configuration:** 

Additionally, you can configure the Jib plugin in your project's POM file to specify the phase at which the TAR file should be built by adding the following configuration:


   <build>
   <plugins>
   <plugin>
   <groupId>com.google.cloud.tools</groupId>
   <artifactId>jib-maven-plugin</artifactId>
   <version><!-- Specify the version of the Jib plugin --></version>
   <executions>
   <execution>
   <phase>package</phase> <!-- Configure the desired build phase -->
   <goals>
   <goal>dockerBuildTar</goal>
   </goals>
   </execution>
   </executions>
   </plugin>
   </plugins>
   </build>



This alternative approach offers an easier way to generate the TAR file for Docker image creation using the Jib plugin, providing flexibility and helping to address line break issues if encountered.



# What does Jib do?


Jib handles all steps of packaging your application into a container image. You don't need to know best practices for creating Dockerfiles or have Docker installed.




![image](https://github.com/OmidZare1991/takeaway/assets/97349246/e3ea7bf1-d190-484f-8317-a7f03843b1f3)



Jib organizes your application into distinct layers; dependencies, resources, and classes; and utilizes Docker image layer caching to keep builds fast by only rebuilding changes.


Jib's layer organization and small base image keeps overall image size small which improves performance and portability.


# Conclusion

You're now ready to run the Takeaway application in a containerized environment.


If you encounter any issues or have questions, feel free to refer to the documentation or seek assistance from the me by sending email to o.zare70@gmail.com.


