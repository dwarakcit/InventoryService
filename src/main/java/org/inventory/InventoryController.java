package org.inventory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Retrieve all
    @GetMapping
    public List<Inventory> getAllInventories() {
        System.out.println("Getting all inventories");
        return inventoryService.findAll();
    }

    // Retrieve by ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        return inventoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createInventory(@RequestBody Inventory inventory) {
        // Example business logic for saving inventory
        System.out.println("Inserting inventory" + inventory.getName());
        inventoryService.save(inventory);
        return ResponseEntity.ok("Inventory item created: " + inventory.getName());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(
            @PathVariable Long id,
            @RequestBody Inventory updatedInventory) {

        return inventoryService.findById(id)
                .map(inventory -> {
                    inventory.setName(updatedInventory.getName());
                    inventory.setQuantity(updatedInventory.getQuantity());
                    inventoryService.save(inventory);
                    return ResponseEntity.ok(inventory);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        if (inventoryService.findById(id).isPresent()) {
            inventoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}