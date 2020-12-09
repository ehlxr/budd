package io.github.ehlxr.viplugin;

public class CreateLicense {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		CheckLicenseFile licenseFile = new CheckLicenseFile();
		String valueString = licenseFile.encrypt("elvin_lee", "elvin_lee@126.com");
		System.out.println("viPlugin2.lic:" + valueString);
	}
}