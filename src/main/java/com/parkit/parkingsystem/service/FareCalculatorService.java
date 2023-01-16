package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.time.temporal.ChronoUnit;
import java.util.Date;

public class FareCalculatorService {

    private DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService();


    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        discountCalculatorService.calculateDiscount(ticket);

        Date inTime = ticket.getInTime();
        Date outTime = ticket.getOutTime();

        double differenceInMinutes = ChronoUnit.MINUTES.between(inTime.toInstant(), outTime.toInstant());
        double differenceInHours = differenceInMinutes / 60;

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice(differenceInHours * Fare.CAR_RATE_PER_HOUR * discountCalculatorService.getDiscount());
                break;
            }
            case BIKE: {
                ticket.setPrice(differenceInHours * Fare.BIKE_RATE_PER_HOUR * discountCalculatorService.getDiscount());
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}