package common.test;
import static org.junit.Assert.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;


public class TestCommon {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws URISyntaxException {
		
		URI uri = new URI("file:/E:/Temp");
		
		File file = new File(uri);
		System.out.println(" filePtah = "  + file.getAbsolutePath());
		if ( !file.isDirectory())
			fail("Error URI for file :" + uri);
	}

}
