# Introduction
As requested, here it follows an application capable of managing device resources by creating,
updating, deleting and fetching them.
#### NOTE: This development was made in a MacOS environment

# Devices API
This Java application was built using Java 21, Maven 3.9, SpringBoot and uses MongoDB as database
The API documentation can be found in the `devices-api.yaml` file.
There are also comments in certain sections of the code base explaining the reasoning behind some of the decisions.

# Getting Started
Please follow the instructions to correctly prepare your machine to test the application:

### Install MongoDB
To be able to use the database
```
brew install mongodb-community@8.0
```

### Install docker, docker-compose and colima (daemon)
In order to compose the application in a container
```
brew install docker
brew install docker-compose
brew install colima
```

### Compose
```
colima start
./mvnw clean package
docker-compose up --build
```

By following these steps you should be able to have the application up and running.

# Test
In order to test the endpoints created in the application you can:
1. Access http://localhost:8080/swagger-ui/index.html to test SAVE, UPDATE, DELETE and FETCH (Single, All, by Brand or by State).
2. Access http://localhost:8080/graphiql?path=/graphql to test FETCH (Single, All, by Brand or by State) in GraphQL (does not work with Docker).
