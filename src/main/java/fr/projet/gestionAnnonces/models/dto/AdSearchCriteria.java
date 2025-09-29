package fr.projet.gestionAnnonces.models.dto;

import fr.projet.gestionAnnonces.models.enums.Category;

import java.math.BigDecimal;
import java.util.List;

public record AdSearchCriteria(
        BigDecimal priceMin,
        BigDecimal priceMax,
        String title,
        List<Category> categories,
        Integer page,
        Integer size,
        String sort,
        String direction
) {}
