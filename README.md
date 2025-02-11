# User Service 

User Service is a Spring Boot microservice designed for user management. It provides functionalities such as user registration, login, and profile management. The service implements JWT-based authentication and supports role-based access control to secure its endpoints.

## Features

- User Registration: Create new user accounts with validation.  
- Login: Authenticate users and generate JWT tokens for session management.  
- Profile Management: Retrieve user details securely.  
- Role-Based Access Control: Restrict endpoint access based on user roles (e.g., Admin, User).  
- Secure Endpoints: Use of JWT for authentication and authorization.  

## Technologies Used

- Java 17  
- Spring Boot 3.x (Spring Security, Spring Data JPA)  
- Hibernate: ORM for database interaction.  
- H2 / PostgreSQL: Database (modifiable based on environment).  
- JWT (JSON Web Token): Secure user authentication.  
- Lombok: Simplified boilerplate code.  

## API Endpoints

### Public Endpoints  
- POST /users/register: Register a new user.  
- POST /users/login: Authenticate a user and return a JWT.  

### Secured Endpoints (Require Authentication)  
- GET /users/me: Get the authenticated user's profile.  
- GET /admin/users: List all users (Admin role required).  

## Getting Started

### Prerequisites
- JDK 17 or later  
- Maven or Gradle  
- PostgreSQL (or any other preferred database)  

### Running Locally  
1. Clone the repository:  
   `git clone https://github.com/your-repo/user-service.git`  
2. Navigate to the project directory:  
   `cd user-service`  
3. Update application properties:  
   Configure the `src/main/resources/application.properties` file to match your database setup and environment.  
4. Build the application:  
   `mvn clean install`  
5. Run the application:  
   `mvn spring-boot:run`  

## Future Enhancements  

- Add Swagger/OpenAPI for better API documentation.  
- Implement user-specific permissions.  
- Add more robust error handling.  

## License  

This project is licensed under the MIT License - see the LICENSE file for details.  
