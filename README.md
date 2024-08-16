# PlanetPulse API 💫

Our platform bridges the gap between investors seeking to support community-driven projects and local
organizations in need of funding and innovative solutions. We foster sustainable local development by offering a
curated selection of impactful projects and a user-friendly experience.

## Overview

This repository contains essential information and guidelines for understanding and contributing to our project. Please read through this README to familiarize yourself with our Definitions Of Done (DoD) and find links to our frontend and backend repositories.

## Definitions Of Done (DoD) ⚙️

The Definitions Of Done (DoD) for our project are aligned as follow:

1. **Authentication & Authorization System:**

   - a) Visitors can access the app without authentication.
   - b) Users must be logged in to publish an article.

2. **Data Collection:**

   - Implement a robust system for collecting and managing data.

3. **Recommendation System:**
   - Develop a recommendation system to enhance the user experience.

### AI Features 🤖

There are various AI features that enhance user experience by providing help depending on the
user's preference such as:

- An AI for generating suggestions
- An AI for streamlining project database searches
- An AI for developing technical solutions

### About the App

Uses the [Java](https://phoenixnap.com/kb/install-java-windows) [Spring Boot](https://spring.io/projects/spring-boot/) framework and [Postgresql](https://www.postgresql.org/)

#### How to use the app ?

#### Requirements

##### JDK installation ☕

- Depending on the OS, [download and install](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html) (Java) JDK 17

- Configure JAVA_HOME

##### PostgreSQL database creation

```shell
    # In postgreSQL CLI, run:
    CREATE DATABASE <DB_NAME>;
    # Then update the env variable "spring_datasource_url" value to : jdbc:postgresql://localhost:5432/{DB_NAME}
```

##### List of environment variables

- AWS_ACCESS_KEY_ID
- AWS_SECRET_ACCESS_KEY
- aws_eventBridge_bus
- aws_region
- aws_s3_bucket
- aws_ses_source
- aws_sqs_queue_url
- firebase_private_key
- spring_datasource_password
- spring_datasource_url
- spring_datasource_username

#### Linter and formatter 📝

##### Coding standard

The coding standard used is Google java style guide

##### Linter

The linter used is checkstyle, configured with gradle.

##### Formatter

The formatter used is Google-java-format, executed with the script format.sh as follows:

```shell
    ./format.sh
```

## Known Issues 🔧

Help us to find bugs !
Put it in the issues :

- [https://github.com/blogify-app/blogify-api/issues](https://github.com/blogify-app/blogify-api/issues)

## How to Contribute 📋

We welcome contributions from the community! If you're interested in contributing to our project, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and ensure they meet the Definitions Of Done.
4. Test your changes thoroughly.
5. Create a pull request, detailing the changes you've made.

## Getting Help 🚀

If you have any questions or need assistance, feel free to reach out to us through the project's GitHub issues.

**Thank you for your contribution to this Project - PlanetPulse 🪐!**
