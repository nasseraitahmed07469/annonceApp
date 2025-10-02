package fr.projet.gestionAnnonces.ressources;

import fr.projet.gestionAnnonces.models.dto.AdSearchCriteria;
import fr.projet.gestionAnnonces.models.enums.Category;
import fr.projet.gestionAnnonces.repositories.AdRepository;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import fr.projet.gestionAnnonces.models.dto.AdRequest;
import fr.projet.gestionAnnonces.models.dto.AdResponse;
import fr.projet.gestionAnnonces.services.AdService;

import static fr.projet.gestionAnnonces.utils.Constants.AD_ROOT;
import static fr.projet.gestionAnnonces.utils.Constants.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT)
@Tag(name = "Ads", description = "API for managing ads")
public class AdResource {

    private final AdService adService;
    private final AdRepository adRepository;

    public AdResource(AdService adService, AdRepository adRepository) {
        this.adService = adService;
        this.adRepository = adRepository;
    }

    @PostMapping(AD_ROOT)
    @Operation(
            summary = "Create a new ad",
            description = "Add a new ad to the database.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ad created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<AdResponse> createAd(@Valid @RequestBody AdRequest adRequest) {
        AdResponse ad = adService.createAd(adRequest);
        return new ResponseEntity<>(ad, HttpStatus.CREATED);
    }

    @GetMapping(AD_ROOT)
    @Operation(
            summary = "Get all ads",
            description = "Retrieve the list of all ads with pagination.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ads retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<Page<AdResponse>> getAllAds(Pageable pageable) {
        return ResponseEntity.ok(adService.getAllAds(pageable));
    }

    @GetMapping(AD_ROOT + "/count")
    public ResponseEntity<Long> getAdsCountByCategory(@RequestParam String category) {
        long count = adRepository.countByCategory(Category.valueOf(category));
        return ResponseEntity.ok(count);
    }

    @GetMapping(AD_ROOT + "/{id}")
    @Operation(
            summary = "Get ad by ID",
            description = "Retrieve a specific ad by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ad found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ad not found",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<AdResponse> getAdById(@PathVariable Long id) {
        return ResponseEntity.ok(adService.getAdById(id));
    }

    @PutMapping(AD_ROOT + "/{id}")
    @Operation(
            summary = "Update an ad",
            description = "Update an existing ad.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ad updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ad not found",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<AdResponse> updateAd(@PathVariable Long id, @Valid @RequestBody AdRequest adRequest) {
        return ResponseEntity.ok(adService.updateAd(id, adRequest));
    }

    @DeleteMapping(AD_ROOT + "/{id}")
    @Operation(
            summary = "Delete an ad",
            description = "Delete an ad from the database.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ad deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Ad not found",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
        adService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(AD_ROOT + "/search")
    @Operation(
            summary = "Search ads",
            description = "Search ads using multiple optional criteria: title, price range, categories.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ads retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<Page<AdResponse>> searchAds(@ModelAttribute AdSearchCriteria criteria) {
        Page<AdResponse> result = adService.searchAds(criteria);
        return ResponseEntity.ok(result);
    }
}