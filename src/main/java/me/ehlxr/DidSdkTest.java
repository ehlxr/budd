package me.ehlxr;
//
// import cn.ceres.did.client.SdkClient;
// import cn.ceres.did.sdk.SdkProto;
//
// import java.util.concurrent.CountDownLatch;
//
// /**
//  * @author ehlxr
//  */
// public class DidSdkTest {
//
//     public static void main(String[] args) throws Exception {
//         SdkClient client = new SdkClient("10.19.248.200", 30581);
//         // SdkClient client = new SdkClient();
//         client.init();
//         client.start();
//
//         // client.invokeOneWay(new SdkProto(), 2000);
//         // System.out.println(client.invokeSync(new SdkProto(), 2000).getDid());
//         CountDownLatch countDownLatch = new CountDownLatch(1);
//         client.invokeAsync(new SdkProto(), 2000, responseFuture -> {
//             System.out.println(responseFuture.getSdkProto().getDid());
//             countDownLatch.countDown();
//         });
//         countDownLatch.await();
//         client.shutdown();
//     }
// }
