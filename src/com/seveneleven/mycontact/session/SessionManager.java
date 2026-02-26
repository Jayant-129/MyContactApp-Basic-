// Tracks the currently logged-in user for the duration of the console session
package com.seveneleven.mycontact.session;

import com.seveneleven.mycontact.model.User;

public class SessionManager {

    private static User currentUser = null;

    public static void login(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
