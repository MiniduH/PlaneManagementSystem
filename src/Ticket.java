import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private String rawLetter;
    private int seatNum;
    private int price;

    private Person person;

    public String getRawLetter() {
        return rawLetter;
    }

    public void setRawLetter(String rawLetter) {
        this.rawLetter = rawLetter;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }

    public Ticket(String rawLetter, int seatNum, int price, Person person){
        this.price=price;
        this.seatNum=seatNum;
        this.rawLetter=rawLetter;
        this.person=person;
    }

    public void printTicketDetails(){
        System.out.println("\nTicket Information");
        System.out.println("Row : "+ this.rawLetter);
        System.out.println("Seat No:"+ this.seatNum);
        System.out.println("Price of the seat : £"+ this.price+"\n");
        System.out.println("Personal Details");
        this.person.printPersonDetails();
        System.out.println("--------------------------");

    }

    // saving Ticket details
    public void saveDetails(){
        String TicketFileName = this.rawLetter + "-" + seatNum + ".txt";
        try {
            FileWriter saveFile = new FileWriter(TicketFileName);

            saveFile.write("Ticket Information\n\n");
            saveFile.write("Row is - "+ this.rawLetter+"\n");
            saveFile.write("Seat is - "+ this.seatNum+"\n");
            saveFile.write("Price is - £"+ this.price+"\n\n");

            saveFile.write("Personal Details\n\n");
            saveFile.write("Name -"+ person.getName()+"\n");
            saveFile.write("Sur Name -" + person.getSurname()+ "\n");
            saveFile.write("Email -"+person.getEmail()+ "\n");
            saveFile.close();
            System.out.println("Ticket " + TicketFileName + " saved successfully!");

        } catch (IOException e) {
            System.out.println("Error occured While saving details !");
        }
    }

    public Ticket(String rawLetter, int seatNum){
        this.rawLetter=rawLetter;
        this.seatNum= seatNum;
    }

    public void deleteDetatails(){
        String TicketFileName = this.rawLetter + "-" + seatNum + ".txt";
        File deteleFile = new File(TicketFileName);
        if (deteleFile.delete()){
            System.out.println("Ticket "+ TicketFileName+ " Deleted!");
        }else {
            System.out.println("Error!");
        }
    }



}
