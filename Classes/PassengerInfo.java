package Classes;

public class PassengerInfo {
    private String name, berthPreference;
    private Integer age;
    private Character gender;

    public PassengerInfo(String name, Integer age, Character gender, String berthPreference) {
        this.name = name;
        this.age = age;
        this.berthPreference = berthPreference;
        this.gender = gender;
    }

    public Integer getAge() {
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
}
