package org.enner.flatbuffers;

import java.nio.ByteBuffer;

import static org.enner.flatbuffers.Utilities.NULL;

/**
 * Pointers are uint32 that contain the address to an element. The
 * value is an offset with respect to the address of the pointer.
 * <p/>
 * Pointers can point to Tables / Strings / Unions / Vectors
 *
 * @author Florian Enner < florian @ hebirobotics.com >
 * @since 10 Jan 2015
 */
public class Pointer {

    public static int dereference(ByteBuffer buffer, int address) {
        // Read the offset. We could check for NULL, but the pointer
        // may be located at position 0.
        int offset = buffer.getInt(address);

        // Make sure that it's unsigned. Note that it's not converted to long
        // in case the protocol is changed to support signed values in the future.
        if (offset < 0)
            throw new IllegalStateException("Pointer offset is not allowed to be signed.");

        // We can use NULL is an invalid state. A NULL value would mean that the pointer
        // is simultaneously some other object, which is impossible.
        return offset == NULL ? NULL : address + offset;
    }

}
