package fr.projet.gestionAnnonces.services;

import fr.projet.gestionAnnonces.exceptions.AdNotFoundException;
import fr.projet.gestionAnnonces.exceptions.DuplicateAdException;
import fr.projet.gestionAnnonces.exceptions.InvalidAdOperationException;
import fr.projet.gestionAnnonces.mappers.AdMapper;
import fr.projet.gestionAnnonces.models.dto.AdRequest;
import fr.projet.gestionAnnonces.models.dto.AdResponse;
import fr.projet.gestionAnnonces.models.dto.AdSearchCriteria;
import fr.projet.gestionAnnonces.repositories.AdRepository;
import fr.projet.gestionAnnonces.specifications.AdSpecification;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;

    public AdService(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }

    public AdResponse createAd(@Valid AdRequest request) {
        if (adRepository.existsByTitleAndCategory(request.getTitle(), request.getCategory())) {
            throw new DuplicateAdException(
                    "Ad with title '" + request.getTitle() + "' already exists",
                    List.of("Title must be unique")
            );
        }

        return Optional.of(request)
                .map(adMapper::toEntity)
                .map(adRepository::save)
                .map(adMapper::toResponse)
                .orElseThrow(() -> new InvalidAdOperationException("Failed to create Ad"));
    }


    public AdResponse getAdById(Long id) {
        return adRepository.findById(id)
                .map(adMapper::toResponse)
                .orElseThrow(() -> new AdNotFoundException(id));
    }

    public Page<AdResponse> getAllAds(Pageable pageable) {
        return adRepository.findAll(pageable)
                .map(adMapper::toResponse);
    }

    public AdResponse updateAd(Long id, @Valid AdRequest request) {
        return adRepository.findById(id)
                .map(ad -> {
                    adMapper.updateAd(request, ad);
                    return adRepository.save(ad);
                })
                .map(adMapper::toResponse)
                .orElseThrow(() -> new AdNotFoundException(id));
    }

    public void deleteAd(Long id) {
        adRepository.findById(id)
                .ifPresentOrElse(adRepository::delete,
                        () -> { throw new AdNotFoundException(id); });
    }

    public Page<AdResponse> searchAds(AdSearchCriteria criteria) {
        Sort sort = Sort.by(
                "desc".equalsIgnoreCase(criteria.direction()) ? Sort.Direction.DESC : Sort.Direction.ASC,
                criteria.sort() != null ? criteria.sort() : "createdAt"
        );

        Pageable pageable = PageRequest.of(
                criteria.page() != null ? criteria.page() : 0,
                criteria.size() != null ? criteria.size() : 10,
                sort
        );

        return adRepository.findAll(AdSpecification.build(criteria), pageable)
                .map(adMapper::toResponse);
    }

}