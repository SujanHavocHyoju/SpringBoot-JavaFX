/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import org.junit.Test;

/**
 *
 * @author bibek
 */
public class IPAddressTest {

    @Test
    public void shouldTest() throws SocketException, UnknownHostException {
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        int ctr = 0;
        while (e.hasMoreElements()) {
//            InetAddress ip;
//            String hostname;
//            ip = InetAddress.getLocalHost();
//            hostname = ip.getHostName();
//            System.out.println("Current IP address : " + ip.getHostAddress());
//            System.out.println("Your current Hostname : " + hostname);
            
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements() && ctr < 3) {
                ctr++;
                if (ctr == 3) {
                    break;
                }
                InetAddress i = (InetAddress) ee.nextElement();
                if (ctr == 2) {
                    System.out.println(i.getLocalHost().getHostAddress());
                }

            }
        }
    }
}
