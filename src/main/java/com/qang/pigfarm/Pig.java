package com.qang.pigfarm;

public class Pig extends GameCharacter{
	
	private int m_weight;
	private String m_breed;
	
	public Pig() {
		super("Pig");
		m_weight = 1;
		m_breed = "hog";
		
	}
	
	public Pig(int health, int weight, String breed ) {
		super("Pig");
		m_weight = weight;
		m_breed = breed;
	}
	
	public void oink() {
		System.out.println("Oink.");
	}
	
	public void squeal() {
		System.out.println("Squeaaaal");
	}
	
	public void sing() {
		System.out.println("Oink Oink Oink Oink Oink");
	}
	
	public void eat(Food food) {
		System.out.println("Eating " + food.toString());
	}

}
