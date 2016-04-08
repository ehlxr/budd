package osc.git.eh3.test;

import com.dxpmedia.hdtsspapitest.CreativeService;
import com.dxpmedia.hdtsspapitest.CreativeServiceSoap;

public class TestWS {

	public static void main(String[] args) {
		CreativeService service = new CreativeService();
		CreativeServiceSoap serviceSoap = service.getCreativeServiceSoap();
		String result = serviceSoap.creativeSubmit("0", "1",2);
		System.out.println(result);
	}
}
