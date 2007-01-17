//: "The contents of this file are subject to the Mozilla Public License
//: Version 1.1 (the "License"); you may not use this file except in
//: compliance with the License. You may obtain a copy of the License at
//: http://www.mozilla.org/MPL/
//:
//: Software distributed under the License is distributed on an "AS IS"
//: basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
//: License for the specific language governing rights and limitations
//: under the License.
//:
//: The Original Code is Guanxi (http://www.guanxi.uhi.ac.uk).
//:
//: The Initial Developer of the Original Code is Alistair Young alistair@smo.uhi.ac.uk.
//: Portions created by SMO WWW Development Group are Copyright (C) 2005 SMO WWW Development Group.
//: All Rights Reserved.
//:
/* CVS Header
   $Id$
   $Log$
   Revision 1.4  2007/01/17 13:09:28  alistairskye
   Updated SAMLP_SUCCESS to solve namespace problems in IdP.

   Revision 1.3  2006/11/24 11:41:30  alistairskye
   Added more namespace and prefix definitions

   Revision 1.2  2005/07/11 10:51:34  alistairskye
   Package restructure

   Revision 1.1  2005/05/04 13:28:14  alistairskye
   Moved here from org.Guanxi.SAMUEL.Definitions

   Revision 1.2  2005/04/15 11:58:35  alistairskye
   no message

*/

package org.guanxi.common.definitions;

/**
 * <font size=5><b></b></font>
 *
 * @author Alistair Young alistair@smo.uhi.ac.uk
 */
public class Shibboleth {
  // Initial GET request parameters
  public static final String SHIRE = "shire";
  public static final String TARGET = "target";
  public static final String TIME = "time";
  public static final String PROVIDER_ID = "providerId";

  // Namespaces
  public static final String NS_NAME_IDENTIFIER = "urn:mace:shibboleth:1.0:nameIdentifier";
  public static final String NS_ATTRIBUTES = "urn:mace:shibboleth:1.0:attributeNamespace:uri";
  public static final String NS_SAML_10_PROTOCOL = "urn:oasis:names:tc:SAML:1.0:protocol";
  public static final String NS_SAML_10_ASSERTION = "urn:oasis:names:tc:SAML:1.0:assertion";
  public static final String NS_PREFIX_SAML_10_PROTOCOL = "samlp";
  public static final String NS_PREFIX_SAML_10_ASSERTION = "saml";

  /** SAML Status value for success */
  public static final String SAMLP_SUCCESS = "Success";
  /** SAML Status value for success */
  public static final String SAMLP_ERROR = "samlp:Error";
}
