package org.com.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketStatisticDto {
    private Date date;
    private Double totalTicketsSold;

    @Override
    public String toString() {
        return "TicketStatisticDto{" +
                "date=" + date +
                ", totalTicketsSold=" + totalTicketsSold +
                '}';
    }
}