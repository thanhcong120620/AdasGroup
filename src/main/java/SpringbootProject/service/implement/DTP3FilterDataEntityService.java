package SpringbootProject.service.implement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import FileUtil.StringProcess;
import SpringbootProject.dto.dataProcess.DTP3SearchRequest;
import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.enums.DataType;
import SpringbootProject.entity.enums.Gender;
import SpringbootProject.entity.enums.Salutation;
import SpringbootProject.repository.DTP3FilterDataRepository;
import SpringbootProject.service.IDTP3FilterDataEntity;

@Service
@Transactional
public class DTP3FilterDataEntityService implements IDTP3FilterDataEntity {
	private static final Logger log = LoggerFactory.getLogger(DTP3FilterDataEntityService.class);
	
	@Autowired
	DTP3FilterDataRepository dPT3FilterDataRepository; 
	
	
//=========================================Filter and Show Function============================================
	
	//Trường hợp lấy tất cả danh sách sau khi lọc
	public List<DTP3FilterData> getAllMatchesWithoutPagination(DTP3SearchRequest request) {
	    log.info(">>> Bắt đầu lấy TOÀN BỘ dữ liệu khớp bộ lọc (Không phân trang)");

	    Specification<DTP3FilterData> spec = (root, query, cb) -> {
	        List<Predicate> predicates = new ArrayList<>();
	     // Lọc khớp chính xác cho zaloName
            if (request.getZaloName() != null && !request.getZaloName().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("zaloName"), request.getZaloName().trim()));
            }
            
            // Lọc khớp chính xác cho phoneNumber1
            if (request.getConsultDiary() != null && !request.getConsultDiary().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("consultDiary"), request.getConsultDiary().trim()));
            }

            // Lọc khớp chính xác cho phoneNumber1
            if (request.getPhoneNumber1() != null && !request.getPhoneNumber1().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("phoneNumber1"), request.getPhoneNumber1().trim()));
            }

            // Lọc khớp chính xác cho resultFollow
            if (request.getResultFollow() != null && !request.getResultFollow().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("resultFollow"), request.getResultFollow().trim()));
            }

            // Lọc khớp chính xác cho accountFollow
            if (request.getAccountFollow() != null && !request.getAccountFollow().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("accountFollow"), request.getAccountFollow().trim()));
            }

            // Lọc khớp chính xác cho DataType (Enum)
            if (request.getDataType() != null) {
                predicates.add(cb.equal(root.get("dataType"), request.getDataType()));
            }

            // Lọc khớp chính xác cho Gender (Enum)
            if (request.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), request.getGender()));
            }
            
         // LỌC KHOẢNG NGÀY (Date Range)
            // 1. Nếu có fromDate: dateOfLead >= fromDate
            if (request.getFromDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("nextFollowDate"), request.getFromDate()));
                log.debug("Filter: nextFollowDate >= {}", request.getFromDate());
            }

            // 2. Nếu có toDate: dateOfLead <= toDate
            if (request.getToDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("nextFollowDate"), request.getToDate()));
                log.debug("Filter: nextFollowDate <= {}", request.getToDate());
            }

	        return cb.and(predicates.toArray(new Predicate[0]));
	    };

	    // Gọi findAll(spec) - KHÔNG truyền pageable
	    List<DTP3FilterData> allResults = dPT3FilterDataRepository.findAll(spec);
	    
	    log.info("<<< Đã lấy toàn bộ List thành công. Số lượng: {}", allResults.size());
	    return allResults;
	}
	
	
	
	
	//Trường hợp chỉ lấy List trong 1 trang phân trang hiển thị
	public List<DTP3FilterData> getFilterListOnly(DTP3SearchRequest request, Pageable pageable) {
		log.info(">>> SERVICE: Bắt đầu lấy List dữ liệu cho Request: {}", request);
	    
	    // Gọi lại hàm filterData đã viết trước đó (trả về Page)
	    Page<DTP3FilterData> pageResult = this.filterData(request, pageable);
	    
	    if (pageResult != null && pageResult.hasContent()) {
	        List<DTP3FilterData> content = pageResult.getContent();
	        log.info("<<< SERVICE: Lấy List thành công. Số lượng bản ghi: {}", content.size());
	        return content;
	    }
	    
	    log.warn("<<< SERVICE: Không có dữ liệu, trả về List rỗng.");
	    return new java.util.ArrayList<>();
	}
	

	public Page<DTP3FilterData> filterData(DTP3SearchRequest request, Pageable pageable) {
        log.info("Thực hiện lọc khớp chính xác. Tiêu chí: {}", request);

        Specification<DTP3FilterData> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc khớp chính xác cho zaloName
            if (request.getZaloName() != null && !request.getZaloName().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("zaloName"), request.getZaloName().trim()));
            }
            
            // Lọc khớp chính xác cho phoneNumber1
            if (request.getConsultDiary() != null && !request.getConsultDiary().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("consultDiary"), request.getConsultDiary().trim()));
            }

            // Lọc khớp chính xác cho phoneNumber1
            if (request.getPhoneNumber1() != null && !request.getPhoneNumber1().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("phoneNumber1"), request.getPhoneNumber1().trim()));
            }
            
            // Lọc khớp chính xác cho resultFollow
            if (request.getResultFollow() != null && !request.getResultFollow().trim().isEmpty()) {
//            	System.out.println("Run: "+ request.getResultFollow());
                predicates.add(cb.equal(root.get("resultFollow"), request.getResultFollow().trim()));
            }

            // Lọc khớp chính xác cho accountFollow
            if (request.getAccountFollow() != null && !request.getAccountFollow().trim().isEmpty()) {
//            	System.out.println("Run: "+ request.getResultFollow());
                predicates.add(cb.equal(root.get("accountFollow"), request.getAccountFollow().trim()));
            }

            // Lọc khớp chính xác cho DataType (Enum)
            if (request.getDataType() != null) {
                predicates.add(cb.equal(root.get("dataType"), request.getDataType()));
            }

            // Lọc khớp chính xác cho Gender (Enum)
            if (request.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), request.getGender()));
            }
            
            // LỌC KHOẢNG NGÀY (Date Range)
            // 1. Nếu có fromDate: dateOfLead >= fromDate
            if (request.getFromDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("nextFollowDate"), request.getFromDate()));
                log.debug("Filter: nextFollowDate >= {}", request.getFromDate());
            }

            // 2. Nếu có toDate: dateOfLead <= toDate
            if (request.getToDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("nextFollowDate"), request.getToDate()));
                log.debug("Filter: nextFollowDate <= {}", request.getToDate());
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        try {
            Page<DTP3FilterData> pageResult = dPT3FilterDataRepository.findAll(spec, pageable);
            log.info("Lọc thành công. Tổng số bản ghi: {}, Trang hiện tại: {}", 
                     pageResult.getTotalElements(), pageResult.getNumber());
            return pageResult;
        } catch (Exception e) {
            log.error("Lỗi khi truy vấn dữ liệu: {}", e.getMessage());
            throw e;
        }
    }
	
	
	
//===================================================================================================================================
	
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
	
    //Hạn chế dùng, vì database có nhiều record trùng sđt
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
	public String dataDTP3FilterUpdateOldDataByPhone(DTP3FilterData dtp3FilterNewData) {        	
		
				
		String phone1Input = "Phone ko tồn tại / Trống";
		String phoneUpdate = "Chưa cập nhật";
		
		// Get Old data from database
//		DTP3FilterData dtp3FilterOldData = dPT3FilterDataRepository.findByphoneNumber1(dtp3FilterNewData.getPhoneNumber1());//phải trả về 1 list oldEntity vì có nhiều dữ liệu bị trùng Phone1
		if(dtp3FilterNewData.getPhoneNumber1()!=null) {
			List<DTP3FilterData> dtp3FilterDataList = dPT3FilterDataRepository.getListByOnePhone1(dtp3FilterNewData.getPhoneNumber1());
			for(DTP3FilterData dtp3FilterOldData : dtp3FilterDataList) {
				if (dtp3FilterOldData != null) {
//					System.out.println("run here !!!");
					//2 Check & Set Data type
					if( dtp3FilterNewData.getDataType() != null) {
						dtp3FilterOldData.setDataType(dtp3FilterNewData.getDataType());
					} 
					if(dtp3FilterOldData.getDataType() ==null) {
						 dtp3FilterOldData.setDataType(DataType.RAW_DATA);
					}
					
					//3 Check & Set dateOfLead
					if(dtp3FilterNewData.getDateOfLead() != null) {
						dtp3FilterOldData.setDateOfLead(dtp3FilterNewData.getDateOfLead());
					} 
					if(dtp3FilterOldData.getDateOfLead() ==null) {
						 dtp3FilterOldData.setDateOfLead(LocalDate.now());
					}
					
					//4 Check & Set DataSource
					if(!dtp3FilterNewData.getDataSource().isEmpty()) {
						if(!dtp3FilterOldData.getDataSource().isEmpty()) {
							dtp3FilterOldData.setDataSource(StringProcess.mergeUnique(dtp3FilterOldData.getDataSource(), dtp3FilterNewData.getDataSource()));
						} else {
							dtp3FilterOldData.setDataSource(dtp3FilterNewData.getDataSource());
						}			
					} 
					
					//5 Check & Set ConsultDiary
					if(!dtp3FilterNewData.getConsultDiary().isEmpty()) {
						if(!dtp3FilterOldData.getConsultDiary().isEmpty()) {
							dtp3FilterOldData.setConsultDiary(StringProcess.mergeUnique(dtp3FilterOldData.getConsultDiary(), dtp3FilterNewData.getConsultDiary()));
						} else {
							dtp3FilterOldData.setConsultDiary(dtp3FilterNewData.getConsultDiary());
						}			
					} 
					
					//6 Check & Set FullName1 xóa dữ liệu cũ, set dữ liệu mới từ new data.
					if(!dtp3FilterNewData.getFullName1().isEmpty()) {
						dtp3FilterOldData.setFullName1(dtp3FilterNewData.getFullName1());
					}
					
					//7 Check & Set FullName2
					if(!dtp3FilterNewData.getFullName2().isEmpty()) {
						dtp3FilterOldData.setFullName2(dtp3FilterNewData.getFullName2());
					}  
				
					//8 Check & Set LastName
					if(dtp3FilterNewData.getLastName()==null) {
						dtp3FilterOldData.setLastName(dtp3FilterNewData.getLastName());
					}  
					
					//9 Check & Set Salutation
					if( dtp3FilterNewData.getSalutation() != null) {
						dtp3FilterOldData.setSalutation(dtp3FilterNewData.getSalutation());
					} 
					if(dtp3FilterOldData.getSalutation() ==null) {
						 dtp3FilterOldData.setSalutation(Salutation.UNDEFINED);
					}  
					
					//10 Check & Set Gender
					if( dtp3FilterNewData.getGender() != null) {
						dtp3FilterOldData.setGender(dtp3FilterNewData.getGender());
					} 
					if(dtp3FilterOldData.getGender() ==null) {
						 dtp3FilterOldData.setGender(Gender.UNDEFINED);
					}
				
					//11 Check & Set ZaloName
//					System.out.println("!dtp3FilterNewData.getZaloName().isEmpty(): "+!dtp3FilterNewData.getZaloName().isEmpty());
//					System.out.println("dtp3FilterNewData.getZaloName(): "+dtp3FilterNewData.getZaloName());
					if(!dtp3FilterNewData.getZaloName().isEmpty()) {
						dtp3FilterOldData.setZaloName(dtp3FilterNewData.getZaloName());
					}  
				
					//12 Check & Set ZaloUid
					if(!dtp3FilterNewData.getZaloUid().isEmpty()) {
						dtp3FilterOldData.setZaloUid(dtp3FilterNewData.getZaloUid());
					}    
				
					//13 Check & Set FacebookLink
					if(!dtp3FilterNewData.getFacebookLink().isEmpty()) {
						dtp3FilterOldData.setFacebookLink(dtp3FilterNewData.getFacebookLink());
					}   
				
					//14 Check & Set PhoneNumber1
					if(dtp3FilterNewData.getPhoneNumber1() != null) {
						dtp3FilterOldData.setPhoneNumber1(dtp3FilterNewData.getPhoneNumber1());
					}   
				
					//15 Check & Set PhoneNumber2
					if(dtp3FilterNewData.getPhoneNumber2() != null) {
						dtp3FilterOldData.setPhoneNumber2(dtp3FilterNewData.getPhoneNumber2());
					}   
				
					//16 Check & Set Gmail
					if(!dtp3FilterNewData.getGmail().isEmpty()) {
						if(!dtp3FilterOldData.getGmail().isEmpty()) {
							dtp3FilterOldData.setGmail(StringProcess.mergeUnique(dtp3FilterOldData.getGmail(), dtp3FilterNewData.getGmail()));
						} else {
							dtp3FilterOldData.setGmail(dtp3FilterNewData.getGmail());
						}	
					}   
				
					//17 Check & Set DateOfBirth
					if(dtp3FilterNewData.getDateOfBirth() != null) {
						dtp3FilterOldData.setDateOfBirth(dtp3FilterNewData.getDateOfBirth());
					}   
				
					//18 Check & Set SavingsAmount
					if(dtp3FilterNewData.getSavingsAmount() != null) {
						dtp3FilterOldData.setSavingsAmount(dtp3FilterNewData.getSavingsAmount());
					}   
				
					//19 Check & Set setAddress
					if(!dtp3FilterNewData.getAddress().isEmpty()) {
						if(!dtp3FilterOldData.getAddress().isEmpty()) {
							dtp3FilterOldData.setAddress(StringProcess.mergeUnique(dtp3FilterOldData.getAddress(), dtp3FilterNewData.getAddress()));
						} else {
							dtp3FilterOldData.setAddress(dtp3FilterNewData.getAddress());
						}	
					}    
				
					//20 Check & Set setAddress
					if(!dtp3FilterNewData.getWorkingArea().isEmpty()) {
						if(!dtp3FilterOldData.getWorkingArea().isEmpty()) {
							dtp3FilterOldData.setWorkingArea(StringProcess.mergeUnique(dtp3FilterOldData.getWorkingArea(), dtp3FilterNewData.getWorkingArea()));
						} else {
							dtp3FilterOldData.setWorkingArea(dtp3FilterNewData.getWorkingArea());
						}	
					}    
				
					//21 Check & setProductBought
					if(!dtp3FilterNewData.getProductBought().isEmpty()) {
						if(!dtp3FilterOldData.getProductBought().isEmpty()) {
							dtp3FilterOldData.setProductBought(StringProcess.mergeUnique(dtp3FilterOldData.getProductBought(), dtp3FilterNewData.getProductBought()));
						} else {
							dtp3FilterOldData.setProductBought(dtp3FilterNewData.getProductBought());
						}	
					}     
				
					//22 Check & setMixContacts
					if(!dtp3FilterNewData.getMixContacts().isEmpty()) {
						if(!dtp3FilterOldData.getMixContacts().isEmpty()) {
							dtp3FilterOldData.setMixContacts(StringProcess.mergeUnique(dtp3FilterOldData.getMixContacts(), dtp3FilterNewData.getMixContacts()));
						} else {
							dtp3FilterOldData.setMixContacts(dtp3FilterNewData.getMixContacts());
						}	
					} 
					
					//23 Check & setNextAction
					if(dtp3FilterNewData.getNextAction() != null) {
						dtp3FilterOldData.setNextAction(dtp3FilterNewData.getNextAction());
					}  
					
					//24 Check & setNextFollowDate
					if(dtp3FilterNewData.getNextFollowDate() != null) {
						dtp3FilterOldData.setNextFollowDate(dtp3FilterNewData.getNextFollowDate());
					}       
					
					//25 Check & setResultFollow
					if(!dtp3FilterNewData.getResultFollow().isEmpty()) {
						dtp3FilterOldData.setResultFollow(dtp3FilterNewData.getResultFollow());
					}       
					
					//26 Check & setAccountFollow
					if(!dtp3FilterNewData.getAccountFollow().isEmpty()) {
						if(!dtp3FilterOldData.getAccountFollow().isEmpty()) {
							dtp3FilterOldData.setAccountFollow(dtp3FilterNewData.getAccountFollow());
						} else {
							dtp3FilterOldData.setAccountFollow(dtp3FilterNewData.getAccountFollow());
						}	
					}      
					dPT3FilterDataRepository.save(dtp3FilterOldData);
					phoneUpdate = "dtp3FilterNewData.getPhoneNumber1()";
				} else {
					
					return phone1Input;
				}
			}
		}
		
		return phoneUpdate;
		
	}
	
	
	@Override
	public DTP3FilterData dataDTP3FilterCreaterAndUpdate(DTP3FilterData dtp3FilterDataEntity) {
		if(dtp3FilterDataEntity.getId() == null) {
			if(dtp3FilterDataEntity.getPhoneNumber1()==null ||dtp3FilterDataEntity.getPhoneNumber1().isBlank() ||dtp3FilterDataEntity.getPhoneNumber1().isEmpty()) {
				dtp3FilterDataEntity.setPhoneNumber1("Chưa có sđt !");
			}
			//Create new user
			dPT3FilterDataRepository.save(dtp3FilterDataEntity);
			DTP3FilterData checkDTP3FilterData = dPT3FilterDataRepository.findById(dtp3FilterDataEntity.getId()).get();
//			System.out.println("Đã tạo mới DTP3FilterData (checkDTP3FilterData: " + checkDTP3FilterData.toString());
			return checkDTP3FilterData;
		} else {
			//Update user
			DTP3FilterData newEntity = dPT3FilterDataRepository.findById(dtp3FilterDataEntity.getId()).get();
			newEntity = dPT3FilterDataRepository.save(dtp3FilterDataEntity);
			DTP3FilterData checkDTP3FilterData = dPT3FilterDataRepository.findById(newEntity.getId()).get();
			return checkDTP3FilterData;
		}
	}

	@Override
	public void deleteDTP3FilterData(Long id) {
		dPT3FilterDataRepository.deleteById(id);
		
	}

	@Override
	public int deleteDTP3FilterDataByPhone1(String phoneNumber1) {
		
		 return dPT3FilterDataRepository.deleteByphoneNumber1(phoneNumber1);
	}

	@Override
	public List<DTP3FilterData> findAllByPhoneDuplicate() {
		System.out.println("Run service !!");
//		List<DTP3FilterData> list = dPT3FilterDataRepository.findAllByPhoneNumber1();
//		for(DTP3FilterData dtp3FilterData : list) {
//			System.out.println(dtp3FilterData.toString());
//		}
		
		return dPT3FilterDataRepository.findAllByPhoneNumber1();
	}

	@Override
	public List<DTP3FilterData> findAllByPhoneNumber1(String phoneNumber1) {
		List<DTP3FilterData> dtp3FilterDataList = dPT3FilterDataRepository.getListByOnePhone1(phoneNumber1);

		return dtp3FilterDataList;
	}

}
