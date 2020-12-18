/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.ehlxr.test;
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
