package com.pokemon.blog.dto.response;

import com.pokemon.blog.entity.Pokemon;

public class PokemonResponse {

    private Long id;
    private String name;
    private String type;
    private String description;
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;

    public PokemonResponse(Long id, String name, String type, String description, int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
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

    public static PokemonResponse fromEntity(Pokemon pokemon){
        return new PokemonResponse(
          pokemon.getId(),
          pokemon.getName(),
          pokemon.getType(),
          pokemon.getDescription(),
          pokemon.getHp(),
          pokemon.getAttack(),
          pokemon.getDefense(),
          pokemon.getSpecialAttack(),
          pokemon.getSpecialDefense(),
          pokemon.getSpeed()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getSpeed() {
        return speed;
    }
}
