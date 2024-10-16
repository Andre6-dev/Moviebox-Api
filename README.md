# MovieBox API

MovieBox API is a Spring Boot application designed to manage movies, categories, and user ratings. It provides endpoints
for creating, updating, deleting, and retrieving movies, as well as user authentication and authorization using JWT.

## Table of Contents

- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Endpoints](#endpoints)
- [Security](#security)
- [Swagger Documentation](#swagger-documentation)
- [Contributing](#contributing)
- [License](#license)

## Project Structure

The project is organized into several packages:

- `com.devandre.moviebox.movie.application`: Contains the application logic for movies.
- `com.devandre.moviebox.movie.domain`: Contains the domain models for movies.
- `com.devandre.moviebox.movie.infrastructure`: Contains the infrastructure code for movies, including controllers.
- `com.devandre.moviebox.shared`: Contains shared utilities and constants.
- `com.devandre.moviebox.user`: Contains the application logic and infrastructure for user management and
  authentication.

## Technologies Used

- Java 17
- Spring Boot 3.3.4
- Spring Data JPA
- Spring Security
- JWT (JSON Web Tokens)
- PostgreSQL
- Liquibase
- Swagger (OpenAPI)
- Maven

## Getting Started

### Prerequisites

- Java 17
- Maven
- PostgreSQL

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/Andre6-dev/moviebox.git
    cd moviebox
    ```

2. You only need to configure the gmail stmp from your email account and put the enviroment variables of, and it's important to init the project. In case you can't
   configure the gmail smtp, you can request me give you the steps but you need to configure your gmail mfa and create an app password.
    ```
   username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
    ```
3. You can use the csv files to obtain the user, the first user is the only that have ROLE_ADMIN to make create, update and delete the movies.
   The credentials are:
    ```
    email: john.doe@example.com
    password:  password123
    ```

4. I provided all the requests in insomnia format, you can import the file `Insomnia_2024-10-14.json` to test the application. You can import it in Postman.

5. When you start the project, it is important to check the container that has been created in the docker desktop and initialize it, then you must connect to the db instance with the credentials that are in the compose.yaml and create the moviebox_db schema manually as liquibase does not do it automatically.
   The script is as follows:
    ```sql
    CREATE SCHEMA moviebox_db;
    ```
6. Run the application and provided the enviroment variables of gmail if you want to register a user and activate the account.
    ```sh
    mvn spring-boot:run
    ```

## Configuration

### Bean Configuration

The `BeanConfig` class configures the OpenAPI documentation and password encoder:

```java

@Configuration
public class BeanConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("Andre Gallegos");
        contact.setEmail("andregallegosq95@gmail.com");
        contact.setUrl("https://github.com/Andre6-dev");

        return new OpenAPI()
                .info(new Info().title("MovieBox Api").version("1.0.0").contact(contact))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

## Endpoints

### Movie Endpoints

- **Create Movie**: `POST /api/v1/movies/create-movie`
- **Update Movie**: `PUT /api/v1/movies/update-movie`
- **Delete Movie**: `DELETE /api/v1/movies/delete-movie`
- **Get All Categories**: `GET /api/v1/movies/categories`
- **Search Movies**: `GET /api/v1/movies/search`

### Movie Rating Endpoints

- **Rate Movie**: `POST /api/v1/rating/rate-movie`
- **Remove Rating**: `DELETE /api/v1/rating/remove-rating`
- **Get Ratings by User**: `GET /api/v1/rating/get-ratings-by-user/{userId}`

### User Endpoints

- **Get User by Public ID**: `GET /api/v1/users/find-one`
- **Get User by Email**: `GET /api/v1/users/find-one-by-email`

### Authentication Endpoints

- **Register**: `POST /api/v1/authentication/register`
- **Authenticate**: `POST /api/v1/authentication/authenticate`
- **Activate Account**: `GET /api/v1/authentication/activate`

## Security

The application uses JWT for authentication and authorization. The `AuthEntryPointJwt` class handles unauthorized access
attempts:

```java

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
```

### Swagger Documentation

The application uses Swagger for API documentation. The Swagger UI can be accessed at
`http://localhost:8080/swagger-ui/`.

### Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

### License

This project is licensed under the MIT License. See the LICENSE file for details.