package com.example.ketamaNode;

/**
 * Created by xingbowu on 17/4/12.
 */
public interface KetamaNodeLocatorConfiguration {

    /**
     * Returns a uniquely identifying key, suitable for hashing by the
     * KetamaNodeLocator algorithm.
     *
     * @param node The MemcachedNode to use to form the unique identifier
     * @param repetition The repetition number for the particular node in question
     *          (0 is the first repetition)
     * @return The key that represents the specific repetition of the node
     */
    String getKeyForNode(String node, int repetition);

    /**
     * Returns the number of discrete hashes that should be defined for each node
     * in the continuum.
     *
     * @return a value greater than 0
     */
    int getNodeRepetitions();
}
