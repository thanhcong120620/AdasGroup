package SpringbootProject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import SpringbootProject.dto.dataProcess.DTP3SearchRequest;
import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.enums.DataType;

public interface IDTP3FilterDataEntity {
	List<DTP3FilterData> findAllDtp3FilterData(); 
	DTP3FilterData findById(Long id);
	DTP3FilterData findByphoneNumber1(String phoneNumber1);
	List<DTP3FilterData> findByDataType(DataType dataType);
	DTP3FilterData dataDTP3FilterCreaterAndUpdate(DTP3FilterData dtp3FilterDataEntity);
	void deleteDTP3FilterData(Long id);
	int deleteDTP3FilterDataByPhone1(String phoneNumber1);
	List<DTP3FilterData> findAllByPhoneDuplicate();
	List<DTP3FilterData> findAllByPhoneNumber1(String phoneNumber1);
	String dataDTP3FilterUpdateOldDataByPhone(DTP3FilterData dtp3FilterDataEntity);
	
	//Cụm chức năng lọc, hiển thị, phân trang
	public Page<DTP3FilterData> filterData(DTP3SearchRequest request, Pageable pageable);
	public List<DTP3FilterData> getFilterListOnly(DTP3SearchRequest request, Pageable pageable);
	public List<DTP3FilterData> getAllMatchesWithoutPagination(DTP3SearchRequest request);
	
}
