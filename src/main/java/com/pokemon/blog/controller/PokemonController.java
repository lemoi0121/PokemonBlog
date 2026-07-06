package com.pokemon.blog.controller;

import com.pokemon.blog.dto.response.PokemonResponse;
import com.pokemon.blog.dto.response.PaginationResponse;
import com.pokemon.blog.dto.request.CreatePokemonRequest;
import com.pokemon.blog.service.PokemonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    /**
     * Lấy tất cả pokemons (phân trang).
     *
     * @param page trang hiện tại (0-indexed), mặc định 0
     * @param size số item trên 1 trang, mặc định 10
     * @param sortBy field để sort, mặc định "id"
     * @param sortDirection ASC hoặc DESC, mặc định ASC
     * @return PaginationResponse
     */
    @GetMapping
    public PaginationResponse<PokemonResponse> getAllPokemons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return pokemonService.getAllPokemons(pageable);
    }

    @GetMapping("/{id}")
    public PokemonResponse getPokemon(@PathVariable Long id) {
        return pokemonService.getPokemon(id);
    }

    @PostMapping
    public ResponseEntity<PokemonResponse> createPokemon(@Valid @RequestBody CreatePokemonRequest request) {
        PokemonResponse response = pokemonService.createPokemon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}