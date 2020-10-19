package Classes;

public class PassengerInfo {
    private String name, age, nationality, berthPreference;
    private Character gender;

    public PassengerInfo(String name, String age, String nationality, Character gender, String berthPreference) {
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.berthPreference = berthPreference;
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public String getBerthPreference() {
        return berthPreference;
    }

    public Character getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }
}
