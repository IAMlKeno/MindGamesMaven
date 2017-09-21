package com.portfolio.elkeno_jones.mindgamesmaven.service;

import com.portfolio.elkeno_jones.mindgamesmaven.dao.SecurityDaoImpl;
import com.portfolio.elkeno_jones.mindgamesmaven.model.SecurityObj;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Elkeno Jones
 */
public class SecurityImpl implements Security {

    protected SecurityDaoImpl ses;

    protected static final Long THIRTY_MINS
            = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES);

    /**
     * This method will generate a unique key to be used for the session to
     * monitor user access
     *
     * @param username
     * @return
     */
    @Override
    public String generateSessionToken(String username) {
        String token = null;
        try {
            if (username != null) {
                token = UUID.randomUUID().toString().toUpperCase()
                        + "|" + username;
                System.out.println("Token generated [" + token + "]");
            }
        } catch (Exception e) {
            System.out.println("[Error during token generation]: " + e.getMessage());
        }

        return token;
    }

    @Override
    public boolean checkAccess(String sessionToken, Integer userId) {
        boolean isSessionValid = false;
        Long currDt;
        Long storedDt;
        if (sessionToken != null && userId != null) {
            SecurityObj session = ses.getSecurityAccess(userId);

            if (session.getToken().equals(sessionToken)) {
                currDt = new Date().getTime();
                storedDt = session.getTimeRemaining();
                Long dif = currDt - storedDt;

                if (dif <= THIRTY_MINS) {
                    isSessionValid = true;
                }
            }
        }

        return isSessionValid;
    }

    @Override
    public boolean beginSession(Integer userId, String sessionToken) {
        boolean isSuccess = false;

        try {
            ses.updateInsertTokenAccess(userId, sessionToken);
            isSuccess = true;
        } catch (Exception e) {

        }

        return isSuccess;
    }

    @Override
    public boolean removeSession(Integer userId) {
        boolean isSuccess = false;
        try {
            ses.deleteTokenEntry(userId);
            isSuccess = true;
        } catch (Exception e) {

        }

        return isSuccess;
    }

    public SecurityDaoImpl getSes() {
        return ses;
    }

    public void setSes(SecurityDaoImpl ses) {
        this.ses = ses;
    }
}
