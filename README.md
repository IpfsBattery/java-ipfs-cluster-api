# java-ipfs-cluster-api

**A client library for the IPFS Cluster HTTP API, implemented in Java.**

UNOFFICIAL AND ALPHA

````
This is a port of ipfs/java-ipfs-api adapted for the API exposed by ipfs/ipfs-cluster.
````

## install

add this code to your project or make it to jar



## Dependencies

This module requires ipfs-cluster to be running. It is assumed that the IPFS Cluster API is running on "127.0.0.1:9094".



## Usage

### To import the module:

````java
IPFSCluster ipfsCluster = new IPFSCluster("127.0.0.1", 9094);
````



### API

The API is currently a work-in-progress. The exposed methods are designed to be similar to ipfs-cluster-ctl provided in ipfs/ipfs-cluster.

````java
ipfsCluster.id();
ipfsCluster.version();
ipfsCluster.pins.ls();
ipfsCluster.pins.ls(String CID);
ipfsCluster.pins.add(String CID);
ipfsCluster.pins.rm(String CID);
ipfsCluster.peers.ls();
ipfsCluster.peers.rm(String CID);
ipfsCluster.sync.sync();
ipfsCluster.sync.sync(String CID);
ipfsCluster.recover.recover();
ipfsCluster.recover.recover(String CID);
ipfsCluster.allocation.ls();
ipfsCluster.allocation(String CID);
ipfsCluster.health.graph();
````



The code is mostly from java-ipfs-api modified to consume the ipfs-cluster API.
