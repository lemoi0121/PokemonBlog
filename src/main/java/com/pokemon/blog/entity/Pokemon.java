package com.pokemon.blog.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên không được trống ")
    private String name;

    @NotBlank(message = "Loại không được trống")
    private String type;

    @NotBlank(message = "Mô tả không được trống")
    private String description;



    @Min(value = 1, message = "HP phải lớn hơn 0")
    private int hp;

    @Min(value = 1, message = "ATK phải lớn hơn 0")
    private int attack;

    @Min(value = 1, message = "Defense phải lớn hơn 0")
    private int defense;

    @Min(value = 1, message = "SpeAtk phải lớn hơn 0")
    private int specialAttack;

    @Min(value = 1, message = "SpeDef phải lớn hơn 0")
    private int specialDefense;

    @Min(value = 1, message = " phải lớn hơn 0")
    private int speed;

    public Pokemon(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Pokemon(Long id, String name, String type, String description, int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
    }
}
