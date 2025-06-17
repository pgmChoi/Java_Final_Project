package studysystem;

public class UserSession {
   private static String currentUser_id;

    public static void setUsername(String user_id) {
        currentUser_id = user_id;
    }

    public static String getUsername() {
        return currentUser_id;
    }

}
