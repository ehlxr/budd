package io.github.ehlxr.readlogs;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IReadLogs {
	String readFile(Date startDate, Date endDate, List<String> mapids, String groupid, Map<String, Double> adxs);
}