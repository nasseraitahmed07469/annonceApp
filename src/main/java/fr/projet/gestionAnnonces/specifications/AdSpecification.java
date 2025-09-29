package fr.projet.gestionAnnonces.specifications;

import fr.projet.gestionAnnonces.models.dto.AdSearchCriteria;
import fr.projet.gestionAnnonces.models.entity.Ad;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class AdSpecification {

    public static Specification<Ad> build(AdSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = Stream.of(
                            Optional.ofNullable(criteria.title())
                                    .filter(s -> !s.isBlank())
                                    .map(title -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"))
                                    .orElse(null),
                            Optional.ofNullable(criteria.priceMin())
                                    .map(min -> cb.ge(root.get("price"), min))
                                    .orElse(null),
                            Optional.ofNullable(criteria.priceMax())
                                    .map(max -> cb.le(root.get("price"), max))
                                    .orElse(null),
                            Optional.ofNullable(criteria.categories())
                                    .filter(list -> !list.isEmpty())
                                    .map(list -> root.get("category").in(list))
                                    .orElse(null)
                    )
                    .filter(Objects::nonNull)
                    .toList();

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}