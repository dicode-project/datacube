/*
Copyright 2012 Urban Airship and Contributors
*/

package com.urbanairship.datacube;

import com.google.common.collect.ImmutableList;

import java.util.List;

public interface Bucketer<F> {
    /**
     * When writing to the cube at some address, the address will have one coordinate for each
     * dimension in the cube, for example (time: 348524388, location: portland). For each
     * dimension, for each bucket type within that dimension, the bucketer must transform the
     * input data into the bucket that should be used to store the data. For example, the
     * bucketer for the location dimension might return "oregon" when asked for the State bucket
     * type.
     */
    public CSerializable<F> bucketForWrite(F coordinate, BucketType bucketType);

    /**
     * When reading from the cube, the reader specifies some coordinates from which to read.
     * The bucketer can choose which cube coordinates to read from based on these input
     * coordinates. For example, if the reader asks for hourly counts (the Hourly BucketType) and
     * passes a timestamp, the bucketer could return the timestamp rounded down to the hour floor.
     */
    public CSerializable<F> bucketForRead(Object coordinate, BucketType bucketType);

    /**
     * Return all bucket types that exist in this dimension. The bucketer should be able to
     * handle calls to {@link #bucketForRead(Object, BucketType)} and
     * {@link Bucketer#bucketForWrite(Object, BucketType)} for these BucketTypes.
     */
    public List<BucketType> getBucketTypes();

    /**
     * Returns an instance of the deserialized Bucket
     * @param key
     * @param btype
     * @return
     */
    F readBucket(BoxedByteArray key, BucketType btype);

    /**
     * This identity/no-op bucketer class is implicitly used for dimensions that don't choose a
     * bucketer.
     */
    @SuppressWarnings("rawtypes")
    public static class IdentityBucketer implements Bucketer<CSerializable> {
        @Override
        public CSerializable bucketForWrite(CSerializable coordinate, BucketType bucketType) {
            if(bucketType != BucketType.IDENTITY) {
                throw new AssertionError();
            }
            return coordinate;
        }

        @Override
        public List<BucketType> getBucketTypes() {
            return ImmutableList.of(BucketType.IDENTITY);
        }

        @Override
        public CSerializable readBucket(BoxedByteArray key, BucketType btype) {
            return (CSerializable)key;
        }

        @Override
        public CSerializable bucketForRead(Object coordinateField, BucketType bucketType) {
            return (CSerializable)coordinateField;
        }

        @Override
        public boolean equals(Object o) {
            return this == o || (o != null && getClass() == o.getClass());
        }

        @Override
        public int hashCode() {
            return getClass().hashCode();
        }
    }
}
