package net.miraeit.cmm.util;

import egovframework.rte.fdl.property.EgovPropertyService;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.model.file.UploadFile;
import net.miraeit.pointout.model.excel.Attachment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * 파일 업로드 관련 처리를 위한 클래스
 */
@Component
@RequiredArgsConstructor
public class FileStore { // todo FileUtil 로 클래스명 변경

    @Resource(name = "propertiesService")
    public EgovPropertyService propertiesService;

	/**
	 * 파일이 저장될 전체 경로를 반환한다.
	 *
	 * @param storeFilename
	 * @return
	 */
	public String getFullPath(String storeFilename) {
		String fileDir = propertiesService.getString("Globals.fileStorePath");
		return fileDir + storeFilename;
	}

	/**
	 * 여러 파일 저장
	 *
	 * @param multipartFiles
	 * @param atchFileId
	 * @return
	 * @throws IOException
	 */
//    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles, Integer atchFileId) throws IOException {
//		// 한글깨짐 utf-8 encoding 코드 여긴에 넣어야 할까
//	    // String fileNm = new String(file.getOriginalFilename().getBytes("8859_1"), "UTF-8");
//
//	    int sn = 1;
//        List<UploadFile> storeFileResult = new ArrayList<>();
//        for (MultipartFile multipartFile : multipartFiles) {
//            if (!multipartFile.isEmpty()) {
//	            storeFileResult.add(storeFile(multipartFile, atchFileId, sn++, fileUploadRequest.getAtchTy()));
//            }
//        }
//        return storeFileResult;
//    }

	/**
	 * 파일 단건 저장 ( multipartFile )
	 *
	 * @param multipartFile
	 * @param atchFileId
	 * @param sn
	 * @param atchTy
	 * @return
	 * @throws IOException
	 */
    public UploadFile storeFile(MultipartFile multipartFile, Integer atchFileId, Integer sn, String atchTy) throws IOException {
        if (multipartFile == null) return null;

	    UploadFile uploadFile = buildUploadFile(multipartFile, atchFileId, sn, atchTy);
	    multipartFile.transferTo(new File(uploadFile.getFileStreCours()));
        return uploadFile;
    }

	/**
	 * UploadFile 객체 생성
	 *
	 * @param multipartFile
	 * @param atchFileId
	 * @param sn
	 * @param atchTy
	 * @return
	 */
	private UploadFile buildUploadFile(MultipartFile multipartFile, Integer atchFileId, int sn, String atchTy) throws UnsupportedEncodingException {

//		String originalFilename;

//		if (methodType.equals("PATCH"))
//			originalFilename = multipartFile.getOriginalFilename();
//		else
//			originalFilename = new String(multipartFile.getOriginalFilename().getBytes(propertiesService.getString("multipart.encoding.post.input")), propertiesService.getString("multipart.encoding.post.output")); // 인코딩

		String storeFilename = createStoreFilename();
		String originalFilename = multipartFile.getOriginalFilename();

		return UploadFile.builder()
				.atchFileId(atchFileId)
				.fileSn(sn)
				.fileStreCours(getFullPath(storeFilename))
				.streFileNm(storeFilename)
				.orignlFileNm(originalFilename)
				.fileExtsn(extractExt(originalFilename))
				.fileSize(multipartFile.getSize())
				.atchTy(atchTy)
				.build();
	}

	/**
	 * UUID를 활용하여 저장시 사용할 파일명 생성
	 *
	 * @return
	 */
	public String createStoreFilename() {
		return UUID.randomUUID().toString();

		/*String ext = extractExt(originalFilename);
		String uuid = UUID.randomUUID().toString();
		return uuid + "." + ext;*/
	}

	/**
	 * 확장자 추출
	 *
	 * @param originalFilename
	 * @return
	 */
	private String extractExt(String originalFilename) {
		int pos = originalFilename.lastIndexOf(".");
		return originalFilename.substring(pos + 1);
	}

	/**
	 * 파일 다수 삭제
	 *
	 * @param filePathList
	 */
	public void removeFiles(List<String> filePathList) {
		for (String path : filePathList) {
			File file = new File(path);
			file.delete();
		}
	}

	/**
	 * 파일 단건 삭제
	 *
	 * @param filePath
	 */
	public void removeFile(String filePath) {
			File file = new File(filePath);
			file.delete();
	}

	/**
	 * 디레곹리에 첨부파일 저장
	 *
	 * @param attachment
	 * @throws IOException
	 */
	public void saveAttachments(Attachment attachment) throws IOException {
		// bytes로 저장
		byte[] bytes = attachment.getBytes();
		FileOutputStream fos = new FileOutputStream(attachment.getFileStreCours());
		fos.write(bytes);

		// 파일로 저장
		/*File newFile = new File(attachment.getFileStreCours()); // 원하는 저장경로의 파일을 생성
		attachment.getFile().renameTo(newFile);

		newFile.createNewFile();    // 경로에 파일 생성*/
	}
}
