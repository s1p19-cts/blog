# Blog JPA CRUD Demo

This is a Spring Boot application to show a simple JPA CRUD example.


## Profiles

* The 'default' profile settings are used when running the application locally, standalone. 
* The application uses settings from 'cloud' profile, when running on Cloud Foundry. 
* The 'test' profile is used for testing only. 

## Running Locally

### Build the project

``` $ gradle clean build ```

### Run it 

1. Run a service instance in `central` region. 

    ``` $ gradle bootRun -PjvmArgs="-Dserver.port=8080 -Dconfig.region=central" ``` 

2. Run second instance in `west` region

    ``` $ gradle bootRun -PjvmArgs="-Dserver.port=8090 -Dconfig.region=west " ``` 

### Test the `central` instance

We will use [httpie](www.http.org) for testing. Please download and install on your machine.

* Make call to the `central` instance
    ```
    ○ → http ":8080/"
    HTTP/1.1 200
    Content-Length: 73
    Content-Type: text/plain;charset=UTF-8
    Date: Mon, 16 Sep 2019 17:43:38 GMT
    
    <h3> blog service - 'blog-crud-service' is running  region - central</h3>
    ```

* Get all blogs from `central` instance
    ```
    ○ → http ":8080/blogs"
    HTTP/1.1 200
    Content-Type: application/json;charset=UTF-8
    Date: Mon, 16 Sep 2019 18:22:17 GMT
    Transfer-Encoding: chunked
    
    []
    ```

### Test the `west` instance

* Make call to the `west` instance
    ```
    ○ → curl localhost:8090/
    <h3> blog service - 'blog-crud-service' is running  region - west</h3>
    ```

* Get all blogs from `west` instance

    ``` 
    ○ → curl localhost:8090/blogs
    []
    ```

## H2 Schema

In client situations, each region will have its own data store, and changes will be replicated from one store to another
at near realtime. 

For workshop purposes, the `central` and `west` instance share the same H2 database.
The H2 database file is located in the project folder (<project>/h2).

The project test resources folder contains scripts to create schema and test data.

To create schema and blogs entry

0. In the browser, open `H2 Console` by going to "http://localhost:8080/h2"
 
0. Goto `<project>/src/test/resources` and locate the "*.sql" files 

0. Copy the sql from `schema-h2.sql`, paste in H2 Console and run it. This should
	* add a new schema called `DEMO`, with a table called `BLOG`.
	* add a new sequence called `HIBERNATE_SEQUENCE`.

0. Copy the sql from `data-h2.sql`, paste in H2 Console, and run it. This should
	* add 4 rows to the `BLOG` table.

0. Get all blogs from `central` instance

    ```
    ○ → http ":8080/blogs"
    HTTP/1.1 200
    Content-Type: application/json;charset=UTF-8
    Date: Mon, 16 Sep 2019 18:22:17 GMT
    Transfer-Encoding: chunked
    
    [
    {
        "content": "Lorem ......",
        "id": 1,
        "title": "lorem"
    },
    ..............]
    ```

* Get all blogs from `west` instance

    ``` 
    ○ → curl localhost:8090/blogs
    [{"id":1,"title":"lorem","content":"Lorem ...."},
    ..............]
    ```
```


