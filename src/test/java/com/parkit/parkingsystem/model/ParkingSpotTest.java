package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ParkingSpotTest {

    @Test
    public void testTwoParkingSpot() {

        ParkingSpot parkingSpotp1 = new ParkingSpot(1, ParkingType.BIKE, true);
        ParkingSpot parkingSpotp2 = new ParkingSpot(1, ParkingType.CAR, true);

        assertThat(parkingSpotp1.equals(parkingSpotp2)).isTrue();
        assertThat(parkingSpotp1.hashCode()).isEqualTo(1);

    }

    @Test
    public void testTwoDifferentParkingSpot() {

        ParkingSpot parkingSpotp1 = new ParkingSpot(1, ParkingType.BIKE, true);
        assertThat(parkingSpotp1.equals(null)).isFalse();
        assertThat(parkingSpotp1.hashCode()).isEqualTo(1);

    }

}