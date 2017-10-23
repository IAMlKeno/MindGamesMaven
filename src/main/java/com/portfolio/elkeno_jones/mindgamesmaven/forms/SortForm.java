/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.elkeno_jones.mindgamesmaven.forms;

import java.io.Serializable;

/**
 *
 * @author Elkeno
 */
public class SortForm implements Serializable{
    private Integer sortById;
    private String sortBy;
    private String sortDir;

    public Integer getSortById() {
        return sortById;
    }

    public void setSortById(Integer sortById) {
        this.sortById = sortById;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
    
    
}
