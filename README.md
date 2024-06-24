## BookStore API.
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
"username": "brian.kamaug@gmail.com",
"password": "password"
}
```
The above should generate a token that can be used to authenticate your requests.

#### Step 4. - Running the task service.
Run the task service. To access the swagger documentation, Use the following url

```
http://localhost:8081/v1/tasks/webjars/swagger-ui/index.html#/
```
**Note**: The port may vary depending on which port you will run the service on.


### 2. Approach #2 - Running on Minikube.
We are going to run the applications on a minikube cluster locally. Minikube is a tool
that setups a minified kubernetes environment locally. 

##### Step #1 Housekeeping required before proceeding. 
- Please follow this [guide](https://minikube.sigs.k8s.io/docs/start/) to install minikube on your local machine
- Please follow this [guide](https://kubernetes.io/docs/tasks/tools/) to install kubectl on your local machine
- Please follow this [guide](https://docs.docker.com/engine/install/ubuntu/) to install docker on your local machine

#### Step #2 Run the "Pipeline".

On the root path of this project, execute bash file as follows. The following script mimicks what would ideally happen
in a CI/CD Pipeline by executing the following steps.

1. Runs Test Cases and ensures everything runs successfully.
2. Building the maven project resulting in a .jar
2. Building a docker image. (running this using a amd64/aarch64 base image)
3. Pushing the docker image to a remote repository. (for our case, we are using dockerhub.
4. Deploying the database and the configs service on a minikube cluster.
5. Exposes the task service and authentication via a LoadBalancer IP.

Run the following commands.
```dockerignore
chmod + ./deployment.sh
```
then. 
```dockerignore
./deployment.sh
```

The following should be the output of the execution at the end.
```codeblock
namespace/staging created
secret/mysql-password created
deployment.apps/mysql created
service/mysql created
usage: sleep seconds
deployment.apps/authentication-services created
deployment.apps/task-services created
usage: sleep seconds
service/authentication-services exposed
service/task-services exposed
```

#### Step #3 Check the status of running services.
Use the following command to check the status of the services.
```dockerignore
minikube kubectl -- --namespace staging  get pods
```
The command will output the following:- 

```codeblock
NAME                                      READY   STATUS             RESTARTS        AGE
authentication-services-666784d5d-kllmz   0/1     CrashLoopBackOff   6 (4m19s ago)   10m
mysql-df6854b4b-hltdb                     0/1     ContainerCreating  0               10m
task-services-59759f7f7-mmhjq             0/1     CrashLoopBackOff   6 (4m34s ago)   10m
```

**Note**: The Authentication service and the Task Services will remain in CrashLoopBack
until the database service is completely setup. 

Once the database is up and running, We will setup the tasks database in step #4. The database running
will have a ready status 1/1 as show below.

```codeblock
NAME                                      READY   STATUS             RESTARTS        AGE
authentication-services-666784d5d-kllmz   0/1     CrashLoopBackOff   6 (4m19s ago)   10m
mysql-df6854b4b-hltdb                     1/1     Running            0               10m
task-services-59759f7f7-mmhjq             0/1     CrashLoopBackOff   6 (4m34s ago)   10m
```


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

---- Authentication Service.
```codeblock
minikube service --namespace staging authentication-services
```
Output.
🏃  Starting tunnel for service authentication-services.
```curl
curl --location 'http://127.0.0.1:63037/jwt' --header 'Content-Type: application/json' --data-raw '{
"username": "brian.kamaug@gmail.com",
"password": "password"
}
```

### Ends.
