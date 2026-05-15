package edu.freelance.booking.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.freelance.booking.Models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

    
}
