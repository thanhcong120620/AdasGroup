package SpringbootProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringbootProject.entity.CRMEntity.DTP1CRMEntity;
import SpringbootProject.entity.CRMEntity.DTP3FilterData;

public interface DTP1CrmDataRepository extends JpaRepository<DTP1CRMEntity, Long> {

}
