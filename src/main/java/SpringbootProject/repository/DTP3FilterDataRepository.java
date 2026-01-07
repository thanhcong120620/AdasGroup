package SpringbootProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.enums.DataType;

public interface DTP3FilterDataRepository extends JpaRepository<DTP3FilterData, Long> {
	DTP3FilterData findByphoneNumber1(String phoneNumber1);
	List<DTP3FilterData> findByDataType(DataType dataType);

}
