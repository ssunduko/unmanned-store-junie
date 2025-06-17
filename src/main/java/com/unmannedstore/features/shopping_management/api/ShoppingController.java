package com.unmannedstore.features.shopping_management.api;

import com.unmannedstore.features.shopping_management.api.dto.BasketContentsResponse;
import com.unmannedstore.features.shopping_management.api.dto.BasketUpdateResponse;
import com.unmannedstore.features.shopping_management.api.dto.ItemAddRequest;
import com.unmannedstore.features.shopping_management.api.dto.ProductDto;
import com.unmannedstore.features.shopping_management.domain.model.BasketItem;
import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.service.ShoppingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for shopping-related operations.
 */
@RestController
@RequestMapping("/api/stores/{storeId}/baskets/{basketId}/items")
public class ShoppingController {

    private final ShoppingService shoppingService;

    /**
     * Constructor for ShoppingController.
     * 
     * @param shoppingService The shopping service
     */
    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    /**
     * Get the contents of a shopping basket.
     * 
     * @param storeId The store ID
     * @param basketId The basket ID
     * @return The basket contents
     */
    @GetMapping
    public ResponseEntity<BasketContentsResponse> getBasketContents(
            @PathVariable String storeId,
            @PathVariable String basketId) {

        Optional<ShoppingSession> sessionOpt = shoppingService.findSessionByBasketId(basketId);

        if (sessionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ShoppingSession session = sessionOpt.get();

        // Convert basket items to DTOs
        List<ProductDto> productDtos = session.getItems().stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toList());

        BasketContentsResponse response = new BasketContentsResponse(
                basketId,
                productDtos,
                session.getRunningTotal().getTotal(),
                session.getItemCount(),
                session.getLastUpdatedAt()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Add an item to a shopping basket.
     * 
     * @param storeId The store ID
     * @param basketId The basket ID
     * @param request The item add request
     * @return The basket update response
     */
    @PostMapping
    public ResponseEntity<BasketUpdateResponse> addItemToBasket(
            @PathVariable String storeId,
            @PathVariable String basketId,
            @RequestBody ItemAddRequest request) {

        Optional<ShoppingSession> sessionOpt = shoppingService.findSessionByBasketId(basketId);

        if (sessionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            ShoppingSession updatedSession;

            // Add item by RFID tag if provided, otherwise by product ID
            if (request.getRfidTag() != null && !request.getRfidTag().isEmpty()) {
                updatedSession = shoppingService.addItemToSessionByRfidTag(
                        sessionOpt.get().getId(),
                        request.getRfidTag()
                );
            } else if (request.getProductId() != null && !request.getProductId().isEmpty()) {
                updatedSession = shoppingService.addItemToSession(
                        sessionOpt.get().getId(),
                        request.getProductId()
                );
            } else {
                return ResponseEntity.badRequest().build();
            }

            // Get the last added item
            BasketItem lastAddedItem = updatedSession.getItems().get(updatedSession.getItems().size() - 1);

            BasketUpdateResponse response = new BasketUpdateResponse(
                    basketId,
                    "item_added",
                    convertToProductDto(lastAddedItem),
                    updatedSession.getRunningTotal().getTotal(),
                    updatedSession.getItemCount(),
                    "Item added to basket"
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Remove an item from a shopping basket.
     * 
     * @param storeId The store ID
     * @param basketId The basket ID
     * @param itemId The item ID
     * @return The basket update response
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<BasketUpdateResponse> removeItemFromBasket(
            @PathVariable String storeId,
            @PathVariable String basketId,
            @PathVariable String itemId) {

        Optional<ShoppingSession> sessionOpt = shoppingService.findSessionByBasketId(basketId);

        if (sessionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Find the item before removing it
        BasketItem itemToRemove = null;
        for (BasketItem item : sessionOpt.get().getItems()) {
            if (item.getId().equals(itemId)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove == null) {
            return ResponseEntity.notFound().build();
        }

        // Convert to DTO before removing
        ProductDto removedItemDto = convertToProductDto(itemToRemove);

        try {
            ShoppingSession updatedSession = shoppingService.removeItemFromSession(
                    sessionOpt.get().getId(),
                    itemId
            );

            BasketUpdateResponse response = new BasketUpdateResponse(
                    basketId,
                    "item_removed",
                    removedItemDto,
                    updatedSession.getRunningTotal().getTotal(),
                    updatedSession.getItemCount(),
                    "Item removed from basket"
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Convert a BasketItem to a ProductDto.
     * 
     * @param basketItem The basket item
     * @return The product DTO
     */
    private ProductDto convertToProductDto(BasketItem basketItem) {
        Product product = basketItem.getProduct();

        return new ProductDto(
                basketItem.getId(),
                product.getId(),
                product.getName(),
                basketItem.getPrice(),
                basketItem.getQuantity(),
                basketItem.getAddedAt(),
                product.getRfidTag(),
                product.getImageUrl()
        );
    }
}
