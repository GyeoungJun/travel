package travel.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import travel.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "lodgingReservations",
    path = "lodgingReservations"
)
public interface LodgingReservationRepository
    extends PagingAndSortingRepository<LodgingReservation, Long> {}
