package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Discount;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

import java.time.temporal.ChronoUnit;
import java.util.Date;


public class DiscountCalculatorService {




    public double calculateDiscountforfirst30minutes(Ticket ticket) {

        Date inTime = ticket.getInTime();
        Date outTime = ticket.getOutTime();

        double differenceInMinutes = ChronoUnit.MINUTES.between(inTime.toInstant(), outTime.toInstant());
        double differenceInHours = differenceInMinutes / 60;

        if (differenceInHours <= 0.5) {

            ticket.setDiscount(Discount.FREE);
        }

        else {

            ticket.setDiscount(Discount.NO_DISCOUNT);
        }

        return ticket.getDiscount();
    }

    public double calulateDiscountforReccuringUsers(Ticket ticket) {

        boolean isReccuring;

        if(isReccuring = true) {

            ticket.setDiscount(Discount.FIVE_PERCENT_OF_DISCOUNT);
        }
        else  {

            ticket.setDiscount(Discount.NO_DISCOUNT);
        }
        return  ticket.getDiscount();

    }
    public double calculateDiscount(Ticket ticket) {

        calulateDiscountforReccuringUsers(ticket);
        calculateDiscountforfirst30minutes(ticket);

        return ticket.getDiscount();
    }



}





