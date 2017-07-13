package com.example.fruit.salerapplication.commontool;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by luxuhui on 2017/7/13.
 */

public class MacInfo {

    public static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces = null;

        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        String hardwareAddress = null;
        NetworkInterface iF = null;

        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();

            try {
                hardwareAddress = bytesToString(iF.getHardwareAddress());

            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        if (iF != null && iF.getName().equals("wlan0")) {
            hardwareAddress = hardwareAddress.replace(":", "");
        }

        return hardwareAddress;
    }

    public static String getMachineIpAddress() {
        Enumeration<NetworkInterface> interfaces = null;

        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        String ipAddress = null;
        NetworkInterface iF = null;

        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();

            Enumeration<InetAddress> addressEnumeration = iF.getInetAddresses();

            if(!addressEnumeration.hasMoreElements()) continue;

            InetAddress address = addressEnumeration.nextElement();
            ipAddress = bytesToString(address.getAddress());
            if (ipAddress == null) continue;
        }


        return ipAddress;
    }


    private static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02x:", b));
        }

        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }

        return buf.toString();
    }
}
