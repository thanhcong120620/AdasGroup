package SpringbootProject.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SpringbootProject.entity.UserEntity;
import SpringbootProject.entity.CRMEntity.DTP1CRMEntity;
import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.enums.DataType;
import SpringbootProject.repository.DTP3FilterDataRepository;
import SpringbootProject.service.IDTP3FilterDataEntity;

@Service
@Transactional
public class DTP3FilterDataEntityService implements IDTP3FilterDataEntity {
	
	@Autowired
	DTP3FilterDataRepository dPT3FilterDataRepository; 
	
	@Override
	public List<DTP3FilterData> findAllDtp3FilterData() {
		List<DTP3FilterData> DTP3FilterDataList = dPT3FilterDataRepository.findAll();
		if(DTP3FilterDataList.size() == 0) {
			System.out.println("User data is null !");
			DTP3FilterData demoDTP3FilterData = new DTP3FilterData();
			demoDTP3FilterData.setAccountFollow(null);
			demoDTP3FilterData.setConsultDiary("-");
			demoDTP3FilterData.setFullName1("-");
			demoDTP3FilterData.setPhoneNumber1(null);
			DTP3FilterDataList.add(demoDTP3FilterData);
			return DTP3FilterDataList;
		} else {
			return DTP3FilterDataList;
		}
		
	}

	@Override
	public DTP3FilterData findById(Long id) {
		DTP3FilterData dtp3FilterData = dPT3FilterDataRepository.findById(id).get();
		return dtp3FilterData;
	}

	@Override
	public DTP3FilterData findByphoneNumber1(String phoneNumber1) {
		DTP3FilterData dtp3FilterData = dPT3FilterDataRepository.findByphoneNumber1(phoneNumber1);
		return dtp3FilterData;
	}
	

	@Override
	public List<DTP3FilterData> findByDataType(DataType dataType) {
		List<DTP3FilterData> dtp3FilterData = dPT3FilterDataRepository.findByDataType(dataType);
		return dtp3FilterData;
	}

	@Override
	public DTP3FilterData dataDTP3FilterCreaterAndUpdate(DTP3FilterData dtp3FilterDataEntity) {
		if(dtp3FilterDataEntity.getId() == null) {
			//Create new user
			dPT3FilterDataRepository.save(dtp3FilterDataEntity);
			DTP3FilterData checkDTP3FilterData = dPT3FilterDataRepository.findById(dtp3FilterDataEntity.getId()).get();
			System.out.println("Đã tạo mới DTP3FilterData (checkDTP3FilterData: " + checkDTP3FilterData.toString());
			return checkDTP3FilterData;
		} else {
			//Update user
			DTP3FilterData newEntity = dPT3FilterDataRepository.findById(dtp3FilterDataEntity.getId()).get();
			newEntity = dPT3FilterDataRepository.save(dtp3FilterDataEntity);
			DTP3FilterData checkDTP3FilterData = dPT3FilterDataRepository.findById(newEntity.getId()).get();
			System.out.println("Đã cập nhật DTP3FilterData (DTP3FilterData: " + checkDTP3FilterData.toString());
			return checkDTP3FilterData;
		}
	}

	@Override
	public void deleteDTP3FilterData(Long id) {
		dPT3FilterDataRepository.deleteById(id);
		
	}

}
