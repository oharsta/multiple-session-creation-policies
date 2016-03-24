# spring-multiple-session-create-policies

This demo application shows how very simple it is with Spring Security to have multiple ```SessionCreationPolicy``` strategies.

One where you do allow cookies (for web clients for example) and one where you do not allow the creation of Sessions (a REST API for example).

See [ApplicationTests](src/test/java/zilverline/ApplicationTests) for the assertions or use cUrl:

```
curl -i --user user:secret http://localhost:8080/user

curl -i --user user:secret http://localhost:8080/api/user
```
