package fr.projet.gestionAnnonces.repositories;

import fr.projet.gestionAnnonces.models.entity.Ad;
import fr.projet.gestionAnnonces.models.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long>, JpaSpecificationExecutor<Ad> {

    boolean existsByTitleAndCategory(String title, Category category);

    long countByCategory(Category category);
}
