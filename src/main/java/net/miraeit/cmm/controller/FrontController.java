package net.miraeit.cmm.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = {"프론트 url을 관리하는 Controller"})
@Controller
public class FrontController {
	@RequestMapping(value="/design/{path:.*}")
	public String design(@PathVariable String path) {
		return "index";
	}

    @RequestMapping(value = "/")
    public String index() { return "index"; }

	@RequestMapping(value = "impl-management")
	public String implManagement() {
		return "index";
	}

	@RequestMapping(value = "impl-result-management")
	public String implResultManagement() {
		return "index";
	}

    /**
        로그인
     */
    @RequestMapping(value = "/login")
	public String login() { return "index"; }

    /**
        지적/조치사항관리
     */
    @RequestMapping(value = "/point-out")
	public String pointMeasure() { return "index"; }

    @RequestMapping(value = "/point-out/detail")
	public String pointMeasureDetail() { return "index"; }

	@RequestMapping(value = "/point-out/detail/form")
	public String pointOutDetailForm() { return "index"; }

    /**
        커뮤니티
     */
	@RequestMapping(value = "/bbs")
	public String bbs() {
		return "index";
	}

    @RequestMapping(value = "/bbs/{path:.*}")
	public String community(@PathVariable String path) { return "index"; }

//	@RequestMapping(value = "/bbs/{path:.*}/{path1:.*}")
//	public String communityDetail(@PathVariable String path, @PathVariable String path1) {
//        return "index";
//    }

	@RequestMapping("/bbs/detail/{bbsId:.*}/{postId:.*}")
	public String communityDetail(@PathVariable String bbsId, @PathVariable String postId) {
		return "index";
	}

	@RequestMapping("/bbs/modify/{bbsId:.*}/{postId:.*}")
	public String communityModify(@PathVariable String bbsId, @PathVariable String postId) {
		return "index";
	}
	@RequestMapping("/bbs/write/{bbsId:.*}")
	public String communityWrite(@PathVariable String bbsId) {
		return "index";
	}

    /**
        관련법령관리
     */
    @RequestMapping(value = "/relatedLaw")
	public String relatedLaw() { return "index"; }

    /**
        시스템관리
     */
	@RequestMapping(value = "/system/master")
	public String systemMaster() { return "index"; }

	@RequestMapping(value = "/system/master-management")
	public String systemMasterManagement() {
		return "index";
	}

	@RequestMapping(value = "/system/users")
	public String systemUsers() { return "index"; }

	@RequestMapping(value = "/system/law-master")
	public String lawMaster() {
		return "index";
	}

	@RequestMapping(value = "/system/equipments")
	public String systemEquipments() { return "index"; }

	@RequestMapping(value = "/system/organizations")
	public String systemOrganizations() { return "index"; }

	@RequestMapping(value="/implements-management")
	public String implementsManagement() {
		return "index";
	}

	@RequestMapping(value="/implements-checklist")
	public String implementsChecklist() {
		return "index";
	}

	@RequestMapping(value="/test")
	public String test() { return "index"; }


	@RequestMapping(value="/pdf-viewer/{attachmentId}/{fileSn}")
	public String pdfViewer(ModelAndView mav, @PathVariable int attachmentId, @PathVariable int fileSn) {
		mav.addObject("attachmentId", attachmentId);
		mav.addObject("fileSn", fileSn);
		return "pdf-viewer";
	}

}
