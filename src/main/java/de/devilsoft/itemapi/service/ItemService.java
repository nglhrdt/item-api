package de.devilsoft.itemapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.devilsoft.itemapi.domain.Item;
import de.devilsoft.itemapi.model.ItemModel;
import de.devilsoft.itemapi.repository.ItemRepository;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository
                .findAll()
                .stream()
                .map(this::modelToDomain)
                .collect(Collectors.toList());
    }

    public Item findById(String id) {
        return modelToDomain(itemRepository.findById(id).get());
    }

    public Item createItem(Item item) {
        final ItemModel itemModel = itemRepository.save(domainToModel(item));
        return modelToDomain(itemModel);
    }

    public void deleteById(String id) {
        itemRepository.deleteById(id);
    }

    private Item modelToDomain(ItemModel itemModel) {
        return new Item(itemModel.getId(), itemModel.getName());
    }

    private ItemModel domainToModel(Item item) {
        return new ItemModel(item.getId(), item.getName());
    }
}
