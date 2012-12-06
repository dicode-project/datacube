package com.urbanairship.datacube;

import com.urbanairship.datacube.ops.IRowOp;
import com.urbanairship.datacube.ops.LongOp;
import com.urbanairship.datacube.ops.RowOp;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests whether merging of multiple
 * RowOps works as expected.
 */

public class RowOpCombinationTest {

    private static final BoxedByteArray COL1_KEY = new BoxedByteArray(new byte[] {0});
    private static final BoxedByteArray COL2_KEY = new BoxedByteArray(new byte[] {1});
    private static final BoxedByteArray COL3_KEY = new BoxedByteArray(new byte[] {2});
    private static final BoxedByteArray COL4_KEY = new BoxedByteArray(new byte[] {3});
    private static final BoxedByteArray[] COL_KEYS = new BoxedByteArray[] {
        COL1_KEY, COL2_KEY, COL3_KEY, COL4_KEY};

    private static RowOp rowOp2;
    private static RowOp rowOp1;

    @BeforeClass
    public static void setUp() {
        rowOp1 = new RowOp();
        rowOp1.addColumnOp(COL1_KEY, new LongOp(1L));
        rowOp1.addColumnOp(COL2_KEY, new LongOp(1L));
        rowOp1.addColumnOp(COL4_KEY, new LongOp(1L));

        rowOp2 = new RowOp();
        rowOp2.addColumnOp(COL1_KEY, new LongOp(1L));
        rowOp2.addColumnOp(COL3_KEY, new LongOp(1L));
    }

    private LongOp[] getLongOps(IRowOp rowOp, BoxedByteArray[] colKeys) {
        LongOp[] longOps = new LongOp[colKeys.length];

        for(int i=0; i<colKeys.length; i++) {
            longOps[i] = (LongOp) rowOp.getColumnOps().get(colKeys[i]);
        }

        return longOps;
    }

    @Test
    public void testAddRowOp() {
        IRowOp addedRowOp = rowOp1.add(rowOp2);
        Assert.assertEquals(4, addedRowOp.getColumnOps().size());

        LongOp[] ops = getLongOps(addedRowOp, COL_KEYS);
        Assert.assertEquals(2L, ops[0].getLong());
        Assert.assertEquals(1L, ops[1].getLong());
        Assert.assertEquals(1L, ops[2].getLong());
        Assert.assertEquals(1L, ops[3].getLong());
    }

    @Test
    public void testSubtractRowOp() {
        IRowOp subtractedRowOp = rowOp1.subtract(rowOp2);
        Assert.assertEquals(4, subtractedRowOp.getColumnOps().size());

        LongOp[] ops = getLongOps(subtractedRowOp, COL_KEYS);
        Assert.assertEquals(0L, ops[0].getLong());
        Assert.assertEquals(1L, ops[1].getLong());
        Assert.assertEquals(-1L, ops[2].getLong());
        Assert.assertEquals(1L, ops[3].getLong());
    }
}
