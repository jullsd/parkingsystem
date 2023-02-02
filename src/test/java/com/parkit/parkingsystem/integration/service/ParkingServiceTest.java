package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith( MockitoExtension.class )
public class ParkingServiceTest {

    private final String VEHICLE_REGLE_NUMBER = "AH707QNN";

    public String getVEHICLE_REGLE_NUMBER() {
        return VEHICLE_REGLE_NUMBER;
    }

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;
    private List listOfticketsVEHICLE_REGLE_NUMBER = new ArrayList();
    private final int TICKET_1 = 1;
    private final int TICKET_2 = 2;


    @Test
    public void isReccuringUser() {

        listOfticketsVEHICLE_REGLE_NUMBER.add(TICKET_1);
        listOfticketsVEHICLE_REGLE_NUMBER.add(TICKET_2);
        when(ticketDAO.getAllTicket(VEHICLE_REGLE_NUMBER)).thenReturn(listOfticketsVEHICLE_REGLE_NUMBER);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

        boolean isReccuring = parkingService.isRecurringUser(VEHICLE_REGLE_NUMBER);

        assertThat(isReccuring).isTrue();
    }

    @Test
    public void testGetVehicleCar() {
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        when(inputReaderUtil.readSelection()).thenReturn(1);

        assertThat(parkingService.getVehichleType()).isEqualTo(ParkingType.CAR);

    }
    @Test
    public void testGetVehicleBike() {
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        when(inputReaderUtil.readSelection()).thenReturn(2);

        assertThat(parkingService.getVehichleType()).isEqualTo(ParkingType.BIKE);

    }

    @Test
    public void testGetVehicleUnknown() {
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        when(inputReaderUtil.readSelection()).thenReturn(0);

        assertThrows(IllegalArgumentException.class, () -> parkingService.getVehichleType());

    }

}

