package com.portfolio.elkeno_jones.mindgamesmaven.model;

import java.util.Date;

/**
 *
 * @author Elkeno
 */
public class SecurityObj {

    private Integer userId;
    private String token;
    private Long timeRemaining;

    public SecurityObj() {

    }

    public SecurityObj(Integer userId, String token, Long timeRemaining) {
        this.timeRemaining = timeRemaining;
        this.token = token;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(Long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

}
