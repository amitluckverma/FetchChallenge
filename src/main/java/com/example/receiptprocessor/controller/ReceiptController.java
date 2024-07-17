
package com.example.receiptprocessor.controller;

import com.example.receiptprocessor.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class ReceiptController {

    private final Map<String, Receipt> receipts = new HashMap<>();
    private final Map<String, Integer> receiptPoints = new HashMap<>();

    @PostMapping("/receipts/process")
    public ResponseEntity<ReceiptID> processReceipt(@RequestBody Receipt receipt) {
        String id = UUID.randomUUID().toString();
        int points = calculatePoints(receipt);
        receipts.put(id, receipt);
        receiptPoints.put(id, points);

        return ResponseEntity.ok(new ReceiptID(id));
    }

    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<Points> getPoints(@PathVariable String id) {
        Integer points = receiptPoints.get(id);
        if (points == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new Points(points));
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // One point for every alphanumeric character in the retailer name
        for (char c : receipt.getRetailer().toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                points++;
            }
        }

        // 50 points if the total is a round dollar amount with no cents
        double total = Double.parseDouble(receipt.getTotal());
        if (total == Math.floor(total)) {
            points += 50;
        }

        // 25 points if the total is a multiple of 0.25
        if (total % 0.25 == 0) {
            points += 25;
        }

        // 5 points for every two items on the receipt
        points += (receipt.getItems().size() / 2) * 5;

        // If the trimmed length of the item description is a multiple of 3,
        // multiply the price by 0.2 and round up to the nearest integer.
        for (Item item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                double price = Double.parseDouble(item.getPrice());
                points += Math.ceil(price * 0.2);
            }
        }

        // 6 points if the day in the purchase date is odd
        String[] dateParts = receipt.getPurchaseDate().split("-");
        int day = Integer.parseInt(dateParts[2]);
        if (day % 2 != 0) {
            points += 6;
        }

        // 10 points if the time of purchase is after 2:00pm and before 4:00pm
        String[] timeParts = receipt.getPurchaseTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        if (hour >= 14 && hour < 16) {
            points += 10;
        }

        return points;
    }
}
