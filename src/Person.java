public class Person {
    private String name;
    private String surname;
    private String email;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Person(String name, String surname, String email){
        this.name=name;
        this.surname=surname;
        this.email=email;
    }

    public void printPersonDetails(){
        System.out.println("Name :"+this.name);
        System.out.println("Surname :"+ this.surname);
        System.out.println("Email :"+this.email+"\n");
    }



}
