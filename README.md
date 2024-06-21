## Tasks API.
### How to the run the application locally ?

There are several ways we can run the application locally.
1. You can run the code by cloning the applications. This will require you to have MYSQL installed.
2. You can run the code on a minikube cluster locally using the deployment script provided.

### 1. Approach #1.

#### Step #1

Clone the two repositories required for this project by running the following commands.
````
git clone https://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@github.com/kamaubrian/task-services.git
````

```
git clone https://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@github.com/kamaubrian/authentication-services.git
```
**Note**: You need the authentication service for the generation of JWT.

**Note**: This Project Database is built on MYSQL5.7🫣

#### Step #2
Now that you have cloned the repositories, you will need to dump the database schema.

At the root of this project, you will find a `data` folder which contains the schema that 
you can use to setup the database layer.

##### Step #2.1 If you are running on ARM architecture - Macbook M1, M2

Let's run the docker mysql 5.7 image.

```code
docker run -d --name mysql-container -p3307:3306 -e MYSQL_ROOT_PASSWORD='RbQ|9raBA3BYd]N)tpEYJ31B5<19' biarms/mysql:5.7
```
The above creates a mysql5 container locally and exposes it on localhost:3307

Let's Copy our database dump by running the following.

```code
docker cp data/ips_schema_table.sql mysql-container:/tmp/ips_schema_table.sql
```

Finally, lets apply our dump.
```code
docker exec -it mysql-container bash -c "mysql -u root -p'RbQ|9raBA3BYd]N)tpEYJ31B5<19' -e \"CREATE DATABASE IF NOT EXISTS ipsl_task_services;\" && mysql -u root -p'RbQ|9raBA3BYd]N)tpEYJ31B5<19' ipsl_task_services < /tmp/ips_schema_table.sql"
```

On your local application properties for both authentication services and task services use the following as database credentials.
```data
spring.datasource.url=jdbc:mysql://localhost:3307/ipsl_task_services?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=RbQ|9raBA3BYd]N)tpEYJ31B5<19

```


#### Step #3
Before running, update the database credentials to point to your local database instance
in the application.properties

#### Step 5. - Running the authentication service.
You will need to generate jwt token from the authentication service.
Once the authentication service has run successfully, use the following curl to generate JWT.

```dockerignore
curl --location 'http://localhost:8080/jwt' --header 'Content-Type: application/json' --data-raw '{
"username": "brian.kamaug@gmail.com",
"password": "password"
}
```
The above should generate a token that can be used to authenticate your requests.

#### Step 6. - Running the task service.
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

**Note**: The mysqldump is locally under data/ips_schema_table.sql

#### Create the database by running the following.
```codeblock
minikube kubectl -- --namespace staging exec -it $(minikube kubectl --  get pods --namespace staging | grep  -om1 'mysql-.\S\w*\-\S\w*') -- mysql -uroot -p'6^{3XK4j]+%f_:~dw1@u' -e "CREATE DATABASE ipsl_task_services;"
```

##### Setup the database tables and populate data.
```codeblock
minikube kubectl -- --namespace staging  exec -it $(minikube kubectl --  get pods --namespace staging | grep  -om1 'mysql-.\S\w*\-\S\w*') -- mysql -u root -p'6^{3XK4j]+%f_:~dw1@u' ipsl_task_services < data/ips_schema_table.sql
```



#### Step #5 Restarting our authentication services and task services.
Now that the database is setup, we need to restart our services. To do that, run the following 
command. 

```codebloack
minikube kubectl -- --namespace staging  delete pods authentication-services-666784d5d-kllmz task-services-59759f7f7-mmhjq 
```
**Note**: replace `authentication-services-666784d5d-kllmz` and `task-services-59759f7f7-mmhjq` with 
your own pod name returned in step #3.

Once deleted, the services should come up without restarting as shown below.
```codeblock
NAME                                      READY   STATUS    RESTARTS   AGE
authentication-services-666784d5d-wvq6x   1/1     Running   0          47s
mysql-65f775b774-49258                    1/1     Running   0          8m58s
task-services-59759f7f7-ngzd9             1/1     Running   0          47s
```

#### Step #6 Exposing our services externally via a Load balancer.
The aim of this to expose the services to our local host machine. Let's expose authentication for jwt,
and task services using the following commands.

---- Authentication Service.
```codeblock
minikube service --namespace staging authentication-services
```
Output.
🏃  Starting tunnel for service authentication-services.
```table
|-----------|-------------------------|-------------|------------------------|
| NAMESPACE |          NAME           | TARGET PORT |          URL           |
|-----------|-------------------------|-------------|------------------------|
| staging   | authentication-services |             | http://127.0.0.1:63037 |
|-----------|-------------------------|-------------|------------------------|
```


---- Task Service.

```codeblock
minikube service --namespace staging task-services
```

Output🏃  Starting tunnel for service task-services.
```table
|-----------|---------------|-------------|------------------------|
| NAMESPACE |     NAME      | TARGET PORT |          URL           |
|-----------|---------------|-------------|------------------------|
| staging   | task-services |             | http://127.0.0.1:63280 |
|-----------|---------------|-------------|------------------------|
```
As shown above we have exposed the services as external endpoints to the localhost.

**Note**: The Port changes and would not be the same as shown above


#### Step #6 Accessing Swagger and JWT Authentication.

Use the following link to access the swagger API documentation.

```
http://127.0.0.1:63280/v1/tasks/webjars/swagger-ui/index.html#/
```


Use the following curl to generate jwt. You can use the same credentials below.

```curl
curl --location 'http://127.0.0.1:63037/jwt' --header 'Content-Type: application/json' --data-raw '{
"username": "brian.kamaug@gmail.com",
"password": "password"
}
```

## Additional Resources.
1. Postman link [here](https://api.postman.com/collections/2090765-828bfd86-c4f4-4638-b59d-32a72cf36ff9?access_key=PMAT-01HSHSAVSXPPF5KN5Q548B9174)
2. Authentication Service [repo](https://kamau_push_pull_token:glpat-j78cf2VJYN1sn1A43SkT@gitlab.com/mtotodev05/authentication-services.git)


### Ends.