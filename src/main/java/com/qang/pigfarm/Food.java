package com.qang.pigfarm;

public class Food {
	
	private String m_name;
	private int m_foodpower;
	
    public Food() {
    	m_name = "undocumented";
    	m_foodpower = 1;
    }
    
    public Food(String foodtype, int power) {
    	m_name = foodtype;
    	m_foodpower = power;
    }
    
    public String toString() {
    	return m_name;
    }
    
    public String getFoodName() {
    	return m_name;
    }
    
    public int getPower() {
    	return m_foodpower;
    }
}
