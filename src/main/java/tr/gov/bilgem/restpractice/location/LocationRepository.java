package tr.gov.bilgem.restpractice.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.gov.bilgem.restpractice.model.Group;
import tr.gov.bilgem.restpractice.model.Location;
import tr.gov.bilgem.restpractice.repository.AbstractRepository;

@Repository
public interface LocationRepository extends AbstractRepository<Location, Long> {

    @Override
    @Query("SELECT l FROM locations l WHERE " +
            "LOWER(l.city) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(l.county) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(l.country) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<Location> search(@Param("q") String keyword, Pageable pageable);
}
