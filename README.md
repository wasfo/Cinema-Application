
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
## *Scheduler*
I created a scheduler bean, to run every 24 hours to delete expired tickets,
and harvest the income of those tickets by recording it into the database.

```javas
 @Scheduled(fixedRate = 60 * 60 * 24 * 1000)
    @Transactional
    public void harvestLoot() throws CinemaException {

        List<Cinema> expiredCinemas = cinemaService.findExpiredCinemas();
        log.info("expired cinemas {}", expiredCinemas);

        if (expiredCinemas.isEmpty())
            return;

        long totalDailySum = 0;
        long numOfTickets = 0;
        for (Cinema cinema : expiredCinemas) {
            List<Ticket> tickets = ticketService.findByCinemaId(cinema.getId());
            double sum = tickets.stream().mapToDouble(Ticket::getPrice).sum();
            numOfTickets += tickets.size();
            totalDailySum += sum;
        }
        log.info("Total Daily Income {}", totalDailySum);

        saveStats(totalDailySum, numOfTickets);

        log.info("Daily stats saved in database");

        deleteExpiredCinemas(expiredCinemas);
    }
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


