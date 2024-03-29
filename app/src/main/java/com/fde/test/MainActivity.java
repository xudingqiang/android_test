package com.fde.test;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fde.test.activity.EglTestActivity;
import com.fde.test.activity.TestBinderActivity;
import com.fde.test.bean.WifiHistory;
import com.fde.test.view.TestJniActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    TextView txtTest1,txtTest2,txtTest3,txtTest4;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);

        initView();

        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
//        testCmd();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                WifiHistory wifiHistory = new WifiHistory();
//                wifiHistory.setWifiName("test1");
//                wifiHistory.setCreateDate(System.currentTimeMillis()+"");
//                wifiHistory.setIsDel("0");
//                wifiHistory.setNodes("testnode");
//                FdeDataBase.getInstance(context).mWifiDao().insert(wifiHistory);
//                queryList();
//            }
//        }).start();

    }

    private void initView(){
        txtTest1 = findViewById(R.id.txtTest1);
        txtTest1.setOnClickListener(view ->{
            startActivity(new Intent(context, EglTestActivity.class));
        });

        txtTest2 = findViewById(R.id.txtTest2);
        txtTest2.setOnClickListener(view ->{
            startActivity(new Intent(context, TestJniActivity.class));
        });

        txtTest3 = findViewById(R.id.txtTest3);
        txtTest3.setOnClickListener(view ->{
            startActivity(new Intent(context, TestBinderActivity.class));
        });

        txtTest4 = findViewById(R.id.txtTest4);
        txtTest4.setOnClickListener(view ->{
            startActivity(new Intent(context, EglTestActivity.class));
        });

    }

    public static String getAllIpAddress(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return getWifiIpAddress(context);
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                return getEthernetIpAddress();
            }
        }

        return null;
    }


    public static String getWifiIpAddress(Context context) {
        Log.i("bella","getWifiIpAddress.............. ");
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        // 将整数形式的 IP 地址转换为字符串形式
        String ipAddressString = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));

        return ipAddressString;
    }

    public static String getEthernetIpAddress() {
        Log.i("bella","getEthernetIpAddress.............. ");
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isUp() && !networkInterface.isLoopback() && !networkInterface.isVirtual()) {
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        if (!address.isLoopbackAddress() && address.getAddress().length == 4) {
                            return address.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void queryList(){
        List<WifiHistory> list =  FdeDataBase.getInstance(context).mWifiDao().getAllWifiList();
        if(list !=null){
            Log.i("bella","list "+list.toString());
        }else {
            Log.i("bella","list  is null ");
        }
    }


    public void testCmd(){
        String ifconfigOutput = executeCommand("ifconfig");

//        String[] arr = ifconfigOutput.split("\n");
//        for(String a :arr){
//
//            String[] netinfo = a.split(" ");
//            Log.i("bella","netinfo "+netinfo[1]);
//        }

        // 使用正则表达式提取所需信息
        Pattern pattern = Pattern.compile("inet addr:(\\S+)\\s+Bcast:(\\S+)\\s+Mask:(\\S+)");
        Matcher matcher = pattern.matcher(ifconfigOutput);

        // 格式化并输出提取的信息
        while (matcher.find()) {
            String ipAddress = matcher.group(1);
            String broadcastAddress = matcher.group(2);
            String subnetMask = matcher.group(3);

            Log.i("bella","IP Address: " + ipAddress);
            Log.i("bella","Broadcast Address: " + broadcastAddress);
            Log.i("bella","Subnet Mask: " + subnetMask);
        }
    }
    public  String executeCommand(String command) {
        StringBuilder output = new StringBuilder();

        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return output.toString();
    }
}