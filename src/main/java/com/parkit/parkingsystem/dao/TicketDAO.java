package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    private static final Logger logger = LogManager.getLogger("TicketDAO");

    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;
    private static final int INDEX_3 = 3;
    private static final int INDEX_4 = 4;
    private static final int INDEX_5 = 5;
    private static final int INDEX_6 = 6;

    public boolean saveTicket(Ticket ticket) {
        Connection con = null;
       PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
           ps = con.prepareStatement(DBConstants.SAVE_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            //ps.setInt(1,ticket.getId());
            ps.setInt(INDEX_1, ticket.getParkingSpot().getId());
            ps.setString(INDEX_2, ticket.getVehicleRegNumber());
            ps.setDouble(INDEX_3, ticket.getPrice());
            ps.setTimestamp(INDEX_4, new Timestamp(ticket.getInTime().getTime()));
            ps.setTimestamp(INDEX_5, (ticket.getOutTime() == null) ? null : (new Timestamp(ticket.getOutTime().getTime())));
            return ps.execute();
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
        return false;
    }

    public Ticket getTicket(String vehicleRegNumber) {
        Connection con = null;
        Ticket ticket = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            ps.setString(INDEX_1, vehicleRegNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                ticket = new Ticket();
                ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(INDEX_1), ParkingType.valueOf(rs.getString(INDEX_6)), false);
                ticket.setParkingSpot(parkingSpot);
                ticket.setId(rs.getInt(INDEX_2));
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(rs.getDouble(INDEX_3));
                ticket.setInTime(rs.getTimestamp(INDEX_4));
                ticket.setOutTime(rs.getTimestamp(INDEX_5));
            }
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);

        }
        return ticket;
    }

    public boolean updateTicket(Ticket ticket) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.UPDATE_TICKET);
            ps.setDouble(INDEX_1, ticket.getPrice());
            ps.setTimestamp(INDEX_2, new Timestamp(ticket.getOutTime().getTime()));
            ps.setInt(INDEX_3, ticket.getId());
            ps.execute();
            return true;
        } catch (Exception ex) {
            logger.error("Error saving ticket info", ex);
        } finally {
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
        return false;
    }

    public List<Ticket> getAllTicket(String vehicleRegNumber) {
        Connection con = null;
        Ticket ticket = null;
        List<Ticket> tickets = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_ALL_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            ps.setString(INDEX_1, vehicleRegNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                ticket = new Ticket();
                ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(INDEX_1), ParkingType.valueOf(rs.getString(INDEX_6)), false);
                ticket.setParkingSpot(parkingSpot);
                ticket.setId(rs.getInt(INDEX_2));
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(rs.getDouble(INDEX_3));
                ticket.setInTime(rs.getTimestamp(INDEX_4));
                ticket.setOutTime(rs.getTimestamp(INDEX_5));
                tickets.add(ticket);
            }

        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
        return tickets;
    }

}

