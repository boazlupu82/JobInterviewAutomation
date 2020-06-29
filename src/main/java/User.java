import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("dayOfBirth")
    private String dayOfBirth;
    @JsonProperty("email")
    private String email;
    @JsonProperty("childrens")
    private List<Child> childrens;

    /**
     *
     * @param name
     * @param dayOfBirth
     * @param email
     * @param childrens
     */
    public User(String name, String dayOfBirth, String email, List<Child> childrens) {
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.email = email;
        this.childrens = childrens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Child> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Child> childrens) {
        this.childrens = childrens;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dayOfBirth='" + dayOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", childrens=" + childrens +
                '}';
    }
}
