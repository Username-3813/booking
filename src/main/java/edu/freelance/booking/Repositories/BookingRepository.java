package edu.freelance.booking.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.freelance.booking.Models.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
}
