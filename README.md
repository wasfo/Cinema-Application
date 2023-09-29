
# **Cinema Application**

Cinema application developed using spring boot.
it introduces a platform for users to browse cinemas and book seats.


## *Entites Relationships*
![Alt text](image link)


## *Browse cinemas*
Users can browse cinemas and see details.
![Alt text](image link)


## *Book Ticket*
Users can book tickets by entering seat number they want to reserve.
![Alt text](image link)

## *Admin Manager*
Only Admin has access to create cinemas or movies or check the statistics of the application.

![Alt text](image link)

## *Spring Security*
Basic authentication is implemented using spring security. 
Authorization is achieved through roles.

![Alt text](image link)
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


