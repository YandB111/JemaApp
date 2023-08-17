package com.jema.app.response;

import java.util.List;

import com.jema.app.dto.InventoryPriceNegotiationHistoryDTO;
import com.jema.app.dto.PriceHistoryListView;
import com.jema.app.entities.InventoryRequest;

import lombok.Data;

@Data
public class InventoryHisRespo {
	   private InventoryPriceNegotiationHistoryDTO priceNegotiationHistoryDTO;

	    // Getter and setter method for priceNegotiationHistoryDTO
	    // ...

	    // Method to set the priceNegotiationHistoryDTO and return this object
	    public InventoryHisRespo withPriceNegotiationHistoryDTO(InventoryPriceNegotiationHistoryDTO dto) {
	        this.priceNegotiationHistoryDTO = dto;
	        return this;
	    }
}
