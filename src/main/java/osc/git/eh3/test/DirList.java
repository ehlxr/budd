package osc.git.eh3.test;

import java.io.File;
import java.io.FilenameFilter;

public class DirList {
	/**
	 * @param args
	 *            The file pattern that need to be matched.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File path = new File("D://");
		String arg = "dsp_impclk_15";
		String[] list;
		if (arg.length() == 0)
			list = path.list();
		else
			list = path.list(new DirFilter(arg));
		for (int i = 0; i < list.length; ++i) {
			System.out.println(list[i]);
		}
	}
}

class DirFilter implements FilenameFilter {
	String afn;

	DirFilter(String afn) {
		this.afn = afn;
	}

	public boolean accept(File dir, String name) {
		// Strip path information.
		String f = new File(name).getName();
		return f.indexOf(afn) != -1;
	}
}