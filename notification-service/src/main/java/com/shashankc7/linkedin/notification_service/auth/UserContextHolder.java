package com.shashankc7.linkedin.notification_service.auth;

public class UserContextHolder
{
    //We are using a thread local here --> if we want to store some data whose scope is within that thread i.e the data only persists until that thread is alive.
    // And also no other thread can access its data.
    private static final ThreadLocal<Long> currentUserID = new ThreadLocal<>();

    public static Long getCurrentUserId()
    {
        return currentUserID.get();
    }

    static void setCurrentUserID(Long userId)
    {
        currentUserID.set(userId);
    }

    static void clear()
    {
        currentUserID.remove();
    }
}
