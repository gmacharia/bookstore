# bookstore
### How to the run the application locally ?

There are several ways we can run the application locally.
1. You can run the code by cloning the applications. This will require you to have MYSQL installed.
2. You can run the code on docker locally using the deployment script provided.

### 1. Approach #1.

#### Step #1

Clone the two repositories required for this project by running the following commands.
````
git clone https://github.com/gmacharia/bookstore.git
````

```
git clone https://github.com/gmacharia/authentication-services.git
```
**Note**: You need the authentication service for the generation of JWT.

**Note**: This Project Database is built on MYSQL5.7🫣


##### Step #2 If you are running on ARM architecture - Macbook M1, M2

Let's run the docker mysql 5.7 image.

```code
docker run -d --name mysql-container -p3307:3306 -e MYSQL_ROOT_PASSWORD='RbQ|9raBA3BYd]N)tpEYJ31B5<19' biarms/mysql:5.7
```
The above creates a mysql5 container locally and exposes it on localhost:3307

```

#### Step 3. - Running the deployment sh file
You will need to generate jwt token from the authentication service.
Once the authentication service has run successfully, use the following curl to generate JWT.

```dockerignore
curl --location 'http://localhost:8080/jwt' --header 'Content-Type: application/json' --data-raw '{
"username": "tester",
"password": "password"
}
```
The above should generate a token that can be used to authenticate your requests.

#### Step 4. - Running the task service.
Run the book service. To access the swagger documentation, Use the following url

```
http://localhost:8090/v1/tasks/webjars/swagger-ui/index.html#/
```
**Note**: The port may vary depending on which port you will run the service on.

#### Step #4 Setting up the schemas on our database.

mysql> desc books;
+--------------+--------------+------+-----+-------------------+-----------------------------------------------+
| Field        | Type         | Null | Key | Default           | Extra                                         |
+--------------+--------------+------+-----+-------------------+-----------------------------------------------+
| bookId       | bigint       | NO   | PRI | NULL              | auto_increment                                |
| title        | varchar(250) | NO   |     | NULL              |                                               |
| author       | varchar(250) | NO   |     | NULL              |                                               |
| isbn         | varchar(250) | YES  |     | NULL              |                                               |
| price        | double       | YES  |     | NULL              |                                               |
| createdBy    | int unsigned | YES  |     | NULL              |                                               |
| dateModified | datetime     | NO   |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED on update CURRENT_TIMESTAMP |
+--------------+--------------+------+-----+-------------------+-----------------------------------------------+

mysql> desc customer;
+--------------------+--------------+------+-----+---------+----------------+
| Field              | Type         | Null | Key | Default | Extra          |
+--------------------+--------------+------+-----+---------+----------------+
| customerId         | bigint       | NO   | PRI | NULL    | auto_increment |
| customerSurName    | varchar(255) | YES  |     | NULL    |                |
| customerOtherNames | varchar(255) | YES  |     | NULL    |                |
| emailAddress       | varchar(255) | NO   | UNI | NULL    |                |
| mobileNumber       | varchar(255) | NO   | UNI | NULL    |                |
+--------------------+--------------+------+-----+---------+----------------+

mysql> desc orders;
+--------------+--------------+------+-----+---------+----------------+
| Field        | Type         | Null | Key | Default | Extra          |
+--------------+--------------+------+-----+---------+----------------+
| orderId      | bigint       | NO   | PRI | NULL    | auto_increment |
| customerId   | bigint       | YES  | MUL | NULL    |                |
| bookId       | bigint       | YES  | MUL | NULL    |                |
| mobileNumber | varchar(255) | YES  | MUL | NULL    |                |
| orderDate    | datetime     | YES  |     | NULL    |                |
+--------------+--------------+------+-----+---------+----------------+

### Ends.
