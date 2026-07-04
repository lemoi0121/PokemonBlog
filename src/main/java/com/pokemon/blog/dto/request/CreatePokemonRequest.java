package com.pokemon.blog.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreatePokemonRequest {

    @NotBlank(message = "Tên không được trống")
    private String name;

    @NotBlank(message = "Loại không được trống")
    private String type;

    @NotBlank(message = "Mô tả không được trống")
    private String description;

    @Min(value = 1, message = "HP phải lớn hơn 0")
    private int hp;

    @Min(value = 1, message = "Attack phải lớn hơn 0")
    private int attack;

    @Min(value = 1, message = "Defense phải lớn hơn 0")
    private int defense;

    @Min(value = 1, message = "Special Attack phải lớn hơn 0")
    private int specialAttack;

    @Min(value = 1, message = "Special Defense phải lớn hơn 0")
    private int specialDefense;

    @Min(value = 1, message = "Speed phải lớn hơn 0")
    private int speed;

    public CreatePokemonRequest() {}

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
}