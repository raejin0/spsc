package net.miraeit.pointout.service;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.property.EgovPropertyService;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.condition.FileDetailConditions;
import net.miraeit.cmm.dao.CMMDAO;
import net.miraeit.cmm.dao.FileDAO;
import net.miraeit.cmm.model.OperationPeriod;
import net.miraeit.cmm.model.file.FileDetailDTO;
import net.miraeit.cmm.service.FileService;
import net.miraeit.cmm.util.ExcelUtil;
import net.miraeit.cmm.util.FileStore;
import net.miraeit.fx.listing.model.PagedList;
import net.miraeit.master.dao.MasterDAO;
import net.miraeit.pointout.dao.PointOutDAO;
import net.miraeit.pointout.exception.ExistingAdvNoException;
import net.miraeit.pointout.exception.OrganizationCodeNotFoundException;
import net.miraeit.pointout.exception.SummaryOrganizationCodeNotFoundException;
import net.miraeit.pointout.model.*;
import net.miraeit.pointout.model.excel.Attachment;
import net.miraeit.pointout.model.request.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PointOutService {
	private final FileService fileService;
	private final FileDAO fileDAO;
	private final PointOutDAO pointOutDAO;
	private final EgovMessageSource msgSrc;
	private final FileStore fileStore;
	private final MasterDAO masterDAO;
	private final EgovPropertyService propertiesService;
	private final CMMDAO cmmdao;
	private final ExcelUtil excelUtil;

	public PagedList<PointOutSummary> getPointOutSummaryPagedList(PointOutSummaryRequest request) {
		PagedList<PointOutSummary> pagedList = pointOutDAO.selectPointOutSummaryPagedList(request);
		Map<Integer, PointOutSummary> summaryMap = pagedList.getList().stream()
			.collect(Collectors.toMap(PointOutSummary::getEvdFileId, Function.identity()));
		if(CollectionUtils.isEmpty(pagedList.getList())) return pagedList;

		FileDetailConditions fileConditions = new FileDetailConditions();
		pagedList.getList().forEach(x -> fileConditions.addAtchFileId(x.getEvdFileId()));
		fileDAO.findFilesOfMultiplId(fileConditions)
			.forEach(x -> summaryMap.get(x.getAtchFileId()).addEvdFile(x));

		return pagedList;
	}

	public int getPointOutSummaryCountByAdvNo(String advNo) {
		return pointOutDAO.selectPointOtSummaryCountByAdvNo(advNo);
	}

	public PointOutSummary regPointOutSummary(PointOutSummaryRegRequest request) {
		request.setEvdFileId(fileDAO.createFileId());
		int advId = pointOutDAO.insertPointOutSummary(request);
		return pointOutDAO.selectPointOutSummary(advId);
	}

	public PointOutSummary regPointOutSummaryWithFile(PointOutSummaryRegRequest request, MultipartFile evdFile) throws IOException {
		request.setEvdFileId((evdFile!=null) ? fileService.saveFileDetail(evdFile) : fileDAO.createFileId());
		int advId = pointOutDAO.insertPointOutSummary(request);
		return pointOutDAO.selectPointOutSummary(advId);
	}

	public PointOutSummary regPointOutSummaryWithFiles(PointOutSummaryRegRequest request, MultipartFile[] evdFiles) throws IOException {
		if(evdFiles != null && evdFiles.length > 0) request.setEvdFileId(fileService.saveFileDetails(evdFiles)); // ?????? ?????????
		else request.setEvdFileId(fileDAO.createFileId()); // ?????? ?????????

		int advId = pointOutDAO.insertPointOutSummary(request);
		return pointOutDAO.selectPointOutSummary(advId);
	}

	public PointOutSummary modPointOutSummary(PointOutSummaryModRequest request) {
		pointOutDAO.updatePointOutSummary(request);
		return pointOutDAO.selectPointOutSummary(request.getAdvId());
	}

	public PointOutSummary modPointOutSummary(PointOutSummaryModRequest request, MultipartFile[] evdFiles) throws IOException {
		PointOutSummary prev = pointOutDAO.selectPointOutSummary(request.getAdvId());
		if(request.isDoDeleteFile()) fileDAO.deleteFileDetailByFileId(prev.getEvdFileId());

		if(evdFiles != null && evdFiles.length > 0) { // ?????? ?????????
//			fileDAO.deleteFileDetailById(prev.getEvdFileId());
			fileService.saveFilesDetail(evdFiles, request.getEvdFileId());
		}
		return modPointOutSummary(request);
	}

	public int rmvPointOutSummary(PointOutSummaryModRequest request) {
		pointOutDAO.deletePointOutDetailByAdvId(request.getAdvId());
		return pointOutDAO.deletePointOutSummary(request);
	}

	public PagedList<PointOutDetail> getPointOutDetailPagedList(PointOutDetailRequest request) {
		PagedList<PointOutDetail> pagedList = pointOutDAO.selectPointOutDetailPagedList(request);
		if(CollectionUtils.isEmpty(pagedList.getList())) return pagedList;
		HashMap<Integer, List<FileDetailDTO>> filesMap = new HashMap<>();
		List<PointOutDetail> list = pagedList.getList();
		FileDetailConditions fileConditions = new FileDetailConditions();
		list.forEach(x -> {
			Integer befFileId = x.getBefFileId();
			Integer aftFileId = x.getAftFileId();

			if (befFileId != null) {
				fileConditions.addAtchFileId(x.getBefFileId());
				filesMap.put(x.getBefFileId(), new ArrayList<>());
			}
			if (aftFileId != null) {
				fileConditions.addAtchFileId(x.getAftFileId());
				filesMap.put(x.getAftFileId(), new ArrayList<>());
			}

		});
		fileDAO.findFilesOfMultiplId(fileConditions).forEach(x -> filesMap.get(x.getAtchFileId()).add(x));
		list.forEach(x -> {
			x.setBefFiles(filesMap.get(x.getBefFileId()));
			x.setAftFiles(filesMap.get(x.getAftFileId()));
		});

		return pagedList;
	}

	public PointOutDetail getPointOutDetail(int advDtlId) {
		PointOutDetail retVal = pointOutDAO.selectPointOutDetail(advDtlId);
		FileDetailConditions conditions = new FileDetailConditions();

		Integer befFileId = retVal.getBefFileId();
		Integer aftFileId = retVal.getAftFileId();
		if ( befFileId != null) conditions.addAtchFileId(retVal.getBefFileId());
		if ( aftFileId != null) conditions.addAtchFileId(retVal.getAftFileId());

		fileDAO.findFilesOfMultiplId(conditions).forEach(x -> {
			if(befFileId.equals(x.getAtchFileId())) retVal.addBefFile(x);
			else retVal.addAftFile(x);
		});
		return retVal;
	}

	public PointOutDetail regPointOutDetail(PointOutDetailRegRequest request) {
		request.setBefFileId(fileDAO.createFileId());
		request.setAftFileId(fileDAO.createFileId());
		int advDtlId = pointOutDAO.insertPointOutDetail(request);
		pointOutDAO.updatePointOutSummarySTS(request.getSummaryForSTSMod());
		PointOutDetail retVal = pointOutDAO.selectPointOutDetail(advDtlId);
		return retVal;
	}

	public PointOutDetail regPointOutDetailWithFile(PointOutDetailRegRequest request, MultipartFile befFile, MultipartFile aftFile) throws IOException {
		request.setBefFileId((befFile!=null) ? fileService.saveFileDetail(befFile) : fileDAO.createFileId());
		request.setAftFileId((aftFile!=null) ? fileService.saveFileDetail(aftFile) : fileDAO.createFileId());

		int advDtlId = pointOutDAO.insertPointOutDetail(request);
		pointOutDAO.updatePointOutSummarySTS(request.getSummaryForSTSMod());
		PointOutDetail retVal = pointOutDAO.selectPointOutDetail(advDtlId);
		return retVal;
	}

	public PointOutDetail modPointOutDetail(PointOutDetailModRequest request) {
		PointOutDetail prev = pointOutDAO.selectPointOutDetail(request.getAdvDtlId());
		pointOutDAO.updatePointOutDetail(request);
		pointOutDAO.updatePointOutSummarySTS(prev.getSummaryForSTSMod(request));
		return pointOutDAO.selectPointOutDetail(request.getAdvDtlId());
	}

	public PointOutDetail modPointOutDetailWithFile(PointOutDetailModRequest request, MultipartFile befFile, MultipartFile aftFile) throws IOException {
		PointOutDetail prev = pointOutDAO.selectPointOutDetail(request.getAdvDtlId());
		if(befFile!=null) {
			fileDAO.deleteFileDetailByFileId(prev.getBefFileId());
			fileService.saveFileDetail(befFile, prev.getBefFileId());
		}
		if(aftFile!=null) {
			fileDAO.deleteFileDetailByFileId(prev.getAftFileId());
			fileService.saveFileDetail(aftFile, prev.getAftFileId());
		}
		pointOutDAO.updatePointOutDetail(request);
		pointOutDAO.updatePointOutSummarySTS(prev.getSummaryForSTSMod(request));
		return pointOutDAO.selectPointOutDetail(request.getAdvDtlId());
	}

	public int rmvPointOutDetail(PointOutDetailModRequest request) {
		int rmvCount = 0;
		PointOutDetail prev = pointOutDAO.selectPointOutDetail(request.getAdvDtlId());
		fileDAO.deleteFileDetailByFileId(prev.getBefFileId());
		fileDAO.deleteFileDetailByFileId(prev.getAftFileId());
		rmvCount += pointOutDAO.deletePointOutDetailById(request.getAdvDtlId());
		pointOutDAO.updatePointOutSummarySTS(prev.getSummaryForSTSCut());
		return rmvCount;
	}

	public List<PointOutSTS> getPointOutSTS(OperationPeriod period) {
		List<PointOutSTS> pointOutSts = pointOutDAO.selectPointOutSTS(period);
		/* KOMIPO ??????????????? ?????? */
		for(int i=0 ; i<pointOutSts.size(); i++) {
			PointOutSTS item = pointOutSts.get(i);
			if("8940".equals(item.getOrgCd())) {
				pointOutSts.remove(item);
				break;
			}
		}
		return pointOutSts;
	}

	/* -------------- ?????? ????????? ----------------------------------------------------------------------- */

	/**
	 * ????????? ????????? ???????????? ????????????.
	 *
	 * @param wb
	 * @param request
	 * @throws IOException
	 */
	public void upload(Workbook wb, PointerOutExcelUploadRequest request) throws IOException {
		// ??????
		List<PointOutExcelSummary> summaries = excelUtil.getSummary(wb); // ?????????????????? ????????? ??? ???????????? ????????? ??????
		summaries = proceedUploadSummaryLogic(summaries, request);     // ????????? ???????????? ??????, ?????? ?????? ?????? ????????? ??????, ??????id ?????? ??? ??????

		// ??????
		List<List<PointOutExcelDetail>> detailSheetList = excelUtil.getDetails(wb, summaries); // ?????? ??????????????? ????????? ??? ???????????? ????????? ??????
		proceedUploadDetailLogic(detailSheetList);
	}

	/**
	 * ????????? ???????????? ??????, ?????? ?????? ?????? ????????? ??????, ?????? id??? ????????? summaryList??? ????????????.
	 *
	 * @param summaries
	 * @param request
	 * @throws IOException
	 */
	public List<PointOutExcelSummary> proceedUploadSummaryLogic(List<PointOutExcelSummary> summaries, PointerOutExcelUploadRequest request) throws IOException {
		List<PointOutExcelSummary> result = new ArrayList<>(); // result object

		// ?????????
		for (PointOutExcelSummary summary : summaries) {
			Integer mstLawId = findMstLawId(request);

			String orgCd = cmmdao.findOrgCdByMstLawIdAndFullOrgNm(mstLawId, summary.getMngOrgNm());
			if (StringUtils.isEmpty(orgCd)) throw new SummaryOrganizationCodeNotFoundException(msgSrc.getMessage("P000"));

			// ????????? ?????? ?????? ??? ??????
			summary.setMstLawId(mstLawId);
			summary.setOptTimeYear(request.getOptTimeYear());
			summary.setOptTimeHalf(request.getOptTimeHalf());

			result.add(insertOrUpdatePointOutSummary(summary)); // ??????/???????????? ?????? ????????? ?????? ?????? ??????, ??????id ??????
		}

		return result;
	}

	/**
	 * mstLawId ??????
	 *
	 * @param request
	 * @return
	 */
	private Integer findMstLawId(PointerOutExcelUploadRequest request) {
		return pointOutDAO.findMstLawId(request);
	}

	/**
	 * ?????? ????????? ????????? ?????? ????????? ??? ?????? ?????? ??? ????????????.
	 *
	 * @param detailSheetList
	 * @throws IOException
	 */
	public void proceedUploadDetailLogic(List<List<PointOutExcelDetail>> detailSheetList) throws IOException {
		for (List<PointOutExcelDetail> detailSheet : detailSheetList) {
			// ???????????? ?????? ??? db ??????
			Integer advId = detailSheet.get(0).getAdvId();
			List<PointOutDetail> findDetailList = pointOutDAO.findPointOutDetailListByAdvId(advId);
			for (PointOutDetail pointOutDetail : findDetailList) {
				// ?????? ??????
				List<Integer> ids = new ArrayList<>();
				if (pointOutDetail.getBefFileId() != null) ids.add(pointOutDetail.getBefFileId());
				if (pointOutDetail.getAftFileId() != null) ids.add(pointOutDetail.getAftFileId());

				List<String> filePathList = fileService.findUploadFilePathListByIds(ids);
				fileStore.removeFiles(filePathList);        // ?????????????????? ?????? ??????
				fileService.deleteFileAndDetailByIds(ids);

				// db ??????
				pointOutDAO.deletePointOutDetailById(pointOutDetail.getAdvDtlId());
			}


			for (PointOutExcelDetail detail : detailSheet) {
				// ?????? ????????? ??????
			    Integer befFileId = fileDAO.createFileId();
			    Integer aftFileId = fileDAO.createFileId();

			    detail.setBefFileId(befFileId);
			    detail.setAftFileId(aftFileId);

				// ?????? ?????? ??????
				for (Attachment attachment : detail.getAttachments()) {
					fileStore.saveAttachments(attachment);

					Integer colNo = attachment.getCol();
					int colBefore = propertiesService.getInt("point-out.excel.detail.column.number.before");
					int colAfter = propertiesService.getInt("point-out.excel.detail.column.number.after");
					if ( colNo == colBefore ) {
						attachment.setAtchFileId(befFileId);
						attachment.setFileSn(fileDAO.spscCmmGetMaxFileSn(befFileId));
					}

					if ( colNo == colAfter ) {
						attachment.setAtchFileId(aftFileId);
						attachment.setFileSn(fileDAO.spscCmmGetMaxFileSn(aftFileId));
					}

					fileDAO.saveFileDetail(attachment);
				}

				// ??????/???????????? ?????? ??????
				String orgCd = cmmdao.findOrgCdByMstLawIdAndFullOrgNm(detail.getMstLawId(), detail.getOrgNm());
				if (StringUtils.isEmpty(orgCd)) throw new OrganizationCodeNotFoundException(msgSrc.getMessage("P002"));

				detail.setOrgCd(orgCd);
				pointOutDAO.insertPointOutDetail(detail);
			}
		}
	}

	/**
	 * ?????? ??? ????????? ???????????? ????????? ????????????.
	 *
	 * @param summary
	 * @return
	 */
	public PointOutExcelSummary insertOrUpdatePointOutSummary(PointOutExcelSummary summary) throws IOException {
		PointOutExcelSummary summaryFound = pointOutDAO.findPointOutSummary(summary.getAdvNo()); // ??????????????? ??????

		if (summaryFound == null) { // ?????? ??????????????? ?????? ??????
			Integer fileId = fileDAO.createFileId();    // fileId ?????? ??? ??????
			summary.setEvdFileId(fileId);               // fileId ?????? (Summary ??????)
			saveFileDetails(summary.getAttachments(), fileId);           // ??????????????? ???????????? ??????, DB??? FileDetail ??????
			Integer advId = pointOutDAO.insertPointOutSummary(summary);// ??????/???????????? ?????? ????????? ??????, ??????id ??????
			summary.setAdvId(advId);

			return summary;

		}else { // ?????? ???????????? ??????????????? ??????

			// ?????? ??????: ?????? ????????? ???????????? ?????????????????? ??????
			if ( summary.getMstLawId() != summaryFound.getMstLawId()) throw new ExistingAdvNoException(msgSrc.getMessage("P003"));

			Integer evdFileId = summaryFound.getEvdFileId();
			List<String> filePathList = fileDAO.findUploadFilePathList(evdFileId); // fileId??? ????????? ?????? ?????? ??????
			fileStore.removeFiles(filePathList);        // ?????????????????? ?????? ??????
			fileDAO.deleteFileDetailByFileId(evdFileId);    // DB ??????????????? ?????? ??????
			saveFileDetails(summary.getAttachments(), evdFileId);        // ??????????????? ???????????? ??????, DB??? FileDetail ??????
			pointOutDAO.updatePointOutSummary(summary); // ??????/???????????? ?????? ????????? ??????

			summary.setAdvId(summaryFound.getAdvId());

			return summary;
		}
	}

	/**
	 * ??????????????? ??????????????? ??????/???????????? Database??? ????????? ????????????.
	 *
	 * @param attachments
	 * @param fileId
	 * @throws IOException
	 */
	private void saveFileDetails(List<Attachment> attachments, Integer fileId) throws IOException {
		for (Attachment attachment : attachments) {
			fileStore.saveAttachments(attachment);          // ??????????????? ???????????? ??????

			attachment.setAtchFileId(fileId);               // fileId ??????
			attachment.setFileSn(fileDAO.spscCmmGetMaxFileSn(fileId)); // fileSn ??????
			fileDAO.saveFileDetail(attachment);             // fileSn ?????? ??? DB??? FileDetail ??????
		}
	}
	/* ////////////// ?????? ????????? /////////////////////////////////////////////////////////////////////// */
}
