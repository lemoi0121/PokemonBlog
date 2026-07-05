package com.pokemon.blog.service.impl;

import com.pokemon.blog.entity.Pokemon;
import com.pokemon.blog.entity.Role;
import com.pokemon.blog.dto.response.PokemonResponse;
import com.pokemon.blog.dto.request.CreatePokemonRequest;
import com.pokemon.blog.exception.DuplicateResourceException;
import com.pokemon.blog.exception.ResourceNotFoundException;
import com.pokemon.blog.exception.UnauthorizedException;
import com.pokemon.blog.repository.PokemonRepository;
import com.pokemon.blog.service.PokemonService;
import com.pokemon.blog.util.SecurityUtils;
import com.pokemon.blog.validator.PokemonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PokemonServiceImpl implements PokemonService {

    private static final Logger logger = LoggerFactory.getLogger(PokemonServiceImpl.class);

    @Autowired
    private PokemonRepository pokemonRepository;

    @Override
    public List<PokemonResponse> getAllPokemons() {
        logger.info("Lấy tất cả pokemons");
        List<PokemonResponse> pokemons = pokemonRepository.findAll()
                .stream()
                .map(PokemonResponse::fromEntity)
                .toList();
        logger.debug("Tổng số pokemons: {}", pokemons.size());
        return pokemons;
    }

    @Override
    public PokemonResponse getPokemon(Long id) {
        logger.info("Lấy pokemon với id: {}", id);
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Pokemon không tìm thấy với id: {}", id);
                    return new ResourceNotFoundException("Không tìm thấy Pokemon với id " + id);
                });
        return PokemonResponse.fromEntity(pokemon);
    }

    @Override
    public PokemonResponse createPokemon(CreatePokemonRequest request) {
        String adminName = SecurityUtils.getCurrentUsername();
        logger.info("Admin {} cố thêm pokemon mới: {}", adminName, request.getName());

        if (!SecurityUtils.isAdmin()) {
            logger.warn("User {} không phải admin, không thể thêm pokemon", adminName);
            throw new UnauthorizedException("Chỉ Admin mới có quyền thêm Pokemon");
        }

        // ✅ Business validation: name format
        if (!PokemonValidator.isNameValid(request.getName())) {
            logger.warn("Pokemon name không hợp lệ: {}", request.getName());
            throw new IllegalArgumentException(PokemonValidator.getNameErrorMessage());
        }

        // ✅ Business validation: type
        if (!PokemonValidator.isTypeValid(request.getType())) {
            logger.warn("Pokemon type không hợp lệ");
            throw new IllegalArgumentException("Pokemon type không được trống");
        }

        // ✅ Business validation: duplicate name (case-insensitive)
        if (pokemonRepository.findByName(request.getName()).isPresent()) {
            logger.warn("Pokemon {} đã tồn tại", request.getName());
            throw new DuplicateResourceException("Pokemon " + request.getName() + " đã tồn tại");
        }

        // ✅ Business validation: stats validity
        if (!PokemonValidator.areStatsValid(
                request.getHp(),
                request.getAttack(),
                request.getDefense(),
                request.getSpecialAttack(),
                request.getSpecialDefense(),
                request.getSpeed())) {
            logger.warn("Pokemon stats không hợp lệ");
            throw new IllegalArgumentException(PokemonValidator.getStatsErrorMessage());
        }

        Pokemon pokemon = new Pokemon();
        pokemon.setName(request.getName());
        pokemon.setType(request.getType());
        pokemon.setDescription(request.getDescription());
        pokemon.setHp(request.getHp());
        pokemon.setAttack(request.getAttack());
        pokemon.setDefense(request.getDefense());
        pokemon.setSpecialAttack(request.getSpecialAttack());
        pokemon.setSpecialDefense(request.getSpecialDefense());
        pokemon.setSpeed(request.getSpeed());

        try {
            Pokemon saved = pokemonRepository.save(pokemon);
            logger.info("Pokemon {} được tạo thành công bởi admin {}", saved.getId(), adminName);
            return PokemonResponse.fromEntity(saved);
        } catch (Exception e) {
            logger.error("Lỗi khi tạo pokemon {}: {}", request.getName(), e.getMessage(), e);
            throw e;
        }
    }
}