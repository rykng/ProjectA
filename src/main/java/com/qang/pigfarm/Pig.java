package com.qang.pigfarm;

public class Pig {
	
	private int m_health;
	private int m_weight;
	private String m_breed;
	
	public Pig() {
		m_health = 10;
		m_weight = 1;
		m_breed = "hog";
		
	}
	
	public Pig(int health, int weight, String breed ) {
		m_health = health;
		m_weight = weight;
		m_breed = breed;
	}
	
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
