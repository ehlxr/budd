package me.ehlxr.readlogs;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IReadLogs {
	public String readFile(Date startDate, Date endDate, List<String> mapids, String groupid,Map<String,Double> adxs);
}