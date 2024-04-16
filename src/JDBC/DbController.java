package JDBC;

import java.nio.file.Path;
import java.io.File;
import java.nio.file.Files;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.OutputStream;

import logic.BookingDetail;
import logic.CancellationData;
import logic.CancellationDetail;
import logic.LoginDetail;
import logic.ManagerRequestDetail;

import logic.ReportDetail;

import logic.ParkEntryDetails;

import logic.WorkerDetail;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
public class DbController {
	
	int maxStayTime;
	private Connection conn;

    // Constructor that takes a SqlConnection object
    public DbController( Connection connection ) {
        this.conn = connection ;
    }
    
    /**
     * Checks if a worker exists in the database with the provided username and password, and logs in the worker if found.
     * Updates the IsLogged field to 1 if the worker is not already logged in.
     *
     * @param password The password of the worker.
     * @param username The username of the worker.
     * @return A WorkerDetail object representing the details of the logged-in worker if found; otherwise, returns a WorkerDetail object with WorkerID = -1.
     */
    public WorkerDetail checkWorkerInDB(String password, String username) {
    	WorkerDetail failedWorkerDetail = new WorkerDetail(-1,null);
    	try {
            // Prepare the SQL statement to select worker by username and password
            String query = "SELECT * FROM gonature.parkworker WHERE Password=? AND UserName=?";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set the parameters for username and password
            ps.setString(1, password);
            ps.setString(2, username);

            // Execute the query
            ResultSet rs = ps.executeQuery();
            
            // Check if a worker with the given username and password exists
            if (rs.next()) {
                // Worker found in the database
                // Check if the worker is not already logged in
                if (rs.getInt("IsLogged") == 0) {
                    // If worker is not logged in, update the IsLogged field to 1
                    String updateQuery = "UPDATE gonature.parkworker SET IsLogged=1 WHERE Password=? AND UserName=?";
                    PreparedStatement psUpdate = conn.prepareStatement(updateQuery);
                    psUpdate.setString(1, password);
                    psUpdate.setString(2, username);
                    
                    // Execute the update query
                    int rowsAffected = psUpdate.executeUpdate();

                    if (rowsAffected > 0) {
                        // If update was successful, return the WorkerID
             
                    	String position = rs.getString("Position");
                    	int workerId = rs.getInt("WorkerID");
                        WorkerDetail workerDetail = new WorkerDetail(workerId,position);
                        workerDetail.setName(rs.getString("FirstName"));
                        if(position.equals("Park Manager"))
                        	workerDetail.setParkName(rs.getString("Park"));
                        	//workerDetail.setParkName(getManagerPark(workerId));
                        if(position.equals("Park Worker"))
                        	workerDetail.setParkName(rs.getString("Park"));
                        return workerDetail;
                    } else {
                        // If update failed, handle accordingly
                        return failedWorkerDetail;//TODO msgtheusererror
                    }
                } else {
                    // If worker is already logged in, return 0
                    return failedWorkerDetail;
                }
            } else {
                // Worker not found in the database
                return failedWorkerDetail;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return failedWorkerDetail; // Return 0 in case of any exception or error
        }
    }
    
    
    private String getManagerPark(int workerId) {
    	
    	try {
    	String sql = "SELECT ParkName FROM gonature.park WHERE ParkManagerID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, workerId);
        ResultSet resultSet;
			resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("ParkName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    	
	}
    
	public void workerLogout(int workerID) {
	    try {
	    	System.out.println("Logging out worker with ID: " + workerID);
	        String query = "UPDATE gonature.parkworker SET IsLogged = 0 WHERE WorkerID = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, workerID);
	        int rowsAffected = ps.executeUpdate();
	        System.out.println("Rows affected: " + rowsAffected);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle the SQLException appropriately
	    }
	}
    

	/**
	 * Checks if a visitor exists in the database with the provided ID and logs in the visitor if found.
	 * If the visitor is found but not already logged in, updates the IsLogged field to 1 to log in the visitor.
	 * If the visitor is not found, adds the visitor to the database and logs in the visitor.
	 *
	 * @param ID The ID of the visitor.
	 * @return True if the visitor is successfully logged in or added to the database; otherwise, false.
	 */
	public boolean checkVisitorInDB(String ID) {
    	try {
	    	// Prepare the SQL statement to select visitor by ID
	        String query = "SELECT * FROM gonature.visitor WHERE ID=?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        
	    	ps.setString(1, ID);
	    	
	    	ResultSet rs = ps.executeQuery();
	    	// Visitor found in the database
	    	if (rs.next()) {
	    		String query1 = "SELECT * FROM gonature.visitor WHERE ID=? AND IsLogged=0";
		        PreparedStatement ps1 = conn.prepareStatement(query1);
		        
		        ps1.setString(1, ID);
		        ResultSet rs1 = ps1.executeQuery();
		        
		        //visitor is not logged in
		        if (rs1.next()) {
		        	String query2 = "UPDATE gonature.visitor SET IsLogged = 1 WHERE ID = ?";
		        	PreparedStatement ps2 = conn.prepareStatement(query2);
		        	ps2.setString(1, ID);
		        	ps2.executeUpdate();
		        	return true;
		        }
		        else
		        	return false;
		     //Visitor not found in the database, adds visitor to DB      
            } else {
            	String query3 = "INSERT INTO gonature.visitor"
            			+ "(ID, GroupGuide, IsLogged) VALUES"
            			+ "(?, 0, 1);";
            	PreparedStatement ps3 = conn.prepareStatement(query3);
            	ps3.setString(1, ID);
            	ps3.executeUpdate();
                return true;
            }
    	}catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of any exception or error
        }
    }
    
	/**
	 * Checks if a visitor exists in the database with the provided ID and is a group guide.
	 *
	 * @param ID The ID of the visitor.
	 * @return True if the visitor is a group guide; otherwise, false.
	 */
    public boolean checkGroupGuideInDB(String ID) {	//checking if visitor is groupguide
        try {
            // Prepare the SQL statement to select GroupGuide by ID
            String query = "SELECT * FROM gonature.visitor WHERE ID=? AND GroupGuide=1";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, ID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	//group guide was found
                return true;
            }
            else {
         
            	return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void visitorLogout(String ID) {
    	try {
    	String query = "UPDATE gonature.visitor SET IsLogged = 0 WHERE ID = ?";
    	PreparedStatement ps = conn.prepareStatement(query);
    	ps.setString(1, ID);
    	ps.executeUpdate();
    	}catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Changes the maximum occupancy capacity for a park in the database.
     *
     * @param valueToChange The new value for the maximum occupancy capacity.
     * @param parkName      The name of the park for which the capacity will be changed.
     * @return True if the capacity is successfully changed; otherwise, false.
     */
	public boolean ChangeParkCapacityInDb(int valueToChange, String parkName) {
		// SQL query to update the MaxOccupancy value
		
        try {
        	String sql = "UPDATE gonature.park SET MaxOccupancy = ? WHERE ParkName = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			// Setting the new value for MaxOccupancy
            stmt.setInt(1, valueToChange);
            stmt.setString(2, parkName);

            int rowsAffected = stmt.executeUpdate();
            
            if(rowsAffected > 0) {
            	return true;
            }else {
            	return false;
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return false;
		
	}

	/**
	 * Changes the online booking capacity for a park in the database.
	 *
	 * @param valueToChange The new value for the online booking capacity.
	 * @param parkName      The name of the park for which the capacity will be changed.
	 * @return True if the capacity is successfully changed; otherwise, false.
	 */
	public boolean ChangeOnlineBookingCapacityInDb(int valueToChange, String parkName) {
        try {
        	System.out.println("test");
        	System.out.println(parkName);
        	System.out.println("test");
        	String sql = "UPDATE gonature.park SET AvailableCapacity = ? WHERE ParkName = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			// Setting the new value for MaxOccupancy
            stmt.setInt(1, valueToChange);
            stmt.setString(2, parkName);
            int rowsAffected = stmt.executeUpdate();
            
            if(rowsAffected > 0) {
            	return true;
            }else {
            	return false;
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return false;
	}

	public boolean ChangeAverageParkStayTimeInDb(int valueToChange, String parkName) {
        try {//TODO: Change TO GET AS A DATE AND NOT INT
        	String sql = "UPDATE gonature.park SET MaxVisitorStayTime = ? WHERE ParkName = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			// Setting the new value for MaxOccupancy
            stmt.setInt(1, valueToChange);
            stmt.setString(2, parkName);
            int rowsAffected = stmt.executeUpdate();
            
            if(rowsAffected > 0) {
            	return true;
            }else {
            	return false;
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return false;
	}
  
	/**
	 * Retrieves the list of bookings for the specified visitor ID.
	 *
	 * @param ID The ID of the visitor.
	 * @return An ArrayList containing BookingDetail objects representing the bookings.
	 */
    public ArrayList<BookingDetail> bookingList(String ID) {
    	ArrayList<BookingDetail> bookings = new ArrayList<>();
        try {
            // Prepare SQL query to retrieve bookings for the specified visitor ID
            String query = "SELECT * FROM gonature.booking WHERE VisitorID = ? "
            		+ "UNION ALL "
            		+ "SELECT * FROM gonature.waitinglist WHERE VisitorID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            // Set the visitor ID parameter
            ps.setString(1, ID);
            ps.setString(2, ID);
            // Execute the query
            ResultSet rs = ps.executeQuery();
            
         // Get current LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime currentDateTime = LocalDateTime.now();
                       
            // Process the result set and populate the list of bookings
            while (rs.next()) {
                // Create BookingDetail objects from the result set
            	BookingDetail booking = new BookingDetail();
                booking.setOrderNumber(rs.getString("OrderNumber"));
                booking.setParkName(rs.getString("ParkName"));
                booking.setDate(rs.getString("Visit_Time_And_Date"));                
                booking.setNumOfVisitors(rs.getString("NumberOfVisitors"));
                booking.setStatus(rs.getString("Status"));
                LocalDateTime visitDateTime = LocalDateTime.parse(booking.getDate(),formatter);
                if(visitDateTime.isAfter(currentDateTime)) 
	                bookings.add(booking); // Add the booking to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
        return bookings;
    }
    
    /**
     * Cancels an order in the database, moves pending bookings from the waiting list to the booking table 
     * if space becomes available, and handles the transition of pending bookings from the waiting list to 
     * the booking table if space becomes available.
     * 
     * @param orderID The ID of the order to be canceled.
     * @return {@code true} if the cancellation was successful, {@code false} otherwise.
     */
    public boolean cancelOrderInDB(String orderID) {
        try {
            // Copy the order details from table 'booking' to table 'cancellation'
            String[] tables = {"booking", "waitinglist"};
            for (int i = 0; i < tables.length; i++) {
                String query1 = "INSERT INTO gonature.cancellation (OrderNumber, ParkName, Visit_Time_And_Date, "
                        + "NumberOfVisitors, VisitType, Email, Telephone, VisitorID, VisitDuration)"
                        + " SELECT OrderNumber, ParkName, Visit_Time_And_Date, "
                        + "NumberOfVisitors, VisitType, Email, Telephone, VisitorID, VisitDuration "
                        + "FROM gonature." + tables[i] + " WHERE OrderNumber = ?";
                // Deletes the order from table 'booking'
                String query2 = "DELETE FROM gonature." + tables[i] + " WHERE OrderNumber = ?";

                PreparedStatement ps1 = conn.prepareStatement(query1);
                PreparedStatement ps2 = conn.prepareStatement(query2);

                ps1.setString(1, orderID);
                ps2.setString(1, orderID);

                ps1.executeUpdate();
                ps2.executeUpdate();
            }

            // Check if there are pending bookings in the waiting list, ordered by OrderNumber
            String checkWaitingListQuery = "SELECT * FROM waitinglist ORDER BY OrderNumber ASC";
            PreparedStatement checkWaitingListStmt = conn.prepareStatement(checkWaitingListQuery);
            ResultSet waitingListResult = checkWaitingListStmt.executeQuery();

            while (waitingListResult.next()) {
                String parkName = waitingListResult.getString("ParkName");
                String visitDateTime = waitingListResult.getString("Visit_Time_And_Date");
                String numOfVisitors = waitingListResult.getString("NumberOfVisitors");
                String visitType = waitingListResult.getString("VisitType");
                String email = waitingListResult.getString("Email");
                String telephone = waitingListResult.getString("Telephone");
                String visitorID = waitingListResult.getString("VisitorID");

                // Check if the park is available for the pending booking
                if (CheckParkAvailability(parkName, visitDateTime, numOfVisitors, visitType, email, telephone, visitorID)) {
                    // If available, move the pending booking from waiting list to booking table
                    AddNewBookingInDB(parkName, visitDateTime, numOfVisitors, visitType, email, telephone, visitorID, "booking");
                    // Remove the fulfilled booking from the waiting list
                    String removeFromWaitingListQuery = "DELETE FROM waitinglist WHERE ParkName = ? AND Visit_Time_And_Date = ?";
                    PreparedStatement removeFromWaitingListStmt = conn.prepareStatement(removeFromWaitingListQuery);
                    removeFromWaitingListStmt.setString(1, parkName);
                    removeFromWaitingListStmt.setString(2, visitDateTime);
                    removeFromWaitingListStmt.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
        return false;
    }



    /**
     * Retrieves visitor statistic data from the database within the specified date range and park name.
     *
     * @param start    The start date of the range.
     * @param end      The end date of the range.
     * @param parkName The name of the park.
     * @return A map containing visitor statistic data with dates as keys and an array of booking amounts as values.
     */
	public Map<LocalDate, int[]> getVisitorStatisticDataInDb(LocalDate start, LocalDate end,String parkName) {
		
        String sqlQuery = "SELECT VisitDate, " +
                "SUM(CASE WHEN VisitType = 'Solo' THEN NumOfVisitors ELSE 0 END) AS solo_trips, " +
                "SUM(CASE WHEN VisitType = 'Group' THEN NumOfVisitors ELSE 0 END) AS group_trips, " +
                "SUM(CASE WHEN VisitType = 'Guided' THEN NumOfVisitors ELSE 0 END) AS guided_group_trips " +
                "FROM gonature.parkvisits " +
                "WHERE VisitDate BETWEEN ? AND ? " +
                "AND parkName = ? " +
                "GROUP BY VisitDate";
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);

            stmt.setDate(1, Date.valueOf(start));
            stmt.setDate(2, Date.valueOf(end));
            stmt.setString(3, parkName);

            ResultSet rs = stmt.executeQuery();
            System.out.println("RS:" + rs);

            Map<LocalDate, int[]> VisitorStatisticData = new HashMap<>();
	        while (rs.next()) {

	        	LocalDate date = rs.getDate("VisitDate").toLocalDate();


	            int soloBooking = rs.getInt("solo_trips");
	            int groupBooking = rs.getInt("group_trips");
	            int guidedGroupBooking = rs.getInt("guided_group_trips");
	            System.out.println("Date: " + date + ", Solo: " + soloBooking + ", Group: " + groupBooking + ", Guided Group: " + guidedGroupBooking);
	            int[] bookingAmounts = new int[]{soloBooking, groupBooking, guidedGroupBooking};
	            VisitorStatisticData.put(date, bookingAmounts);
	            
        	}

            return VisitorStatisticData;
        } catch (SQLException e) {
        e.printStackTrace();
        }
        return null;
	
	
	}

//	public CancellationData getCancellationReportInDb(LocalDate start, LocalDate end, String parkName) {
//		
//       String sql = "SELECT OrderNumber, Visit_Time_And_Date, NumberOfVisitors, VisitType, VisitorID " +
//                "FROM gonature.cancellation  "+
//    		   "WHERE Visit_Time_And_Date BETWEEN ? AND ?";
//	   if (parkName != null) {
//	       sql += "AND ParkName = ? ";
//	   }
//	   try {
//	   PreparedStatement stmt = conn.prepareStatement(sql);
//       stmt.setDate(1, Date.valueOf(start));
//       stmt.setDate(2, Date.valueOf(end));
//       if (parkName != null) {
//           stmt.setString(1, parkName);
//       }
//       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//       ArrayList<CancellationDetail> Cancellations  = new ArrayList<CancellationDetail>();
//       int[] dayCount = new int[] {0,0,0,0,0,0,0};//0 is monday 6 is sunday 
//       ResultSet rs = stmt.executeQuery();
//       while (rs.next()) {
//    	   String orderNumber = rs.getString("OrderNumber");
//           String visitTime = rs.getString("Visit_Time_And_Date");
//           String[] visitTimeSplit = visitTime.split(" ");
//           try {
//			java.util.Date visitDateAsDate = dateFormat.parse(visitTimeSplit[0]);
//			java.sql.Date sqlDate = new java.sql.Date(visitDateAsDate.getTime());
//			dayCount[sqlDate.toLocalDate().getDayOfWeek().getValue()-1]+=1;
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//           int numVisitors = rs.getInt("NumberOfVisitors");
//           String visitType = rs.getString("VisitType");
//           String visitorID = rs.getString("VisitorID");
//           CancellationDetail cancellationDetail = new CancellationDetail(orderNumber,visitTime,numVisitors,visitorID);
//           Cancellations.add(cancellationDetail);
//       }
//        
//       return new CancellationData(Cancellations,dayCount);
//       
//       
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//	return null;
//} 
		
	
	//return solo group and guided group
    
    
    
	/**
	 * Checks the availability of a park for booking at the specified date and time, considering the maximum occupancy
	 * and maximum visitor stay time constraints.
	 * 
	 * @param parkName The name of the park to check availability for.
	 * @param dateTime The date and time of the proposed visit.
	 * @param numOfVisitors The number of visitors for the booking.
	 * @param visitType The type of visit (e.g., "Guided", "Solo", "Group").
	 * @param email The email address of the visitor.
	 * @param telephone The telephone number of the visitor.
	 * @param visitorID The ID of the visitor.
	 * @return {@code true} if the park is available for booking at the specified date and time, {@code false} otherwise.
	 */
    public boolean CheckParkAvailability(String parkName, String dateTime, String numOfVisitors, String visitType, String email,
            String telephone, String visitorID) {
    	int totalVisitors;
    	try {
    		// Split the dateTime string into date and time components
    		String[] dateTimeParts = dateTime.split(" ");
    		String dateString = dateTimeParts[0]; // Date part
    		String timeString = dateTimeParts[1]; // Time part

    		// Convert date string to LocalDate
    		LocalDate visitDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    		// Convert time string to LocalTime
    		LocalTime visitStartTime = LocalTime.parse(timeString);
    		
    		// Query to fetch park details including MaxOccupancy and MaxVisitorStayTime
    		String parkQuery = "SELECT AvailableCapacity, MaxVisitorStayTime FROM park WHERE ParkName = ?";
    		PreparedStatement parkStmt = conn.prepareStatement(parkQuery);
    		parkStmt.setString(1, parkName);
    		ResultSet parkResult = parkStmt.executeQuery();

    		// Check if park exists
    		if (parkResult.next()) {
    			int maxOccupancy = parkResult.getInt("AvailableCapacity");
    			maxStayTime = parkResult.getInt("MaxVisitorStayTime");
    			
    			// Calculate visit end time based on the visit start time and duration
        		LocalTime visitEndTime = visitStartTime.plusHours(maxStayTime);
        		
    			// Query to count the number of visitors during the specified time slot
    			String countVisitorsQuery = "SELECT SUM(NumberOfVisitors) AS TotalVisitors " +
    			"FROM booking WHERE ParkName = ? " + "AND VisitDate = ? " + "AND VisitStartTime < ? AND VisitEndTime > ?";
    			PreparedStatement countVisitorsStmt = conn.prepareStatement(countVisitorsQuery);
    			countVisitorsStmt.setString(1, parkName);
    			countVisitorsStmt.setObject(2, visitDate);
    			countVisitorsStmt.setObject(3, visitEndTime);
    			countVisitorsStmt.setObject(4, visitStartTime);
    			ResultSet countVisitorsResult = countVisitorsStmt.executeQuery();

    			// Check if there are already bookings during the specified time slot
    			if (countVisitorsResult.next()) {
    				totalVisitors = countVisitorsResult.getInt("TotalVisitors");

    				// Check if adding the new booking exceeds the MaxOccupancy
    				if (totalVisitors + Integer.parseInt(numOfVisitors) <= maxOccupancy) {

    					// Park is available for booking
    					System.out.println("db: Booking is available.");
    					return true;
    				}
    			else {
    				// Park is fully occupied during the specified time slot
    				System.out.println("db: Park is fully occupied.");
    				return false;
    				}
    			} 
    			else {
    				// No existing bookings during the specified time slot, park is available
    				return true;
    				}
    		} 
    		else {
    			// Park not found in the database
    			return false;
    			}
    		} catch (SQLException | DateTimeParseException e) {
    			e.printStackTrace();
    			// Handle the exception
    			return false;
    			}
        }



    /**
     * Adds a new booking to the specified table in the database.
     * 
     * @param parkName The name of the park for the booking.
     * @param dateTime The date and time of the booking.
     * @param numOfVisitors The number of visitors for the booking.
     * @param visitType The type of visit (e.g., "Guided", "Solo", "Group").
     * @param email The email address of the visitor.
     * @param telephone The telephone number of the visitor.
     * @param visitorID The ID of the visitor.
     * @param TableName The name of the table in which to add the booking (e.g., "booking", "waitinglist").
     * @return The order number of the new booking if successfully added, {@code null} otherwise.
     */
	public String AddNewBookingInDB(String parkName, String dateTime, String numOfVisitors, String visitType, String email,
	                                  String telephone, String visitorID, String TableName) {
		int maxOrderNumber=0,maxCnclNumber=0,maxWaitNumber=0, newOrderNumber;
		String orderNumberstr;
	    try {
	        // Retrieve the maximum order number from the booking table
	    	// Query to get the maximum OrderNumber from the booking table
	        String maxOrderNumberQuery = "SELECT MAX(OrderNumber) FROM booking";
	        PreparedStatement maxOrderNumberStmt = conn.prepareStatement(maxOrderNumberQuery);
	        ResultSet maxOrderNumberResult = maxOrderNumberStmt.executeQuery();

	        if (maxOrderNumberResult.next()) {
	            maxOrderNumber = maxOrderNumberResult.getInt(1);
	        }

	        String maxCnclNumberQuery = "SELECT MAX(OrderNumber) FROM cancellation";
	        PreparedStatement maxCnclNumberStmt = conn.prepareStatement(maxCnclNumberQuery);
	        ResultSet maxCnclNumberResult = maxCnclNumberStmt.executeQuery();

	        if (maxCnclNumberResult.next()) {
	            maxCnclNumber = maxCnclNumberResult.getInt(1);
	        }

	        String maxWaitNumberQuery = "SELECT MAX(OrderNumber) FROM waitinglist";
	        PreparedStatement maxWaitNumberStmt = conn.prepareStatement(maxWaitNumberQuery);
	        ResultSet maxWaitNumberResult = maxWaitNumberStmt.executeQuery();

	        if (maxWaitNumberResult.next()) {
	            maxWaitNumber = maxWaitNumberResult.getInt(1);
	        }

	        // Increment the maximum OrderNumber by 1 to get the new order number
	        newOrderNumber = Math.max(Math.max(maxOrderNumber, maxCnclNumber), maxWaitNumber) + 1;
	
	        // Split the dateTime string into date and time components
	        String[] dateTimeParts = dateTime.split(" ");
	        String dateString = dateTimeParts[0]; // Date part
	        String timeString = dateTimeParts[1]; // Time part

	        // Convert date string to LocalDate
	        LocalDate visitDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	
	        // Convert time string to LocalTime
	        LocalTime visitStartTime = LocalTime.parse(timeString);
	        LocalTime visitEndTime = visitStartTime.plusHours(maxStayTime);
	        	        
	        // Insert the new booking into the booking table
	        String insertBookingQuery = "INSERT INTO " + TableName +
	                " (OrderNumber, ParkName, VisitDate, VisitStartTime, VisitEndTime, "
	                + "NumberOfVisitors, VisitType, Email, Telephone, VisitorID, VisitDuration, Visit_Time_And_Date, Status) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement insertBookingStmt = conn.prepareStatement(insertBookingQuery);
	
	        // Set parameters for the prepared statement
	        insertBookingStmt.setInt(1, newOrderNumber); // Use the new order number
	        insertBookingStmt.setString(2, parkName);
	        insertBookingStmt.setObject(3, visitDate); // Use setObject to set LocalDate
	        insertBookingStmt.setObject(4, visitStartTime); // Use setObject to set LocalTime
	        insertBookingStmt.setObject(5, visitEndTime); // Use setObject to set LocalTime
	        insertBookingStmt.setString(6, numOfVisitors);
	        insertBookingStmt.setString(7, visitType);
	        insertBookingStmt.setString(8, email);
	        insertBookingStmt.setString(9, telephone);
	        insertBookingStmt.setString(10, visitorID);
	        insertBookingStmt.setInt(11, maxStayTime);
	        insertBookingStmt.setString(12, dateTime);
	        insertBookingStmt.setString(13, "Pending");
	
	        // Execute the insertion
	        insertBookingStmt.executeUpdate();
	    } catch (SQLException | DateTimeParseException e) {
	        e.printStackTrace();
	        // Handle the exception
	        return null;
	    }
	    orderNumberstr = String.valueOf(newOrderNumber);
	    return orderNumberstr;
	}


	/**
	 * Imports external data into the database, including park workers and group guides.
	 */
	public void importExternalData() {
		//import parkworker from external data
		try {
			String st1 = "INSERT INTO gonature.parkworker (WorkerID, FirstName, LastName, Position, Email, Telephone, UserName, Password, IsLogged) "
					+ "SELECT WorkerID, FirstName, LastName, Position, Email, Telephone, UserName, Password, 0 "
					+ "FROM externaldata.extparkworker "
					+ "WHERE WorkerID NOT IN (SELECT WorkerID FROM gonature.parkworker)";
			PreparedStatement ps1 = conn.prepareStatement(st1);
			ps1.executeUpdate();
		}catch (SQLException | DateTimeParseException e) {
	        e.printStackTrace();
	        // Handle the exception
	        return;
		}
		//import visitor from external data
		try {
			String st2 = "INSERT INTO gonature.visitor (ID, GroupGuide, IsLogged) "
					+ "SELECT ID, 1, 0 "
					+ "FROM externaldata.extgroupguide "
					+ "WHERE ID NOT IN (SELECT ID FROM gonature.visitor)";
			PreparedStatement ps2 = conn.prepareStatement(st2);
			ps2.executeUpdate();
		}catch (SQLException | DateTimeParseException e) {
	        e.printStackTrace();
	        // Handle the exception
	        return;
		}
	}
	
	/**
	 * Retrieves the maximum capacity of the specified park from the database.
	 * 
	 * @param ParkName The name of the park for which to retrieve the maximum capacity.
	 * @return The maximum capacity of the park if found, or 0 if the park is not found.
	 * @throws SQLException If a database access error occurs or this method is called on a closed connection.
	 */
	public int getMaxCapacityfromdb(String ParkName) throws SQLException {
	    int maxCapacity = 0;
	    try {
	        // Query to fetch park details including AvailableCapacity
	        String parkQuery = "SELECT AvailableCapacity FROM park WHERE ParkName = ?";
	        PreparedStatement parkStmt = conn.prepareStatement(parkQuery);
	        parkStmt.setString(1, ParkName);
	        ResultSet parkResult = parkStmt.executeQuery();

	        // Check if park exists
	        if (parkResult.next()) {
	            maxCapacity = parkResult.getInt("AvailableCapacity");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	    return maxCapacity;
	}

	
	/**
	 * Retrieves the nearest available dates for booking based on the specified parameters.
	 * 
	 * @param parkName   The name of the park for which to find available dates.
	 * @param dateTime   The date and time for which to start searching for available slots.
	 * @param numOfVisitors   The number of visitors for the booking.
	 * @param visitType  The type of visit (e.g., Guided, Group, Solo).
	 * @param email      The email of the visitor making the booking.
	 * @param telephone  The telephone number of the visitor making the booking.
	 * @param visitorID  The ID of the visitor making the booking.
	 * @return An ArrayList containing the nearest available dates for booking, up to a maximum of 6 dates.
	 */
	public ArrayList<String> getNearestAvailableDates(String parkName, String dateTime, String numOfVisitors, String visitType, String email,
            String telephone, String visitorID) {	
		ArrayList<String> availableDates = new ArrayList<>();
		int maxparkcapacity=0;
		
		try {
			
			// Split the dateTime string into date and time components
	        String[] dateTimeParts = dateTime.split(" ");
	        String currentDateString = dateTimeParts[0]; // Date part
	        String startTimeString = dateTimeParts[1]; // Time part
	        // Convert date string to LocalDate
	        LocalDate currentDate = LocalDate.parse(currentDateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	        
	        // Convert time string to LocalTime
	        LocalTime startTime = LocalTime.parse(startTimeString);
	        
	        int slotsFound = 0;
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	        maxparkcapacity=getMaxCapacityfromdb(parkName);
	        if (maxparkcapacity< Integer.valueOf(numOfVisitors)) {
	        	//availableDates.add("error");
	        	return availableDates;
	        }
	        	
	        	
	        while (slotsFound < 6) {
	        	String dateTimeParam = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " + startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
	        	if (CheckParkAvailability(parkName, dateTimeParam, numOfVisitors, visitType, email, telephone, visitorID)) {
	        		String formattedDateTime = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " + startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
	        		availableDates.add(formattedDateTime);
	        		slotsFound++;
	        	}
	        	// Advance the time by one hour
	        	startTime = startTime.plusHours(1);
	        	// If time reaches 16:00, advance to the next day and reset time to 8:00
	        	if (startTime.getHour() == 16) {
	        		currentDate = currentDate.plusDays(1);
	        		startTime = LocalTime.of(8, 0);
	        	}
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
			// Handle the exception
		}
		return availableDates;
	}

	public int getVisitorAmountInDb(String parkName) {
        LocalTime currentTime = LocalTime.now();
        String query = "SELECT SUM(NumberOfVisitors) FROM booking " +
                       "WHERE ParkName = ? " +
                       "AND VisitStartTime <= ? AND VisitEndTime > ?";
        
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, parkName);
			stmt.setTime(2, Time.valueOf(currentTime));
			stmt.setTime(3, Time.valueOf(currentTime));
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) 
                return rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Adds a manager request to the database.
	 *
	 * @param parkName   The name of the park for which the request is made.
	 * @param changeTo   The field to be changed (e.g., MaxOccupancy).
	 * @param amountTo   The new value to which the field will be changed.
	 * @param requestNumber The unique number assigned to the request.
	 * @param changes    Additional details or comments regarding the request.
	 */
	public void addRequestToDb(String parkName, String changeTo, String amountTo, int requestNumber, String changes) {

		    String query = "INSERT INTO managerrequest (parkName, changeTo, amountTo, changes) VALUES (?, ?, ?, ?)";

			try {
			PreparedStatement stmt = conn.prepareStatement(query);

	        stmt.setString(1, parkName);
	        stmt.setString(2, changeTo);
	        stmt.setString(3, amountTo);
	        stmt.setString(4, changes);

	        int affectedRows = stmt.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Adding request failed, no rows affected.");
	        } else {
	            System.out.println("Request added successfully.");
	        }

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

	/**
	 * Retrieves a list of manager requests from the database.
	 *
	 * @return An ArrayList containing ManagerRequestDetail objects representing the manager requests.
	 */
	public ArrayList<ManagerRequestDetail> getRequestList() {
	    ArrayList<ManagerRequestDetail> requestList = new ArrayList<>();
	    String query = "SELECT parkName, changeTo, amountTo, requestNumber, changes FROM managerrequest";
	    try {
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery(); 

	        while (rs.next()) {
	            String parkName = rs.getString("parkName");
	            String changeTo = rs.getString("changeTo");
	            String amountTo = rs.getString("amountTo");
	            int requestNumber = rs.getInt("requestNumber");
	            String changes = rs.getString("changes");

	            ManagerRequestDetail requestDetail = new ManagerRequestDetail(parkName, changeTo, amountTo);
	            requestDetail.setRequestNumber(requestNumber);
	            requestList.add(requestDetail);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return requestList;
	}

	/**
	 * Removes a manager request from the database based on the request number.
	 *
	 * @param requestIndex The request number of the request to be removed.
	 */
	public void removeRequestToDb(int requestIndex) {
	    String query = "DELETE FROM managerrequest WHERE requestNumber = ?";

	    try{
	        PreparedStatement stmt = conn.prepareStatement(query);

	        stmt.setInt(1, requestIndex);

	        int affectedRows = stmt.executeUpdate();

	        if (affectedRows == 0) {
	            System.out.println("No request found with requestNumber=" + requestIndex);
	        } else {
	            System.out.println("Request with requestNumber=" + requestIndex + " removed successfully.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	

	/**
	 * Cancels a non-paid booking from the database based on the provided order ID.
	 * 
	 * @param OrderID The order ID of the booking to be canceled.
	 */
	public void CancelNonPayedBookingFromDB(String OrderID) {
	    try {
	        // Construct SQL DELETE query to remove the booking with the provided order ID
	        String query = "DELETE FROM gonature.booking WHERE OrderNumber = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        
	        // Set the order ID parameter
	        ps.setString(1, OrderID);
	        
	        // Execute the update to delete the booking
	        ps.executeUpdate();
	        
	        // Close the prepared statement
	        ps.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle the exception
	    }
	}

		
	public String getCurrOccupancy(String parkName) {
		try {
			String query = "SELECT CurrentOccupancy FROM gonature.park WHERE ParkName = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, parkName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String currOcc = rs.getString("CurrentOccupancy");
				return currOcc;				
			}
		}catch (Exception e) {
			e.printStackTrace();
			// Handle the exception
		}
		return null;
	}

	public String getMaxOccupancy(String parkName) {
		try {
			String query = "SELECT MaxOccupancy FROM gonature.park WHERE ParkName = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, parkName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String maxOcc = rs.getString("MaxOccupancy");
				return maxOcc;
			}
		}catch (Exception e) {
			e.printStackTrace();
			// Handle the exception
		}
		return null;
	}

	/**
	 * Retrieves booking details from the database based on the provided BookingDetail object.
	 * If the booking details are found in the 'booking' table, they are returned directly.
	 * If not found, the method searches in the 'parkvisits' table and returns the details.
	 * 
	 * @param bookingDet The BookingDetail object containing the order number and park name.
	 * @return The BookingDetail object containing the retrieved booking details, or null if not found.
	 */
	public BookingDetail getBookingDetails(BookingDetail bookingDet) {
	    try {
	        // First, try to retrieve booking details from the 'booking' table
	        String query = "SELECT * FROM gonature.booking WHERE OrderNumber = ? AND ParkName = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setString(1, bookingDet.getOrderNumber());
	        ps.setString(2, bookingDet.getParkName());
	        ResultSet rs = ps.executeQuery();
	        
	        // If booking details found in the 'booking' table
	        if (rs.next()) {
	            BookingDetail bd = new BookingDetail();
	            bd.setOrderNumber(rs.getString("orderNumber"));
	            bd.setParkName(rs.getString("ParkName"));
	            bd.setDate(rs.getString("Visit_Time_And_Date"));
	            bd.setNumOfVisitors(rs.getString("NumberOfVisitors"));
	            bd.setVisitType(rs.getString("VisitType"));
	            bd.setPaymentStatus(rs.getString("Payment"));
	            bd.setTelephone(rs.getString("Telephone"));
	            bd.setEmail(rs.getString("Email"));
	            bd.setVisitorID(rs.getString("VisitorID"));
	            return bd;
	        } else {
	            // If not found in the 'booking' table, try another table
	            String secondQuery = "SELECT * FROM gonature.parkvisits WHERE OrderNumber = ? AND ParkName = ?";
	            PreparedStatement secondPs = conn.prepareStatement(secondQuery);
	            secondPs.setString(1, bookingDet.getOrderNumber());
	            secondPs.setString(2, bookingDet.getParkName());
	            ResultSet secondRs = secondPs.executeQuery();
	            
	            // If booking details found in the 'parkvisits' table
	            if (secondRs.next()) {
	                // Populate BookingDetail object from the second table
	                BookingDetail bd = new BookingDetail();
	                bd.setOrderNumber(secondRs.getString("orderNumber"));
	                bd.setParkName(secondRs.getString("ParkName"));
	                // Set current date and time in the desired format
	                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	                String currentDateAndTime = sdf.format(new java.util.Date());
	                bd.setDate(currentDateAndTime);
	                bd.setNumOfVisitors(secondRs.getString("NumOfVisitors"));
	                bd.setVisitType(secondRs.getString("VisitType"));
	                return bd;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle the exception
	    }
	    return null; // Return null if no booking details found
	}



	/**
	 * Updates the payment status of a booking in the database.
	 * 
	 * @param orderNumber The order number of the booking to update.
	 * @param paymentStatus The new payment status to set.
	 */
	public void ChangePaymentStatusInDB(String orderNumber, String paymentStatus) {
	    try {
	        // Prepare and execute the SQL update query
	        String query = "UPDATE gonature.booking SET Payment = ? WHERE OrderNumber = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setString(1, paymentStatus);
	        ps.setString(2, orderNumber);
	        ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle the exception
	    }
	}

	/**
	 * Adds a client to the pending table in the database if they are not already present.
	 * 
	 * @param order The booking detail of the client to be added.
	 * @throws SQLException if a database access error occurs or this method is called on a closed connection.
	 */
	public void addClientToPendingTable(BookingDetail order) throws SQLException {
		int count=0;
		String query = "SELECT COUNT(*) FROM gonature.commingbooks WHERE OrderNumber = ?";
	    PreparedStatement ps = conn.prepareStatement(query);
	    ps.setString(1, order.getOrderNumber());
	    ResultSet rs = ps.executeQuery();
	    rs.next();
	    count = rs.getInt(1);
	    if (count==0) 
	    {
			java.time.LocalDate todayDate = java.time.LocalDate.now();
			
	        // Convert time string to LocalTime
	        LocalTime AddTime = LocalTime.now();
	        LocalTime AddDelTime = AddTime.plusHours(2);
	        	        
	        // Insert the new booking into the booking table
	        String insertBookingQuery = "INSERT INTO gonature.commingbooks (OrderNumber, ParkName, Visit_Time_And_Date, VisitorID, "
	        															  + "AddedDate, AddedTime, AddedDelTime) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement insertBookingStmt = conn.prepareStatement(insertBookingQuery);
	
	        // Set parameters for the prepared statement
	        insertBookingStmt.setString(1, order.getOrderNumber()); // Use the new order number
	        insertBookingStmt.setString(2, order.getParkName());
	        insertBookingStmt.setString(3, order.getDate()); // Use setObject to set LocalDate
	        insertBookingStmt.setString(4, order.getVisitorID()); // Use setObject to set LocalTime
	        insertBookingStmt.setObject(5, todayDate); // Use setObject to set LocalTime
	        insertBookingStmt.setObject(6, AddTime);
	        insertBookingStmt.setObject(7, AddDelTime);
	        
			
	        insertBookingStmt.executeUpdate();
        }
	}
	
	/**
	 * Retrieves a list of pending booking orders from the database.
	 *
	 * @return An ArrayList containing BookingDetail objects representing pending booking orders.
	 * @throws SQLException if a database access error occurs or this method is called on a closed connection.
	 */
	public ArrayList<BookingDetail> getPendingOrders() throws SQLException {
		ArrayList<BookingDetail> bookings = new ArrayList<>();
        try {
            // Prepare SQL query to retrieve bookings for the specified visitor ID
            String query = "SELECT * FROM gonature.booking WHERE Status = 'Pending' ";
            PreparedStatement ps = conn.prepareStatement(query);

            // Execute the query
            ResultSet rs = ps.executeQuery();
                                    
            // Process the result set and populate the list of bookings
            while (rs.next()) {
                // Create BookingDetail objects from the result set
            	BookingDetail booking = new BookingDetail();
                booking.setOrderNumber(rs.getString("OrderNumber"));
                booking.setParkName(rs.getString("ParkName"));
                booking.setDate(rs.getString("Visit_Time_And_Date"));                
                booking.setNumOfVisitors(rs.getString("NumberOfVisitors"));
                booking.setVisitorID(rs.getString("VisitorID"));
                booking.setVisitDuration(rs.getInt("VisitDuration"));
                bookings.add(booking); // Add the booking to the list
                }
        }
         catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
        return bookings;
		
	}
	
	/**
	 * Cancels an order identified by the provided order ID from the database. 
	 * This involves moving the order details to the cancellation table and 
	 * removing the order from the booking and coming books tables.
	 *
	 * @param orderID the ID of the order to be canceled.
	 */
	public void cancelOrderFromDB(String orderID) {
	    try {
	        // Copy the order details from table 'booking' to table 'cancellation'
	        String[] tables = {"booking"};
	        for (int i = 0; i < tables.length; i++) {
	            String query1 = "INSERT INTO gonature.cancellation (OrderNumber, ParkName, Visit_Time_And_Date, "
	                    + "NumberOfVisitors, VisitType, Email, Telephone, VisitorID, VisitDuration)"
	                    + " SELECT OrderNumber, ParkName, Visit_Time_And_Date, "
	                    + "NumberOfVisitors, VisitType, Email, Telephone, VisitorID, VisitDuration "
	                    + "FROM gonature." + tables[i] + " WHERE OrderNumber = ?";
	            // Deletes the order from table 'booking'
	            String query2 = "DELETE FROM gonature." + tables[i] + " WHERE OrderNumber = ?";
	            String query3 = "DELETE FROM gonature.commingbooks WHERE OrderNumber = ?";
	            PreparedStatement ps1 = conn.prepareStatement(query1);
	            PreparedStatement ps2 = conn.prepareStatement(query2);
	            PreparedStatement ps3 = conn.prepareStatement(query3);

	            ps1.setString(1, orderID);
	            ps2.setString(1, orderID);
	            ps3.setString(1, orderID);

	            ps1.executeUpdate();
	            ps2.executeUpdate();
	            ps3.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle the exception
	    }
	}

	/**
	 * Checks if a visitor with the provided ID needs to be alerted. 
	 * This method queries the 'comingbooks' table to determine if there are any pending 
	 * bookings associated with the specified visitor ID.
	 *
	 * @param visitorID the ID of the visitor to check for pending bookings.
	 * @return {@code true} if the visitor has pending bookings and needs to be alerted, {@code false} otherwise.
	 */
	public boolean CheckIfNeedToBeAlert(String visitorID) {
	    try {
	    	
	        String sqlQuery = "SELECT * FROM gonature.commingbooks WHERE VisitorID = ?";
	        PreparedStatement statement = conn.prepareStatement(sqlQuery);
	        
	        // Set the parameter value for the VisitorID
	        statement.setString(1, visitorID);
	        
	        // Execute the query
	        ResultSet resultSet = statement.executeQuery();
	        
	        // Check if any rows are returned
	        boolean visitorFound = resultSet.next();
	        	        
	        return visitorFound;
	        
	    } catch (SQLException e) {
	        // Handle any SQL exception (e.g., log, throw, etc.)
	        e.printStackTrace();
	        return false; // or handle the exception according to your application's logic
	    }
	}

	/**
	 * Retrieves a list of bookings associated with a visitor ID that has been alerted.
	 * This method queries the 'comingbooks' table to fetch bookings for the specified visitor ID.
	 *
	 * @param AlertedID the ID of the visitor who has been alerted.
	 * @return an ArrayList of BookingDetail objects representing the bookings associated with the alerted visitor.
	 */
	public ArrayList<BookingDetail> AlrtedbookingList(String AlertedID) {
	    	ArrayList<BookingDetail> bookings = new ArrayList<>();
	        try {
	            // Prepare SQL query to retrieve bookings for the specified visitor ID
	            String query = "SELECT * FROM gonature.commingbooks WHERE VisitorID = ? ";
	            PreparedStatement ps = conn.prepareStatement(query);
	            // Set the visitor ID parameter
	            ps.setString(1, AlertedID);
	            // Execute the query
	            ResultSet rs = ps.executeQuery();
	            
	            while (rs.next()) {
	                // Create BookingDetail objects from the result set
	            	BookingDetail booking = new BookingDetail();
	                booking.setOrderNumber(rs.getString("OrderNumber"));
	                booking.setParkName(rs.getString("ParkName"));
	                booking.setDate(rs.getString("Visit_Time_And_Date"));
	                bookings.add(booking);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle the exception
	        }
	        return bookings;
	    }

	/**
	 * Confirms the alert for a pending order and updates its status in the database.
	 * This method checks if the provided orderID exists in the 'commingbooks' table,
	 * and if found, updates its status to 'Approved' in the 'booking' table.
	 * If the update is successful, the record is deleted from the 'commingbooks' table.
	 *
	 * @param orderID the ID of the order to be confirmed.
	 * @return true if the confirmation and status update were successful, false otherwise.
	 */
	public boolean AlertOrderConfirmationInDB(String orderID) {
	    boolean isSuccess = false;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
		try {
			
			String query = "SELECT * FROM gonature.commingbooks WHERE OrderNumber = ?";
			preparedStatement = conn.prepareStatement(query);
	        preparedStatement.setString(1, orderID);
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            // If the orderID exists, update its status to "Approved"
	            String updateQuery = "UPDATE gonature.booking SET Status = 'Approved' WHERE OrderNumber = ?";
	            preparedStatement = conn.prepareStatement(updateQuery);
	            preparedStatement.setString(1, orderID);
	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                // If the status was successfully updated, delete the record
	                String deleteQuery = "DELETE FROM gonature.commingbooks WHERE OrderNumber = ?";
	                preparedStatement = conn.prepareStatement(deleteQuery);
	                preparedStatement.setString(1, orderID);
	                preparedStatement.executeUpdate();
	                
	                isSuccess = true;
	            
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle any SQL exceptions here
	    }

	    return isSuccess;
	}

	/**
	 * Inserts a report into the database.
	 *
	 * @param title     The title of the report.
	 * @param from      The start date of the report.
	 * @param to        The end date of the report.
	 * @param document  The byte array representing the report document.
	 * @param parkName  The name of the park associated with the report.
	 * @return True if the report is successfully inserted, false otherwise.
	 */
    public boolean insertReportToDb(String title, LocalDate from, LocalDate to, byte[] document, String parkName) {
        String sql = "INSERT INTO report (ReportTitle, date_from, date_to,parkName, file) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setDate(2, Date.valueOf(from));
            statement.setDate(3, Date.valueOf(to));
            statement.setString(4, parkName);


            //Set the PDF bytes as a BLOB in the SQL statement
            statement.setBytes(5, document);
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Report inserted successfully.");
                return true;
            } else {
                System.out.println("Failed to insert report.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
    }

    /**
     * Retrieves a list of report details from the database based on the specified report type.
     * 
     * @param reportType The type of report to retrieve.
     * @return An ArrayList containing ReportDetail objects representing the reports retrieved from the database.
     */
	public ArrayList<ReportDetail> getReportTableInDb(String reportType) {
	    ArrayList<ReportDetail> reportList = new ArrayList<>();
	    String query = "SELECT ReportID, ReportTitle, date_from, date_to, parkName ,file  FROM gonature.report"
	    		+ " WHERE ReportTitle = ? ";
	    try {
	         PreparedStatement stmt = conn.prepareStatement(query);
	         stmt.setString(1, reportType);
	         ResultSet rs = stmt.executeQuery(); 

	        while (rs.next()) {
	            int ReportID = rs.getInt("ReportID");
	            String ReportTitle = rs.getString("ReportTitle");
	            Date date_from = rs.getDate("date_from");
	            Date date_to = rs.getDate("date_to");
	            String parkName = rs.getString("parkName");
	            Blob blob = rs.getBlob("file");
	            int blobLength = (int) blob.length();  
	            byte[] blobAsBytes = blob.getBytes(1, blobLength);

	            //release the blob and free up memory. (since JDBC 4.0)
	            blob.free();

	            ReportDetail reportDetail = new ReportDetail(ReportID, ReportTitle, date_from.toLocalDate(),date_to.toLocalDate(),parkName,blobAsBytes);
	            reportList.add(reportDetail);
	        }
	        return reportList;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

		return null;
	}
	
    /**
     * Retrieves visitor data from the database for the specified date range and park.
     *
     * @param start     The start date of the date range.
     * @param end       The end date of the date range.
     * @param parkName  The name of the park.
     * @return A map containing visitor data categorized by visit type.
     * @throws SQLException if a database access error occurs.
     */
	public Map<String, int[]> getVisitorDataInDb(LocalDate start, LocalDate end, String parkName) throws SQLException {
		
	    Map<String, int[]> visitorData = new HashMap<>();


	    String soloSql = "SELECT " +
	            "SUM(CASE WHEN StartTime BETWEEN '08:00:00' AND '10:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Solo_8-10', " +
	            "SUM(CASE WHEN StartTime BETWEEN '10:00:00' AND '12:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Solo_10-12', " +
	            "SUM(CASE WHEN StartTime BETWEEN '12:00:00' AND '14:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Solo_12-14', " +
	            "SUM(CASE WHEN StartTime >= '14:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Solo_14+', " +
	            "SUM(CASE WHEN VisitDuration BETWEEN 0 AND 59 THEN NumOfVisitors ELSE 0 END) AS 'Solo_1', " +
	            "SUM(CASE WHEN VisitDuration BETWEEN 60 AND 119 THEN NumOfVisitors ELSE 0 END) AS 'Solo_1-2', " +
	            "SUM(CASE WHEN VisitDuration BETWEEN 120 AND 179 THEN NumOfVisitors ELSE 0 END) AS 'Solo_2-3', " +
	            "SUM(CASE WHEN VisitDuration >= 180 THEN NumOfVisitors ELSE 0 END) AS 'Solo_4+' " +
	            "FROM parkvisits " +
	            "WHERE VisitType = 'Solo' AND ParkName = ? AND VisitDate BETWEEN ? AND ?";
	    
	    try (PreparedStatement soloStatement = conn.prepareStatement(soloSql)) {
	        soloStatement.setString(1, parkName);
	        soloStatement.setDate(2, Date.valueOf(start));
	        soloStatement.setDate(3, Date.valueOf(end));
	        
	        
	        ResultSet soloResultSet = soloStatement.executeQuery();

	        if (soloResultSet.next()) {
	            int[] soloData = {
	                    soloResultSet.getInt("Solo_8-10"),
	                    soloResultSet.getInt("Solo_10-12"),
	                    soloResultSet.getInt("Solo_12-14"),
	                    soloResultSet.getInt("Solo_14+"),
	                    soloResultSet.getInt("Solo_1"),
	                    soloResultSet.getInt("Solo_1-2"),
	                    soloResultSet.getInt("Solo_2-3"),
	                    soloResultSet.getInt("Solo_4+")
	            };
	            visitorData.put("Solo", soloData);
	        }
	    }

	    String groupSql = "SELECT " +
	            "SUM(CASE WHEN StartTime BETWEEN '08:00:00' AND '10:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Group_8-10', " +
	            "SUM(CASE WHEN StartTime BETWEEN '10:00:00' AND '12:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Group_10-12', " +
	            "SUM(CASE WHEN StartTime BETWEEN '12:00:00' AND '14:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Group_12-14', " +
	            "SUM(CASE WHEN StartTime >= '14:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Group_14+', " +
	            "SUM(CASE WHEN VisitDuration BETWEEN 0 AND 59 THEN NumOfVisitors ELSE 0 END) AS 'Group_1', " +
	            "SUM(CASE WHEN VisitDuration BETWEEN 60 AND 119 THEN NumOfVisitors ELSE 0 END) AS 'Group_1-2', " +
	            "SUM(CASE WHEN VisitDuration BETWEEN 120 AND 179 THEN NumOfVisitors ELSE 0 END) AS 'Group_2-3', " +
	            "SUM(CASE WHEN VisitDuration >= 180 THEN NumOfVisitors ELSE 0 END) AS 'Group_4+' " +
	            "FROM parkvisits " +
	            "WHERE VisitType = 'Group' AND ParkName = ? AND VisitDate BETWEEN ? AND ?";
	    try (PreparedStatement groupStatement = conn.prepareStatement(groupSql)) {
	        groupStatement.setString(1, parkName);
	        groupStatement.setDate(2, Date.valueOf(start));
	        groupStatement.setDate(3, Date.valueOf(end));
	        ResultSet groupResultSet = groupStatement.executeQuery();

	        if (groupResultSet.next()) {
	        	int [] groupData = {
	                    groupResultSet.getInt("Group_8-10"),
	                    groupResultSet.getInt("Group_10-12"),
	                    groupResultSet.getInt("Group_12-14"),
	                    groupResultSet.getInt("Group_14+"),
	                    groupResultSet.getInt("Group_1"),
	                    groupResultSet.getInt("Group_1-2"),
	                    groupResultSet.getInt("Group_2-3"),
	                    groupResultSet.getInt("Group_4+")
	            };
	            visitorData.put("Group", groupData);
	        }
	    }

	    String guidedSql = "SELECT " +
	            "SUM(CASE WHEN StartTime BETWEEN '08:00:00' AND '10:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Guided_8-10', " +
	            "SUM(CASE WHEN StartTime BETWEEN '10:00:00' AND '12:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Guided_10-12', " +
	            "SUM(CASE WHEN StartTime BETWEEN '12:00:00' AND '14:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Guided_12-14', " +
	            "SUM(CASE WHEN StartTime >= '14:00:00' THEN NumOfVisitors ELSE 0 END) AS 'Guided_14+', " +
	            "SUM(CASE WHEN VisitDuration BETWEEN 0 AND 59 THEN NumOfVisitors ELSE 0 END) AS 'Guided_1', " +
	            "SUM(CASE WHEN VisitDuration BETWEEN 60 AND 119 THEN NumOfVisitors ELSE 0 END) AS 'Guided_1-2', " +
	            "SUM(CASE WHEN VisitDuration BETWEEN 120 AND 179 THEN NumOfVisitors ELSE 0 END) AS 'Guided_2-3', " +
	            "SUM(CASE WHEN VisitDuration >= 180 THEN NumOfVisitors ELSE 0 END) AS 'Guided_4+' " +
	            "FROM parkvisits " +
	            "WHERE VisitType = 'Guided' AND ParkName = ? AND VisitDate BETWEEN ? AND ?";
	    try (PreparedStatement guidedStatement = conn.prepareStatement(guidedSql)) {
	        guidedStatement.setString(1, parkName);
	        guidedStatement.setDate(2, Date.valueOf(start));
	        guidedStatement.setDate(3, Date.valueOf(end));
	        ResultSet guidedResultSet = guidedStatement.executeQuery();

	        if (guidedResultSet.next()) {
	        	int [] guidedData = {
	                    guidedResultSet.getInt("Guided_8-10"),
	                    guidedResultSet.getInt("Guided_10-12"),
	                    guidedResultSet.getInt("Guided_12-14"),
	                    guidedResultSet.getInt("Guided_14+"),
	                    guidedResultSet.getInt("Guided_1"),
	                    guidedResultSet.getInt("Guided_1-2"),
	                    guidedResultSet.getInt("Guided_2-3"),
	                    guidedResultSet.getInt("Guided_4+")
	            };
	            visitorData.put("Guided", guidedData);
	        }
	    }
	    
        System.out.println("Key-Value pairs:");
        for (Map.Entry<String, int[]> entry : visitorData.entrySet()) {
            System.out.println(entry.getKey() + ": " + Arrays.toString(entry.getValue()));
        }

	    return visitorData;
	}


	/**
	 * Retrieves the average number of cancellations per day of the week from the database.
	 * If a park name is specified, it retrieves the average cancellations for that park only.
	 *
	 * @param parkName The name of the park (optional). If null, cancellations from all parks are considered.
	 * @return An array containing the average number of cancellations for each day of the week (Monday to Sunday).
	 *         Returns null if an error occurs.
	 */
	public int[] getAverageCancellationReportInDb(String parkName) {
	    // Construct SQL query to count cancellations and group by day of the week
	    String sql = "SELECT COUNT(OrderNumber) AS CancellationCount, DAYOFWEEK(STR_TO_DATE(Visit_Time_And_Date, '%d.%m.%Y')) AS DayOfWeek " +
	                 "FROM gonature.cancellation ";

	    // Append WHERE clause if parkName is specified
	    if (!parkName.equals("All Parks")) {
	        sql += "WHERE ParkName = ? ";
	    }

	    sql += "GROUP BY DAYOFWEEK(STR_TO_DATE(Visit_Time_And_Date, '%d.%m.%Y'))";

	    try {
	        // Prepare the SQL statement
	        PreparedStatement stmt = conn.prepareStatement(sql);

	        // Set parkName parameter if specified
	        if (!parkName.equals("All Parks")) {
	            stmt.setString(1, parkName);
	        }

	        // Initialize arrays to store cancellation counts and day counts
	        int[] dayCounts = new int[7]; // 0 is Monday, 1 is Tuesday, ..., 6 is Sunday
	        int[] cancellationCounts = new int[7];

	        // Execute the query
	        ResultSet rs = stmt.executeQuery();
	        
	        // Process the query results
	        while (rs.next()) {
	            int cancellationCount = rs.getInt("CancellationCount");
	            int dayOfWeek = rs.getInt("DayOfWeek");

	            // Increment the cancellation and day count arrays
	            dayCounts[dayOfWeek - 1]++;
	            cancellationCounts[dayOfWeek - 1] += cancellationCount;
	        }

	        // Calculate the average cancellations for each day of the week
	        int[] averageCancellations = new int[7];
	        for (int i = 0; i < 7; i++) {
	            if (dayCounts[i] != 0) {
	                averageCancellations[i] = (cancellationCounts[i] / dayCounts[i]);
	            } else {
	                averageCancellations[i] = 0; // Avoid division by zero
	            }
	        }
	        
	        // Return the array of average cancellations
	        return averageCancellations;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    // Return null if an error occurs
	    return null;
	}
	
	/**
	 * Retrieves cancellation details from the database for a specified park.
	 *
	 * @param parkName The name of the park for which cancellation details are to be retrieved.
	 * @return An ArrayList containing CancellationDetail objects representing the cancellation details for the specified park.
	 */
	public ArrayList<CancellationDetail> getDetails(String parkName) {
        ArrayList<CancellationDetail> details = new ArrayList<>();

        String sql = "SELECT OrderNumber, Visit_Time_And_Date , NumberOfVisitors, VisitorID " +
                     "FROM gonature.cancellation";
        if (!parkName.equals("All Parks")) {
        	sql += " WHERE ParkName = ? ";
         }


        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        if (!parkName.equals("All Parks")) {
	            stmt.setString(1, parkName);
	        }
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                String orderNumber = rs.getString("OrderNumber");
                String visitDate = rs.getString("Visit_Time_And_Date");
                int numVisitors = rs.getInt("NumberOfVisitors");
                String visitorID = rs.getString("VisitorID");


                CancellationDetail detail = new CancellationDetail(orderNumber, visitDate, numVisitors, visitorID);
                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return details;
    }

	/**
	 * Retrieves visitor statistics report from the database for a specified date range and park.
	 *
	 * @param start    The start date of the date range.
	 * @param end      The end date of the date range.
	 * @param parkName The name of the park.
	 * @return A HashMap containing hourly visitor statistics for the specified date range and park.
	 *         The keys represent each hour of the day (in HH:00 format) and the values represent the average occupancy during that hour.
	 */
	public HashMap<String, Integer> GetVisitorStatReportInDb(LocalDate start, LocalDate end, String parkName) {
	    HashMap<String, Integer> visitorStatMap = new HashMap<>();

	    try {
	        // Iterate over each one-hour interval from 8:00 to 16:00
	        for (int hour = 8; hour <= 16; hour++) {
	            String startTimeStr = String.format("%02d:00:00", hour);
	            String endTimeStr = String.format("%02d:59:59", hour);

	            String query = "SELECT " +
	                    "SUM(MaxOccupancy) AS maxOccupancySum, " +
	                    "SUM(ParkCapacity) AS parkCapacitySum, " +
	                    "COUNT(*) AS numOfRows " +
	                    "FROM parkvisits " +
	                    "WHERE (StartTime <= ? AND EndTime >= ?) " +
	                    "AND VisitDate BETWEEN ? AND ? " +
	                    "AND parkName = ?";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter Hourformatter = DateTimeFormatter.ofPattern("HH:mm");
                
                LocalTime startTime = LocalTime.parse(startTimeStr, formatter);
                LocalTime endTime = LocalTime.parse(endTimeStr, formatter);
                
               
                
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setTime(1,Time.valueOf(startTime));
                pstmt.setTime(2, Time.valueOf(endTime));
                pstmt.setDate(3, Date.valueOf(start));
                pstmt.setDate(4, Date.valueOf(end));
                pstmt.setString(5, parkName);
                ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                int maxOccupancySum = rs.getInt("maxOccupancySum");
	                int parkCapacitySum = rs.getInt("parkCapacitySum");
	                int numOfRows = rs.getInt("numOfRows");

	                if (numOfRows > 0) {
	                    int occupancyAverage = (maxOccupancySum - parkCapacitySum) / numOfRows;
	                    visitorStatMap.put(String.format("%02d:00", hour), occupancyAverage);
	                }
	            }

	            rs.close();
	            pstmt.close();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return visitorStatMap;
	}
		
		
	
	
	/**
	 * Records the entry of visitors into the park and updates park occupancy details in the database.
	 *
	 * @param enterPark an object containing details of the park entry including order number, park name, start time, visit type,
	 *                  number of visitors, park capacity at the time, visit date.
	 */
	public void enterPark(ParkEntryDetails enterPark) {
	    int currentOccupancy = 0;
	    int maxOccupancy = 0;
	    try {
	        // Retrieve the maximum occupancy of the park
	        String maxOccupancyQuery = "SELECT MaxOccupancy FROM gonature.park WHERE ParkName = ?";
	        PreparedStatement maxOccupancyStmt = conn.prepareStatement(maxOccupancyQuery);
	        maxOccupancyStmt.setString(1, enterPark.getParkName());
	        ResultSet maxOccupancyResult = maxOccupancyStmt.executeQuery();
	        if (maxOccupancyResult.next())
	            maxOccupancy = maxOccupancyResult.getInt("MaxOccupancy");

	        // Insert the park visit details into the 'parkvisits' table
	        String insertParkVisitQuery = "INSERT INTO gonature.parkvisits (OrderNumber, ParkName, StartTime, VisitType, NumOfVisitors, ParkCapacity, VisitDate, MaxOccupancy) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement insertParkVisitStmt = conn.prepareStatement(insertParkVisitQuery);
	        insertParkVisitStmt.setString(1, enterPark.getOrderNumber());
	        insertParkVisitStmt.setString(2, enterPark.getParkName());
	        insertParkVisitStmt.setTime(3, enterPark.getStartTime());
	        insertParkVisitStmt.setString(4, enterPark.getVisitType());
	        insertParkVisitStmt.setString(5, enterPark.getNumOfVisitors());
	        insertParkVisitStmt.setString(6, enterPark.getParkCapacityAtm());
	        insertParkVisitStmt.setDate(7, enterPark.getVisitDate());
	        insertParkVisitStmt.setInt(8, maxOccupancy);
	        insertParkVisitStmt.executeUpdate();

	        // Retrieve the current occupancy of the park
	        String currentOccupancyQuery = "SELECT CurrentOccupancy FROM gonature.park WHERE ParkName = ?";
	        PreparedStatement currentOccupancyStmt = conn.prepareStatement(currentOccupancyQuery);
	        currentOccupancyStmt.setString(1, enterPark.getParkName());
	        ResultSet currentOccupancyResult = currentOccupancyStmt.executeQuery();
	        if (currentOccupancyResult.next())
	            currentOccupancy = currentOccupancyResult.getInt("CurrentOccupancy");

	        // Update the current occupancy of the park
	        String updateOccupancyQuery = "UPDATE gonature.park SET CurrentOccupancy = ? WHERE ParkName = ?";
	        PreparedStatement updateOccupancyStmt = conn.prepareStatement(updateOccupancyQuery);
	        updateOccupancyStmt.setInt(1, currentOccupancy + Integer.parseInt(enterPark.getNumOfVisitors()));
	        updateOccupancyStmt.setString(2, enterPark.getParkName());
	        updateOccupancyStmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle the exception
	    }
	}


	/**
	 * Records the exit of visitors from the park and updates park occupancy details in the database.
	 *
	 * @param exitPark an object containing details of the park exit including order number, park name, end time, number of visitors.
	 */
	public void exitPark(ParkEntryDetails exitPark) {
	    int currentOccupancy = 0;
	    Time startTime = null;
	    try {
	        // Retrieve the start time of the park visit
	        String startTimeQuery = "SELECT StartTime FROM gonature.parkvisits WHERE OrderNumber = ?";
	        PreparedStatement startTimeStmt = conn.prepareStatement(startTimeQuery);
	        startTimeStmt.setString(1, exitPark.getOrderNumber());
	        ResultSet startTimeResult = startTimeStmt.executeQuery();
	        if (startTimeResult.next())
	            startTime = startTimeResult.getTime("StartTime");

	        // Calculate the visit duration based on start and end times
	        Duration duration = Duration.between(startTime.toLocalTime(), exitPark.getEndTime().toLocalTime());

	        // Update the park visit details with end time and visit duration
	        String updateParkVisitQuery = "UPDATE gonature.parkvisits SET EndTime = ?, VisitDuration = ? WHERE OrderNumber = ?";
	        PreparedStatement updateParkVisitStmt = conn.prepareStatement(updateParkVisitQuery);
	        updateParkVisitStmt.setTime(1, exitPark.getEndTime());
	        updateParkVisitStmt.setLong(2, duration.toMinutes());
	        updateParkVisitStmt.setString(3, exitPark.getOrderNumber());
	        updateParkVisitStmt.executeUpdate();

	        // Retrieve the current occupancy of the park
	        String currentOccupancyQuery = "SELECT CurrentOccupancy FROM gonature.park WHERE ParkName = ?";
	        PreparedStatement currentOccupancyStmt = conn.prepareStatement(currentOccupancyQuery);
	        currentOccupancyStmt.setString(1, exitPark.getParkName());
	        ResultSet currentOccupancyResult = currentOccupancyStmt.executeQuery();
	        if (currentOccupancyResult.next())
	            currentOccupancy = currentOccupancyResult.getInt("CurrentOccupancy");

	        // Update the current occupancy of the park
	        String updateOccupancyQuery = "UPDATE gonature.park SET CurrentOccupancy = ? WHERE ParkName = ?";
	        PreparedStatement updateOccupancyStmt = conn.prepareStatement(updateOccupancyQuery);
	        updateOccupancyStmt.setInt(1, currentOccupancy - Integer.parseInt(exitPark.getNumOfVisitors()));
	        updateOccupancyStmt.setString(2, exitPark.getParkName());
	        updateOccupancyStmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle the exception
	    }
	}


	/**
	 * Retrieves the maximum order number from the parkvisits table in the database and generates an occasional booking number.
	 * If no order number exists or the maximum order number is less than 90000, it sets the occasional booking number to 90000.
	 *
	 * @return a string representing the occasional booking number.
	 */
	public String occasionalBookingNumber() {
	    String number = null;
	    try {
	        // Query to retrieve the maximum order number from the parkvisits table
	        String query = "SELECT MAX(OrderNumber) AS MaxOrderNumber FROM gonature.parkvisits";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ResultSet rs = ps.executeQuery();

	        // Retrieve the maximum order number
	        if (rs.next())
	            number = rs.getString("MaxOrderNumber");

	        // If no order number exists or the maximum order number is less than 90000, set the number to 90000
	        if (number == null || Integer.valueOf(number) < 90000) {
	            number = "90000";
	        }
	        return number;
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle the exception
	    }
	    return null;
	}
	public ArrayList<String> getParkNames()
	{
		ArrayList<String> parkNames = new ArrayList<>();
		try
		{
			String query = "SELECT ParkName FROM gonature.park";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			 while (rs.next())
			 {
				 parkNames.add(rs.getString("ParkName"));
				 
			 }
		 }
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		for (String parkName:parkNames)
			System.out.println(parkName);
		return parkNames;
	}

	/**
	 * Checks if a booking with the given order number exists in the parkvisits table in the database.
	 *
	 * @param BookNum the order number to check for existence in the database.
	 * @return an integer value:
	 *         - 1 if a booking with the given order number exists in the database.
	 *         - 0 if no booking with the given order number exists in the database.
	 */
	public int checkBookInDB(String BookNum) {
	    try {
	        // Query to check if a booking with the given order number exists in the parkvisits table
	        String query = "SELECT * FROM gonature.parkvisits WHERE OrderNumber = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setString(1, BookNum);
	        ResultSet rs = ps.executeQuery();
	        
	        // If a booking with the given order number exists, return 1
	        if (rs.next()) {
	            return 1;
	        }
	        // If no booking with the given order number exists, return 0
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle the exception
	    }
	    return 0;
	}


}


 
