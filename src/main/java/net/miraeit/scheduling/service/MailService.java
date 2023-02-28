package net.miraeit.scheduling.service;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.property.EgovPropertyService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.miraeit.pointout.dao.PointOutDAO;
import net.miraeit.scheduling.dao.SchedulingDAO;
import net.miraeit.cmm.exception.NullMailSettingException;
import net.miraeit.scheduling.model.link.OrganizationToFulfill;
import net.miraeit.scheduling.model.link.PersonnelToFulfill;
import net.miraeit.scheduling.model.mail.ImplItemStates;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

	private final EgovPropertyService propertiesService;
	private final EgovMessageSource msgSrc;
	private final SchedulingDAO schedulingDAO;
	private final PointOutDAO pointOutDAO;

	public void sendMail() {
		log.info("[START] MailService.sendMail");

		String sort = propertiesService.getString("mail.sort");

		try {
			MailSettings mailSettings = null;

			// mail setting fork: 메일 설정 분기
			if (sort.equals("local"))   mailSettings = getLocalTestMailSettings(); // 로컬 테스트
			if (sort.equals("jb"))      mailSettings = getJbMailSettings();        // 중부발전

			// 이행상태별 건수 조회
			ImplItemStates implItemStates = pointOutDAO.getImplItemStates();

			// 제목 및 내용
			String subject = "CPnet 체크리스트 이행 상태 알림";
			String content = "1.이행 건수 표시<br/>" +
								" - 이행 : " + implItemStates.getV() + "건<br/>" +
								" - 불이행 : " + implItemStates.getX() + "건<br/>" +
								" - 해당없음 : " + implItemStates.getN() + "건<br/><br/>" +

							"2.이행 상태 확인 및 조치 방법<br/>" +
								" - CPnet 접속하여 이행관리 대시보드에서<br/>" +
								" - 실적입력 버튼을 통해 해당 부서로 이동해서 체크리스트 확인하여, 조치 요망.<br/><br/>" +
								" 본 메일은 CPnet 체크리스트 상태를 확인하여, 취합되는 메일입니다.<br/>" +
								" 발송 시점은 반기별 종료일 2주전에 발송됩니다.";

			if ( mailSettings == null) throw new NullMailSettingException(msgSrc.getMessage("EM000")); // exception handling

			// 메세지 객체 생성 및 설정
			MimeMessage msg = new MimeMessage(mailSettings.getSession());                  // 메시지 객체 생성
			msg.setFrom(new InternetAddress(mailSettings.getMailFrom()));                  // 발신자(MessagingException 발생)
			msg.setRecipients(Message.RecipientType.TO, mailSettings.getToList());         // 수신자 목록 설정
			msg.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B") ); // 제목 설정 및 인코딩
			msg.setContent(content, "text/html;charset=UTF-8");                       // 내용 설정 및 인코딩
			Transport.send(msg);                                                           // 전송
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException:", e);
		} catch (AddressException e) {
			log.error("AddressException:", e);
		} catch (MessagingException e) {
			log.error("MessagingException:", e);
		}

		log.info("[END] MailService.sendMail");
	}

	/**
	 * 메일 설정_로컬 개발용
	 *
	 * @return
	 * @throws AddressException
	 */
	private MailSettings getLocalTestMailSettings() throws AddressException {
			log.info("MailService.getLocalTestMailSettings");
			// 편의 변수 정의
			String host     = propertiesService.getString("mail.local.host");
			String mailFrom = propertiesService.getString("mail.local.from");
			String port     = propertiesService.getString("mail.local.port");
			String password = propertiesService.getString("mail.local.password");

			// properties 설정
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.starttls.enable", "true"); // ssl
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.trust", host);
			log.info("[Mail] host : " + host + " | port : " + port);

			// Authenticator 객체 생성 (관리자 메일주소와 비밀번호 사용)
			Authenticator authenticator = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailFrom, password);
				}
			};

			Session session = Session.getInstance(props, authenticator);    // 로컬 테스트

			// 수신자 목록 설정 - 로컬 테스트
			InternetAddress[] toList = new InternetAddress[2];
			toList[0] = new InternetAddress("raejin0@gmail.com");
			toList[1] = new InternetAddress("raejin0@naver.com");

			return MailSettings.builder()
					.mailFrom(mailFrom)
					.session(session)
					.toList(toList)
					.build();
	}

	/**
	 * 메일 설정_중부발전
	 *
	 * @return
	 * @throws AddressException
	 */
	private MailSettings getJbMailSettings() throws AddressException {
		log.info("MailService.getJbMailSettings");
		// 편의 변수 정의
		String jbSort     = propertiesService.getString("mail.jb.sort");
		String host     = propertiesService.getString("mail.jb.host");
		String mailFrom = propertiesService.getString("mail.jb.from");
		String port     = propertiesService.getString("mail.jb.port");

		// properties 설정
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.starttls.enable", "false"); // tls
		log.info("[Mail] host : " + host + " | port : " + port);

		Session session = Session.getDefaultInstance(props);   // 로컬 테스트

		MailSettings mailSettings = MailSettings.builder()
			.mailFrom(mailFrom)
			.session(session)
			.build();

		// toList fork: 메일 수신자 분기
		if (jbSort.equals("test"))   mailSettings.setToList(getJbTestToList()); // 중부발전 테스트 ( 김태연 대리 메일 )
		if (jbSort.equals("real"))   mailSettings.setToList(getJbRealToList()); // 중부발전 운영  ( 이행 담당자 메일 리스트 )

		return mailSettings;
	}

	/**
	 * 중부발전 테스트용_김태연 대리 이메일
	 *
	 * @return
	 * @throws AddressException
	 */
	private InternetAddress[] getJbTestToList() throws AddressException {
		log.info("MailService.getJbTestToList");
		InternetAddress[] toList = new InternetAddress[2];
		toList[0] = new InternetAddress(propertiesService.getString("mail.jb.to.test1"));
		toList[1] = new InternetAddress(propertiesService.getString("mail.jb.to.test2"));
		return toList;
		
	}

	/**
	 * 중부발전 운영용_ 미이행 수신자 목록 조회
	 *
	 * @return
	 * @throws AddressException
	 */
	private InternetAddress[] getJbRealToList() throws AddressException {
		log.info("MailService.getJbRealToList");
		// 이행 담당자 조회
		List<OrganizationToFulfill> orgList = schedulingDAO.findOrganizationToFulfill();
		List<PersonnelToFulfill> personnelList = schedulingDAO.findPersonnelToFulfill(orgList);
		log.info("toList Count - {}", personnelList.size() );

		// 수신자 목록 설정 - 중부발전
		InternetAddress[] toList = new InternetAddress[personnelList.size()];
		for (int i = 0; i < personnelList.size(); i++) {
			String emailTo = personnelList.get(i).getEmail();
			toList[i] = new InternetAddress(emailTo);
			log.info("to " + i + " : " + emailTo );
		}
		return toList;
	}
}

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class MailSettings {
	private Session session;
	private InternetAddress[] toList;
	private String mailFrom;
}
