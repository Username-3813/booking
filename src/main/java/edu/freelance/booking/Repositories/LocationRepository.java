package edu.freelance.booking.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.freelance.booking.Models.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
