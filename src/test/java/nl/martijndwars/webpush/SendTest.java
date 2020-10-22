package nl.martijndwars.webpush;


import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

public class SendTest {

//./gradlew run --args='send-notification --endpoint="https://fcm.googleapis.com/fcm/send/dKgFcmb3UzQ:APA91bEXS0V8Lc8CQQ-gvie2Ji4jsBEeWjSafJ8i1clSczr7hYdLHh6dpSf3UTWjXZyK9epjOXMN3zs_ZoBjkm6EOHDPqHoGIeBP_kKcZcDf_S2I-2ZuQQlpdgQiav6a-wehErWmmlxq" --key="BPHniLhgMolkEDtu2j5bbPYOt9cB65vQSM2MYK0SgDHjNBvIrAOlPcj2lxKxul_lEYQFekPs-Dq8zVAe8nG2VF4" --auth="BGMPkIihr-w3JniGvEokaA" --publicKey="BMszYyueEqvNm8ZWFM9jjwP7lsZoOFPCSvjWrJz6mfoeI_fvcqmzRzodju-rkR7nCbwnszfkE552lQrCGrQoEJc" --privateKey="BGMPkIihr-w3JniGvEokaA" --payload="Hello world"'

    String publicKey = "BMszYyueEqvNm8ZWFM9jjwP7lsZoOFPCSvjWrJz6mfoeI_fvcqmzRzodju-rkR7nCbwnszfkE552lQrCGrQoEJc";
    String privateKey = "LH2NrprqFIwoYRqpDcmARQ-Slj2oGz6fTqixdshUFBs";
    @Test
    public void sendToChrome() throws GeneralSecurityException, InterruptedException, JoseException, ExecutionException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        PushService pushService = new PushService()
                .setPublicKey(publicKey)
                .setPrivateKey(privateKey)
                .setSubject("mailto:admin@domain.com");

        String endpoint = "https://fcm.googleapis.com/fcm/send/dKgFcmb3UzQ:APA91bEXS0V8Lc8CQQ-gvie2Ji4jsBEeWjSafJ8i1clSczr7hYdLHh6dpSf3UTWjXZyK9epjOXMN3zs_ZoBjkm6EOHDPqHoGIeBP_kKcZcDf_S2I-2ZuQQlpdgQiav6a-wehErWmmlxq";
        String p256dh = "BPHniLhgMolkEDtu2j5bbPYOt9cB65vQSM2MYK0SgDHjNBvIrAOlPcj2lxKxul_lEYQFekPs-Dq8zVAe8nG2VF4";
        String auth = "BGMPkIihr-w3JniGvEokaA";
        String payload = "{\"title\":\"title1\",\"body\":\"body1\",\"icon\":\"/html/app-manifest/logo_512.png\",\"data\":{\"url\":\"https://www.baidu.com\"}}";
//        Notification notification = new Notification(subscription, payload);
        Notification notification = Notification.builder()
                .endpoint(endpoint)
//                .topic("tp1")
                .payload(payload)
                .ttl(0)
                .userPublicKey(p256dh)
                .userAuth(auth).build();
        HttpResponse response = pushService.send(notification);
        System.out.println(response);
    }

    @Test
    public void sendToFireFox() throws GeneralSecurityException, InterruptedException, JoseException, ExecutionException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        PushService pushService = new PushService()
                .setPublicKey(publicKey)
                .setPrivateKey(privateKey)
                .setSubject("mailto:admin@domain.com");
        String endpoint = "https://updates.push.services.mozilla.com/wpush/v2/gAAAAABfkEksWL-s0ZfUN4NkFNoq70fI9Tavi9zd064X2VD8H348MCltNRArkTMGTG2XNLst9FqJloWE0BWz-LQcb81MNUO0r1CIyI3O2LUSXFpIOVu494oerlh2aKVQ4SGtkva2LZjkIPHhy2AuDcUJPUQxULvYGRJ8eSiVcfwjJ--mn-QCLSc";
        String p256dh = "BHFgeRVxre_3oLkPNEWcbHJL-dnOf4mLo76sglkkolco8zpiBRdn14qZaw-TDB4Ic2kIagoBOGFoYK9ezc9rTu4";
        String auth = "MZix4Iw14P5gQK1sBNvhAQ";
        Subscription subscription = new Subscription(endpoint, new Subscription.Keys(p256dh, auth));
        String payload = "{\"title\":\"title1\",\"body\":\"body1\",\"icon\":\"/html/app-manifest/logo_512.png\",\"data\":{\"url\":\"https://www.baidu.com\"}}";
        Notification notification = new Notification(subscription, payload);
        HttpResponse response = pushService.send(notification);
        System.out.println(response);
    }
}
