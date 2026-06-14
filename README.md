# Prayer App Service
This is a backend API that is written to support the Prayer Requests App. Users can create and join prayer groups where they can post and share their prayer requests. See the frontend repository here - [Frontend repository](https://github.com/kdu2030/prayer-requests-app)

# Libraries, Frameworks, and Technologies
* Java 21
* Gradle
* Spring Boot
* Postgresql
* Docker
* Flyway

# Major Features
* Supports JWT Authentication
* Creating prayer groups, adding/removing prayer group users with roles of Admin or Member
* Fuzzy searching for prayer groups by name
* Adding prayer requests, liking, and commenting on prayer requests
* Filtering prayer requests and paginating prayer requests

# Screenshots
<img width="1701" height="777" alt="image" src="https://github.com/user-attachments/assets/bebe96db-4ff6-4da0-934f-dc5806e2b11c" />
<img width="1582" height="689" alt="image" src="https://github.com/user-attachments/assets/e1a54e04-922a-4034-aeb1-97d29503acaa" />


# How to Run
1. Clone the repository
2. Install the JDK for Java 21
3. Set up a local Postgres database. Create a database with the name prayer_app. Create a user for the Prayer App Service. Make sure that you run GRANT ALL PRIVILEGES ON SCHEMA public TO [Prayer App Service user]; for the prayer_app database.
4. Set the environment variables DB_USERNAME to the username of the Prayer App Service User and DB_PASSWORD to the password of the Prayer App Service User
5. Compile and run the Prayer App Service.
6. See the Swagger page at http://localhost:8080/swagger-ui/index.html#/

