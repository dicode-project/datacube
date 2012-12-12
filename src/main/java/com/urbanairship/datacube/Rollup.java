/*
Copyright 2012 Urban Airship and Contributors
*/

package com.urbanairship.datacube;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;


/**
 * Use this class to describe a rollup that you want the datacube to keep.
 * 
 * For example, if you're counting events with the dimensions (color, size, flavor) and you
 * want to keep a total count for a (color, size) combination, you'd specify that using a Rollup. 
 */
public class Rollup {
    private final Set<DimensionAndBucketType> components;
    
    public Rollup(Set<DimensionAndBucketType> components) {
        this.components = new HashSet<DimensionAndBucketType>(components); // defensive copy
    }
    
    /**
     * Convenient wrapper around {@link #Rollup(Set)} that builds a set for you.
     */
    public Rollup(Dimension<?> d) {
        this(ImmutableSet.of(new DimensionAndBucketType(d, BucketType.IDENTITY)));
    }
    
    /**
     * Convenient wrapper around {@link #Rollup(Set)} that builds a set for you.
     */
    public Rollup(Dimension<?> d, BucketType bt) {
        this(ImmutableSet.of(new DimensionAndBucketType(d, bt)));
    }
    
    /**
     * Convenient wrapper around {@link #Rollup(Set)} that builds a set for you.
     */
    public Rollup(Dimension<?> d1, BucketType bt1, Dimension<?> d2, BucketType bt2) {
        this(ImmutableSet.of(new DimensionAndBucketType(d1, bt1),
                new DimensionAndBucketType(d2, bt2)));
    }

    /**
     * Convenient wrapper around {@link #Rollup(Set)} that builds a set for you.
     */
    public Rollup(Dimension<?> d1, BucketType bt1, Dimension<?> d2, BucketType bt2,
            Dimension<?> d3, BucketType bt3) {
        this(ImmutableSet.of(new DimensionAndBucketType(d1, bt1),
                new DimensionAndBucketType(d2, bt2), new DimensionAndBucketType(d3, bt3)));
    }
    
    /**
     * Convenient wrapper around {@link #Rollup(Set)} that builds a set for you.
     */
    public Rollup(Dimension<?> d1, Dimension<?> d2) {
        this(ImmutableSet.of(new DimensionAndBucketType(d1, BucketType.IDENTITY),
                new DimensionAndBucketType(d2, BucketType.IDENTITY)));
    }
    
    /**
     * Convenient wrapper around {@link #Rollup(Set)} that builds a set for you.
     */
    public Rollup(Dimension<?> d1, Dimension<?> d2, BucketType bt2) {
        this(ImmutableSet.of(new DimensionAndBucketType(d1, BucketType.IDENTITY),
                new DimensionAndBucketType(d2, bt2)));
    }

    public Set<DimensionAndBucketType> getComponents() {
        return components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rollup rollup = (Rollup) o;

        if (components != null ? !components.equals(rollup.components) : rollup.components != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return components != null ? components.hashCode() : 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(Rollup over ");
        sb.append(components);
        sb.append(")");
        return sb.toString();
    }
}
