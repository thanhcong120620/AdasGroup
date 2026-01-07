package SpringbootProject.service;

import java.util.List;

import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.enums.DataType;

public interface IDTP3FilterDataEntity {
	List<DTP3FilterData> findAllDtp3FilterData(); 
	DTP3FilterData findById(Long id);
	DTP3FilterData findByphoneNumber1(String phoneNumber1);
	List<DTP3FilterData> findByDataType(DataType dataType);
	DTP3FilterData dataDTP3FilterCreaterAndUpdate(DTP3FilterData dtp3FilterDataEntity);
	void deleteDTP3FilterData(Long id);
}
