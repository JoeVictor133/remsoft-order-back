# Remsoft-back

## Description

This project is the backend service for the Remsoft full-stack order management application. The backend is built using **Spring Boot** and provides RESTful API endpoints for managing orders, products, and suppliers. It also includes authentication and registration services secured with JWT (JSON Web Token). The main technologies used in this project are:

### Technologies Used

- **Spring Boot**: Provides a powerful and flexible framework for building Java applications, including features for dependency injection, web MVC, data persistence, and security.
- **Spring Data JPA**: Simplifies data access and management by abstracting the data layer and offering repository interfaces.
- **Spring Security**: Manages authentication and authorization, including JWT-based security.
- **PostgreSQL (via Docker)**: An open-source relational database used to store and manage the application's data, deployed using Docker for ease of setup.
- **JWT (JSON Web Token)**: Used for securely transmitting information between parties as a JSON object.
- **Mockito**: A testing framework for creating mock objects, used for unit testing the application.
- **Springdoc OpenAPI**: Provides interactive API documentation using Swagger UI.

## Setup Instructions

### Prerequisites

- **Java 21**: Ensure that Java 21 is installed and configured on your machine.
- **Maven**: Apache Maven is required for building the project.
- **Docker**: Docker is required to run the PostgreSQL database container.

### Cloning the Repository

1. Open a terminal or command prompt.
2. Clone the repository:

   ```bash
   git clone https://github.com/JoeVictor133/remsoft-order-back.git
   cd remsoft-back

### Setting Up PostgreSQL with Docker

1. Make sure Docker is installed and running on your machine.
2. To start the PostgreSQL container, run the following command from the root directory of the project:

   ```bash
   docker-compose up -d
   
This will set up a PostgreSQL instance running on localhost:5432 with the database orders_db, and the username and password as specified in the docker-compose.yml file.

### Application Properties

The application configuration is managed in the `application.properties` file located in the `src/main/resources/` directory. Update the following properties with your PostgreSQL details if necessary:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/orders_db
   spring.datasource.username=username
   spring.datasource.password=password
   spring.jpa.hibernate.ddl-auto=update
   
   security.encryption.secret-key=my-secret-key-12
   ```

**Note:** The `secret-key` used for encryption can be updated as per your security requirements.

### Building and Running the Application

1.  To build the application, run the following command in the root directory:

    ```bash
    `mvn clean install`

2.  After the build is successful, you can run the application with:

    ```bash
    `mvn clean install`
    `mvn spring-boot:run`

### Running the Application in an IDE

You can also run the application as a Maven project directly from an IDE. Here are examples for **IntelliJ IDEA** and **Eclipse**:

#### IntelliJ IDEA

1. **Open the Project**: Open IntelliJ IDEA, and select `File > Open` to open the `pom.xml` file in the root directory of your project.

2. **Build the Project**: Once the project is loaded, go to the `Maven` tool window (usually on the right side), and click the `Lifecycle > clean` and `install` phases to build the project.

3. **Run the Application**: Right-click on the `OrdersApplication` class (or the main application class annotated with `@SpringBootApplication`) and select `Run 'OrdersApplication.main()'`.

#### Eclipse

1. **Import the Project**: Open Eclipse, and select `File > Import > Existing Maven Projects`. Browse to the root directory of your project and select the `pom.xml` file.

2. **Build the Project**: After importing, Eclipse will automatically build the project. If not, right-click the project in the `Project Explorer` and select `Run As > Maven build...`. In the Goals field, enter `clean install` and click `Run`.

3. **Run the Application**: Right-click on the `OrdersApplication` class (or the main application class annotated with `@SpringBootApplication`) in the `Project Explorer` and select `Run As > Java Application`.


#### The application will start on `http://localhost:8080`.

### Swagger Documentation

The API documentation is automatically generated using Springdoc OpenAPI and can be accessed at:

`http://localhost:8080/swagger-ui.html`

This provides an interactive interface for exploring the API endpoints.

### Testing

Unit tests are provided using **JUnit** and **Mockito**. To run the tests, use:

`mvn test`

### Authentication and Registration

-   To **register** a new user, send a POST request to:


    `POST /register`

-   To **authenticate** a user and receive a JWT token, send a POST request to:


    `POST /authenticate`

The token should be included in the `Authorization` header with the `Bearer` prefix for all subsequent requests to protected endpoints.
