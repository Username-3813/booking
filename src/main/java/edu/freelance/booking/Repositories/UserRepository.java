package edu.freelance.booking.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.freelance.booking.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
