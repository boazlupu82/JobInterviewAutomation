import java.util.Arrays;

public class Users {

    private User[] users;

    /**
     *
     * @param users
     */
    public Users(User[] users) {
        this.users = users;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Users{" +
                "users=" + Arrays.toString(users) +
                '}';
    }
}
