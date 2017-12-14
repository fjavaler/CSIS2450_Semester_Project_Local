package gui;

public enum TIMESOFDAY {
	MORNING("06:00:00", "11:59:59"), AFTERNOON("12:00:00", "17:59:59"), EVENING("18:00:00",
			"23:59:59"), NIGHT("00:00:00", "05:59:00");

	private String startime;
	private String endtime;

	TIMESOFDAY(String beg, String end) {
		this.startime = beg;
		this.endtime = end;
	}

	public String getStartTime() {
		return startime;
	}

	public String getEndTime() {
		return endtime;
	}
}
