package fr.projet.gestionAnnonces.mappers;

import fr.projet.gestionAnnonces.models.dto.AdRequest;
import fr.projet.gestionAnnonces.models.dto.AdResponse;
import fr.projet.gestionAnnonces.models.entity.Ad;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdMapper {

    Ad toEntity(AdRequest request);
    AdResponse toResponse(Ad ad);
    void updateAd(AdRequest request, @MappingTarget Ad ad);

}