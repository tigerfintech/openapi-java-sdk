package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import com.tigerbrokers.stock.openapi.client.struct.enums.Protocol;

/**
 * @author liutongping
 * @version 1.0
 * @date 2022/3/9 下午5:15
 */
public class NetworkUtilTest {

  public static void main(String[] args) {
    // test1();

    // test data
    // data = "{\"ret\":0,\"serverTime\":1646652924198,\"items\":[{\"openapi\":{\"port\":8887,\"socket_port\":8883,\"COMMON\":\"https://openapi.skytigris.cn\",\"TBNZ\":\"https://openapi.skytigris.cn/nz\",\"TBSG\":\"https://openapi.skytigris.cn/sg\"},\"openapi-sandbox\":{\"port\":8889,\"socket_port\":8885,\"COMMON\":\"https://openapi-sandbox.skytigris.cn\"},\"cash-plus\":\"https://cash-plus-api.skytigris.cn\"}]}";

    ClientConfig config = ClientConfig.DEFAULT_CONFIG;
    config.setEnv(Env.PROD);
    config.setLicense(License.TBNZ);
    test();
    config.setLicense(License.TBSG);
    test();

    config.setEnv(Env.SANDBOX);
    config.setLicense(License.TBNZ);
    test();
    config.setLicense(License.TBSG);
    test();
  }

  private static void test() {
    ClientConfig config = ClientConfig.DEFAULT_CONFIG;
    config.setSubscribeProtocol(Protocol.STOMP);
    System.out.println("\r\nenv:" + config.getEnv()
        + ", license:" + config.getLicense()
        + ", subscribeProtocal:" + config.getSubscribeProtocol());
    System.out.println(NetworkUtil.getHttpServerAddress());
    System.out.println(NetworkUtil.getServerAddress());
    config.setSubscribeProtocol(Protocol.SOCKET);
    System.out.println(NetworkUtil.getServerAddress());

  }

  private static void test1() {
    //https://openapi.skytigris.cn/gateway
    //wss://openapi.skytigris.cn:8887/stomp
    //wss://openapi.skytigris.cn:8883
    System.out.println(NetworkUtil.getHttpServerAddress());
    System.out.println(NetworkUtil.getServerAddress());
    ClientConfig.DEFAULT_CONFIG.setSubscribeProtocol(Protocol.SOCKET);
    System.out.println(NetworkUtil.getServerAddress());

    System.out.println("\r\nchange to sandbox.......");
    //https://openapi-sandbox.skytigris.cn/gateway
    //wss://openapi-sandbox.skytigris.cn:8889/stomp
    //wss://openapi-sandbox.skytigris.cn:8885
    ClientConfig.DEFAULT_CONFIG.setEnv(Env.SANDBOX);
    ClientConfig.DEFAULT_CONFIG.setSubscribeProtocol(Protocol.STOMP);

    System.out.println(NetworkUtil.getHttpServerAddress());
    System.out.println(NetworkUtil.getServerAddress());
    ClientConfig.DEFAULT_CONFIG.setSubscribeProtocol(Protocol.SOCKET);
    System.out.println(NetworkUtil.getServerAddress());
  }

}
