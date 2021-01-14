package com.survivorapi.dto;

import java.util.List;
import java.util.Set;

import com.survivorapi.entity.Item;

/**
 * Classe usada para realização das trocas de itens.
 * 
 * Created by Beatriz on 11/01/21.
 */
public class ExchangeDTO {

    private Long userId;

    private String recipientName;

    private Set<Item> itemsWanted;

    private Set<Item> itemsForPayment;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Set<Item> getItemsWanted() {
        return itemsWanted;
    }

    public void setItemsWanted(Set<Item> itemsWanted) {
        this.itemsWanted = itemsWanted;
    }

    public Set<Item> getItemsForPayment() {
        return itemsForPayment;
    }

    public void setItemsForPayment(Set<Item> itemsForPayment) {
        this.itemsForPayment = itemsForPayment;
    }
}
