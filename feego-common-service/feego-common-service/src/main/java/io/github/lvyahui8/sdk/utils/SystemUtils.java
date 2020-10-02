package io.github.lvyahui8.sdk.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/2
 */
public class SystemUtils {
    public static InetAddress getLocalAddress() {
        InetAddress localHost;

        try {
            localHost = InetAddress.getLocalHost();
        } catch (Exception e) {
            throw new RuntimeException("",e);
        }

        if (localHost.isLoopbackAddress()) {
            Enumeration<NetworkInterface> interfaces;
            try {
                interfaces = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException e) {
                throw new RuntimeException("",e);
            }
            while(interfaces.hasMoreElements()) {
                NetworkInterface element = interfaces.nextElement();
                Enumeration<InetAddress> addresses = element.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address.isLoopbackAddress()) {
                        continue;
                    }

                    if (address.getHostAddress().contains(":")) {
                        // ipv6
                        continue;
                    }
                    return address;
                }
            }
        }
        return localHost;
    }
}
