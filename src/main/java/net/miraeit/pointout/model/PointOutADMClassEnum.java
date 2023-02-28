package net.miraeit.pointout.model;

public enum PointOutADMClassEnum {
	사법조치("ADM001"),
	사용중지("ADM002"),
	시정명령("ADM003"),
	시정지시("ADM004"),
	권고사항("ADM005"),
	기타사항("ADM006");

	private final String code;
	private PointOutADMClassEnum(String code) {
		this.code = code;
	}

	public boolean equalsCode(String code) {
		return this.code.equals(code);
	}

	public String toString() {
		return code;
	}
}
