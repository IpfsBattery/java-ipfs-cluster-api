package io.ipfs.cluster.api;

import io.ipfs.cluster.api.http.Pins;
import io.ipfs.multihash.Multihash;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

public class TEST {

    public static void main(String args[]){

        IPFSCluster ipfsCluster = new IPFSCluster("127.0.0.1", 9094);
        try {
            System.out.println("id" + ipfsCluster.id());
            System.out.println("version" + ipfsCluster.version());
            System.out.println("peers.ls" + ipfsCluster.peers.ls());
            System.out.println("pin.ls" + ipfsCluster.peers.ls());
//            System.out.println("pin.ls" + ipfsCluster.pins);
            Pins pins = ipfsCluster.pins("QmdJbpQWDKsgwmiSw7tTvL42Wy6M3YYz1DnnYK2bGAUzLR");
            pins.get(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code()==202) {
                        pins.get(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String s = response.body().string();
                                System.out.print(s);
                            }
                        });
                    }
                    String s = response.body().string();
                    System.out.print(s);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
