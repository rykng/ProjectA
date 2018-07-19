package com.qang.pigfarm;

public class Food {
	
	private String m_foodtype;
	private int m_foodpower;
	
    public Food() {
    	m_foodtype = "beets";
    	m_foodpower = 1;
    }
    
    public Food(String foodtype, int power) {
    	m_foodtype = foodtype;
    	m_foodpower = power;
    }
    
    public String toString() {
    	return m_foodtype;
    }
    
    public String getFoodType() {
    	return m_foodtype;
    }
    
    public int getPower() {
    	return m_foodpower;
    }
}
