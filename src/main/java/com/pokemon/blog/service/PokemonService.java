package com.pokemon.blog.service;

import com.pokemon.blog.dto.response.PokemonResponse;
import com.pokemon.blog.dto.request.CreatePokemonRequest;
import java.util.List;

public interface PokemonService {

    /**
     * Lấy tất cả pokemons.
     *
     * @return danh sách PokemonResponse
     */
    List<PokemonResponse> getAllPokemons();

    /**
     * Lấy chi tiết một pokemon.
     *
     * @param id ID của pokemon
     * @return PokemonResponse nếu tìm thấy
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu pokemon không tồn tại
     */
    PokemonResponse getPokemon(Long id);

    /**
     * Tạo pokemon mới (chỉ admin).
     *
     * @param request thông tin pokemon
     * @return PokemonResponse của pokemon vừa tạo
     * @throws com.pokemon.blog.exception.UnauthorizedException nếu user không phải admin
     * @throws com.pokemon.blog.exception.DuplicateResourceException nếu pokemon đã tồn tại
     */
    PokemonResponse createPokemon(CreatePokemonRequest request);
}