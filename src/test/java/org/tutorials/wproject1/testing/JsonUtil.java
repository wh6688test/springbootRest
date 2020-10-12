package org.tutorials.wproject1.testing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    public static byte[]toJsonByte(Object object) throws IOException {
        ObjectMapper mymapper = new ObjectMapper();
        mymapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mymapper.writeValueAsBytes(object);
    }



}
