package SpringbootProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.enums.DataType;

public interface DTP3FilterDataRepository extends JpaRepository<DTP3FilterData, Long> {
	DTP3FilterData findByphoneNumber1(String phoneNumber1);
	List<DTP3FilterData> findByDataType(DataType dataType);
//	void deleteByphoneNumber1 (String phoneNumber1);
	
	@Modifying
    @Transactional
    int deleteByphoneNumber1(String phone);
	
	@Modifying
    @Transactional
	@Query(
		    value = "SELECT * FROM abc.dtp3filter_data WHERE phone_number1 IN (SELECT phone_number1 FROM abc.dtp3filter_data GROUP BY phone_number1 HAVING COUNT(*) > 1)",
		    nativeQuery = true
		)
	List<DTP3FilterData> findAllByPhoneNumber1();
}
