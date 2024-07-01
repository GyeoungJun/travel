package travel.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import travel.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "myFlightInfos",
    path = "myFlightInfos"
)
public interface MyFlightInfoRepository
    extends PagingAndSortingRepository<MyFlightInfo, Long> {
    List<MyFlightInfo> findByStatus(String status);
}
