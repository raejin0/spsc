package net.miraeit.scheduling.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import net.miraeit.scheduling.model.link.OrganizationToFulfill;
import net.miraeit.scheduling.model.link.Personnel;
import net.miraeit.scheduling.model.link.PersonnelToFulfill;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SchedulingDAO extends EgovAbstractDAO {
	/* -------- 인사 데이터 연계 -------- */
	public List<Personnel> findPersonnelInfo() {
		return (List<Personnel>) list("spsc.cmm.link.select.personnel");
	}

	public void mergePersonnel(Personnel personnel) {
		update("spsc.cmm.link.merge.personnel", personnel); // insert or update
	}
	/* //////// 인사 데이터 연계 //////// */

	/* -------- 조직 데이터 연계 -------- */
    public void patchLinkOrganizationUseN() {
        update("spsc.cmm.link.patch.organization.use.n");
    }

    public void mergeLinkOrganization() {
        update("spsc.cmm.link.merge.organization");
    }

    public void deleteLinkOrganizationCurrent() {
        delete("spsc.cmm.link.delete.organization.current");
    }

    public void postLinkOrganizationCurrent() {
        insert("spsc.cmm.link.post.organization.current");
    }
	/* //////// 조직 데이터 연계 //////// */

	/* -------- 미이행 메일 전송  -------- */
	public List<OrganizationToFulfill> findOrganizationToFulfill() {
		return (List<OrganizationToFulfill>) list("spsc.cmm.mail.select.org.to.fulfill");
	}

	public List<PersonnelToFulfill> findPersonnelToFulfill(List<OrganizationToFulfill> personnelList) {
		return (List<PersonnelToFulfill>) list("spsc.cmm.mail.select.personnel.to.fulfill", personnelList);
	}
	/* //////// 미이행 메일 전송 //////// */




}
