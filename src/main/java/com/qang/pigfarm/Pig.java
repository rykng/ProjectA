package com.qang.pigfarm;

public class Pig {
	
	private int m_health;
	private int m_weight;
	private String m_breed;
	
	public void oink() {
		System.out.println("Oink Oink.");
	}
	
	public void squeal() {
		System.out.println("Squeaaaal");
	}
	
	public void sing() {
		System.out.println("");
	}
	
	public void eat(Food food) {
		System.out.println("Eating " + food.toString());
	}

}
