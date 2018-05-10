package org.ipfsbox.battery.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.ipfs.multiaddr.MultiAddress;
import org.ipfsbox.battery.api.http.HttpUtil;

import java.io.*;

/**
 * @author liang
 * @Date 2018.5.9
 * */
public class IPFSCluster {

    public final String host;
    public final int port;
    private final String version;
    public final Pins pins = new Pins();
    public final Peers peers = new Peers();
    public final Sync sync = new Sync();
    public final Recover recover = new Recover();
    public final Allocation allocation = new Allocation();
    public final Health health = new Health();

    public IPFSCluster(String host, int port) {
        this(host, port, "");
    }

    public IPFSCluster(String multiaddr) {
        this(new MultiAddress(multiaddr));
    }

    public IPFSCluster(MultiAddress addr) {
        this(addr.getHost(), addr.getTCPPort(), "");
    }

    public IPFSCluster(String host, int port, String version) {
        this.host = host;
        this.port = port;
        this.version = version;
    }


    public class Pins {

        public JSONArray ls() {
            String url = "http://" + host + ":" + port + "/pins";
            JSONArray result = new JSONArray();
            try {
                String request = HttpUtil.get(url);
                result = JSONArray.parseArray(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        public JSONObject ls(String cid){
            String url = "http://" + host + ":" + port + "/pins" + "/" + cid;
            JSONObject result = new JSONObject();
            try {
                String request = HttpUtil.get(url);
                result = JSONObject.parseObject(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        public JSONArray add(String cid){
            String url = "http://" + host + ":" + port + "/pins/" + cid;
            JSONArray result = new JSONArray();
            try {
                String request = HttpUtil.post(url, null);
                result = JSONArray.parseArray(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        public JSONArray rm (String cid) {
            String url = "http://" + host + ":" + port + "/pins/" + cid;

            JSONArray result = new JSONArray();
            try {
                String request = HttpUtil.delete(url);
                result = JSONArray.parseArray(request);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;

        }

    }

    public class Peers {

        public JSONArray ls(){

            String url = "http://" + host + ":" + port + "/peers";
            JSONArray result = new JSONArray();
            try {
                String request = HttpUtil.get(url);
                result = JSONArray.parseArray(request);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        public JSONArray rm (String cid) {
            String url = "http://" + host + ":" + port + "/peers/" + cid;

            JSONArray result = new JSONArray();
            try {
                String request = HttpUtil.delete(url);
                result = JSONArray.parseArray(request);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;

        }

    }

    public class Sync {

        public JSONArray sync(){

            String url = "http://" + host + ":" + port + "/pins/sync";
            JSONArray result = new JSONArray();
            try {
                String request = HttpUtil.post(url, null);
                result = JSONArray.parseArray(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        public JSONObject sync(String cid){

            String url = url = "http://" + host + ":" + port + "/pins/" + cid + "/sync";
            JSONObject result = new JSONObject();
            try {
                String request = HttpUtil.post(url, null);
                result = JSONObject.parseObject(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public class Recover {

        public JSONArray recover(){

            String url = "http://" + host + ":" + port + "/pins/recover";
            JSONArray result = new JSONArray();
            try {
                String request = HttpUtil.post(url, null);
                result = JSONArray.parseArray(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        public JSONObject recover(String cid){

            String url = "http://" + host + ":" + port + "/pins/" + cid + "/recover";

            JSONObject result = new JSONObject();
            try {
                String request = HttpUtil.post(url, null);
                result = JSONObject.parseObject(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

    }

    public class Allocation {
        public JSONArray ls() {
            String url = "http://" + host + ":" + port + "/allocations";

            JSONArray result = new JSONArray();
            try {
                String request = HttpUtil.get(url);
                result = JSONArray.parseArray(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        public JSONObject ls(String cid){
            String url = "http://" + host + ":" + port + "/allocations" + "/" + cid;

            JSONObject result = new JSONObject();
            try {
                String request = HttpUtil.get(url);
                result = JSONObject.parseObject(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public class Health {
        public JSONObject graph(){
            String url = "http://" + host + ":" + port + "/health/graph";
            JSONObject result = new JSONObject();
            try {
                String request = HttpUtil.get(url);
                result = JSONObject.parseObject(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public JSONObject id() {
        String url = "http://" + host + ":" + port + "/id";
        JSONObject result = new JSONObject();
        try {
            String request = HttpUtil.get(url);
            result = JSONObject.parseObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public JSONObject version() {
        String url = "http://" + host + ":" + port + "/version";
        JSONObject result = new JSONObject();
        try {
            String request = HttpUtil.get(url);
            result = JSONObject.parseObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
