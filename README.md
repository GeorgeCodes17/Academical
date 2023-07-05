# Academical

### Description
This is a school management software meant for teachers & school administrators. It has features like setting a lesson schedule, a dashboard with information like all your lessons and a link to Teams.

### Technologies
- Java
- Java Swing
- Gradle
- Okta Auth
> This uses a reliable, popular Java GUI package in Java Swing & a comprehensive enterprise-safe authentication platform. So it is a really robust piece of software

### Requirements
- OS X
- Gradle 7.6+ with JVM 17.0.6

### Installation
1. Navigate to root of project
2. Run `gradle build` to install dependencies
3. Ensure Academical API is running & `ACADEMICAL_API_URL` value in config.properties file matches
4. Run `gradle run` to run the app

### Naming Convention
Variables
- static final vars or var is not calling a method/ class/ is a font = THIS_CASE
- everything else = snakeCase