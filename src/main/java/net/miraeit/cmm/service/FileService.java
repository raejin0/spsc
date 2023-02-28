package net.miraeit.cmm.service;

import egovframework.rte.fdl.property.EgovPropertyService;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.condition.FileDetailConditions;
import net.miraeit.cmm.dao.FileDAO;
import net.miraeit.cmm.exception.file.NullFileException;
import net.miraeit.cmm.model.file.FileDetailDTO;
import net.miraeit.cmm.model.file.UploadFile;
import net.miraeit.cmm.util.FileStore;
import net.miraeit.pointout.model.excel.Attachment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileService {
	private final FileStore fileStore;
	private final FileDAO fileDAO;
	private final EgovPropertyService propertyService;

	public FileDetailDTO findFileDetail(Integer atchFileId, Integer fileSn) {

		FileDetailConditions condition = FileDetailConditions.builder()
				.atchFileId(atchFileId)
				.fileSn(fileSn)
				.build();

		return fileDAO.findFileDetail(condition);
	}

	/**
	 * fileId 생성 후 파일상세정보를 저장한다.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public int saveFileDetail(MultipartFile file) throws IOException {
		if (file == null) throw new NullFileException();

		int atchFileId = fileDAO.createFileId();
		saveFileDetail(file, atchFileId);
		return atchFileId;
	}

	/**
	 * 파일상세정보를 저장한다. (단건)
	 *
	 * @param file
	 * @param atchFileId
	 * @return
	 * @throws IOException
	 */
	public int saveFileDetail(MultipartFile file, int atchFileId) throws IOException {
		if (file == null) throw new NullFileException();

		int fileSn = fileDAO.spscCmmGetMaxFileSn(atchFileId);        // fileSn 설정
		// 파일 저장
		UploadFile uploadFile = fileStore.storeFile(file, atchFileId, fileSn, "F");
		fileDAO.saveFileDetail(uploadFile);

		return atchFileId;
	}

	/**
	 * 받아온 파일id로 파일상세정보를 저장(수정)한다. (다건)
	 *
	 * @param files
	 * @param atchFileId
	 * @return
	 * @throws IOException
	 */
	public int saveFilesDetail(MultipartFile[] files, int atchFileId) throws IOException {
		for(MultipartFile file : files) {
			saveFileDetail(file, atchFileId);
		}
		return atchFileId;
	}

	/**
	 * fileId 생성 후 다건의 파일상세정보를 저장한다.
	 *
	 * @param files
	 * @return
	 * @throws IOException
	 */
	public int saveFileDetails(MultipartFile[] files) throws IOException {
		if(files == null) throw new NullFileException();
		int atchFileId = fileDAO.createFileId();
		for (MultipartFile file : files) {
			saveFileDetail(file, atchFileId);
		}
		return atchFileId;
	}

	/**
	 * fileSn 설정 후 DB에 FileDetail 저장
	 *
	 * @param attachment
	 */
	private void saveFileDetail(Attachment attachment) {
		attachment.setFileSn(fileDAO.spscCmmGetMaxFileSn(attachment.getAtchFileId())); // fileSn 조회 및 설정
		fileDAO.saveFileDetail(attachment); // 파일디테일 저장
	}

	public List<String> findUploadFilePathListByIds(List<Integer> ids) {
		List<String> filePathList = new ArrayList<>();
		for (Integer id : ids) {
			List<String> paths = fileDAO.findUploadFilePathList(id);
			for (String path : paths) {
				filePathList.add(path);
			}
		}
		return filePathList;
	}

	public void deleteFileAndDetailByIds(List<Integer> ids) {
		for (Integer id : ids) {
			fileDAO.deleteFileDetailByFileId(id);
			fileDAO.deleteFileById(id);
		}
	}

	/**
	 * 엑셀 양식 파일 id 및 sn 설정 후 조회
	 *
	 * @return
	 */
	public FileDetailDTO findEmptyExcelFormat() {
	    Integer atchFileId = propertyService.getInt("point-out.excel.empty.format.atchFileId");
	    Integer fileSn = propertyService.getInt("point-out.excel.empty.format.fileSn");
	    FileDetailConditions fileDetailConditions = FileDetailConditions.builder()
			    .atchFileId(atchFileId)
			    .fileSn(fileSn)
			    .build();
	     return fileDAO.findFileDetail(fileDetailConditions);

	}

	public XSSFWorkbook readXlsFile(FileDetailDTO fileDetail) throws IOException {
	    File xlsFile = new File(fileDetail.getFileStreCours()); // 저장된 위치에서 파일 읽어오기
		FileInputStream fileInputStream = new FileInputStream(xlsFile);
		XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
		return sheets; // 워크북 생성
	}
}
