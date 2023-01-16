package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Discount;
import com.parkit.parkingsystem.model.Ticket;

import java.time.temporal.ChronoUnit;
import java.util.Date;


public class DiscountCalculatorService {

    public double getDiscount() {
        return discount;
    }

    private double discount;

    public double calculateDiscount(Ticket ticket) {

        calculateDiscountforfirst30minutes(ticket);

        return discount;
    }


    public double calculateDiscountforfirst30minutes(Ticket ticket) {

        Date inTime = ticket.getInTime();
        Date outTime = ticket.getOutTime();

        double differenceInMinutes = ChronoUnit.MINUTES.between(inTime.toInstant(), outTime.toInstant());
        double differenceInHours = differenceInMinutes / 60;

        if (differenceInHours <= 0.5) {

            discount = Discount.FREE;
        } else {
            discount = Discount.NO_DISCOUNT;
        }

        return discount;
    }

}





