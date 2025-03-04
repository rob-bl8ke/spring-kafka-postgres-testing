# Getting Started

To run in the development environment make sure to do a `docker compose up -d`. 
- **The integration tests** rely on `Testcontainers` and will run without a `docker compose up`. They will run just fine after doing a `docker compose up`. To test that they work occassionaly do a test run without doing a `docker compose up`.
- Running the application for debugging purposes requires the use of `docker compose up -d`.

Run in development on the local profile. See `application-local.yml` for profile related settings as well as the settings required to connect the docker instances.

In Visual Studio Code this is managed by opening the Spring Boot Dashboard and right clicking on the app. Select "Run with profile..." and select the local profile.  Run it from the command line with these commands:

## Running and Debugging

### Command-line (PowerShell)
```PowerShell
$env:SPRING_PROFILES_ACTIVE="local"
mvn spring-boot:run
```

or 

```PowerShell
mvn spring-boot:run -D"spring-boot.run.profiles=local"
```

### Command-line (Bash)

```Bash
mvn spring-boot:run -D"spring-boot.run.profiles=local"
```

Before running, make sure that Docker is running. Both the development environment and the integration tests rely on Docker.

### "Run and Debug" in Visual Studio Code

Set up your `launch.json` file like such. Note the "env" property to target the local profile.

```json
{
    // Use IntelliSense to learn about possible attributes.
    // Hover to view descriptions of existing attributes.
    // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [
    
        {
            "type": "java",
            "name": "Current File",
            "request": "launch",
            "mainClass": "${file}"
        },
        {
            "type": "java",
            "name": "DemoApplication",
            "request": "launch",
            "mainClass": "com.cyg.demo.DemoApplication",
            "projectName": "demo",
            "env": {
                "SPRING_PROFILES_ACTIVE": "local"
            }
        }
    ]
}
```

## Testing

Integration tests are postfixed with "IT" and unit tests are postfixed with "Test". Running `mvn test` will target unit tests only. Running `mvn verify` will run both unit tests and integration tests. To target specific tests one can do this at the terminal:

```bash
# Target unit tests
mvn test -Dtest=*Test
# Target integration tests
mvn test -Dtest=*IT
```



# Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.3/maven-plugin/build-image.html)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

