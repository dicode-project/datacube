/*
Copyright 2012 Urban Airship and Contributors
*/

package com.urbanairship.datacube;

import com.google.common.collect.Maps;
import com.urbanairship.datacube.ops.IRowOp;
import com.urbanairship.datacube.ops.SerializableOp;

import java.util.Map;

public class Batch<T extends SerializableOp> {
    private Map<Address,IRowOp> map;

    /**
     * Normally you should not create your own Batches but instead have {@link DataCubeIo} create
     * them for you. You can use this if you intend to bypass the high-level magic and you really
     * know what you're doing.
     */
    public Batch() {
        this.map = Maps.newHashMap();
    }

    /**
     * Normally you should not create your own Batches but instead have {@link DataCubeIo} create
     * them for you. You can use this if you intend to bypass the high-level magic and you really
     * know what you're doing.
     * @param map some Ops to wrap in this Batch
     */
    public Batch(Map<Address,IRowOp> map) {
        this.map = map;
    }
    
    public void putAll(Batch<T> b) {
        this.putAll(b.getMap());
    }
    
    public void putAll(Map<Address,IRowOp> other) {
//        DebugHack.log("In Batch.putAll() with existing map size " + map.size() + " and incoming map size " +
//                other.size());
        for(Map.Entry<Address,IRowOp> entry: other.entrySet()) {
            Address c = entry.getKey();
            IRowOp alreadyExistingVal = map.get(entry.getKey());
            IRowOp newVal;
            if(alreadyExistingVal == null) {
//                DebugHack.log("No existing value in batch, not combining");
                newVal = entry.getValue();
            } else {
//                DebugHack.log("Combining entries in batch");
                newVal = (IRowOp)alreadyExistingVal.add(entry.getValue());
            }
            this.map.put(c, newVal);
        }
    }
    
    public Map<Address,IRowOp> getMap() {
        return map;
    }
    
    public String toString() {
        return map.toString();
    }
    
    public void reset() {
        map = Maps.newHashMap();
    }
}
