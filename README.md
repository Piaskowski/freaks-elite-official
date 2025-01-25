<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a id="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
![obraz](https://github.com/user-attachments/assets/7f48d04b-e19a-48b7-bc94-26f9484a33f2)

<!-- BUILT WITH -->
## Built With:
- [![Spring][Spring Boot Starter]][spring-initializr]
  - [![Spring][Spring Boot Starter Web]][spring-initializr]
  - [![Spring][Spring Boot Starter JDBC]][spring-initializr]
  - [![Spring][Spring Boot Starter Security]][spring-initializr]
  - [![thymeleaf][thymeleaf]][spring-initializr]
- [![MySql][MySQL Connector Java]][MySQL-url]
- [![Jakarta][Jakarta Mail API]][Jakarta-url]

<!-- GETTING STARTED -->
## Getting Started
### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/Piaskowski/freaks-elite-official.git
   ```
2. Install [MySQL](https://dev.mysql.com/downloads/installer/)
3. Create Database "freaks"
4. Create tables:
   ```sql
   CREATE TABLE `band_member` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    `surname` varchar(45) NOT NULL,
    `nick` varchar(45) DEFAULT NULL,
    `description` mediumtext NOT NULL,
    `facebook_url` varchar(255) DEFAULT NULL,
    `instagram_url` varchar(255) DEFAULT NULL,
    `img` varchar(255) NOT NULL,
    `arrangement` int DEFAULT '0',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
  
    CREATE TABLE `concerts` (
      `id` int NOT NULL AUTO_INCREMENT,
      `event_name` varchar(45) DEFAULT NULL,
      `event_url` varchar(120) DEFAULT NULL,
      `date` datetime NOT NULL,
      `spot` varchar(120) DEFAULT NULL,
      `street` varchar(60) NOT NULL,
      `city` varchar(60) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
    CREATE TABLE `news` (
      `id` int NOT NULL AUTO_INCREMENT,
      `title` varchar(45) NOT NULL,
      `content` mediumtext NOT NULL,
      `description` mediumtext NOT NULL,
      `url` varchar(120) DEFAULT NULL,
      `img` varchar(120) NOT NULL,
      `date` date DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
    CREATE TABLE `links` (
      `id` int NOT NULL AUTO_INCREMENT,
      `name` varchar(60) NOT NULL,
      `link` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
    CREATE TABLE `users` (
      `id` int NOT NULL,
      `username` varchar(60) NOT NULL,
      `email` varchar(60) NOT NULL,
      `password` varchar(70) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
    CREATE TABLE `roles` (
      `id` int NOT NULL,
      `name` varchar(45) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
    CREATE TABLE `users_roles` (
      `user_id` int NOT NULL,
      `role_id` int NOT NULL,
      KEY `user_id` (`user_id`),
      KEY `role_id` (`role_id`),
      CONSTRAINT `users_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
      CONSTRAINT `users_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

   ```
 5. Add following records to the `roles` table:
    ```sql
    INSERT INTO `freaks`.`roles` (`id`, `name`) VALUES ('1', 'ADMIN');
    INSERT INTO `freaks`.`roles` (`id`, `name`) VALUES ('2', 'USER');
    ```
 6. Create the admin user:
      - add a user to the `users` table
      - add a record to the `users_roles` table:
        ```sql
        INSERT INTO `freaks`.`users_roles`
          (`user_id`,
          `role_id`)
        VALUES
          ('1',
          '1');
        ```
 7. Add a `application.properties` file to the project and create following params:
      ```java
        spring.datasource.url=jdbc:mysql://localhost:example/freaks
        spring.datasource.username=example
        spring.datasource.password=example
        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
        spring.servlet.multipart.max-file-size=3MB
        spring.servlet.multipart.max-request-size=256MB
        spring.http.multipart.max-file-size=3MB
        spring.http.multipart.max-request-size=256MB
        
        store-images-path=target/classes/static
        
        email-username=example@gmail.com
        email-password=example
        email-recipient=examplee@gmail.com
      ```


<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE -->
## Usage

<!-- ROADMAP -->
## Roadmap

<!-- CONTACT -->
## Contact

Michał Piasecki - [@LinkedIn](https://www.linkedin.com/in/mich-pia/) - mpiasecki.0103@gmail.com

Project Link: [https://github.com/Piaskowski/freaks-elite-official](https://github.com/Piaskowski/freaks-elite-official)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[Spring Boot Starter]: https://img.shields.io/badge/Spring%20Boot%20Starter-4d9900?style=for-the-badge&logo=spring&logoColor=white
[Spring Boot Starter Web]: https://img.shields.io/badge/Spring%20Boot%20Starter%20Web-408000?style=for-the-badge&logo=spring&logoColor=white
[Spring Boot Starter JDBC]: https://img.shields.io/badge/Spring%20Boot%20Starter%20JDBC-408000?style=for-the-badge&logo=spring&logoColor=white
[Spring Boot Starter Security]: https://img.shields.io/badge/Spring%20Boot%20Starter%20Security-408000?style=for-the-badge&logo=spring&logoColor=white
[thymeleaf]: https://img.shields.io/badge/thymeleaf-408000?style=for-the-badge&logo=thymeleaf&logoColor=white
[spring-initializr]: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
[MySQL Connector Java]: https://img.shields.io/badge/MySQL%20Connector/J-0769AD?style=for-the-badge&logo=mysql&logoColor=white
[MySQL-url]: https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
[Jakarta Mail API]: https://img.shields.io/badge/MySQL%20Connector/J-DD0031?style=for-the-badge&logo=gmail&logoColor=white
[Jakarta-url]: https://mvnrepository.com/artifact/com.sun.mail/jakarta.mail
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
