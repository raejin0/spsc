package net.miraeit.cmm.service;

import egovframework.rte.fdl.property.EgovPropertyService;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.exception.NoneCodeIdException;
import net.miraeit.cmm.exception.NoneOrganizationKomipoException;
import net.miraeit.cmm.model.CMMCodesGetResponse;
import net.miraeit.cmm.dao.CMMDAO;
import net.miraeit.cmm.model.Letter;
import net.miraeit.cmm.model.OperationPeriod;
import net.miraeit.cmm.model.SimpleCount;
import net.miraeit.cmm.model.equipment.*;
import net.miraeit.cmm.model.law.LawDetailDeleteRequest;
import net.miraeit.cmm.model.law.LawDetailInsertDTO;
import net.miraeit.cmm.model.law.LawDetailPatchRequest;
import net.miraeit.cmm.model.law.LawDetailPostRequest;
import net.miraeit.cmm.model.organization.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CMMService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CMMDAO cmmdao;
	private EgovPropertyService propertiesService;

    public List<CMMCodesGetResponse> getCodes(String request){
        if (cmmdao.checkCodeId(request) == null) throw new NoneCodeIdException();
        return cmmdao.getCodes(request);
    }

    public Map<String, List<CMMCodesGetResponse>> getCodesMap(List<String> codeIds) {
        Map<String, SimpleCount> countMap = cmmdao.checkCodeIds(codeIds).stream()
                .collect(Collectors.toMap(SimpleCount::getName, Function.identity()));
        // 처음부터 String 리스트를 받으면 되긴 하지만...
        for(String codeId : codeIds)
            if(countMap.get(codeId)==null) throw new NoneCodeIdException(codeId);

        Map<String, List<CMMCodesGetResponse>> codeMap = new HashMap<>();
        cmmdao.getCodes(codeIds).forEach(x -> {
            List<CMMCodesGetResponse> belong = codeMap.get(x.getCodeId());
            if(belong==null) codeMap.put(x.getCodeId(), belong = new ArrayList<>());
            belong.add(x);
        });
        return codeMap;
    }

    public OrganizationGetResponse getOrganization(OrganizationGetRequest request){
        OrganizationGetResponse result = cmmdao.getOrganizationKomipo();
        if (result == null) throw new NoneOrganizationKomipoException();

        List<OrganizationGetResponse> orgList = cmmdao.getOrganizationList(request);

        OrganizationDfs((List<OrganizationGetResponse>)(Object)result.getSubOrganization(),orgList,result.getOrgCd());

        result.setOrgList(orgList); // tree구조 아닌 orgList 필요해서 response에 추가함

        return result;
    }

    public List<ManagementGetResponse> getOrganizationManagement(OrganizationGetRequest request){
        return cmmdao.getManagement(request);
    }

    public String patchOrganizationManagement(ManagementPatchRequest request){
        cmmdao.patchOrganizationManagement(request);
        if (request.getMngYn().equals("N")) cmmdao.deleteOrganizationManagementMap(request);
        return null;
    }

    public String patchOrganizationType(OrganizationTypePatchRequest request){
        cmmdao.patchOrganizationType(request);
        if (request.getOrgType() == 1) cmmdao.deleteOrganizationMap(request);
        return null;
    }

    private void OrganizationDfs(List<OrganizationGetResponse> list,List<OrganizationGetResponse> orgList,String parentOrgCd){
        if (orgList == null || orgList.size() ==0) return;
        for (int i = 0; i < orgList.size(); i++){
            if (orgList.get(i).getParentOrgCd().equals(parentOrgCd)) {
                int listIdx = list.size();
                list.add(orgList.get(i));
                OrganizationDfs((List<OrganizationGetResponse>)(Object)list.get(listIdx).getSubOrganization(),orgList,list.get(listIdx).getOrgCd());
            }
        }
//        result.setSubOrganization(cmmdao.getOrganization(result.getOrgCd()));
//        for (Object data : result.getSubOrganization()) OrganizationDfs((OrganizationGetResponse)data);
    }

    public TargetEquipmentPostRequest regTargetEquipment(TargetEquipmentPostRequest targetEquipment) {
        targetEquipment.setOrgLawRelEquipId(cmmdao.saveTargetEquipment(targetEquipment));
        return targetEquipment;
    }

    public int deleteTargetEquipment(TargetEquipmentPostRequest targetEquipment) {
        return cmmdao.deleteTargetEquipment(targetEquipment);
    }

    private List<TargetEquipments> toTargetEquipmentsList(List<TargetEquipment> equipmentList) {
        List<TargetEquipments> equipmentsList = new ArrayList<>();
        Map<String, TargetEquipments> equipmentsMap = new HashMap<>();

        equipmentList.forEach(equip -> {
            String key = String.format("%d-%d-%s", equip.getLawRelId(), equip.getMstLawId(), equip.getOrgCd());
            TargetEquipments equipments = equipmentsMap.get(key);
            if(equipments==null) {
                equipments = TargetEquipments.builder()
                    .statute(
                        TargetStatute.builder()
                            .lawRelId(equip.getLawRelId())
                            .lawRelNm(equip.getLawRelNm())
                            .build()
                    )
                    .department(
                        TargetDepartment.builder()
                            .orgCd(equip.getOrgCd())
                            .orgNm(equip.getOrgNm())
                            .fullOrgNm(equip.getFullOrgNm())
                            .build()
                    )
                    .build();
                equipmentsMap.put(key, equipments);
                equipmentsList.add(equipments);
            }
            equipments.addTargetEquipment(equip);
        });

        return equipmentsList;
    }
    public List<TargetEquipments> getTargetEquipmentsList(TargetEquipmentListGetRequest targetEquipment) {
        List<TargetEquipment> equipmentList = cmmdao.selectTargetEquipmentList(targetEquipment);
        return toTargetEquipmentsList(equipmentList);
    }

    public List<TargetEquipmentsOnStatute> getTargetEquipmentsOnStatuteList(TargetEquipmentOnLawGetRequest request) {
        List<TargetEquipment> equipmentList = cmmdao.selectTargetEquipmentsOnLaws(request);
        List<TargetEquipments> equipmentsList = toTargetEquipmentsList(equipmentList);
        Map<Long, TargetEquipmentsOnStatute> map = new HashMap<>();
        equipmentsList.forEach(x -> {
            TargetStatute statute = x.getStatute();
            TargetEquipmentsOnStatute belong = map.get(statute.getLawRelId());
            if(belong==null) {
                belong = new TargetEquipmentsOnStatute();
                belong.setStatute(statute);
                map.put(statute.getLawRelId(), belong);
            }
            belong.addTargetEquipments(x);
        });
        return new ArrayList<>(map.values());
    }

    public List<TargetEquipment> getEquipmentAll() {
        return cmmdao.selectEquipmentAll();
    }

    public List<String> getUsableYears() {
        return cmmdao.selectUsableYears();
    }

    public List<OperationPeriod> getUsablePeriods() {
        return cmmdao.selectUsablePeriods();
    }

	public void saveRawDetail(LawDetailPostRequest request) {
		LawDetailInsertDTO lawDetailInsertDTO = LawDetailInsertDTO.builder()
				.lawRelAtc(request.getLawRelAtc())
				.lawRelText(request.getLawRelText())
				.lawRelId(request.getLawRelId())
				.build();
		cmmdao.saveRawDetail(lawDetailInsertDTO);

	}

	public void updateRawDetail(LawDetailPatchRequest request) {
		cmmdao.updateRawDetail(request);

//		List<LawMapDTO> lawMapList = cmmdao.findLawImplId(request);

//		if (lawMapList.size() > 0) {
//			cmmdao.updateUsedRawDetail(request);
//			cmmdao.saveRawDetail(request);
//		}else {
//			cmmdao.updateRawDetail(request);
//		}

	}

	public void deleteLawDetail(LawDetailDeleteRequest request) {
		cmmdao.deleteLawDetail(request);
	}



    public void sendMail(Letter letter) throws MessagingException {
		logger.info("[START] sendMail");

		try{
			letter.setFrom(propertiesService.getString("mailFrom"));
	//		Session session = getDaumMailSession();

			// 다음메일 - 내부테스트용
			if(propertiesService.getString("mailTestOpt").equals("daum")) {
				String host = propertiesService.getString("siteUrl");
				String port = propertiesService.getString("mailPort");
				String username = propertiesService.getString("mailUsername");
				String password = propertiesService.getString("mailPassword");
				Properties props = new Properties();
				Authenticator authenticator = null;
				if(username != null && password != null){
					authenticator = new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					};
				}
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.port", port);
				props.put("mail.smtp.ssl.enable", "true");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.ssl.trust", host);
				props.put("mail.smtp.ssl.protocols", "TLSv1.2");

				Session session = Session.getDefaultInstance(props,authenticator );

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(letter.getFrom()));
				InternetAddress[] toAddr = new InternetAddress[letter.getToArr().size()];
				for(int i = 0 ; i < letter.getToArr().size() ; i++) {
					toAddr[i] = new InternetAddress(letter.getToArr().get(i));
				}

				// 주소목록 처리 수정
//				List<InternetAddress> adrssList = new ArrayList<>();
//				for(int i = 0 ; i < letter.getToArr().size() ; i++) {
//					if( !StringUtils.isEmpty(letter.getToArr().get(i)) ){
//						adrssList.add(new InternetAddress(letter.getToArr().get(i)));
//					}
//				}
//				Address[] adressArr = (Address[]) adrssList.toArray();

				message.setRecipients(Message.RecipientType.TO, toAddr);
				message.setSubject(letter.getTitle());
				message.setContent(letter.getContent(), "text/html;charset=UTF-8");
				Transport.send(message);
			}
			// 중부발전 운영용
			if(propertiesService.getString("mailTestOpt").equals("jb")) {
				String hostName = propertiesService.getString("mail.jb.host");
				int port = propertiesService.getInt("mail.jb.port");

				Properties props = new Properties();
				props.put("mail.transport.protocol", "smtp");
				props.put("mail.smtp.starttls.enable","false");
				props.put("mail.smtp.host", hostName);
				props.put("mail.smtp.port", port);

				logger.info("[Mail] host : " + hostName + " | port : " + port);

				Session session = Session.getDefaultInstance(props);

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(letter.getFrom()));

				logger.info("letter Count - {}", letter.getToArr().size() );
				for(int i = 0 ; i < letter.getToArr().size() ; i++) {
					logger.info("{} - {}", i+1, letter.getToArr().get(i));
//					if(StringUtils.isEmpty(letter.getToArr().get(i))){
//						letter.getToArr().remove(i);
//					}
				}

				InternetAddress[] toAddr = new InternetAddress[letter.getToArr().size()];
				for(int i = 0 ; i < letter.getToArr().size() ; i++) {
					toAddr[i] = new InternetAddress(letter.getToArr().get(i));
					logger.info("to : " + letter.getToArr().get(i));
				}

				// 주소목록 처리 수정 -ERRor
//				List<InternetAddress> adrssList = new ArrayList<>();
//				for(int i = 0 ; i < letter.getToArr().size() ; i++) {
//					if( !StringUtils.isEmpty(letter.getToArr().get(i)) ){
//						adrssList.add(new InternetAddress(letter.getToArr().get(i)));
//					}
//				}
//				InternetAddress[] adressArr = (InternetAddress[]) adrssList.toArray();

				message.setRecipients(Message.RecipientType.TO, toAddr );
//				한글깨짐 수정
//				message.setSubject(letter.getTitle());
				message.setSubject(MimeUtility.encodeText(letter.getTitle(), "UTF-8", "B") );
				message.setContent(letter.getContent(), "text/html;charset=UTF-8");

				Transport.send(message);

			}
		} catch (MessagingException e){
			logger.error("Err\n{}", e);
		} catch (Exception e){
			logger.error("Err\n{}", e);
		}

		logger.info("[END] sendMail");

/*		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(letter.getFrom()));

//		InternetAddress[] mailTo = { new InternetAddress(letter.getTo()) };
//		message.setRecipients(Message.RecipientType.TO, mailTo);
		InternetAddress[] toAddr = new InternetAddress[letter.getToArr().size()];
		for(int i = 0 ; i < letter.getToArr().size() ; i++) {
			toAddr[i] = new InternetAddress(letter.getToArr().get(i));
		}

		message.setRecipients(Message.RecipientType.TO, toAddr);

		message.setSubject(letter.getTitle());
		message.setContent(letter.getContent(), "text/html;charset=UTF-8");
		Transport.send(message);*/
	}
}
