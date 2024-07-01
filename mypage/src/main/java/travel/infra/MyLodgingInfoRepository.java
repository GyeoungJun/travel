package travel.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import travel.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "myLodgingInfos",
    path = "myLodgingInfos"
)
public interface MyLodgingInfoRepository
    extends PagingAndSortingRepository<MyLodgingInfo, Long> {
    List<MyLodgingInfo> findByStatus(String status);
}
