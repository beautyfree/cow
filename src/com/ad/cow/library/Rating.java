package com.ad.cow.library;

import java.util.Date;

public class Rating {
    private String name;
	private int  level;
	private float exp;
 
    public Rating(String name, int level, float exp) {
    	this.name = name;
    	this.level = level;
    	this.exp = exp;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
    public void setExp(float exp) {
        this.exp = exp;
    }
    public float getExp() {
        return exp;
    }
}
