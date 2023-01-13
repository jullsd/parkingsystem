package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static java.lang.Thread.sleep;
import static junit.framework.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    private final String VEHICLE_REGLE_NUMBER = "AH707QN";


    public String getVEHICLE_REGLE_NUMBER() {
        return VEHICLE_REGLE_NUMBER;


    }

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(VEHICLE_REGLE_NUMBER);
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown() {

    }

    @Test
    public void testParkingACar() {

        //Arrange
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        Ticket ticketVehiculeRegleNumberInCommingUser = ticketDAO.getTicket(VEHICLE_REGLE_NUMBER);

        //Act
        ParkingSpot parkingSpotVehiculeRegleInCommingUser = ticketVehiculeRegleNumberInCommingUser.getParkingSpot();
        ParkingSpot pakingSpotVehiculeRegleNextInCommingUser = parkingService.getNextParkingNumberIfAvailable();

        // Assert
        assertThat(ticketVehiculeRegleNumberInCommingUser.getVehicleRegNumber()).isEqualTo(VEHICLE_REGLE_NUMBER);
        assertThat(parkingSpotVehiculeRegleInCommingUser).isNotSameAs(pakingSpotVehiculeRegleNextInCommingUser);


        //TODO: check that a ticket is actualy saved in DB and Parking table is updated with availability
    }

    @Test
    public void testParkingLotExit() throws InterruptedException {
        //Arrange

        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        sleep(1000);
        parkingService.processExitingVehicle();
        Ticket ticketVehiculeRegleNumberOutComingUser = ticketDAO.getTicket(VEHICLE_REGLE_NUMBER);


        //Assert
        assertThat(ticketVehiculeRegleNumberOutComingUser).isNotNull();
        assertThat(ticketVehiculeRegleNumberOutComingUser.getOutTime()).isNotNull();
        assertThat(ticketVehiculeRegleNumberOutComingUser.getPrice()).isNotNull();

        //TODO: check that the fare generated and out time are populated correctly in the database
    }

}