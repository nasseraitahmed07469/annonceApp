package fr.projet.gestionAnnonces.models.dto;

import fr.projet.gestionAnnonces.models.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String author;
    private String email;
    private String phone;
    private Boolean active;
}