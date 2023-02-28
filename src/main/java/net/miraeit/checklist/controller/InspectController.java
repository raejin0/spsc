package net.miraeit.checklist.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.miraeit.checklist.dao.InspectDAO;
import net.miraeit.checklist.model.inspect.DeleteInspectContentRequest;
import net.miraeit.checklist.model.inspect.PostInspectContentRequest;
import net.miraeit.checklist.model.inspect.PostInspectContentResponse;
import net.miraeit.checklist.service.InspectService;
import net.miraeit.cmm.model.SuccessResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"체크리스트 점검사항 API 를 제공하는 Controller"})
@RequestMapping("/api/checklist/inspect")
@RestController
@RequiredArgsConstructor
public class InspectController {

	private final InspectService inspectService;
	private final InspectDAO inspectDAO;

    @PostMapping("/content")
    @ApiOperation(value = "점검내용", notes = "점검내용의 추가")
    public SuccessResponse<PostInspectContentResponse> postInspectContent(@Valid @RequestBody PostInspectContentRequest request){
        return new SuccessResponse(inspectService.postInspectContent(request));
    }

    @DeleteMapping("/content")
	@ApiOperation(value = "점검내용", notes = "점검내용의 삭제")
    public SuccessResponse deleteInspectContent(@Valid @RequestBody DeleteInspectContentRequest request){
    	inspectDAO.deleteInspectContent(request);
        return new SuccessResponse(null);
    }

}
