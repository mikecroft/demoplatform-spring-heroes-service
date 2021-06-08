package io.mikecroft.superheroes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hero {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;
  private String power;
  private String weakness;
  private int hp = 100;

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
      return id;
  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getWeakness() {
    return weakness;
  }

  public void setWeakness(String weakness) {
    this.weakness = weakness;
  }

  public String getPower() {
    return power;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public int getHp() {
    return hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }
}