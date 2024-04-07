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
- To add voting host, use `http://localhost:8080/admin/create-voting-host/` username `host` and password `host`, or you
  can use

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
  - Run `java -jar build/libs/vote-film-1.2.1.jar`
  - Or just double-click on the jar file

- #### Use precompiled jar
  - Download the jar from the [releases](https://github.com/ikvict07/vote-film/releases)
  - Run `java -jar vote-film-1.2.1.jar`
    - Or just double-click on the jar file

- #### Use Docker Repo
    - Install and run Docker
  - Run `docker pull ikvict/choose-film:1.2.1`
  - Run `docker run -p 8080:8080 ikvict/choose-film:1.2.1`

- #### Use Dockerfile
    - Clone the repository
    - Open the terminal and go to the project directory
    - Run `docker build -t choose-film .`
    - Run `docker run -p 8080:8080 choose-film`

### What patterns are used in the project?

- **_MVC_** - ```src/main/java/org/fiit/votefilm/controller``` - all
  controllers, ```src/main/java/org/fiit/votefilm/model``` - all models, ```src/main/resources/templates``` - all views
- **_DAO_** - ```src/main/java/org/fiit/votefilm/repository``` - all repositories
- **_DTO_** - ```src/main/java/org/fiit/votefilm/dto``` - all DTOs
- **_Singleton_** - ```src/main/java/org/fiit/votefilm/service``` - all services are singletons
- **_Abstract Factory_** - ```src/main/java/org/fiit/votefilm/service/apiFilm/FilmFactory.java``` - factory for creating
  film finders
- **_Facade_** - ```src/main/java/org/fiit/votefilm/service/apiFilm/FindFilmService.java``` - facade for film finding
- **_Strategy_** - ```src/main/java/org/fiit/votefilm/service/apiFilm/FilmFinder.java``` - interface for film
  finders, ```src/main/java/org/fiit/votefilm/service/apiFilm/FindFilmService.java``` - context for film finders
- **_Dependency Injection_** - All services are injected into controllers
- **_Aggregation_** - ```src/main/java/org/fiit/votefilm/dto/OMDBResponse.java``` - list of ratings inside dto
- **_Composition_** - ```src/main/java/org/fiit/votefilm/model/users/AbstractUser.java``` - list of roles inside user

### Project Conditions:

- [x] Own Exceptions - ```src/main/java/org/fiit/votefilm/exceptions```
- [x] GUI divided from logic - [look MVC](#what-patterns-are-used-in-the-project)
- [x] Explicitly used multithreading - ```src/main/java/org/fiit/votefilm/service/apiFilm/FindFilmService.java```
- [x] Using generics - ```src/main/java/org/fiit/votefilm/service/apiFilm/ApiResponseHandler.java```
- [x] Explicitly used RTTI - ```src/main/java/org/fiit/votefilm/controller/RepresentationController.java```
- [x] Using nested classes - ```src/main/java/org/fiit/votefilm/dto/OMDBResponse.java```
- [x] Using lambda expressions - almost everywhere,
  example ```src/main/java/org/fiit/votefilm/service/VotingLogic.java```
- [x] Using default methods in
  interfaces - ```src/main/java/org/fiit/votefilm/repository/users/VoterUserRepository.java```
- [ ] AspectJ
- [x] Serialization used - ```src/main/java/org/fiit/votefilm/service/apiFilm/FilmFactory.java```