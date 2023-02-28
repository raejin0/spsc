package net.miraeit.cmm.controller;

import egovframework.rte.fdl.property.EgovPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.dao.CMMDAO;
import net.miraeit.cmm.dao.FileDAO;
import net.miraeit.cmm.exception.file.FileDetailNotFoundException;
import net.miraeit.cmm.exception.file.NotImageException;
import net.miraeit.cmm.exception.file.NullFileException;
import net.miraeit.cmm.model.SuccessResponse;
import net.miraeit.cmm.model.file.*;
import net.miraeit.cmm.model.law.LawDetail;
import net.miraeit.cmm.service.FileService;
import net.miraeit.cmm.util.FileStore;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.*;

@Api(tags = {"파일 업로드/다운로드 관련 API 를 제공하는 Controller"})
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class FileController {

	private final FileDAO fileDAO;
	private final CMMDAO cmmdao;
	private final FileService fileService;
    private final FileStore fileStore;

    @javax.annotation.Resource(name = "propertiesService")
    public EgovPropertyService propertiesService;

    @PostMapping("/file/upload")
    @ApiOperation(value = "파일 업로드")
    public SuccessResponse<FileUploadResponse> fileUpload(@Valid FileUploadRequest request
		                                                , @RequestPart(required = false) MultipartFile file
		                                                , HttpServletRequest httpServletRequest) throws IOException {

	    if (request.getAtchFileId() == null) request.setAtchFileId(fileDAO.createFileId()); // ATCH_FILE_ID 없을 경우 생성 및 값설정
    	request.setFileSn(fileDAO.spscCmmGetMaxFileSn(request.getAtchFileId()));        // fileSn 설정

		saveOrUpdateFileDetail(request, file, httpServletRequest.getMethod());

		FileDetailDTO fileDetail = fileService.findFileDetail(request.getAtchFileId(), request.getFileSn()); // 조회
	    return new SuccessResponse(new FileUploadResponse(fileDetail)); // response 객체 생성 및 반환
    }

	@PatchMapping("/file/upload")
	@ApiOperation(value = "업로드한 파일 수정")
	public SuccessResponse patchUploadedFile( @Valid FileUploadRequest request
											, @RequestPart(required = false) MultipartFile file
											, HttpServletRequest httpServletRequest) throws IOException {

		saveOrUpdateFileDetail(request, file, httpServletRequest.getMethod());

		FileDetailDTO fileDetail = fileService.findFileDetail(request.getAtchFileId(), request.getFileSn()); // 조회
	    return new SuccessResponse(new FileUploadResponse(fileDetail)); // response 객체 생성 및 반환
    }

	/**
	 * 파일 위치 정보를 입력, 인코딩, 다운로드를 진행한다.
	 *
	 * @param fileDetail
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	private ResponseEntity<Resource> download(FileDetailDTO fileDetail) throws MalformedURLException, UnsupportedEncodingException {
	    // resource(파일) 위치 설정
    	UrlResource resource = new UrlResource("file:" + fileDetail.getFileStreCours());

		// 인코딩
		String encodedOriginlFileNm = URLEncoder.encode(fileDetail.getOrignlFileNm(), "UTF-8").replaceAll("\\+", "%20");
		String contentDisposition = "attachment; filename=\"" + encodedOriginlFileNm + "\"";    // 다운로드 규악

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(resource);
    }

    @GetMapping("/image/download")
    @ApiOperation(value = "이미지 다운로드")
    public ResponseEntity<Resource> imageDownload(@Valid @ModelAttribute FileDownloadRequest request) throws MalformedURLException, UnsupportedEncodingException {
    	FileDetailDTO fileDetail = fileService.findFileDetail(request.getAtchFileId(), request.getFileSn());    // db 조회
        if( fileDetail == null) throw new FileDetailNotFoundException(); // 예외처리

	    // 이미지 파일인지 확인
	    String[] imgExtensions = {"jpg", "jpeg", "png","gif", "bmp", "rle", "dib", "exif", "tif","tiff", "raw"};
	    if (!Arrays.asList(imgExtensions).contains(fileDetail.getFileExtsn())) // img 파일이 아니면
	    	throw new NotImageException();

	    return download(fileDetail);
    }

	@PostMapping("/file/download")
	@ApiOperation(value = "파일 다운로드")
	public ResponseEntity<Resource> fileDownload(@Valid @RequestBody FileDownloadRequest request) throws MalformedURLException, UnsupportedEncodingException {
    	FileDetailDTO fileDetail = fileService.findFileDetail(request.getAtchFileId(), request.getFileSn());     // db 조회
		if( fileDetail == null) throw new FileDetailNotFoundException(); // 예외처리
		return download(fileDetail);
	}

	@GetMapping("/file/download/{atchFileId}/{fileSn}")
	@ApiOperation("파일다운로드")
	@ApiImplicitParams({@ApiImplicitParam(name = "atchFileId" , value = "첨부파일 아이디"),
                        @ApiImplicitParam(name = "fileSn" , value = "파일 순번")})
	public void download(HttpServletResponse response, @PathVariable final int atchFileId, @PathVariable final int fileSn) throws Exception {
        try {
        	FileDetailDTO fileDetail = fileService.findFileDetail(atchFileId, fileSn);     // db 조회
        	String path = fileDetail.getFileStreCours();
        	String encoding = propertiesService.getString("multipart.encoding.post.output");
        	response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileDetail.getOrignlFileNm(), encoding));

        	FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
        	OutputStream out = response.getOutputStream();

        	int read = 0;
                byte[] buffer = new byte[1024];
                while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을 파일이 없음
                    out.write(buffer, 0, read);
                }

        } catch (Exception e) {
            throw new Exception("download error", e);
        }
    }

	@DeleteMapping("/file/upload")
	@ApiOperation(value = "업로드한 파일 삭제")
	public SuccessResponse deleteUploadedFile(@RequestBody DeleteUploadedFileRequest request) {

    	// 파일일 경우 경로에서 파일 삭제
		if(request.getAtchTy().equals("F")) {
			String filePathList = fileDAO.findUploadFilePath(request);
			fileStore.removeFile(filePathList);
		}

		// DB 삭제
		fileDAO.deleteFileDetail(request);

	    return new SuccessResponse(null);
	}

	/**
	 * 1. url, file을 구분하여 상응하는 dao 호출
	 * 2. http method에 따라 저장 또는 갱신
	 *
	 * @param request
	 * @param file
	 * @param httpMethod
	 * @throws IOException
	 */
	private void saveOrUpdateFileDetail(  @Valid FileUploadRequest request
							            , @RequestPart(required = false) MultipartFile file
							            , String httpMethod) throws IOException {
		// url 일 경우
		if (request.getAtchTy().equals("L")) {
			if (httpMethod.equals("POST")) fileDAO.saveURLDetail(request); // save
			else fileDAO.updateURLDetail(request); // update

		// 파일일 경우
		} else {
			if (file == null) throw new NullFileException();

			UploadFile uploadFile = fileStore.storeFile(file, request.getAtchFileId(), request.getFileSn(), request.getAtchTy()); // 파일 저장

			if (httpMethod.equals("POST")) fileDAO.saveFileDetail(uploadFile); // save
			else fileDAO.updateFileDetail(uploadFile); // update
		}
	}

	@GetMapping("/law/download")
	@ApiOperation(value = "관련법령 엑셀 다운로드")
	private SuccessResponse ExcelDownload(HttpServletResponse response) throws IOException {

		// 관련법령 전체 조회
		List<LawDetail> lawList = cmmdao.findLawList();

		// 관련법령 별로 구분
		Map<String, List> lawMap = new HashMap<>();
		for (LawDetail lawDetail: lawList) {
			String key = lawDetail.getLawRelNm() + " [lawRelId: " + lawDetail.getLawRelId() + "]";

			List<LawDetail> list;
			if( lawMap.get(key) == null) list = new ArrayList();   // lawRelId key 없으면 리스트 생성
			else list = lawMap.get(key);                           // lawRelId key 있으면 조회

			list.add(lawDetail);
			lawMap.put(key, list);
		}

		// sorting
		List<String> keyList = new ArrayList<>(lawMap.keySet());
		keyList.sort((s1, s2) -> s1.compareTo(s2));

		// xlsx
		Workbook wb = new XSSFWorkbook();                       // xlsx 파일 객체 생성
		for (String key: keyList ) {
			List<LawDetail> lawDetails = lawMap.get(key);       // 편의 변수

			Sheet sheet = wb.createSheet(lawDetails.get(0).getLawRelNm());    // 관련법령명으로 sheet 생성

			// ---- 셀의 크기, 스타일, 폰트 등 정의 -----
			// 사용 변수 선언
			int rowCnt = 0;
			Row row;
			Cell cell;

			// 헤더 설정
			String[] headers = {"조항", "내용"};
			row = sheet.createRow(rowCnt++);            // 행 생성

			// column 크기
			sheet.setColumnWidth(0, 20 * 256);
			sheet.setColumnWidth(1, 130 * 256);

			// 헤더 셀 스타일
			CellStyle headerStyle = wb.createCellStyle();
			headerStyle.setAlignment(HorizontalAlignment.CENTER); // 가운데 정렬

			headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerStyle.setBorderBottom(BorderStyle.THICK);

			// 헤더 폰트
			Font headerFont = wb.createFont();
			headerFont.setBold(true);
			headerStyle.setFont(headerFont);

			for (int i = 0; i < headers.length; i++) {  // 셀 생성 및 헤더 입력
				cell = row.createCell(i);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(headers[i]);
			}

			// -------- 데이터 입력
			// 조항 셀 스타일(가운데 정렬)
			CellStyle articleStyle = wb.createCellStyle();
			articleStyle.setAlignment(HorizontalAlignment.CENTER); // 가운데 정렬
			articleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 가운데 정렬

			// 내용 셀 스타일(오른쪽 bold)
			CellStyle contentStyle = wb.createCellStyle();
			contentStyle.setBorderRight(BorderStyle.THICK); // 오른쪽 선 두껍게
			contentStyle.setWrapText(true);                 // 줄바꿈


			// 데이터
//			List<LawDetail> lawDetails = request.getLawDetails();
			for (int i = 0; i < lawDetails.size(); i++) {
				row = sheet.createRow(rowCnt++);

				// 조항
				cell = row.createCell(0);
				cell.setCellStyle(articleStyle);
				cell.setCellValue(lawDetails.get(i).getLawRelAtc());

				// 내용
				cell = row.createCell(1);
				cell.setCellStyle(contentStyle);
				cell.setCellValue(lawDetails.get(i).getLawRelText());
			}

			// 마지막줄
			CellStyle lastRowStyle1 = wb.createCellStyle();
			lastRowStyle1.setAlignment(HorizontalAlignment.CENTER);         // 가운데 정렬
			lastRowStyle1.setVerticalAlignment(VerticalAlignment.CENTER);   // 세로 가운데 정렬
			lastRowStyle1.setBorderBottom(BorderStyle.THICK);               // 하단 선 두껍게

			CellStyle lastRowStyle2 = wb.createCellStyle();
			lastRowStyle2.setBorderRight(BorderStyle.THICK);                // 오른쪽 선 두껍게
			lastRowStyle2.setWrapText(true);                                // 줄바꿈
			lastRowStyle2.setBorderBottom(BorderStyle.THICK);               // 하단 선 두껍게

			row.getCell(0).setCellStyle(lastRowStyle1);
			row.getCell(1).setCellStyle(lastRowStyle2);
		}

		// 파일명 설정
		String filename = propertiesService.getString("law.download.excel.filename");           // "관련법령.xlsx"

		// encoding: local 개발환경에만 해당함
//		String encodingInput = propertiesService.getString("excel.filename.encoding.input");    // "KSC5601"
//		String encodingOutput = propertiesService.getString("excel.filename.encoding.output");  // "8859_1"
//		filename = new String(filename.getBytes(encodingInput), encodingOutput);

		response.setHeader("Content-Disposition", "attachment; fileName=\"" + filename + "\"");

		// 다운로드
		ServletOutputStream outputStream = response.getOutputStream();
		wb.write(outputStream);
		outputStream.close();
		wb.close();

		return new SuccessResponse<>(null);
	}


}
