
# **Cinema Application**

Cinema application developed using spring boot.
it introduces a platform for users to browse cinemas and book seats.


## *Entites Relationships*
![Alt text](/pics/RelationShipDiagram.png)

## *Login Screen*
Users can login to their accounts in order to book tickets.
![Alt text](/pics/login.png)

## *Browse cinemas*
Users can browse cinemas and see details.
![Alt text](/pics/cata.png)


## *Book Ticket*
Users can book tickets by entering seat number they want to reserve.
![Alt text](/pics/seats.png)

## *Admin Manager*
Only Admin has access to create cinemas or movies or check the statistics of the application.

![Alt text](/pics/manager.png)
![Alt text](/pics/stats.png)
![Alt text](/pics/mc.png)


## *Spring Security*
Basic authentication is implemented using spring security. 
Authorization is achieved through roles.

```java

 http.
                authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/images/**").permitAll()
                                .requestMatchers("/styles/**").permitAll()
                                .requestMatchers("/register/**").permitAll()
                                .requestMatchers("/seats/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/cinemas").permitAll()
                                .requestMatchers("/cinemas/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/about").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
```
## *Validation On User Input*
Users requests are validated using validation service to guarantee correctness and consistency. an error message will pop up to the user indicating what went wrong.

* Users can't reserve seats for expired cinemas.
* Users can't reserve seats for non-existing seat numbers.
* No two users can reserve the same seat.
* Users can't refund tickets after the cinema show date is expired.

## *Validation On Admin Input*
Admin requests on creating cinemas is validated:
* Admin can't create cinemas for past dates.
* Admin can't reserve cinema with overlapping time with other cinema in the same room.


