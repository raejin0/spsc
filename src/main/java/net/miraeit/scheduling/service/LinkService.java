package net.miraeit.scheduling.service;

import egovframework.let.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.property.EgovPropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.miraeit.scheduling.dao.SchedulingDAO;
import net.miraeit.scheduling.model.link.Personnel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("linkService")
@RequiredArgsConstructor
@Slf4j
public class LinkService {
	private final SchedulingDAO schedulingDAO;
	public final EgovPropertyService propertiesService;

	public void linkPersonnel() throws Exception {
		log.info("[START] linkPersonnel: 인사 데이터 연계");
		List<Personnel> personnelList = schedulingDAO.findPersonnelInfo();      // 조회

		// 비밀번호를 암호화 후 DB를 갱신한다.
		for (Personnel personnel: personnelList) {
			// 암호화
			String password = propertiesService.getString("spsc.link.personnel.password");          //context-properties 에 정의한 비밀번호
			String encodedPassword = EgovFileScrty.encryptPassword(password, personnel.getSabun());   // 암호화
			personnel.setPassword(encodedPassword);

			// insert or update
			schedulingDAO.mergePersonnel(personnel); // ** 제약조건이 위배될 경우 rollback 되기만 할 뿐 에러메시지가 뜨지 않는다. **
		}
		log.info("[END] linkPersonnel: 인사 데이터 연계");
	}

    public void linkOrganization() {
    	log.info("[START] 조직 데이터 연계");
        schedulingDAO.patchLinkOrganizationUseN();
        schedulingDAO.mergeLinkOrganization();
        schedulingDAO.deleteLinkOrganizationCurrent();
        schedulingDAO.postLinkOrganizationCurrent();
        log.info("[END] 조직 데이터 연계");
    }

}
