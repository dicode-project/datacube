/*
Copyright 2012 Urban Airship and Contributors
*/

package com.urbanairship.datacube;

import java.util.Arrays;

public class BucketType {
    public static final BucketType IDENTITY = new BucketType("nobucket", new byte[] {});
    public static final BucketType WILDCARD = new BucketType("wildcard", new byte[] {});
    
    private final String nameInErrMsgs;
    private final byte[] uniqueId;
    
    public BucketType(String nameInErrMsgs, byte[] uniqueId) {
        this.nameInErrMsgs = nameInErrMsgs;
        this.uniqueId = uniqueId;
    }
    
    public byte[] getUniqueId() {
        return uniqueId;
    }
    
    public String toString() {
        return nameInErrMsgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BucketType that = (BucketType) o;

        if (!nameInErrMsgs.equals(that.nameInErrMsgs)) return false;
        if (!Arrays.equals(uniqueId, that.uniqueId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nameInErrMsgs.hashCode();
        result = 31 * result + Arrays.hashCode(uniqueId);
        return result;
    }
}
