/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.utils;

import com.sun.javafx.PlatformUtil;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bibek
 */
public class NetworkUtils {

    public static String provideIP4(String portNumber) {
        if (PlatformUtil.isWindows()){
            try {
                StringBuilder str = new StringBuilder("http://");
                str.append(InetAddress.getLocalHost().getHostAddress());
                str.append(":").append(portNumber).append("/");
                return str.toString();
            } catch (UnknownHostException ex) {
                Logger.getLogger(NetworkUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            StringBuilder str = new StringBuilder("http://");
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            int ctr = 0;
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements() && ctr < 3) {
                    ctr++;
                    if (ctr == 3) {
                        break;
                    }
                    InetAddress i = (InetAddress) ee.nextElement();
                    if (ctr == 2) {
                        str.append(i.getHostAddress());
                    }

                }
            }
            str.append(":").append(portNumber).append("/");
            return str.toString();
        } catch (SocketException s) {
            return null;
        }
    }
}
