/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates.
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amazonaws.services.kinesis.producer.sample;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Random;

public class Utils {
    private static final Random RANDOM = new Random();
    
    /**
     * @return A random unsigned 128-bit int converted to a decimal string.
     */
    public static String randomExplicitHashKey() {
        return new BigInteger(128, RANDOM).toString(10);
    }

    public static Integer randomIntGenerator(int boundInt) {
        Random random = new Random();
        int randInt = random.nextInt(boundInt);
        return randInt + 1;
    }
    
    /**
     * Generates a blob containing a UTF-8 string. The string begins with the
     * sequence number in decimal notation, followed by a space, followed by
     * padding.
     * 
     * @param sequenceNumber
     *            The sequence number to place at the beginning of the record
     *            data.
     * @param totalLen
     *            Total length of the data. After the sequence number, padding
     *            is added until this length is reached.
     * @return ByteBuffer containing the blob
     */
    public static ByteBuffer generateData(long sequenceNumber, String timestamp) {
        JsonObject data = new JsonObject();
        data.addProperty("producerSequenceNumber", Long.toString(sequenceNumber));
        data.addProperty("producerTimestamp", timestamp);
        data.addProperty("firstName", "John");
        data.addProperty("lastName", "Doe");
        data.addProperty("age", randomIntGenerator(76).toString());
        try {
            return ByteBuffer.wrap(data.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
