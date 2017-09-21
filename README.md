# scout24

# Technology Stack :
- Java 8
- Spring boot
- Libraries
  - Jsoup
  - commons-validator (Apache)
  - Guava
- Lombok
- maven
- AngularJS 1.4.0
- BootStrap
- yeoman (AngularJS app generator)
- grunt (Task runner)
- npm (package manager)
- bower (package manager for web)

# How To Run :

 - Run below command in terminal :

   ```
    mvn spring-boot:run
   ```
 - This will start project , it will install all dependency required for angularjs application to run. It will
   take some time initially. Once Application is started , open browser with URL:

   ```
    http://localhost:8080
   ```

# Use of frontend-maven-plugin :

 - We have used frontend-maven-plugin to install all dependencies required to run AngularJS project. It will
   install node, npm, bower, grunt. In addition to this, it will install all dependencies too.


# Application design :

 - Identifying Login form : We will look for input type and will check if type = password.If its true we will
   assume that web document contains login form.
 - Resource Validations : Wed document generally contains more links.Validating all this links sequentially will
   take more time , which will result in performance impact.So acheive better performance I have used
   ExecutorService as a thread pool. Each thread will take a subset of the collected hyperlink, and check if the
   resource are reachable. The way to know this is to check the HttpResponse Code.
 - There will be a chance of redirection , so we have set connection object to not allow redirection.

  ```
  HttpURLConnection.setFollowRedirects(false);
  ```

# Future Scope :
 - Better error handling on UI (Angular side)
 - Upgrade AngularJS to Angular 4 (Newest Version)
 - More unit test cases




