package com.wazxb.zhuxuebao.network.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.utils.BdLog;

public class BdNetUtil {
    static public enum NetTpyeEnmu {
        UNAVAIL, WIFI, NET, WAP
    }

    ;

    public static enum NetworkStateInfo {
        UNAVAIL, WIFI, TwoG, ThreeG
    }

    ;

    public static String getStatusInfoString() {
        NetworkStateInfo info = getStatusInfo();
        switch (info) {
            case WIFI:
                return "WIFI";
            case TwoG:
                return "2G";
            case ThreeG:
                return "3G";
            default:
                return "UNAVAIL";
        }
    }

    public static NetworkStateInfo getStatusInfo() {
        boolean netSataus = false;
        NetworkInfo networkinfo = null;
        NetworkStateInfo ret = NetworkStateInfo.UNAVAIL;
        try {
            ConnectivityManager cwjManager = (ConnectivityManager) ZXApplicationDelegate.getApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            networkinfo = cwjManager.getActiveNetworkInfo();
            netSataus = networkinfo.isAvailable();

            if (!netSataus) {
                ret = NetworkStateInfo.UNAVAIL;
                BdLog.i("NetWorkCore", "NetworkStateInfo", "UNAVAIL");
            } else {
                if (networkinfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    BdLog.i("NetWorkCore", "NetworkStateInfo", "WIFI");
                    ret = NetworkStateInfo.WIFI;
                } else {
                    TelephonyManager tm = (TelephonyManager) ZXApplicationDelegate.getApplication()
                            .getSystemService(Context.TELEPHONY_SERVICE);
                    int subType = tm.getNetworkType();
                    switch (subType) {
                        case TelephonyManager.NETWORK_TYPE_1xRTT: // ~ 50-100 kbps
                        case TelephonyManager.NETWORK_TYPE_CDMA: // ~ 14-64 kbps
                        case TelephonyManager.NETWORK_TYPE_EDGE: // ~ 50-100 kbps
                        case TelephonyManager.NETWORK_TYPE_GPRS: // ~ 100 kbps
                        case /* Connectivity.NETWORK_TYPE_IDEN */ 11: // ~25 kbps
                        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                            BdLog.i("NetWorkCore", "NetworkStateInfo", "TwoG");
                            return NetworkStateInfo.TwoG;
                        case TelephonyManager.NETWORK_TYPE_EVDO_0: // ~ 400-1000
                            // kbps
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // ~ 600-1400
                            // kbps
                        case TelephonyManager.NETWORK_TYPE_UMTS: // ~ 400-7000 kbps
                        case /* Connectivity.NETWORK_TYPE_EHRPD */ 14: // ~ 1-2 Mbps
                        case /* Connectivity.NETWORK_TYPE_EVDO_B */ 12: // ~ 5 Mbps
                        case /* Connectivity.NETWORK_TYPE_HSPAP */ 15: // ~ 10-20 Mbps
                        case /* Connectivity.NETWORK_TYPE_LTE */ 13: // ~ 10+ Mbps
                        case /* TelephonyManager.NETWORK_TYPE_HSDPA */ 8: // ~ 4-8
                            // Mbps
                        case /* TelephonyManager.NETWORK_TYPE_HSUPA */ 9: // ~
                            // 1.4-5.8Mbps
                        case /* TelephonyManager.NETWORK_TYPE_HSPA */ 10: // ~20Mbps
                            BdLog.i("NetWorkCore", "NetworkStateInfo", "ThreeG");
                            return NetworkStateInfo.ThreeG;
                        default:
                            BdLog.i("NetWorkCore", "NetworkStateInfo-default",
                                    "TwoG");
                            return NetworkStateInfo.TwoG;
                    }
                }
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    /**
     * 获取网络状态
     *
     * @return
     */
    public static NetTpyeEnmu getNetType() {
        NetTpyeEnmu ret = NetTpyeEnmu.UNAVAIL;
        try {
            ConnectivityManager cwjManager = (ConnectivityManager) ZXApplicationDelegate.getApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = cwjManager.getActiveNetworkInfo();
            if (networkinfo == null) {
                return NetTpyeEnmu.UNAVAIL;
            }
            boolean netSataus = networkinfo.isAvailable();

            if (!netSataus) {
                ret = NetTpyeEnmu.UNAVAIL;
            } else {
                if (networkinfo.getTypeName().equalsIgnoreCase("WIFI")) {
                    ret = NetTpyeEnmu.WIFI;
                } else {
                    String proxyHost = android.net.Proxy.getDefaultHost();
                    if (proxyHost != null && proxyHost.length() > 0) {
                        ret = NetTpyeEnmu.WAP;
                    } else {
                        ret = NetTpyeEnmu.NET;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

}
