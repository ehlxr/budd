package osc.git.eh3.readlogs;

import java.util.Date;
import java.util.List;

public interface IReadLogs {
	public String readFile(Date startDate, Date endDate, List<String> mapids);
}