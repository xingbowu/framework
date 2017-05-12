package com.example.ketamaNode;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by xingbowu on 17/4/12.
 */
class KetamaIterator extends SpyObject implements Iterator<String> {

    private final String key;
    private long hashVal;
    private int remainingTries;
    private int numTries = 0;
    private final HashAlgorithm hashAlg;
    private final TreeMap<Long, String> ketamaNodes;

    /**
     * Create a new KetamaIterator to be used by a client for an operation.
     *
     * @param k the key to iterate for
     * @param t the number of tries until giving up
     * @param ketamaNodes the continuum in the form of a TreeMap to be used when
     *          selecting a node
     * @param hashAlg the hash algorithm to use when selecting within the
     *          continuumq
     */
    protected KetamaIterator(final String k, final int t,
                             TreeMap<Long, String> ketamaNodes, final HashAlgorithm hashAlg) {
        super();
        this.ketamaNodes = ketamaNodes;
        this.hashAlg = hashAlg;
        hashVal = hashAlg.hash(k);
        remainingTries = t;
        key = k;
    }

    private void nextHash() {
        // this.calculateHash(Integer.toString(tries)+key).hashCode();
        long tmpKey = hashAlg.hash((numTries++) + key);
        // This echos the implementation of Long.hashCode()
        hashVal += (int) (tmpKey ^ (tmpKey >>> 32));
        hashVal &= 0xffffffffL; /* truncate to 32-bits */
        remainingTries--;
    }

    public boolean hasNext() {
        return remainingTries > 0;
    }

    public String next() {
        try {
            return getNodeForKey(hashVal);
        } finally {
            nextHash();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }

    private String getNodeForKey(long hash) {
        final String rv;
        if (!ketamaNodes.containsKey(hash)) {
            // Java 1.6 adds a ceilingKey method, but I'm still stuck in 1.5
            // in a lot of places, so I'm doing this myself.
            SortedMap<Long, String> tailMap = ketamaNodes.tailMap(hash);
            if (tailMap.isEmpty()) {
                hash = ketamaNodes.firstKey();
            } else {
                hash = tailMap.firstKey();
            }
        }
        rv = ketamaNodes.get(hash);
        return rv;
    }
}
