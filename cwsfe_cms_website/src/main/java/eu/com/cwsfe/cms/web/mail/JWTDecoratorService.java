package eu.com.cwsfe.cms.web.mail;

import eu.com.cwsfe.cms.dao.KeystoresDAO;
import eu.com.cwsfe.cms.model.Keystore;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;


/**
 * @author Radoslaw Osinski
 */
@Service
public class JWTDecoratorService {

    @Resource
    private Environment environment;

    @Resource
    private KeystoresDAO keystoresDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTDecoratorService.class);

    SendEmailRequest getSendEmailRequest(String requestJWT) {
        try {
            Key key = getFrontendWebsitePublicKey();
            final Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(requestJWT);
            final Claims body = claimsJws.getBody();
            LOGGER.info("Request JWT " + body.toString());
            return new SendEmailRequest(
                (String) body.get("replayToEmail"),
                (String) body.get("emailText")
            );
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            LOGGER.error("Problem with send email JWT: ", e);
        }
        return new SendEmailRequest("", "");
    }

    /**
     * @return key for reading JWT
     */
    private Key getFrontendWebsitePublicKey() {
        try {
            KeyStore keystore = KeyStore.getInstance("JKS");
            Keystore frontendApplicationKeystore = keystoresDAO.getByName("frontendApplicationKeystore");
            keystore.load(frontendApplicationKeystore.getContent(), environment.getRequiredProperty("keystore.password").toCharArray());
            final Certificate frontendWebsiteCertificate = keystore.getCertificate(environment.getRequiredProperty("keystore.frontendWebsiteCertificate"));
            return frontendWebsiteCertificate.getPublicKey();
        } catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException e) {
            LOGGER.error("Failed to retrieve frontend website key from domain system keystore.");
            return null;
        }
    }

}
