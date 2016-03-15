package com.wazxb.xuerongbao.network.http;

import android.os.Build;

import com.wazxb.xuerongbao.network.http.BdHttpStat.ExecuteStatus;
import com.wazxb.xuerongbao.network.http.BdNetUtil.NetTpyeEnmu;
import com.zxzx74147.devlib.utils.BdLog;

import org.apache.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTTP 网络请求的基础实现
 *
 * @author sunqitang
 */
class BdHttpImpl2 {
    private HttpContext2 context;

    private HttpURLConnection mConn;

    static private Pattern mPattern = Pattern.compile(
            "^[0]{0,1}10\\.[0]{1,3}\\.[0]{1,3}\\.(172|200)$", Pattern.MULTILINE);

    static private String boundary = "--------7da3d81520810*";
    static private final int POSTDATATIMEOUT = 15 * 1000;

    static {
        if (Integer.parseInt(Build.VERSION.SDK) < 8) {
            System.setProperty("http.keepAlive", "false");
        } else {
            System.setProperty("http.keepAlive", "true");
        }
        HttpURLConnection.setFollowRedirects(true);
    }

    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            try {
                cancelNetConnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
    private Timer timer;

    public BdHttpImpl2(HttpContext2 context) {
        if (context == null) {
            throw new NullPointerException("init HttpImpl's args context is null");
        }
        this.context = context;
    }

    /**
     * 取消当前的网络请求
     */
    public void cancelNetConnect() {
        context.getResponse().isCancel = true;
        BdCloseHelper.close(mConn);
    }

//	private void checkDNS(URL url) {
//		try {
//			InetAddress address = InetAddress.getByName(url.getHost());
//			address.getHostAddress();
//		} catch (Exception e) {
//			BdLog.e(getClass().getName(), "checkDNS", e.toString());
//		}
//	}

    /**
     * 连接服务器, 获取连接
     *
     * @param url         地址
     * @return HttpURLConnection null表示失败
     */
    private HttpURLConnection getConnect(URL url) {
        NetTpyeEnmu state = BdNetUtil.getNetType();

        try {
            if (state == NetTpyeEnmu.UNAVAIL) {
                return null;
            } else if (state == NetTpyeEnmu.NET || state == NetTpyeEnmu.WAP) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                if (proxyHost != null && proxyHost.length() > 0) {
                    if (isCMCCServer(proxyHost)) {
                        StringBuilder new_address = new StringBuilder(80);
                        new_address.append("http://");
                        new_address.append(android.net.Proxy.getDefaultHost());
                        String file = url.getFile();
                        if (file != null && file.startsWith("?")) {
                            new_address.append("/");
                        }
                        new_address.append(file);
                        URL new_url = new URL(new_address.toString());
                        mConn = (HttpURLConnection) new_url.openConnection();
                        context.getRequest().addHeadData("X-Online-Host", url.getHost());
                    } else {
                        java.net.Proxy p = null;
                        p = new java.net.Proxy(java.net.Proxy.Type.HTTP,
                                new InetSocketAddress(
                                        android.net.Proxy.getDefaultHost(),
                                        android.net.Proxy.getDefaultPort()));
                        mConn = (HttpURLConnection) url.openConnection(p);
                    }
                }
            }

            if (mConn == null) {
                mConn = (HttpURLConnection) url.openConnection();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mConn;
    }

    public void getNetData(int readTimeout, int connTimeout, BdHttpStat stat) throws Exception {
        stat.executeStatus = ExecuteStatus.BEGIN;
        if (context.getResponse().isCancel == true) {
            throw new BdHttpCancelException();
        }
        String urlString = context.getRequest().generateGetString(stat);

        URL url = new URL(urlString);
        if (context.getResponse().isCancel == true) {
            throw new BdHttpCancelException();
        }
        stat.executeStatus = ExecuteStatus.CREATE_CONN_BEFORE;
        HttpURLConnection conn = getConnect(url);
        stat.executeStatus = ExecuteStatus.CREATE_CONN_SUCC;
        long time = System.currentTimeMillis();
        try {
            if (mConn == null) {
                throw new java.net.SocketException("network not available.");
            }
            mConn.setRequestMethod("GET");
            mConn.setConnectTimeout(connTimeout);
            mConn.setReadTimeout(readTimeout);
            context.getRequest().wrapHead(conn);
            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }
            // 拖慢网络速度,暂时去掉
            // checkDNS(url);
            stat.dnsTime = new Date().getTime() - time;
            BdLog.i("GET:" + urlString);
            stat.executeStatus = ExecuteStatus.CONN_BEFORE;
            conn.connect();
            stat.executeStatus = ExecuteStatus.CONN_SUCC;
            stat.connectTime = new Date().getTime() - time - stat.dnsTime;
            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }

            stat.executeStatus = ExecuteStatus.GETDATA_BEFORE;
            context.getResponse().getResponseHead(mConn);
            stat.responsedCode = context.getResponse().responseCode;
            context.getResponse().retBytes = getResponse(mConn);

            if (context.getResponse().retBytes != null) {
                stat.downloadSize = context.getResponse().retBytes.length;
            }
            stat.executeStatus = ExecuteStatus.GETDATA_SUCC;
            stat.rspTime = new Date().getTime() - time;
        } finally {
            if (mConn != null) {
                mConn.disconnect();
            }
        }
    }


    /**
     * 处理返回值
     *
     * @param mConn
     * @throws Exception
     */
    private byte[] getResponse(HttpURLConnection mConn) throws Exception {
        byte[] ret = null;
        ByteArrayOutputStream outputstream = new ByteArrayOutputStream(BUFFERSIZE);
        ;
        InputStream in = null;
        try {
            if (mConn != null) {

                byte[] buf = new byte[BUFFERSIZE];
                int num = -1;
                in = mConn.getInputStream();

                while (context.getResponse().isCancel == false && (num = in.read(buf)) != -1) {
                    outputstream.write(buf, 0, num);
                }

                if (context.getResponse().isCancel) {
                    throw new BdHttpCancelException();
                }

                ret = outputstream.toByteArray();

            }
            return ret;
        } finally {
            BdCloseHelper.close(outputstream);
            BdCloseHelper.close(in);
        }
    }


    private boolean isCMCCServer(String ip) {
        boolean ret = false;
        Matcher m = mPattern.matcher(ip);
        if (m.find()) {
            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }

    /**
     * 以multipart/form-data格式post网络数据
     *
     * @param readTimeout
     */
    public void postBytesNetData(int readTimeout, int connTimeout, BdHttpStat stat) throws Exception {
        stat.executeStatus = ExecuteStatus.BEGIN;
        try {
            URL url = new URL(context.getRequest().getUrl());
            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }
            stat.executeStatus = ExecuteStatus.CREATE_CONN_BEFORE;
            HttpURLConnection conn = getConnect(url);
            stat.executeStatus = ExecuteStatus.CREATE_CONN_SUCC;
            long time = System.currentTimeMillis();
            if (mConn == null) {
                throw new java.net.SocketException("network not available.");
            }
            mConn.setRequestMethod("POST");
            mConn.setDoOutput(true);
            mConn.setDoInput(true);
            mConn.setConnectTimeout(connTimeout);
            mConn.setReadTimeout(readTimeout);
            mConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }
            context.getRequest().wrapHead(conn);
            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }
            time = new Date().getTime();
            // 拖慢网络速度,暂时去掉
            // checkDNS(url);
            stat.dnsTime = new Date().getTime() - time;
            stat.executeStatus = ExecuteStatus.CONN_BEFORE;
            conn.connect();
            stat.executeStatus = ExecuteStatus.CONN_SUCC;
            stat.connectTime = new Date().getTime() - time - stat.dnsTime;
            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }

            // 发送大文件，使用wap模式会引起getResponseCode无法返回
            if (timer != null) {
                timer.schedule(timerTask, POSTDATATIMEOUT * 3);
            }
            stat.executeStatus = ExecuteStatus.POST_BEFORE;
            context.getRequest().wrapPost2Conn(conn, boundary, stat);
            stat.executeStatus = ExecuteStatus.POST_SUCC;

            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }
            stat.executeStatus = ExecuteStatus.GETDATA_BEFORE;
            context.getResponse().getResponseHead(mConn);
            stat.responsedCode = context.getResponse().responseCode;
            context.getResponse().retBytes = getResponse(mConn);

            if (context.getResponse().retBytes != null) {
                stat.downloadSize = context.getResponse().retBytes.length;
            }
            stat.rspTime = new Date().getTime() - time;
            stat.executeStatus = ExecuteStatus.GETDATA_SUCC;
        } finally {
            if (timer != null) {
                timer.cancel();
            }
            BdCloseHelper.close(mConn);

        }
    }

    static private final int BUFFERSIZE = 1024;


    private boolean isFileSegSuccess() {
        if (context.getResponse().responseCode != HttpStatus.SC_OK
                && context.getResponse().responseCode != HttpStatus.SC_PARTIAL_CONTENT) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 以application/x-www-form-urlencoded格式post网络数据
     *
     * @param readTimeout
     * @throws Exception
     */
    public void postNetData(int readTimeout, int conntimeOut, BdHttpStat stat) throws Exception {
        stat.executeStatus = ExecuteStatus.BEGIN;
        try {
            URL url = new URL(context.getRequest().getUrl());
            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }
            stat.executeStatus = ExecuteStatus.CREATE_CONN_BEFORE;
            HttpURLConnection conn = getConnect(url);
            stat.executeStatus = ExecuteStatus.CREATE_CONN_SUCC;
            long time = System.currentTimeMillis();
            if (mConn == null) {
                throw new java.net.SocketException("network not available.");
            }
            mConn.setRequestMethod("POST");
            mConn.setDoOutput(true);
            mConn.setDoInput(true);
            mConn.setConnectTimeout(conntimeOut);
            mConn.setReadTimeout(readTimeout);
            mConn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }
            context.getRequest().wrapHead(conn);
            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }
            // 拖慢网络速度,暂时去掉
            // checkDNS(url);
            stat.dnsTime = System.currentTimeMillis() - time;
            stat.executeStatus = ExecuteStatus.CONN_BEFORE;
            conn.connect();
            stat.executeStatus = ExecuteStatus.CONN_SUCC;
            stat.connectTime = System.currentTimeMillis() - time - stat.dnsTime;
            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }
            stat.executeStatus = ExecuteStatus.POST_BEFORE;
            context.getRequest().wrapPost2Conn(conn, stat);
            stat.executeStatus = ExecuteStatus.POST_SUCC;
            if (context.getResponse().isCancel == true) {
                throw new BdHttpCancelException();
            }
            stat.executeStatus = ExecuteStatus.GETDATA_BEFORE;
            context.getResponse().getResponseHead(mConn);
            stat.responsedCode = context.getResponse().responseCode;
            context.getResponse().retBytes = getResponse(mConn);

            if (context.getResponse().retBytes != null) {
                stat.downloadSize = context.getResponse().retBytes.length;
            }
            stat.rspTime = new Date().getTime() - time;
            stat.executeStatus = ExecuteStatus.GETDATA_SUCC;
        } finally {
            BdCloseHelper.close(mConn);
        }
    }

}
