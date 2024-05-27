import java.io.*;
import java.util.Scanner;

public class ElectricityBillingSystem
{
    static class Customer
    {
        private int customerId;
        private String name;
        private String address;
        private double previousMeterReading;

        public Customer(int customerId, String name, String address, double previousMeterReading)
        {
            this.customerId = customerId;
            this.name = name;
            this.address = address;
            this.previousMeterReading = previousMeterReading;
        }

        // to access customer record
        public int getCustomerId()
        {
            return customerId;
        }

        public String getName()
        {
            return name;
        }

        public String getAddress()
        {
            return address;
        }

        public double getPreviousMeterReading()
        {
            return previousMeterReading;
        }
    }

    private static final double RATE_PER_UNIT = 10; // Rate per unit in rs

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do
        {
            System.out.println("========================================");
            System.out.println("Electricity Billing System Menu:");
            System.out.println("========================================");
            System.out.println("1. Add new customer");
            System.out.println("2. Generate electricity bill");
            System.out.println("3. Remove customer record");
            System.out.println("4. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            System.out.println("========================================");

            switch (choice)
            {
                case 1:
                    addNewCustomer(scanner);
                    break;
                case 2:
                    generateElectricityBill(scanner);
                    break;
                case 3:
                    removeCustomerRecord(scanner);
                    break;
                case 4:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 4);

        scanner.close();
    }

    private static void addNewCustomer(Scanner scanner)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true));

            System.out.println("Enter customer details:");
            System.out.println("========================================");
            System.out.print("Customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Address: ");
            String address = scanner.nextLine();
            System.out.print("Previous Meter Reading: ");
            double previousMeterReading = scanner.nextDouble();

            writer.write(customerId + "," + name + "," + address + "," + previousMeterReading);
            writer.newLine();
            writer.close();

            System.out.println("========================================");
            System.out.println("Customer added successfully!");
        } catch (IOException e)
        {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
  	private static void generateElectricityBill(Scanner scanner)
  	{
        try
        {
            System.out.print("Enter customer ID to generate bill: ");
            int customerId = scanner.nextInt();
			System.out.println("========================================");
            BufferedReader reader = new BufferedReader(new FileReader("customers.txt"));
            String line;
            boolean customerFound = false;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id == customerId)
                {
                    System.out.println("\nCustomer found:\n Costomer Name : " + parts[1] + " \n Address :" + parts[2]);
                    double previousReading = Double.parseDouble(parts[3]);
                    System.out.println("========================================");
                    System.out.print("Enter current meter reading: ");
                    double currentReading = scanner.nextDouble();
                    System.out.println("========================================");

                    double unitsConsumed = currentReading - previousReading;
                    double billAmount = unitsConsumed * RATE_PER_UNIT;
					System.out.println();
					System.out.println("=========== Electricity Bill ===========");
					System.out.println("Costomer Name : " + parts[1] );
					System.out.println("========================================");
					System.out.println("Address :" + parts[2]);
 					System.out.println("========================================");
                  	System.out.println("Units consumed: " + unitsConsumed);
                  	System.out.println("========================================");
                    System.out.println("Bill amount: Rs." + billAmount);
                  	System.out.println("========================================");
					System.out.println();

                    customerFound = true;
                    break;
                }
            }
            reader.close();

            if (!customerFound)
            {
                System.out.println("Customer with ID " + customerId + " not found.");
            }
        } catch (IOException e)
        {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }


    private static void removeCustomerRecord(Scanner scanner)
    {
        try
        {
            System.out.print("Enter customer ID to remove: ");
            int customerId = scanner.nextInt();

            File inputFile = new File("customers.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean customerFound = false;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id != customerId)
                {
                    writer.write(line);
                    writer.newLine();
                }
                else
                {
                    customerFound = true;
                }
            }
            reader.close();
            writer.close();

            if (customerFound)
            {
                if (!inputFile.delete())
                {
                    System.out.println("Error deleting file");
                    return;
                }
                if (!tempFile.renameTo(inputFile))
                {
                    System.out.println("Error renaming file");
                }
                else
                {
					System.out.println("========================================");
                    System.out.println("Customer record removed successfully.");
                    System.out.println("========================================");
                  	System.out.println();
                }
            }
            else
            {
                System.out.println("Customer with ID " + customerId + " not found.");
            }
        } catch (IOException e)
        {
            System.out.println("Error processing file: " + e.getMessage());
			System.out.println();
        }
    }
}