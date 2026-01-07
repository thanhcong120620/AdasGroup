package SpringbootProject.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SpringbootProject.entity.CRMEntity.DTP1CRMEntity;
import SpringbootProject.repository.DTP1CrmDataRepository;
import SpringbootProject.service.IDTP1CRMEntity;

@Service
@Transactional
public class DPT1CrmEntityService implements IDTP1CRMEntity {
	
	@Autowired
	DTP1CrmDataRepository dTP1CrmDataRepository; 

	@Override
	public List<DTP1CRMEntity> findAllDtp1CRMEntity() {
		List<DTP1CRMEntity> DTP1CRMList = dTP1CrmDataRepository.findAll();
		if(DTP1CRMList.size() == 0) {
			System.out.println("User data is null !");
			DTP1CRMEntity demoDTP1CRM = new DTP1CRMEntity();
			demoDTP1CRM.setCodeCrmDTP1(null);
			demoDTP1CRM.setFullName(null);
			demoDTP1CRM.setGmail("-");
			demoDTP1CRM.setPhoneNumber1(null);
			DTP1CRMList.add(demoDTP1CRM);
			return DTP1CRMList;
		} else {
			return DTP1CRMList;
		}
		
	}
	
}
