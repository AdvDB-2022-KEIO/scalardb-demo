# ScalarDB-Demo

This is a demo Application of Scalar DB.<br>
Made by Group X of Advanced Course in Database Systems, Keio University.

It operates as a hotel booking system which leverage Scalar DB, Cassandra and MySQL for transaction management.

## Preparation of Database environment

### Required Environment

This Application is developed in following environmental conditions:

* Oracle OpenJDK Version 16
* Apache Cassandra 4.0.5
* MySQL Server 5.7.37
* Scalar DB 3.5.2

### Database configuration
The database configuration file located in path `src/main/resources/database.properties`:

Before the initiation of database, part of the configurations in this file need to be correct as the user's environment:

```
# Database connection URL and port of Cassandra
scalar.db.multi_storage.storages.cassandra.contact_points=localhost

# Username of Cassandra
scalar.db.multi_storage.storages.cassandra.username=cassandra

# Password of Cassandra
scalar.db.multi_storage.storages.cassandra.password=cassandra

# Database connection URL and port of MySQL
scalar.db.multi_storage.storages.mysql.contact_points=jdbc:mysql://localhost:3306/

# Username of MySQL
scalar.db.multi_storage.storages.mysql.username=root

# Password of MySQL
scalar.db.multi_storage.storages.mysql.password=123456
```

### Database schema initiation
This application use the `ScalarDB-Schema-Loader` for initiating the database:

Run the following command in the `command.txt` located in the root directory:
```
java -jar scalardb-schema-loader-3.5.3.jar --config database.properties -f schema.json --coordinator
```

## Run the Application

This Application is built based-on Apache Maven and SpringBoot Framework.

### By using the `mvnw` binary file in root directory

To run the Application with `mvnw` binary file in root directory, use the command:
```
./mvnw spring-boot:run
```
### By using the local Maven environment
To build and run this Application on local maven environment, try:
```
mvn spring-boot:run
```

## APIs

### Room

<ol>

<li>Add new room in hotels:</li><br>

* API Style: Restful API
* HTTP method: **POST**
* Request URL of API: `localhost:8080/room/`

<br>Data Format:<br>
Data should be **Raw Data** in **JSON** format.
```
# Example
{
    "room_id": 2222, # Room id
    "room_number": 1123123, # Room telephone number
    "room_status": 1, # Room status [1:available, 0:unavailable]
    "hotel_id": 1 # hotel id
}
```

<li>Search for Room information by room id:</li><br>

* API Style: Restful API
* HTTP method: **GET**
* Request URL of API: `localhost:8080/room/{id}`

<br>Data Format:<br>
Replace the `{id}` with the Room ID which is going to be retrieved.

<li>Search for the list of all the Room information:</li><br>

* API Style: Restful API
* HTTP method: **GET**
* Request URL of API: `localhost:8080/room/`

<br>Data Format:<br>
Not request data required.

</ol>

### Hotel

Search for the list of all the Hotel information:

* API Style: Restful API
* HTTP method: **GET**
* Request URL of API: `localhost:8080/hotel/`

<br>Data Format:<br>
Not request data required.

### Booking

<ol>

<li>Book an available room in hotels:</li><br>

* API Style: Restful API
* HTTP method: **POST**
* Request URL of API: `localhost:8080/booking/`

<br>Data Format:<br>
Data should be **Raw Data** in **JSON** format.
```
# Example
{
    "hotel_id": 1, # Hotel ID
    "guest_id": 1, # Guest ID
    "room_id": 2222, # ID of an available room
    "date_from": "2022-01-06", # Begin date of room booking
    "date_to": "2022-01-10", # End date of room booking
    "booking_status": 1 # Booking Status [1:vaild (default), 0:expired]
}
```

<li>Search for Booking information by booking order id:</li><br>

* API Style: Restful API
* HTTP method: **GET**
* Request URL of API: `localhost:8080/booking/{id}`

<br>Data Format:<br>
Replace the `{id}` with the Booking Order ID which is going to be retrieved.

<li>Search for the list of all the Booking information:</li><br>

* API Style: Restful API
* HTTP method: **GET**
* Request URL of API: `localhost:8080/booking/`

<br>Data Format:<br>
Not request data required.

</ol>
