
import java.util.InputMismatchException;
import java.util.Scanner;

public class PlainManagement {
    private static Scanner input = new Scanner(System.in);


//    Jagged Array to getSeating plan
    private static int[][] seatingPlanList = {
            new int[14],
            new int[12],
            new int[12],
            new int[14]
    };

    // Array to store sold tickets
    private static Ticket[][] soldTicketArray = new Ticket[4][14];
    private static String rowLetter;


    public static void main(String[] args) {
        System.out.println("\nWelcome to the Plain Management Application \n");
        homepage();
    }
    public static void homepage(){

        System.out.println("*****************************************************");
        System.out.println("*\t\t\t\t\tMenu Options\t\t\t\t\t*");
        System.out.println("*****************************************************");
        System.out.println("\t 1. Buy a seat");
        System.out.println("\t 2. Cansel a seat");
        System.out.println("\t 3. Find first available seat");
        System.out.println("\t 4. Show seating plan");
        System.out.println("\t 5. Print tickets information and total sales");
        System.out.println("\t 6. Search tickets");
        System.out.println("\t 0. Quit");
        System.out.println("*****************************************************");
//        System.out.print("Please select an option :");
        int option;
        while (true) {
            try {
                System.out.print("Please select an option:");
                option = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                input.nextLine(); // Consume the invalid input
            }
        }
        switch (option){
            case 0:
                System.exit(0);

            case 1:
                System.out.println();
                System.out.println("******************");
                System.out.println("*\tBuy Seat\t*");
                System.out.println("******************\n");

                buy_seat();
                break;

            case 2:
                System.out.println();
                System.out.println("******************");
                System.out.println("*\tCansel Seat\t*");
                System.out.println("******************\n");

                cansel_seat();
                break;

            case 3:
                System.out.println();
                System.out.println("********************************");
                System.out.println("*\tFind First Available  Seat\t*");
                System.out.println("********************************\n");

                find_first_available();
                break;

            case 4:
                System.out.println();
                System.out.println("*******************");
                System.out.println("*\tSeating Plan\t*");
                System.out.println("*******************");

                show_seating_plan();
                break;

            case 5:
                System.out.println();
                printTicketInfo();
                break;

            case 6:
                System.out.println();
                System.out.println("*******************");
                System.out.println("*\tSearch Ticket\t*");
                System.out.println("*******************");
                search_ticket();
                break;

            default:
                System.out.println("Invalid input");
                homepage();

        }

    }


    private static void buy_seat() {
        rowLetter = validRowLetter();
        int seatNum = validSeatNum();

        // check the seat is sold or available
        if (seatingPlanList[getRowNumber(rowLetter)][seatNum-1] == 1){
            System.out.println("Seat is already Sold! Select another seat\n");
            homepage();
            return;
        }else {
            String name,surname,email;
            //Validate FirstName
            while (true) {
                System.out.print("Enter your First Name :");
                name = input.next();
                if (!name.matches("[a-zA-Z]+")) {
                    System.out.println("First name invalid! Use only letters");
                }else {
                    break;
                }
            }
            //Validate SurName
            while (true){
                System.out.print("Enter your Surname :");
                surname = input.next();
                if (!surname.matches("[a-zA-Z]+")){
                    System.out.println("Surname invalid! Use only letters");
                }else {
                    break;
                }
            }
            //Validate Email
            while (true) {
                System.out.print("Enter your Email :");
                email = input.next();
                if (isValidEmail(email)){
                    break;
                }
            }

            Person newRecord = new Person(name,surname,email);

            int price = checkPrice(seatNum);
            Ticket ticket = new Ticket(rowLetter,seatNum,price,newRecord);

            ticket.printTicketDetails();

            //Store ticket object in the 2D array
            soldTicketArray[getRowNumber(rowLetter)][seatNum-1] = ticket;

            System.out.println("Seat No. "+seatNum+" sold successfully.\n");
            seatingPlanList[getRowNumber(rowLetter)][seatNum-1] =1;

            // saving to a file
            ticket.saveDetails();

        }

        homepage();
    }


    private static boolean isValidEmail(String email){
       String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
       //https://www.geeksforgeeks.org/check-email-address-valid-not-java/
       if (!email.matches(emailRegex)) {
           System.out.println("Email invalid! Try again");
           return false;
       }
        return true;
    }


    private static void cansel_seat() {
        rowLetter = validRowLetter();
        int seatNum = validSeatNum();

        // check the seat is sold or available
        if (seatingPlanList[getRowNumber(rowLetter)][seatNum-1] == 0){
            System.out.println("Seat is already Cancelled! Select another seat\n");
            homepage();
            return;
        }else {

            // Remove the Ticket object from the 2D array
            soldTicketArray[getRowNumber(rowLetter)][seatNum-1] = null;

            System.out.println("Seat No. "+seatNum+" cancelled successfully.\n");
            seatingPlanList[getRowNumber(rowLetter)][seatNum-1] =0;

            Ticket ticket = new Ticket(rowLetter, seatNum);
            ticket.deleteDetatails();

        }

        homepage();

    }

    private static void find_first_available(){
        boolean seatFound = false;
        for (int i=0; i<seatingPlanList.length; i++ ){
            for(int j=0; j<seatingPlanList[i].length; j++){
                if(seatingPlanList[i][j] == 0){
                    System.out.println("First available seat is in row number "+ getRowLetter(i) + " seat number " + (j+1));
                    seatFound = true;
                    break;
                }
            }
            if(seatFound){
                break;
            }else {
                System.out.println("No available seats found!\n");
            }
        }
        System.out.println();
        homepage();
    }

    private static void show_seating_plan() {
        for (int i=0; i<seatingPlanList.length; i++){
            for (int j = 0; j < seatingPlanList[i].length; j++) {
                if(seatingPlanList[i][j] == 0) {
                    System.out.print("O");
                }
                if (seatingPlanList[i][j] == 1){
                    System.out.print("X");
                }
            }
            System.out.println();

            }
        System.out.println("\n");
        homepage();
    }

    private static void search_ticket(){
        rowLetter = validRowLetter();
        int seatNum = validSeatNum();

        if (soldTicketArray[getRowNumber(rowLetter)][seatNum-1] != null){
            Ticket ticket = soldTicketArray[getRowNumber(rowLetter)][seatNum-1];
            ticket.printTicketDetails();
            homepage();
        }else {
            System.out.println("This seat is available ");
            homepage();
        }

    }

    private static String validRowLetter(){
        boolean valid;
        String value = null;

        // validate row letter
        valid=true;
        while (valid) {
            System.out.print("Enter a row letter (A-D) : ");
            value = input.next().toUpperCase();
            if (!value.matches("[A-D]")){
                System.out.println("Invalid row letter! Try again.\n");
            }else {
                valid = false;
            }
        }
        return value;
    }

    private static int validSeatNum(){
        boolean valid;
        int value=0;
        int rowNumber = getRowNumber(rowLetter);
        // validate seat number
        valid = true;
        while (valid) {
            try {
                System.out.print("Enter a seat Number (1-" + seatingPlanList[rowNumber].length + ") : ");
                value = input.nextInt();
                if (value < 1 || value > seatingPlanList[rowNumber].length) {
                    System.out.println("Invalid seat Number for row " + rowLetter + " ! Try again.\n");
                } else {
                    valid = false;
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input!");
                input.next();
            }
        }
        return value;
    }

    //Checking the price
    private static int checkPrice(int seatNum){
        int price;
        if (seatNum <= 5){
            price = 200;
        }else if (seatNum <= 9){
            price = 150;
        }else{
            price = 180;
        }
        return price;
    }

    private static void printTicketInfo(){
        int totalPrice = 0;
        for (int rows=0; rows<soldTicketArray.length; rows++){
            for (int seat=0; seat<soldTicketArray[rows].length;seat++){
                if (soldTicketArray[rows][seat] != null){
                    Ticket ticket= soldTicketArray[rows][seat];
                    ticket.printTicketDetails();
                    totalPrice += ticket.getPrice();
                }
            }
        }
        System.out.println("Total sales : £"+ totalPrice);
        homepage();
    }

    // Converting the row letter  to access the jagged array
    private static int getRowNumber(String letter) {
        switch (letter) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            default:
                return -1;
        }
    }


    //Converting the jagged array index to rowLetter
    public static String getRowLetter(int number){
        switch (number) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            default:
                return "O";
        }
    }

}