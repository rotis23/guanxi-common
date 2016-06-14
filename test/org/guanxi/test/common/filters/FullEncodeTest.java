package org.guanxi.test.common.filters;

import java.net.URLEncoder;

import org.guanxi.common.Utils;
import org.junit.Test;

public class FullEncodeTest {

	private static String authnRequestDoc = "<samlp:AuthnRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" AssertionConsumerServiceURL=\"https://auth.elsevier.com/SHIRE/SAML2/POST\" Destination=\"https://iss-openathensla-runtime.swan.ac.uk/oala/sso\" ID=\"_3207a289a69daeaba1957478851fb2e3\" IssueInstant=\"2016-06-14T15:48:04Z\" ProtocolBinding=\"urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST\" Version=\"2.0\"><saml:Issuer xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">https://sdauth.sciencedirect.com/</saml:Issuer><samlp:NameIDPolicy AllowCreate=\"1\"/></samlp:AuthnRequest>";

	@Test
	public void fullEncode() throws Exception {
		String authnRequestForIdP = authnRequestDoc;
		authnRequestForIdP = Utils.deflateBase64(authnRequestForIdP, Utils.RFC1951_DEFAULT_COMPRESSION_LEVEL, Utils.RFC1951_NO_WRAP);
		// authnRequestForIdP = authnRequestForIdP.replaceAll(System.getProperty("line.separator"),
		authnRequestForIdP = URLEncoder.encode(authnRequestForIdP, "UTF-8");

		System.out.println(authnRequestForIdP);
	}
	
}
