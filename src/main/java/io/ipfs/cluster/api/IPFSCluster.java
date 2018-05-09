package io.ipfs.cluster.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.ipfs.cid.*;
import io.ipfs.multihash.Multihash;
import io.ipfs.multiaddr.MultiAddress;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;

public class IPFSCluster {
    public static final Version MIN_VERSION = Version.parse("0.3.4");
    public enum PinType {all, direct, indirect, recursive}
    public List<String> ObjectTemplates = Arrays.asList("unixfs-dir");
    public List<String> ObjectPatchTypes = Arrays.asList("add-link", "rm-link", "set-data", "append-data");

    public final String host;
    public final int port;
    private final String version;
    public final Pin pin = new Pin();
    public final Peers peers = new Peers();

    /* Pinning an object ensures a local copy of it is kept.
     */
    public class Pin {
        public List<Multihash> add(Multihash hash) throws IOException {
            return ((List<Object>)((Map)retrieveAndParse("pins/add?stream-channels=true&arg=" + hash)).get("Pins"))
                    .stream()
                    .map(x -> Cid.decode((String) x))
                    .collect(Collectors.toList());
        }

        public Map<Multihash, Object> ls() throws IOException {
            return ls(PinType.direct);
        }

        public Map<Multihash, Object> ls(PinType type) throws IOException {
            return ((Map<String, Object>)(((Map)retrieveAndParse("pins?stream-channels=true&t="+type.name())).get("Keys"))).entrySet()
                    .stream()
                    .collect(Collectors.toMap(x -> Cid.decode(x.getKey()), x-> x.getValue()));
        }

        public List<Multihash> rm(Multihash hash) throws IOException {
            return rm(hash, true);
        }

        public List<Multihash> rm(Multihash hash, boolean recursive) throws IOException {
            Map json = retrieveMap("pins/rm?stream-channels=true&r=" + recursive + "&arg=" + hash);
            return ((List<Object>) json.get("Pins")).stream().map(x -> Cid.decode((String) x)).collect(Collectors.toList());
        }
    }

    public class Peers {
        public List<Multihash> add(Multihash hash) throws IOException {
            return ((List<Object>)((Map)retrieveAndParse("peers/add?stream-channels=true&arg=" + hash)).get("Pins"))
                    .stream()
                    .map(x -> Cid.decode((String) x))
                    .collect(Collectors.toList());
        }

        public List<Map<String, Object>> ls() throws IOException {
            List<Map<String, Object>> map = ((List<Map<String, Object>>)retrieveAndParse("peers"));
            return map;
        }

//        public Map<Multihash, Object> ls() throws IOException {
//            return ls(PinType.direct);
//        }

//        public Map<Multihash, Object> ls(PinType type) throws IOException {
//            return ((Map<String, Object>)(((Map)retrieveAndParse("peers")).get("Keys"))).entrySet()
//                    .stream()
//                    .collect(Collectors.toMap(x -> Cid.decode(x.getKey()), x-> x.getValue()));
//        }

        public List<Multihash> rm(Multihash hash) throws IOException {
            return rm(hash, true);
        }

        public List<Multihash> rm(Multihash hash, boolean recursive) throws IOException {
            Map json = retrieveMap("peers/rm?stream-channels=true&r=" + recursive + "&arg=" + hash);
            return ((List<Object>) json.get("Peers")).stream().map(x -> Cid.decode((String) x)).collect(Collectors.toList());
        }
    }

    public IPFSCluster(String host, int port) {
        this(host, port, "/api/v0/");
    }

    public IPFSCluster(String multiaddr) {
        this(new MultiAddress(multiaddr));
    }

    public IPFSCluster(MultiAddress addr) {
        this(addr.getHost(), addr.getTCPPort(), "/api/v0/");
    }

    public IPFSCluster(String host, int port, String version) {
        this.host = host;
        this.port = port;
        this.version = version;
        // Check IPFS is sufficiently recent
        try {
            Version detected = Version.parse(version());
            if (detected.isBefore(MIN_VERSION))
                throw new IllegalStateException("You need to use a more recent version of IPFSCluster! >= " + MIN_VERSION);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map id() throws IOException {
        return retrieveMap("id");
    }

    public Map sync() throws IOException {
        return retrieveMap("sync");
    }

    public Map recover() throws IOException {
        return retrieveMap("recover");
    }

    public String version() throws IOException {
        Map m = (Map)retrieveAndParse("version");
        return (String)m.get("Version");
    }

    private Map retrieveMap(String path) throws IOException {
        return (Map)retrieveAndParse(path);
    }

    private Object retrieveAndParse(String path) throws IOException {
        byte[] res = retrieve(path);
        return JSONParser.parse(new String(res));
    }

    private byte[] retrieve(String path) throws IOException {
        URL target = new URL("http", host, port, "/" + path);
        return IPFSCluster.get(target);
    }

    private static byte[] get(URL target) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) target.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        try {
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream resp = new ByteArrayOutputStream();

            byte[] buf = new byte[4096];
            int r;
            while ((r = in.read(buf)) >= 0)
                resp.write(buf, 0, r);
            return resp.toByteArray();
        } catch (ConnectException e) {
            throw new RuntimeException("Couldn't connect to IPFSCluster daemon at "+target+"\n Is IPFSCluster running?");
        } catch (IOException e) {
            String err = new String(readFully(conn.getErrorStream()));
            throw new RuntimeException("IOException contacting IPFSCluster daemon.\nTrailer: " + conn.getHeaderFields().get("Trailer") + " " + err, e);
        }
    }

    private static final byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream resp = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int r;
        while ((r=in.read(buf)) >= 0)
            resp.write(buf, 0, r);
        return resp.toByteArray();
    }


}
