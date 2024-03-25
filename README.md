# Choose Film For Me

## Description

### Project

This is a website for voting on a movie for joint viewing with your favorite content creator.
The site has three roles: Voting Host, Voter and Admin.
Voting Host and Voter have points (which, ideally, should be transferred from the Twitch account, but due to the
problems, points would be manually added from the admin panel, which is available to the Admin).
Voting Hosts will be able to create a voting session, after which a unique Session-ID will be generated, which the hosts
must share with their voters, and they can join the session.
Each voter can write down the movie they would like to watch and the number of points they are willing to spend on it.
When the host decides that the voting should end, they can spin the roulette, where the chances of winning for a movie
depend on the number of votes for that movie.
You can also see information about film (for this purpose, third-party APIs are used).
All actions on the site will require user authentication, for this purpose, endpoints for registration and login will be
available (they will not be protected by authentication).

### Requirements

- **_YOU MUST HAVE SQLite INSTALLED_**
- Java 21

### Technologies

- Database: SQLite
- Frameworks: Spring Boot, Spring Web, Spring Security, H2, Spring Thymeleaf, Spring JPA

### FAQ

- To access the admin panel, go to `http://localhost:8080/admin`
- To add admin user, use `http://localhost:8080/admin/create-admin-user/` username `admin` and password `admin`
- To add voting host, use `http://localhost:8080/admin/create-voting-host/` username `host` and password `host`

### How to run

- #### Using your IDE
    - Clone the repository
    - Open the project in your IDE
    - Run the project
    - Open the browser and go to `http://localhost:8080/`
    - Enjoy!


- #### Compile by yourself using Gradle
    - Clone the repository
    - Open the terminal and go to the project directory
    - Run `./gradlew bootJar`
    - Run `java -jar build/libs/vote-film-1.0.0.jar`

- #### Use precompiled jar
    - Download the jar from the [releases](https://github.com/ikvict07/vote-film/releases/tag/first-working-version)
    - Run `java -jar vote-film-1.0.0.jar`

- #### Use Docker Repo
    - Install and run Docker
    - Run 'docker pull ikvict/choose-film:first'
    - Run 'docker run -p 8080:8080 ikvict/choose-film:first'

- #### Use Dockerfile
    - Clone the repository
    - Open the terminal and go to the project directory
    - Run `docker build -t choose-film .`
    - Run `docker run -p 8080:8080 choose-film`
