package org.com.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketStatisticDto {
    private String movieName;
    private Double totalTicketsSold;

    @Override
    public String toString() {
        return "TicketStatisticDto{" +
                "movieName='" + movieName + '\'' +
                ", totalTicketsSold=" + totalTicketsSold +
                '}';
    }
}