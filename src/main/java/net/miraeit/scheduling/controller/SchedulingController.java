package net.miraeit.scheduling.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.model.SuccessResponse;
import net.miraeit.scheduling.service.LinkService;
import net.miraeit.scheduling.service.MailService;
import net.miraeit.scheduling.service.SemiannualDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"scheduling service 호출을 위한 Controller"})
@RequestMapping("/api/scheduling")
@RestController
@RequiredArgsConstructor
public class SchedulingController {
    private final MailService mailService;
    private final SemiannualDataService semiannualDataService;
    private final LinkService linkService;

	@GetMapping("/mail/send")
	@ApiOperation(value="미이행 사업소 관리자에게 안내 메일 전송")
	public SuccessResponse sendMail() {
		mailService.sendMail();
		return new SuccessResponse(null);
	}

	@GetMapping("/link/personnel")
	@ApiOperation(value="인사 연계")
	public SuccessResponse linkPersonnel() throws Exception {
		linkService.linkPersonnel();
		return new SuccessResponse(null);
	}

	@GetMapping("/link/organization")
	@ApiOperation(value="조직 연계")
	public SuccessResponse linkOrganization(){
		linkService.linkOrganization();
		return new SuccessResponse(null);
	}

	@GetMapping("/semiannual/update")
	@ApiOperation(value="반기 데이터 생성")
	public SuccessResponse createSemiannualInfo() {
		semiannualDataService.update();
    	return new SuccessResponse(null);
	}
}
