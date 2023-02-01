package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    private final String VEHICLE_REGLE_NUMBER = "AH707QNN";


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
    @DisplayName( "Check that a ticket is actualy saved in DB,Parking table is updated with availability and That we can retrieve the list of all associated tickets in database" )
    public void testParkingACar() {
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

        parkingService.processIncomingVehicle();

        Ticket ticketIncommingVehicule = ticketDAO.getTicket(VEHICLE_REGLE_NUMBER);
        ParkingSpot parkingSpotInCommingVehicule = ticketIncommingVehicule.getParkingSpot();
        ParkingSpot pakingSpotNextIncommingVehicule = parkingService.getNextParkingNumberIfAvailable();

        assertThat(ticketIncommingVehicule.getVehicleRegNumber()).isEqualTo(VEHICLE_REGLE_NUMBER);
        assertThat(parkingSpotInCommingVehicule).isNotSameAs(pakingSpotNextIncommingVehicule);
    }

    @Test
    @DisplayName( "Check that the fare generated and out time are populated correctly in the database" )
    public void testParkingLotExit() throws InterruptedException {

        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        sleep(1000);

        parkingService.processExitingVehicle();

        Ticket ticketOutcomingVehicule = ticketDAO.getTicket(VEHICLE_REGLE_NUMBER);
        assertThat(ticketOutcomingVehicule).isNotNull();
        assertThat(ticketOutcomingVehicule.getOutTime()).isNotNull();
        assertThat(ticketOutcomingVehicule.getPrice()).isNotNull();


    }
}







