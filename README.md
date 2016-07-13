# SpringLogIn
A demo project showing how to do basic login with cookies in Spring


##Reference:

1. http://stackoverflow.com/questions/14268451/spring-security-userdetailsservice-implementation-login-fails

2. http://www.mkyong.com/spring-security/get-current-logged-in-username-in-spring-security/

3. http://www.mkyong.com/spring-security/spring-security-form-login-using-database/

4. http://www.baeldung.com/2011/10/31/securing-a-restful-web-service-with-spring-security-3-1-part-3/

5. github tutorial: https://github.com/eugenp/tutorials/tree/master/spring-security-rest

###To Login:

1. curl -i -X POST -d username=Tom -d password=haha http://localhost:8080/login     (Tom is a user stored in database)

2. curl -i -X POST -d username=Tom -d password=haha -c ./cookies.txt http://localhost:8080/login (To store cookie)

3. curl -i --header "Accept:application/json" -X GET -b ./cookies.txt http://localhost:8080/api/foos
	
	This would return 200 if the cookie is correct, 202 if not cookie is in the GET call
	
	







 






