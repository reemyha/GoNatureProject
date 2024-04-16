package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import logic.*;
import ocsf.server.ConnectionToClient;
import JDBC.DbController;
import enums.Commands;



public class NotifyThread implements Runnable {

	private final int second = 1000;
	private final int minute = second * 60;
	
	private volatile boolean running = true; // Flag to control the execution of the thread

    // Method to stop the thread gracefully
    public void stopThread() {
        running = false;
    }


	@Override
	public void run() {

		while (running) {
			System.out.println("Hello from thread");

			ArrayList<BookingDetail> pendingOrders = null;
			try {
				pendingOrders = ServerUI.sv.dbController.getPendingOrders();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (BookingDetail order : pendingOrders) {
				if (isDateLessThan24Hours(order.getDate())) 
				{
					//add to 24hour table
					try {
						ServerUI.sv.dbController.addClientToPendingTable(order);
					} catch (SQLException e) {
						e.printStackTrace();
					}	
				}
				if (dateIsPassed(order.getDate()))
				{
					ServerUI.sv.dbController.cancelOrderFromDB(order.getOrderNumber());															
				}
				
			}

			try {
				Thread.sleep(1 * minute);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	


	

	

	public static boolean isDateLessThan24Hours(String date) {
        // Get the current date and time
        Date todayDate = new Date();

        // Parse the input date
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date visitDate;
        try {
            visitDate = sdfDate.parse(date);
        } catch (ParseException e) {
            System.out.println("Failed to parse date");
            e.printStackTrace();
            return false;
        }

        // Calculate the difference in milliseconds between visitDate and todayDate
        long diffInMills = visitDate.getTime() - todayDate.getTime();

        // Convert milliseconds to hours
        long diffInHour = TimeUnit.MILLISECONDS.toHours(diffInMills);

        // Check if the difference is less than 24 hours
        return diffInHour <= 24;
    }
	
	private boolean dateIsPassed(String date) {
		// Get the current date and time
        Date todayDate = new Date();

        // Parse the input date
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date visitDate;
        try {
            visitDate = sdfDate.parse(date);
        } catch (ParseException e) {
            System.out.println("Failed to parse date");
            e.printStackTrace();
            return false;
        }

        // Calculate the difference in milliseconds between visitDate and todayDate
        long diffInMills = visitDate.getTime() - todayDate.getTime();

        // Convert milliseconds to hours
        long diffInHour = TimeUnit.MILLISECONDS.toHours(diffInMills);

        // Check if the difference is less than 24 hours
        return diffInHour <= 22;
	}
	
	
	



}
