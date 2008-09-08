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
//: The Initial Developer of the Original Code is Alistair Young alistair@codebrane.com
//: All Rights Reserved.
//:

package org.guanxi.common.security.ssl;

import javax.net.ssl.*;

import org.guanxi.common.GuanxiException;

import java.security.KeyStore;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Defines trust and keystore managers for HTTPS connections.
 *
 * @author Alistair Young alistair@smo.uhi.ac.uk
 */
public class SSL {
  /**
   * Returns an array of TrustManager objects that the JVM will use to verify or authenticate a remote
   * server when using HTTPS.
   *
   * @param trustStore The full path and name of the truststore to use for server verification.
   * Can be null if probeForServerCert is true.
   * @param password The password for the truststore.
   * Can be null if probeForServerCert is true.
   * @param probeForServerCert If this is true, then an instance of GuanxiX509ProbingTrustManager will
   * be returned. This is to allow probing servers to extract their certificates.
   * @return An array of TrustManage objects that can be used by the JVM to verify an HTTPS connection
   *  or null if an error occurred.
   * @throws GuanxiException 
   */
  public static TrustManager[] getTrustManagers(String trustStore, String password, boolean probeForServerCert) throws GuanxiException {
    String algorithm;
    TrustManagerFactory trustManagerFactory;
    KeyStore keystore;
    InputStream in;
    
    try {
      // If we're just after the server's certificate, delegate trust checking to Guanxi
      if (probeForServerCert) {
        return new TrustManager[]{new GuanxiX509ProbingTrustManager()};
      }

      // First, get the default TrustManagerFactory.
      algorithm           = TrustManagerFactory.getDefaultAlgorithm();
      trustManagerFactory = TrustManagerFactory.getInstance(algorithm);

      // Next, set up the TrustStore to use. We need to load the file into
      // a KeyStore instance.
      in = new FileInputStream(trustStore);
      try {
        keystore = KeyStore.getInstance("jks");
        keystore.load(in, password.toCharArray());
      }
      finally {
        in.close();
      }

      // Now we initialise the TrustManagerFactory with this KeyStore
      trustManagerFactory.init(keystore);

      // And now get the TrustManagers
      return trustManagerFactory.getTrustManagers();
    }
    catch(Exception e) {
      throw new GuanxiException(e); // don't eat exceptions
    }
  }
  
  /**
   * This creates a list of TrustManagers from a given truststore object. This allows in memory
   * management of the truststore, which works well with the in memory management of the metadata.
   * 
   * @param truststore
   * @param probeForServerCert
   * @return
   * @throws GuanxiException
   */
  public static TrustManager[] getTrustManagers(KeyStore truststore, boolean probeForServerCert) throws GuanxiException {
    String algorithm;
    TrustManagerFactory trustManagerFactory;
    
    try {
      // If we're just after the server's certificate, delegate trust checking to Guanxi
      if (probeForServerCert) {
        return new TrustManager[]{new GuanxiX509ProbingTrustManager()};
      }

      // First, get the default TrustManagerFactory.
      algorithm           = TrustManagerFactory.getDefaultAlgorithm();
      trustManagerFactory = TrustManagerFactory.getInstance(algorithm);

      // Now we initialise the TrustManagerFactory with this KeyStore
      trustManagerFactory.init(truststore);

      // And now get the TrustManagers
      return trustManagerFactory.getTrustManagers();
    }
    catch(Exception e) {
      throw new GuanxiException(e); // don't eat exceptions
    }
  }

  /**
   * Returns an array of GuanxiX509KeyManager objects that can be used to added certificates to an HTTPS connection.
   * The Engine uses this method to load a keystore belonging to a Guard, in order to add the Guard's
   * certificate to the HTTPS request to, for example, an AttributeAuthority.
   * Note that only X509KeyManager instances are supported.
   *
   * @param guardID The ID of the Guard who's keystore must be loaded. This is also the alias within the keystore
   * under which the Guard's certificate is stored. It's this certificate that's added to the HTTPS connection.
   * @param keystoreFile The full path and name of the keystore to load.
   * @param password The password for the keystore.
   * @return An array of GuanxiX509KeyManager objects or null if an error occurred.
   * @throws GuanxiException 
   */
  public static KeyManager[] getKeyManagers(String guardID, String keystoreFile, String password) throws GuanxiException {
    String algorithm;
    KeyManagerFactory keyManagerFactory;
    InputStream in;
    KeyStore keystore;
    KeyManager[] keyManagers;
    
    try {
      algorithm = KeyManagerFactory.getDefaultAlgorithm();
      keyManagerFactory = KeyManagerFactory.getInstance(algorithm);

      in = new FileInputStream(keystoreFile);
      try {
        keystore = KeyStore.getInstance("jks");
        keystore.load(in, password.toCharArray());
      }
      finally {
        in.close();
      }

      keyManagerFactory.init(keystore, password.toCharArray());

      keyManagers = keyManagerFactory.getKeyManagers();

      for (int c=0; c < keyManagers.length; c++) {
        if (keyManagers[c] instanceof X509KeyManager) {
          keyManagers[c]= new GuanxiX509KeyManager((X509KeyManager)keyManagers[c], guardID);
        }
      }

      return keyManagers;
    }
    catch(Exception e) {
      throw new GuanxiException(e); // don't eat exceptions
    }
  }
}
