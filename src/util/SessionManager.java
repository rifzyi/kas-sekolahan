// File: util/SessionManager.java
package util;

import model.User;

/** Singleton sederhana untuk menyimpan user yang sedang login. */
public final class SessionManager {
    private static final SessionManager INSTANCE = new SessionManager();
    private User currentUser;

    private SessionManager() {}

    public static SessionManager getInstance() { return INSTANCE; }
    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User currentUser) { this.currentUser = currentUser; }
    public int getCurrentUserId() { return currentUser == null ? 0 : currentUser.getIdUser(); }
    public void clear() { currentUser = null; }
}
