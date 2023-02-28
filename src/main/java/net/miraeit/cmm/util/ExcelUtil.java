package net.miraeit.cmm.util;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.property.EgovPropertyService;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.model.file.FileDetailDTO;
import net.miraeit.pointout.exception.SheetNameNotMatchedException;
import net.miraeit.pointout.model.PointOutDetail;
import net.miraeit.pointout.model.PointOutExcelDetail;
import net.miraeit.pointout.model.PointOutExcelSummary;
import net.miraeit.pointout.model.PointOutSummary;
import net.miraeit.pointout.model.excel.Attachment;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ExcelUtil {
	private final EgovPropertyService propertiesService;
	private final FileStore fileStore;
	private final EgovMessageSource msgSrc;

	/**
	 * 총괄표탭에서 데이터 및 첨부파일 정보를 추출하여 반환한다.
	 *
	 * @param wb
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public List<PointOutExcelSummary> getSummary(Workbook wb) throws IOException {
		XSSFSheet summarySheet = (XSSFSheet) wb.getSheetAt(0);                      // 총괄표탭
		List<PointOutExcelSummary> summary = getSummaryData(summarySheet);          // 총괄표탭 데이터 가져오기
		List<Attachment> summaryAttachments = getAttachments(summarySheet);  // 총괄표탭 첨부(이미)파일 정보 가져오기
		addAttachmentsToSummary(summary, summaryAttachments);                       // 원본파일명 설정, 첨부파일 리스트 추가

		return summary;
	}


	/**
	 * 지적/조치사항 excel 총괄표에서 값 추출
	 * 총괄 정보를 담은 객체를 리스트 형태로 생성하여 반환한다.
	 *
	 * @param summarySheet
	 * @return
	 */
	public List<PointOutExcelSummary> getSummaryData(XSSFSheet summarySheet) {
		int firstRowNum = propertiesService.getInt("point-out.excel.summary.first.row.number"); // 추출할 데이터가 존재하는 첫번째 행: row contains the value to extract :
		int lastRowNum = summarySheet.getLastRowNum(); // sheet에서 확인되는 마지막 행: detected row by excel

		List<PointOutExcelSummary> summaryList = new ArrayList<>(); // return object
		for (int i = firstRowNum; i < lastRowNum; i++) {
			XSSFRow row = summarySheet.getRow(i);
			if (row == null) continue;         // handle null row exception: 데이터가 없는 행 건너뛰기

			int cellNum = propertiesService.getInt("point-out.excel.summary.first.cell.number");
			PointOutExcelSummary summary = PointOutExcelSummary.builder()
					.attachments(new ArrayList<>())                                            // 첨부파일 리스트
					.rowNumber(i)                                                              // 행번호 for image

					.advNo(row.getCell(cellNum++).getStringCellValue())                        // 관리번호
					.mngOrgNm(row.getCell(cellNum++).getStringCellValue())                     // 총괄관리부서
					.chkOrgNm(row.getCell(cellNum++).getStringCellValue())                     // 기관명
					.chkNm(row.getCell(cellNum++).getStringCellValue())                        // 점검명
					.chkDt(row.getCell(cellNum++).getStringCellValue())                        // 점검일
					.cntTotalViolation((int) row.getCell(cellNum++).getNumericCellValue())     // 총위반건수 //
					.cntJudicialAct((int) row.getCell(cellNum++).getNumericCellValue())        // 사법조치(건수)
					.cntStopUse((int) row.getCell(cellNum++).getNumericCellValue())            // 사용중지(건수)
					.cntStopFacilityTotal((int) row.getCell(cellNum++).getNumericCellValue())  // 사용중지(대수) : 장비 및 시설
					.cntCorrectOrder((int) row.getCell(cellNum++).getNumericCellValue())       // 시정명령(건수)
					.cntCorrectInstruct((int) row.getCell(cellNum++).getNumericCellValue())    // 시정지시(건수)
					.cntFine((int) row.getCell(cellNum++).getNumericCellValue())               // 과태료 위반조항수
					.amtFineTotal((int) row.getCell(cellNum++).getNumericCellValue())          // 과태료 금액
					.cntRecmd((int) row.getCell(cellNum++).getNumericCellValue())              // 권고사항(건수)
					.cntEtc((int) row.getCell(cellNum++).getNumericCellValue())                // 기타사항(건수)
					.build();

			summaryList.add(summary);
		}

		return summaryList;
	}

	/**
	 * 시트(탭) 내에 존재하는 첨부(이미지) 파일 찾는다.
	 * 파일 및 위치 정보를 담은 객체를 리스트 형태로 생성하여 반환한다.
	 *
	 * @param summarySheet
	 * @return
	 */
	public List<Attachment> getAttachments(XSSFSheet summarySheet) throws IOException {
		List<Attachment> attachments = new ArrayList<>(); // 반환 리스트 객체 생성: construct return list object

		// 이미지 파일 위치 확인
		List<XSSFShape> shapes = summarySheet.createDrawingPatriarch().getShapes();
		for (XSSFShape shape : shapes) {
			if (!(shape instanceof XSSFPicture)) continue;

			// 활용 변수 선언
			XSSFPicture picture = (XSSFPicture) shape;
			XSSFPictureData pictureData = picture.getPictureData();
			byte[] pictureBytes = pictureData.getData();
			String storeFilename = fileStore.createStoreFilename();

			// 객체 빌드
			XSSFClientAnchor preferredSize = picture.getPreferredSize();
			Attachment attachment = Attachment.builder()
					// AttachmentInExcel
					.row(preferredSize.getRow1())       // 행번호
					.col((int) preferredSize.getCol1()) // 열번호
					.bytes(pictureBytes)
					.file(bytesToFile(pictureBytes, pictureData.suggestFileExtension()))    // file

					// FileDetailDTO
					.fileStreCours(fileStore.getFullPath(storeFilename))
					.streFileNm(storeFilename)
					.fileExtsn(pictureData.suggestFileExtension())  // 확장자: extension
					.fileSize(pictureBytes.length)                  // 파일사이즈: fileSize
					.build();

			attachments.add(attachment);
		}

		return attachments;
	}

	/**
	 * bytes 데이터를 File로 변환한다.
	 *
	 * @param pictureBytes
	 * @param extension
	 * @return
	 * @throws IOException
	 */
	private File bytesToFile(byte[] pictureBytes, String extension) throws IOException {
		String suffix = "." + extension;
		File tempFile = Files.createTempFile(UUID.randomUUID().toString(), suffix).toFile();
		Files.write(tempFile.toPath(), pictureBytes);
		return tempFile;
	}

	/**
	 * (총괄) 원본파일명 설정, 첨부파일 리스트 추가
	 *
	 * @param summaryData
	 * @param summaryAttachments
	 */
	private void addAttachmentsToSummary(List<PointOutExcelSummary> summaryData, List<Attachment> summaryAttachments) {
		for (PointOutExcelSummary summary : summaryData) {
			for (int i = 0; i < summaryAttachments.size(); i++) {
				Attachment attachment = summaryAttachments.get(i);
				if (summary.getRowNumber() == attachment.getRow()) {
					// setOriginalFilename
					String originalFilename = summary.getAdvNo() + "_첨부파일_" + i + "." + attachment.getFileExtsn();
					attachment.setOrignlFileNm(originalFilename);

					summary.getAttachments().add(attachment); // add
				}
			}
		}
	}

	/**
	 * 상세탭(시트)에서 데이터 및 첨부파일 정보를 추출하여 반환한다.
	 *
	 *  @param wb
	 * @param summaries
	 * @return
	 */
	public List<List<PointOutExcelDetail>> getDetails(Workbook wb, List<PointOutExcelSummary> summaries) throws IOException {

		List<List<PointOutExcelDetail>> result = new ArrayList<>();
		for (int i = 0; i < summaries.size(); i++) {
			XSSFSheet detailSheet = (XSSFSheet) wb.getSheetAt(i + 1);
			List<PointOutExcelDetail> detailData = getDetailData(detailSheet, summaries.get(i)); // 상세 시트(탭)
			List<Attachment> detailAttachments = getAttachments(detailSheet);        // 해당시트의 첨부(이미)파일 정보 가져오기
			addAttachmentsToDetail(detailData, detailAttachments); // 원본파일명 설정, 첨부파일 리스트 추가

			result.add(detailData);
		}

		return result;
	}

	/**
	 * 상세탭(시트)에서 값 추출 후 객체 리스트 형태로 반환
	 *
	 * @param detailSheet
	 * @param summary
	 * @return
	 */
	private List<PointOutExcelDetail> getDetailData(XSSFSheet detailSheet, PointOutExcelSummary summary) {
		// 예외처리: 총괄탭의 관리번호 목록과 시트(탭) 명칭이 일치하지 않습니다.
		if (!summary.getAdvNo().equals(detailSheet.getSheetName()))
			throw new SheetNameNotMatchedException(msgSrc.getMessage("P001"));


		int firstRowNum = propertiesService.getInt("point-out.excel.detail.first.row.number"); // 추출할 데이터가 존재하는 첫번째 행
		int lastRowNum = detailSheet.getLastRowNum(); // sheet에서 확인되는 마지막 행: detected row by excel

		List<PointOutExcelDetail> detailList = new ArrayList<>(); // return object
		for (int i = firstRowNum; i < lastRowNum + 1; i++) {
			XSSFRow row = detailSheet.getRow(i);
			if (row == null) continue;     // handle null row exception: 데이터가 없는 행 건너뛰기

			int cellNum = propertiesService.getInt("point-out.excel.detail.first.cell.number");
			PointOutExcelDetail detail = PointOutExcelDetail.builder()
					// MasterLawTemplate
					.mstLawId(summary.getMstLawId())
					.optTimeYear(summary.getOptTimeYear())
					.optTimeHalf(summary.getOptTimeHalf())

					// ExcelBaseModel
					.attachments(new ArrayList<>())
					.rowNumber(i)

					// PointOutExcelDetail
					.advDtlSn((int) row.getCell(cellNum++).getNumericCellValue())           // 순번
					.advDtlText(row.getCell(cellNum++).getStringCellValue())                // 지적사항
					.admNm(row.getCell(cellNum++).getStringCellValue())                     // 행정조치사항 구분
//					.cntStopFacility()  // 불필요
					.amtFine(omitLastValue(row.getCell(cellNum++).getStringCellValue()))    // 과태료
					.imprPlan(row.getCell(cellNum++).getStringCellValue()) // 개선계획
//					.orgCd()    // 쿼리에서 입력
					.orgNm(row.getCell(cellNum++).getStringCellValue())    // 조치부서명
					.imprRst(row.getCell(cellNum++).getStringCellValue())   // 개선조치결과
//					.befFileId()    // proceedDetailLogic 에서 입력
//					.aftFileId()    // proceedDetailLogic 에서 입력
					.advId(summary.getAdvId())
					.build();

			detailList.add(detail);
		}

		return detailList;
	}

	/**
	 * (상세) 원본파일명 설정, 첨부파일 리스트 추가
	 * originalFileNm 설정
	 *
	 * @param detailData
	 * @param detailAttachments
	 */
	private void addAttachmentsToDetail(List<PointOutExcelDetail> detailData, List<Attachment> detailAttachments) {
		for (int i = 0; i < detailData.size(); i++) {
			PointOutExcelDetail detail = detailData.get(i);

			for (int j = 0; j < detailAttachments.size(); j++) {
				Attachment attachment = detailAttachments.get(j);
				if (detail.getRowNumber() == attachment.getRow()) {

					// 파일명 설정
					String originalFilename = (i + 1) + "_첨부파일_";
					originalFilename += ( attachment.getCol() == 8 ) ? "조치전" : "조치후";   // 전, 후 구분
					originalFilename += "." + attachment.getFileExtsn();                    // extension

					attachment.setOrignlFileNm(originalFilename);
					detail.getAttachments().add(attachment); // add
				}
			}
		}
	}

	/**
	 * 맨 뒤에 '만' 제거
	 *
	 * @param fineString
	 * @return
	 */
	private int omitLastValue(String fineString) {
		if (fineString.equals("-") || StringUtils.isEmpty(fineString)) return 0;

		int len = fineString.length();
		String substring = fineString.substring(0, len - 1);
		return Integer.parseInt(substring);
	}

	/**
	 * 이미지 삽입
	 *
	 * @param wb
	 * @param sheet
	 * @param rowNum
	 * @param cellNum
	 * @param fileList
	 * @param isSummary
	 * @throws IOException
	 */
	private void drawPicture(XSSFWorkbook wb, XSSFSheet sheet, int rowNum, short cellNum, List<FileDetailDTO> fileList, boolean isSummary) throws IOException {
		if (fileList != null) {
			for (FileDetailDTO file : fileList) {
				int pictureType = XSSFWorkbook.PICTURE_TYPE_PNG;
				FileInputStream fis = new FileInputStream(file.getFileStreCours());
				byte[] bytes = IOUtils.toByteArray(fis);
				String fileExt = file.getFileExtsn();

				if (fileExt.equals("png")) pictureType = XSSFWorkbook.PICTURE_TYPE_PNG;
				else if(fileExt.equals("jpg") || fileExt.equals("jpeg")) pictureType = XSSFWorkbook.PICTURE_TYPE_JPEG;

				int picIdx = wb.addPicture(bytes, pictureType);
				fis.close();

				XSSFCreationHelper creationHelper = wb.getCreationHelper();
				XSSFDrawing drawingPatriarch = sheet.createDrawingPatriarch();
				XSSFClientAnchor clientAnchor = creationHelper.createClientAnchor();

				clientAnchor.setRow1(rowNum);
				clientAnchor.setCol1(cellNum++);

				XSSFPicture picture = drawingPatriarch.createPicture(clientAnchor, picIdx);

				// 사이즈는 일단 샘플에 맞게 해둠..
				if (isSummary == true) picture.resize(0.22, 0.03);
				else picture.resize(1.5, 0.3);
			}
		}

	}

	/**
	 * 총괄탭 정보 입력
	 *
	 * @param pointOutSummaryList
	 * @param wb
	 * @return
	 */
	public XSSFWorkbook writeSummaries(List<PointOutSummary> pointOutSummaryList, XSSFWorkbook wb) throws IOException {
		XSSFSheet sheet = wb.getSheetAt(0);

		int rowNum = propertiesService.getInt("point-out.excel.download.summary.first.row.number");
		short height = (short)propertiesService.getInt("point-out.excel.download.summary.row.style.height");
		Row row;
		Cell cell;


		// style
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		for (PointOutSummary summary : pointOutSummaryList) {
			row = sheet.createRow(rowNum++);
			row.setHeight(height);
			short cellNum = 1;

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getAdvNo());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getMngOrgNm());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getChkOrgNm());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getChkOrgNm());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getChkDt());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getCntTotalViolation());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getCntJudicialAct());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getCntStopUse());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getCntStopFacilityTotal());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getCntCorrectOrder());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getCntCorrectInstruct());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getCntFine());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getAmtFineTotal());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getCntRecmd());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(summary.getCntEtc());

			drawPicture(wb, sheet, rowNum, cellNum, summary.getEvdFiles(), true);
		}

		return wb;
	}

	/**
	 * 관리번호별 탭 정보 입력
	 *
	 * @param summaryList
	 * @param wb
	 * @return
	 */
	public XSSFWorkbook writeDetails(List<PointOutSummary> summaryList, XSSFWorkbook wb) throws IOException {
		// variables
		int baseSheetNum = 1;
		int baseRowNum = 4;
		float toCentimeter = 20;
		int createSheetNum = 2;

		for (PointOutSummary summary : summaryList) {
			// variables
	        int rowNum = 4;

			// sheet
			XSSFSheet sheet = wb.cloneSheet(baseSheetNum);
			wb.setSheetName(createSheetNum++, summary.getAdvNo());

			// style
			XSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			// row & cell
			Row row;
			Cell cell;
			List<PointOutDetail> detailList = summary.getPointOutDetailList();
			for (PointOutDetail detail : detailList) {
		    	// variables
				short cellNum = 0;

				// row
				row = sheet.createRow(rowNum++);
				row.setHeight((short) (52 * toCentimeter));

				// cell
				cell = row.createCell(cellNum++);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(summary.getAdvNo());

				cell = row.createCell(cellNum++);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(detail.getAdvDtlSn());

				cell = row.createCell(cellNum++);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(detail.getAdvDtlText());

				cell = row.createCell(cellNum++);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(detail.getAdmCdNm());

				cell = row.createCell(cellNum++);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(detail.getAmtFine());

				cell = row.createCell(cellNum++);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(detail.getImprPlan());

				cell = row.createCell(cellNum++);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(detail.getFullOrgNm());

				cell = row.createCell(cellNum++);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(detail.getImprRst());

				drawPicture(wb, sheet, rowNum, cellNum++, detail.getBefFiles(), false);
				drawPicture(wb, sheet, rowNum, cellNum, detail.getAftFiles(), false);
			}

			// 관리번호 병합
		    sheet.addMergedRegion(new CellRangeAddress(baseRowNum, baseRowNum + detailList.size() - 1, 0, 0));
		}

		wb.removeSheetAt(baseSheetNum);
		return wb;
	}
}
