package com.qang.pigfarm;


public class GameCharacter {
    protected String name;
    protected double health = 100.0;
    protected long experience = 0;
    protected int attackPower = 1;

    public GameCharacter(String name) {
        this.name = name;
        gainExperience(1);
    }

    public long gainExperience(long experience){
        this.experience += experience;
        return experience;
    }

    public long addAttackPower(long attackPower) {
        this.attackPower += attackPower;
        return attackPower;
    }

    public double heal(double additionalHealth) {
        health += additionalHealth;
        return health;
    }

    public void attack(GameCharacter opponent) {
        opponent.decreaseHealth(attackPower);
    }

    private void decreaseHealth(int attackPower) {
        health -= attackPower;
    }

    public void jump() {
        System.out.println("Jumped up");
    }
    
}
