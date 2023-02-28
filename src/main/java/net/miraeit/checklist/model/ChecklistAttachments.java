package net.miraeit.checklist.model;

import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.model.file.FileDetailDTO;
import net.miraeit.cmm.model.file.FileInformation;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChecklistAttachments {
    List<FileInformation> implFiles = new ArrayList<>();
    List<FileInformation> implLinks = new ArrayList<>();
    List<FileInformation> pointedFiles = new ArrayList<>();
    List<FileInformation> pointedLinks = new ArrayList<>();

    private FileInformation toFileInformation(FileDetailDTO fileDetailDTO) {
        FileInformation fileInfo = new FileInformation();
        fileInfo.setAtchFileId(fileDetailDTO.getAtchFileId());
        fileInfo.setFileSn(fileDetailDTO.getFileSn());
        fileInfo.setName(fileDetailDTO.getOrignlFileNm());
        fileInfo.setUrl(fileDetailDTO.getStreFileNm());
        fileInfo.setAtchTy(fileDetailDTO.getAtchTy());
        return fileInfo;
    }

    public void addImplAttachment(FileDetailDTO fileDetailDTO) {
        FileInformation fileInfo = toFileInformation(fileDetailDTO);
        if("F".equals(fileInfo.getAtchTy())) {
            implFiles.add(fileInfo);
        }
        if("L".equals(fileInfo.getAtchTy())) {
            implLinks.add(fileInfo);
        }
    }
    public void addPointedAttachment(FileDetailDTO fileDetailDTO) {
        FileInformation fileInfo = toFileInformation(fileDetailDTO);
        if("F".equals(fileInfo.getAtchTy())) {
            pointedFiles.add(fileInfo);
        }
        if("L".equals(fileInfo.getAtchTy())) {
            pointedLinks.add(fileInfo);
        }
    }
}
