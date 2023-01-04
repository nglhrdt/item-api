package de.devilsoft.itemapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import de.devilsoft.itemapi.api.ItemsApi;
import de.devilsoft.itemapi.domain.Item;
import de.devilsoft.itemapi.model.ItemDto;
import de.devilsoft.itemapi.service.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;

@RestController
public class ItemsController implements ItemsApi {
    @Autowired
    ItemService itemService;

    @Override
    public ResponseEntity<List<ItemDto>> getItems(@Max(100) @Valid Integer limit) {
        return new ResponseEntity<>(itemService.findAll().stream().map(this::domainToApi).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ItemDto> createItem(@Valid ItemDto item) {
        final Item domainItem = itemService.createItem(apiToDomain(item));
        return new ResponseEntity<>(domainToApi(domainItem), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ItemDto> getItemById(String id) {
        return new ResponseEntity<ItemDto>(domainToApi(itemService.findById(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteItemById(String id) {
        itemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ItemDto domainToApi(Item domainItem) {
        final ItemDto item = new ItemDto();
        item.id(domainItem.getId());
        item.name(domainItem.getName());
        return item;
    }

    private Item apiToDomain(ItemDto item) {
        return new Item(item.getId(), item.getName());
    }
}
