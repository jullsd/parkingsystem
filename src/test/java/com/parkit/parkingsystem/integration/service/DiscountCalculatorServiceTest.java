package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.DiscountCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;


public class DiscountCalculatorServiceTest {



    private static DiscountCalculatorService discountCalculatorService;
    private Ticket ticket;

    private final int FREE =0;

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
        inTime.setTime(System.currentTimeMillis()-( 30 * 60 * 1000));
        Date outTime = new Date();
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        discountCalculatorService.calculateDiscountforfirst30minutes(ticket);

        assertThat(discountCalculatorService.getDiscount()).isEqualTo(FREE);


    }

    @Test
    public void calulateDiscountForMoreThan30minutes() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis()-( 60 * 60 * 1000));
        Date outTime = new Date();
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        discountCalculatorService.calculateDiscountforfirst30minutes(ticket);

        assertThat(discountCalculatorService.getDiscount()).isEqualTo(NODISCOUNT);

    }




}

