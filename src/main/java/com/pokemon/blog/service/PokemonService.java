package com.pokemon.blog.service;

import com.pokemon.blog.dto.response.PokemonResponse;
import com.pokemon.blog.dto.request.CreatePokemonRequest;
import java.util.List;

public interface PokemonService {
    List<PokemonResponse> getAllPokemons();

    PokemonResponse getPokemon(Long id);

    PokemonResponse createPokemon(CreatePokemonRequest request);
}