package net.miraeit.pointout.controller;

import egovframework.rte.fdl.property.EgovPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.controller.FileController;
import net.miraeit.cmm.dao.FileDAO;
import net.miraeit.cmm.model.OperationPeriod;
import net.miraeit.cmm.model.SuccessResponse;
import net.miraeit.cmm.model.file.FileDetailDTO;
import net.miraeit.cmm.model.file.FileDownloadRequest;
import net.miraeit.cmm.service.FileService;
import net.miraeit.cmm.util.ExcelUtil;
import net.miraeit.fx.listing.model.PagedList;
import net.miraeit.pointout.dao.PointOutDAO;
import net.miraeit.pointout.model.*;
import net.miraeit.pointout.model.excel.PointOutExcelDownloadRequest;
import net.miraeit.pointout.model.request.*;
import net.miraeit.pointout.service.PointOutService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;


@Api(tags = {"지적/조치사항 API Controller"})
@RequestMapping("/api/pointout")
@RestController
@RequiredArgsConstructor
public class PointOutController {
	private final PointOutService pointOutService;
	private final PointOutDAO pointOutDAO;
	private final FileController fileController;
	private final EgovPropertyService propertyService;
	private final FileService fileService;
	private final FileDAO fileDAO;
	private final ExcelUtil excelUtil;


	@GetMapping("pointout-summary-paged-list")
	@ApiOperation(value = "지적조치사항 총괄 목록 조회", notes = "지적조치사항 총괄목록의 페이지된 리스트를 조회한다.")
	@ApiImplicitParams({ // 아... 진짜로 하나씩 다 써줘야 한다...
		@ApiImplicitParam(name = "optTimeYear" , value = "연도"),
		@ApiImplicitParam(name = "optTimeHalf" , value = "반기"),
		@ApiImplicitParam(name = "chkDt", value = "점검일"),
		@ApiImplicitParam(name = "currentPageNo" , value = "페이지번호"),
		@ApiImplicitParam(name = "fetchCount" , value = "항목 수"),
		@ApiImplicitParam(name = "linksPerPage" , value = "링크 수"),
	})
	public SuccessResponse<PagedList<PointOutSummary>> getPointOutSummaryPagedList(@Valid @ModelAttribute PointOutSummaryRequest request) {
		return new SuccessResponse<>(pointOutService.getPointOutSummaryPagedList(request));
	}

	@GetMapping("pointout-summary-adv-no-check/{advNo}")
	@ApiOperation(value = "지적조치사항 총괄 관리번호 확인", notes = "관리번호 중복 여부 확인(관리번호를 사용하는 데이터 카운트)")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "advNo" , value = "관리번호"),
	})
	public SuccessResponse<Integer> getPointOutSummaryCountByAdvNo(@PathVariable final String advNo) {
		return new SuccessResponse<>(pointOutService.getPointOutSummaryCountByAdvNo(advNo));
	}

	@PostMapping("pointout-summary")
	@ApiOperation(value = "지적조치사항 총괄 항목 등록")
	public SuccessResponse<PointOutSummary> regPointOutSummary(@Valid @RequestBody PointOutSummaryRegRequest request) {
		return new SuccessResponse<>(pointOutService.regPointOutSummary(request));
	}

	@PostMapping("pointout-summary-reg")
	@ApiOperation(value = "지적조치사항 총괄 항목 등록")
	public SuccessResponse<PointOutSummary> regPointOutSummaryWithFile(
			@Valid PointOutSummaryRegRequest request,
			@RequestPart(required = false) MultipartFile[] evdFiles
	) throws IOException {
		return new SuccessResponse<>(pointOutService.regPointOutSummaryWithFiles(request, evdFiles));
	}

//	@PatchMapping("pointout-summary")
	@ApiOperation(value = "지적조치사항 총괄 항목 수정")
	public SuccessResponse<PointOutSummary> modPointOutSummary(@Valid @RequestBody PointOutSummaryModRequest request) {
		return new SuccessResponse<>(pointOutService.modPointOutSummary(request));
	}

	// todo patch
	@PostMapping("pointout-summary-mod")
	@ApiOperation("지적조치사항 총괄 항목 수정")
	public SuccessResponse<PointOutSummary> modPointOutSummary( @Valid PointOutSummaryModRequest request,
																@RequestPart(required = false) MultipartFile[] evdFiles) throws IOException {
		return new SuccessResponse<>(pointOutService.modPointOutSummary(request, evdFiles));
	}

//	@PatchMapping("pointout-summary")
//	@ApiOperation("지적조치사항 총괄 항목 수정")
//	public SuccessResponse<PointOutSummary> modPointOutSummary(
//			@Valid PointOutSummaryModRequest request,
//			@RequestPart(required = false) MultipartFile evdFile
//	) throws IOException {
//		request.setAdvNo(decodeString(request.getAdvNo(), encodingInput, encodingOutput));
//		request.setChkOrgNm(decodeString(request.getChkOrgNm(), encodingInput, encodingOutput));
//		request.setChkNm(decodeString(request.getChkNm(), encodingInput, encodingOutput));
//		return new SuccessResponse<>(service.modPointOutSummary(request, evdFile));
//	}

	@DeleteMapping("pointout-summary")
	@ApiOperation(value = "지적조치사항 총괄 항목 삭제")
	public SuccessResponse<Void> rmvPointOutSummary(@Valid @RequestBody PointOutSummaryModRequest request) {
		pointOutService.rmvPointOutSummary(request);
		return new SuccessResponse<>(null);
	}

	@GetMapping("pointout-detail-paged-list")
	@ApiOperation(value = "지적조치사항 상세 목록 조회", notes = "지적조치사항 상세 목록의 페이지된 리스트를 조회한다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "currentPageNo" , value = "페이지번호"),
		@ApiImplicitParam(name = "fetchCount" , value = "항목 수"),
		@ApiImplicitParam(name = "linksPerPage" , value = "링크 수"),
	})
	public SuccessResponse<PagedList<PointOutDetail>> getPointOutDetailPagedList(@Valid @ModelAttribute PointOutDetailRequest request) {
		return new SuccessResponse<>(pointOutService.getPointOutDetailPagedList(request));
	}

	@GetMapping("pointout-detail/{advDtlId}")
	@ApiOperation("지적조치사항 상세항목 조회")
	public SuccessResponse<PointOutDetail> getPointOutDetail(@PathVariable int advDtlId) {
		return new SuccessResponse<>(pointOutService.getPointOutDetail(advDtlId));
	}

	@PostMapping(value = "pointout-detail")
	@ApiOperation(value = "지적조치사항 상세 항목 등록")
	public SuccessResponse<PointOutDetail> regPointOutDetail(@Valid @RequestBody PointOutDetailRegRequest request) {
		return new SuccessResponse<>(pointOutService.regPointOutDetail(request));
	}

	@PostMapping("pointout-detail-reg")
	@ApiOperation("지적조치사항 상세 항목 등록")
	public SuccessResponse<PointOutDetail> regPointOutDetailWithFile(
		@Valid PointOutDetailRegRequest request,
		@RequestPart(required=false) MultipartFile befFile, @RequestPart(required=false) MultipartFile aftFile
	) throws IOException {
		return new SuccessResponse<>(pointOutService.regPointOutDetailWithFile(request, befFile, aftFile));
	}

	@PatchMapping(value = "pointout-detail")
	@ApiOperation(value = "지적조치사항 상세 항목 수정")
	public SuccessResponse<PointOutDetail> modPointOutDetail(@Valid @RequestBody PointOutDetailModRequest request) {
		return new SuccessResponse<>(pointOutService.modPointOutDetail(request));
	}

	// 수정인데 patch 로는 계속 bad request 떠서 임시로 post로 메소드 변경해둠
	@PostMapping(value = "pointout-detail-mod")
	@ApiOperation(value = "지적조치사항 상세 항목 수정")
	public SuccessResponse<PointOutDetail> modPointOutDetail(
		@Valid PointOutDetailModRequest request,
		@RequestPart(required=false) MultipartFile befFile, @RequestPart(required=false) MultipartFile aftFile
	) throws IOException {
//		String input = propertiesService.getString("multipart.encoding.post.input");
//		String output = propertiesService.getString("multipart.encoding.post.output");
		return new SuccessResponse<>(pointOutService.modPointOutDetailWithFile(request, befFile, aftFile));
	}

	@DeleteMapping(value = "pointout-detail")
	@ApiOperation(value = "지적조치사항 상세 항목 삭제")
	public SuccessResponse<Void> rmvPointOutDetail(@Valid @RequestBody PointOutDetailModRequest request) {
		pointOutService.rmvPointOutDetail(request);
		return new SuccessResponse<>(null);
	}

	@GetMapping("pointout-sts/{optTimeYear}")
	@ApiOperation("지적조치사항 집계 조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "optTimeYear", value = "연도"),
	})
	public SuccessResponse<List<PointOutSTS>> getPointOutSTS(@PathVariable String optTimeYear) {
		OperationPeriod period = new OperationPeriod();
		period.setOptTimeYear(optTimeYear);
		List<PointOutSTS> sts = pointOutService.getPointOutSTS(period);
		return new SuccessResponse<>(sts);
	}


	@GetMapping("pointout-sts/{optTimeYear}/{optTimeHalf}")
	@ApiOperation("지적조치사항 집계 조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "optTimeYear", value = "연도"),
			@ApiImplicitParam(name = "optTimeHalf", value = "반기"),
	})
	public SuccessResponse<List<PointOutSTS>> getPointOutSTS(@PathVariable String optTimeYear, @PathVariable String optTimeHalf) {
		OperationPeriod period = new OperationPeriod();
		period.setOptTimeYear(optTimeYear);
		period.setOptTimeHalf(optTimeHalf);
		return new SuccessResponse<>(pointOutService.getPointOutSTS(period));
	}

	@PostMapping("/excel/upload")
    @ApiOperation(value = "지적/조치사항 엑셀 업로드")
	public SuccessResponse uploadExcel(@RequestPart MultipartFile excel
		                             , @Valid PointerOutExcelUploadRequest request) throws IOException {
	    File xlsFile = Files.createTempFile(UUID.randomUUID().toString(), ".xlsx").toFile(); // Construct empty File object
		excel.transferTo(xlsFile);      // insert the content of MultipartFile into File object

		Workbook wb = new XSSFWorkbook(new FileInputStream(xlsFile));

		pointOutService.upload(wb, request);

		return new SuccessResponse(null); // response 객체 생성 및 반환
    }

    @GetMapping("/excel/format/download")
	@ApiOperation(value = "지적/조치사항 엑셀 양식 다운로드")
	public ResponseEntity<Resource> downloadExcelFormat() throws MalformedURLException, UnsupportedEncodingException {
	    FileDownloadRequest request = FileDownloadRequest.builder()
				.atchFileId(propertyService.getInt("point-out.excel.format.download.atchFileId"))
				.fileSn(propertyService.getInt("point-out.excel.format.download.fileSn"))
			    .build();
	    return fileController.fileDownload(request);
    }

    @GetMapping("/excel/download")
	@ApiOperation(value = "지적/조치사항 엑셀 다운로드")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "optTimeYear" , value = "연도"),
		@ApiImplicitParam(name = "optTimeHalf" , value = "반기")
	})
	public SuccessResponse downloadExcel(@Valid @ModelAttribute PointOutExcelDownloadRequest pointOutExcelDownloadRequest, HttpServletResponse response) throws IOException {
		// DB 정보 조회
		FileDetailDTO emptyFileDetail = fileService.findEmptyExcelFormat(); // 파일
	    List<PointOutSummary>summaryList = pointOutDAO.findPointOutSummaryList(pointOutExcelDownloadRequest); // summary
	    for (PointOutSummary summary : summaryList) {
	    	// summary files
		    for (FileDetailDTO fileDetail : fileDAO.findFileDetailListById(summary.getEvdFileId()))
		    	summary.getEvdFiles().add(fileDetail);

	    	// detail
		    for (PointOutDetail detail : pointOutDAO.findPointOutDetailListByAdvId(summary.getAdvId())) {
			    summary.getPointOutDetailList().add(detail);

			    // detail files
			    for (FileDetailDTO fileDetail : fileDAO.findFileDetailListById(detail.getBefFileId())) detail.addBefFile(fileDetail);
			    for (FileDetailDTO fileDetail : fileDAO.findFileDetailListById(detail.getAftFileId())) detail.addAftFile(fileDetail);
		    }
	    }

	    XSSFWorkbook wb = fileService.readXlsFile(emptyFileDetail);
	    wb = excelUtil.writeSummaries(summaryList, wb);         // summary 작성
	    wb = excelUtil.writeDetails(summaryList, wb);           // detail 작성

		String filename = emptyFileDetail.getOrignlFileNm();


		// encoding: local 개발환경에만 해당함
		if (propertyService.getString("point-out.excel.download.sort").equals("tomcat")) {
			String encodingInput = propertyService.getString("excel.filename.encoding.input");    // "KSC5601"
			String encodingOutput = propertyService.getString("excel.filename.encoding.output");  // "8859_1"
			filename = new String(filename.getBytes(encodingInput), encodingOutput);
		}

		response.setHeader("Content-Disposition", "attachment; fileName=\"" + filename + "\"");

        // 다운로드
		ServletOutputStream outputStream = response.getOutputStream();
		wb.write(outputStream);
		outputStream.close();
		wb.close();

		return new SuccessResponse<>(null);
    }
}
