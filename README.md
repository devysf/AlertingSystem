# AlertSystem

My first Spring-React-PostgresSql Full Stack Web Application to practise what i learned in the Chad Darby and John Thompson's Spring Courses and Brad Traversy and Stephen Grider React Courses. 
(
links: 
https://www.udemy.com/course/spring-hibernate-tutorial/
https://www.udemy.com/course/spring-framework-5-beginner-to-guru/
https://www.udemy.com/course/modern-react-front-to-back/
https://www.udemy.com/course/react-redux/
)

## Live Demo

To see the app in action, go to [https://alertingsystem.herokuapp.com/](https://alertingsystem.herokuapp.com/)

### Installing

## Steps to Setup the Spring Boot Back end app (alerting-system)

1. **Clone the application**

	```bash
	git clone https://github.com/devysf/AlertingSystem.git
	cd alerting-system
	```

2. **Add Postgres url, username and password as per your Postgress or any other dbms**

	+ open `src/main/resources/application.properties` file.

	+ change `spring.datasource.url`, `spring.datasource.username` and `spring.datasource.password` properties as per your Postgress installation
	
	+ Or, add environment variable so you can use ${...} form in `application.properties` file

3. **Add sample email address and password for business logic.**

	+ open `src/main/resources/application.properties` file.

	+ change `spring.mail.username` and `spring.mail.password` properties as your sample mail.
	
	+ Or, add environment variable so you can use ${...} form in `application.properties` file
	

4. **Run the app**

	You can run the spring boot app by typing the following command -

	```bash
	mvn spring-boot:run
	```

	The server will start on port 8080.


## Steps to Setup the React Front end app (client-alerting-system)

1. **First go to the `client-alerting-system` folder**

```bash
cd client-alerting-system
```

2. **Then type the following command to install the dependencies and start the application**

```bash
npm install && npm start
```

The front-end server will start on port `3000`.
