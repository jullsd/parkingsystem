package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.constants.Discount;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.DiscountCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


public class DiscountCalculatorServiceTest {

    private static DiscountCalculatorService discountCalculatorService;
    private Ticket ticket;
    private final int FREE = 0;
    private final int NODISCOUNT = 1;


    @BeforeAll
    private static void setUp() {
        discountCalculatorService = new DiscountCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }


    @Test
    public void calulateDiscountForLessThan30minutes() {

        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (30 * 60 * 1000));
        Date outTime = new Date();
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        discountCalculatorService.calculateDiscountforfirst30minutes(ticket);

        assertThat(ticket.getDiscount()).isEqualTo(FREE);

    }

    @Test
    public void calulateDiscountForMoreThan30minutes() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Date outTime = new Date();
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        discountCalculatorService.calculateDiscountforfirst30minutes(ticket);

        assertThat(ticket.getDiscount()).isEqualTo(NODISCOUNT);

    }

    @Test

    public void calulateDiscountforReccuringUsers() {


        ticket.setReccuring(true);

        discountCalculatorService.calulateDiscountforReccuringUsers(ticket);

        assertThat(ticket.getDiscount()).isEqualTo(Discount.FIVE_PERCENT_OF_DISCOUNT);
    }


    @Test
    public void calulateDiscountforNoReccuringUsers() {

        ticket.setReccuring(false);

        discountCalculatorService.calulateDiscountforReccuringUsers(ticket);

        assertThat(ticket.getDiscount()).isEqualTo(Discount.NO_DISCOUNT);
    }
}