package jdbcProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;



public class Train {

	private static final int min=10000000;
	private static final int max=99999999;
	
	public static class user{
		private String username;
		private String password;
		
		Scanner sc= new Scanner (System.in);
		
		
		public String getUsername() {
			System.out.println("Enter Username : ");
			username=sc.nextLine();
			return username;
		}
		public String getPassword() {
			System.out.println("Enter Password:");
			password=sc.nextLine();
			return password;
		}
		
	}
	public static class PnrRecord{
		private int pnrNumber;
		private String passengerName;
		private String trainNumber;
		private String classType;
		private String jouraneyDate;
		private String from;
		private String to;
		
		Scanner sc=new Scanner(System.in);
		
		public int getpnrNumber() {
			Random r1=new Random();
			pnrNumber=r1.nextInt(max)+min;
			return pnrNumber;
		} 
		public String getPassengerName() {
			System.out.println("Enter Passenger Name : ");
			passengerName=sc.nextLine();
			return passengerName;
		}
		public String gettrainNumber() {
			System.out.println("Enter Train Number : ");
			trainNumber=sc.nextLine();
			return trainNumber;
			
		}
		public String getclassType() {
			System.out.println("Enter Class Type : ");
			classType=sc.nextLine();
			return classType;
			
		}
		public String getjouraneyDate() {
			System.out.println("Enter Jourany Date as 'YYYY-MM-DD' Format : ");
			jouraneyDate=sc.nextLine();
			return jouraneyDate;
			
		}
		public String getfrom() {
			System.out.println("Enter from");
			from= sc.nextLine();
			return from;
		}
		public String getto() {
			System.out.println("To : ");
			to=sc.nextLine();
			return to;
			
		}
	}
	
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        user u1 = new user();
        String username = u1.getUsername();
        String password = u1.getPassword();

        String url = ("jdbc:mysql://localhost:3306/school"); 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                System.out.println("User Connection Granted.\n");
                while (true) {
                    String InsertQuery = "insert into ticket values (?, ?, ?, ?, ?, ?, ?)";
                    String DeleteQuery = "DELETE FROM ticket WHERE pnr_number = ?";
                    String ShowQuery = "Select * from ticket";
                     String showPassengerDetails = "select * from ticket where pnr_number=?";

                    System.out.println("Enter the choice : ");
                    System.out.println("1. Insert Record.\n");
                    System.out.println("2. Delete Record.\n");
                    System.out.println("3. Show All Records.\n");
                    System.out.println("4. Enter PNR of so passenger details.\n");
                    System.out.println("5. Exit.\n");
                    int choice = sc.nextInt();

                    if (choice == 1) {

                        PnrRecord p1 = new PnrRecord();
                        int pnr_number = p1.getpnrNumber();
                        String passengerName = p1.getPassengerName();
                        String trainNumber = p1.gettrainNumber();
                        String classType = p1.getclassType();
                        String journeyDate = p1.getjouraneyDate();
                        String getfrom = p1.getfrom();
                        String getto = p1.getto();

                        try (PreparedStatement preparedStatement = connection.prepareStatement(InsertQuery)) {
                            preparedStatement.setInt(1, pnr_number);
                            preparedStatement.setString(2, passengerName);
                            preparedStatement.setString(3, trainNumber);
                            preparedStatement.setString(4, classType);
                            preparedStatement.setString(5, journeyDate);
                            preparedStatement.setString(6, getfrom);
                            preparedStatement.setString(7, getto);

                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Record added successfully.");
                            }

                            else {
                                System.out.println("No records were added.");
                            }
                        }

                        catch (SQLException e) {
                            System.err.println("SQLException: " + e.getMessage());
                        }

                    }

                    else if (choice == 2) {
                        System.out.println("Enter the PNR number to delete the record : ");
                        int pnrNumber = sc.nextInt();
                        try (PreparedStatement preparedStatement = connection.prepareStatement(DeleteQuery)) {
                            preparedStatement.setInt(1, pnrNumber);
                            int rowsAffected = preparedStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Record deleted successfully.");
                            }

                            else {
                                System.out.println("No records were deleted.");
                            }
                        }

                        catch (SQLException e) {
                            System.err.println("SQLException: " + e.getMessage());
                        }
                    }

                    else if (choice == 3) {
                        try (PreparedStatement ps3 = connection.prepareStatement(ShowQuery);
                                ResultSet rs3 = ps3.executeQuery()) {
                            //System.out.println("\nAll records printing.\n");
                            while (rs3.next()) {
                                int pnrNumber = rs3.getInt(1);
                                String passengerName = rs3.getString(2);
                                String trainNumber = rs3.getString(3);
                                String classType = rs3.getString(4);
                                String journeyDate = rs3.getString(5);
                                String fromLocation =rs3.getString(6);
                                String toLocation =rs3.getString(7);
                                System.out.println("PNR Number: " + pnrNumber);
                                System.out.println("Passenger Name: " + passengerName);
                                System.out.println("Train Number: " + trainNumber);
                                System.out.println("Class Type: " + classType);
                                System.out.println("Journey Date: " + journeyDate);
                                System.out.println("From Location: " + fromLocation);
                                System.out.println("To Location: " + toLocation);
                                System.out.println("--------------");
                            }
                        } catch (SQLException e) {
                            System.err.println("SQLException: " + e.getMessage());
                        }
                    }
                    
                    else if (choice == 4) {
                    	System.out.println("Enter PnrNumber For Passenger Details : ");
                    	int pnrnumber=sc.nextInt();		
                        try (PreparedStatement ps4 = connection.prepareStatement(showPassengerDetails))
                        {   
                        	ps4.setInt(1, pnrnumber);
                        	 ResultSet rs4 = ps4.executeQuery() ;
                            System.out.println("\nAll records printing\n");
                            while (rs4.next()) {
                                int pnrNumber = rs4.getInt(1);
                                String passengerName = rs4.getString(2);
                                String trainNumber = rs4.getString(3);
                                String classType = rs4.getString(4);
                                String journeyDate = rs4.getString(5);
                                String fromLocation =rs4.getString(6);
                                String toLocation =rs4.getString(7);
                                System.out.println("PNR Number: " + pnrNumber);
                                System.out.println("Passenger Name: " + passengerName);
                                System.out.println("Train Number: " + trainNumber);
                                System.out.println("Class Type: " + classType);
                                System.out.println("Journey Date: " + journeyDate);
                                System.out.println("From Location: " + fromLocation);
                                System.out.println("To Location: " + toLocation);
                                System.out.println("--------------");
                            }
                        } catch (SQLException e) {
                            System.err.println("SQLException: " + e.getMessage());
                        }
                    }


                    else if (choice == 5) {
                        System.out.println("Exiting the program.\n");
                        break;
                    }

                    else {
                        System.out.println("Invalid Choice Entered.\n");
                    }
                }

            }

            catch (SQLException e) {
                System.err.println("SQLException: " + e.getMessage());
            }
        }

        catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: " + e.getMessage());
        }

        sc.close();
    }
}
