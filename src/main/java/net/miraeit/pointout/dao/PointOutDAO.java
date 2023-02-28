package net.miraeit.pointout.dao;

import net.miraeit.cmm.dao.BaseDAO;
import net.miraeit.cmm.model.OperationPeriod;
import net.miraeit.fx.listing.model.PagedList;
import net.miraeit.pointout.model.*;
import net.miraeit.pointout.model.excel.PointOutExcelDownloadRequest;
import net.miraeit.pointout.model.request.*;
import net.miraeit.scheduling.model.mail.ImplItemStates;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PointOutDAO extends BaseDAO {
	public PagedList<PointOutSummary> selectPointOutSummaryPagedList(PointOutSummaryRequest request) {
		return selectPagedList(
			"spsc.pointout.select.pointout.summary.count",
			"spsc.pointout.select.pointout.summary.list",
			request
		);
	}

	public PointOutSummary selectPointOutSummary(int advId) {
		return selectOne("spsc.pointout.select.pointout.summary.by.id", advId);
	}

	public int selectPointOtSummaryCountByAdvNo(String advNo) {
		return selectOne("spsc.pointout.select.pointout.summary.count.by.adv.no", advNo);
	}

	public int insertPointOutSummary(PointOutSummaryRegRequest request) {
		return (int)insert("spsc.pointout.insert.pointout.summary", request);
	}

	public int updatePointOutSummary(PointOutSummaryModRequest request) {
		return update("spsc.pointout.update.pointout.summary", request);
	}

	public int updatePointOutSummarySTS(PointOutSummary summary) {
		return update("spsc.pointout.update.pointout.summary.sts", summary);
	}

	public int deletePointOutSummary(PointOutSummaryModRequest request) {
		return delete("spsc.pointout.delete.pointout.summary", request);
	}

	public PagedList<PointOutDetail> selectPointOutDetailPagedList(PointOutDetailRequest request) {
		return selectPagedList(
			"spsc.pointout.select.pointout.detail.count",
			"spsc.pointout.select.pointout.detail.list",
			request
		);
	}

	public PointOutDetail selectPointOutDetail(int advDtlId) {
		return selectOne("spsc.pointout.select.pointout.detail.by.id", advDtlId);
	}

	public int insertPointOutDetail(PointOutDetailRegRequest request) {
		return (int)insert("spsc.pointout.insert.pointout.detail", request);
	}

	public int updatePointOutDetail(PointOutDetailModRequest request) {
		return update("spsc.pointout.update.pointout.detail", request);
	}

	public int deletePointOutDetailById(Integer advDtlId) {
		return delete("spsc.pointout.delete.pointout.detail.by.id", advDtlId);
	}

	public int deletePointOutDetailByAdvId(int advId) {
		return delete("spsc.pointout.delete.pointout.detail.by.adv.id", advId);
	}

	public List<PointOutSTS> selectPointOutSTS(OperationPeriod period) {
		return selectList("spsc.pointout.select.pointout.sts", period);
	}

	public PointOutExcelSummary findPointOutSummary(String advNo) {
		return (PointOutExcelSummary) select("spsc.point-out.select.summary", advNo);
	}

	// 지적/조치사항 총괄 데이터 삽입
	public Integer insertPointOutSummary(PointOutExcelSummary summary) {
		return (Integer) insert("spsc.point-out.xls.insert.summary", summary);
	}

	// 지적/조치사항 총괄 데이터 갱신
	public void updatePointOutSummary(PointOutExcelSummary summary) {
		update("spsc.point-out.xls.update.summary", summary);
	}

	public void insertPointOutDetail(PointOutExcelDetail detail) {
		insert("spsc.point-out.xls.insert.detail", detail);
	}

	public List<PointOutDetail> findPointOutDetailListByAdvId(Integer advId) {
		return (List<PointOutDetail>) list("spsc.point-out.xls.selet.detail.list.by.adv.id", advId);
	}

	public Integer findMstLawId(PointerOutExcelUploadRequest request) {
		return (Integer) select("spsc.point-out.select.mst.law.id", request);
	}

	public List<PointOutSummary> findPointOutSummaryList(PointOutExcelDownloadRequest pointOutExcelDownloadRequest) {
		return (List<PointOutSummary>) list("spsc.point-out.select.summary.list", pointOutExcelDownloadRequest);
	}

	// 이행상태별 건수 조회
	public ImplItemStates getImplItemStates() {
		return (ImplItemStates) select("spsc.point-out.select.impl.item.states");
	}
}
