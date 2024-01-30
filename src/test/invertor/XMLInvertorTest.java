package test.invertor;


import org.junit.jupiter.api.Test;

import invertor.XMLInvertor;

class XMLInvertorTest {

	@Test
	void testMain() throws Exception {
		
		XMLInvertor.main(new String[] { "src/test/resources/inversion_example.xml" });

	}

}
