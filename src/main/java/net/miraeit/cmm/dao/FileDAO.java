package net.miraeit.cmm.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import net.miraeit.cmm.condition.FileDetailConditions;
import net.miraeit.cmm.model.file.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FileDAO extends EgovAbstractDAO {

	// fileId 생성 및 반환
	public Integer createFileId() {
		return (Integer) insert("spsc.cmm.insert.file", new FileDTO());
	}

	public void saveFileDetail(UploadFile uploadFile) {
		insert("spsc.cmm.insert.file.detail", uploadFile);
	}
	public void updateFileDetail(UploadFile request) {
	   update("spsc.cmm.update.file.detail", request);
	}
	public void saveURLDetail(FileUploadRequest fileUploadRequest) {
		insert("spsc.cmm.insert.url.detail", fileUploadRequest);
	}

	public void updateURLDetail(FileUploadRequest request) {
	   update("spsc.cmm.update.url.detail", request);
	}

	public FileDetailDTO findFileDetail(FileDetailConditions request) {
		return (FileDetailDTO) select("spsc.cmm.select.file.detail", request);
	}

	public List<UploadFileResponse> findFileDetails(FileDetailConditions condition) {
		return (List<UploadFileResponse>) list("spsc.cmm.select.file.detail.list", condition);
	}

	@SuppressWarnings("unchecked")
	public List<String> findUploadFilePathList(Integer atchFileId) {
		return (List<String>) list("spsc.cmm.select.file.path.list", new FileDetailConditions(atchFileId));
	}

	@SuppressWarnings("unchecked")
	public List<FileDetailDTO> findFilesOfMultiplId(FileDetailConditions conditions) {
		if(CollectionUtils.isEmpty(conditions.getAtchFileIds()))
			return new ArrayList<>();
		return (List<FileDetailDTO>) list("spsc.cmm.select.file.detail.list.of.multiple.id", conditions);
	}

	public String findUploadFilePath(DeleteUploadedFileRequest request) {
		return (String) select("spsc.cmm.select.file.path", request);
	}

	public void deleteFileDetail(DeleteUploadedFileRequest request) {
		delete("spsc.cmm.delete.file.detail", request);
	}

	public void deleteFileDetailByFileId(Integer atchFileId) {
		delete("spsc.cmm.delete.file.detail.by.id", atchFileId);
	}

	public void deleteFileById(Integer atchFileId) {
		delete("spsc.cmm.delete.file", atchFileId);
	}

	public Integer spscCmmGetMaxFileSn(Integer atchFileId) {
		return (Integer) select("spsc.cmm.get.maxFileSn", atchFileId);
	}

	// 파일디테일 저장
	public void saveFileDetail(FileDetailDTO fileDetail) {
		insert("spsc.file.insert.file.detail", fileDetail);
	}

	// 파일디테일 조회
	public List<FileDetailDTO> findFileDetailListById(Integer evdFileId) {
		return (List<FileDetailDTO>) list("spsc.cmm.select.file.detail.list.by.id", evdFileId);
	}
}
