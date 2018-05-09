package io.ipfs.cluster.api;

import java.io.IOException;

public class TEST {

    public static void main(String args[]){

        IPFSCluster ipfsCluster = new IPFSCluster("127.0.0.1", 9094);
        try {
            System.out.println("id" + ipfsCluster.id());
            System.out.println("version" + ipfsCluster.version());
            System.out.println("peers.ls" + ipfsCluster.peers.ls());
            System.out.println("pin.ls" + ipfsCluster.peers.ls());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
