package com.pokemon.blog.controller;

import com.pokemon.blog.dto.response.PokemonResponse;
import com.pokemon.blog.dto.request.CreatePokemonRequest;
import com.pokemon.blog.service.PokemonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping
    public List<PokemonResponse> getAllPokemons(){
        return pokemonService.getAllPokemons();
    }

    @GetMapping("/{id}")
    public PokemonResponse getPokemon(@PathVariable Long id) {
        return pokemonService.getPokemon(id);
    }

    @PostMapping
    public PokemonResponse createPokemon(@Valid @RequestBody CreatePokemonRequest request) {
        return pokemonService.createPokemon(request);
    }
}