package de.devilsoft.itemapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.devilsoft.itemapi.model.ItemModel;

public interface ItemRepository extends MongoRepository<ItemModel, String> {
    
}
