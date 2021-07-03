package com.vinsguru.inventory.service;

import com.vinsguru.dto.InventoryDto;
import com.vinsguru.events.inventory.InventoryEvent;
import com.vinsguru.events.inventory.InventoryOutOfStockEvent;
import com.vinsguru.events.inventory.InventoryStatus;
import com.vinsguru.events.order.OrderEvent;
import com.vinsguru.inventory.entity.OrderInventoryConsumption;
import com.vinsguru.inventory.repository.OrderInventoryConsumptionRepository;
import com.vinsguru.inventory.repository.OrderInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    @Autowired
    private OrderInventoryRepository inventoryRepository;

    @Autowired
    private OrderInventoryConsumptionRepository consumptionRepository;

    @Autowired
    private InventoryPublisher inventoryPublisher;

    @Transactional
    public void newOrderInventory(OrderEvent orderEvent){
        InventoryDto dto = InventoryDto.of(orderEvent.getPurchaseOrder());
        inventoryRepository.findById(orderEvent.getPurchaseOrder().getProductId())
                .filter(i -> i.getAvailableInventory() > 0 )
                .ifPresentOrElse(i -> {
                    i.setAvailableInventory(i.getAvailableInventory() - 1);
                    consumptionRepository.save(OrderInventoryConsumption.of(orderEvent.getPurchaseOrder().getOrderId(), orderEvent.getPurchaseOrder().getProductId(), 1));
                    inventoryPublisher.emit(new InventoryEvent(dto, InventoryStatus.RESERVED));
                }, () -> {
                    inventoryPublisher.emit(new InventoryOutOfStockEvent(dto, InventoryStatus.RESERVED));
                });
    }

    @Transactional
    public void cancelOrderInventory(OrderEvent orderEvent){
        consumptionRepository.findById(orderEvent.getPurchaseOrder().getOrderId())
                .ifPresent(ci -> {
                    inventoryRepository.findById(ci.getProductId())
                            .ifPresent(i ->
                                i.setAvailableInventory(i.getAvailableInventory() + ci.getQuantityConsumed())
                            );
                    consumptionRepository.delete(ci);
                });
    }

}
