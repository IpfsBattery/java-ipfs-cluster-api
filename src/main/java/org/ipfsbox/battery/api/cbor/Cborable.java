package org.ipfsbox.battery.api.cbor;

public interface Cborable {

    CborObject toCbor();

    default byte[] serialize() {
        return toCbor().toByteArray();
    }
}
