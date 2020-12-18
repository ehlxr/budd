/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.ehlxr.token;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public enum TokenVersionEnum {

    VERSION_1(1),
    VERSION_2(2);

    // provider数字与字符串映射字典表
    private static final BiMap<String, Integer> VERSION_MAPPING_DICT = HashBiMap.create();

    static {
        VERSION_MAPPING_DICT.put("1", VERSION_1.getValue());
        VERSION_MAPPING_DICT.put("2", VERSION_2.getValue());
    }

    private final int value;

    TokenVersionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static String getStringVersion(int version) {
        return VERSION_MAPPING_DICT.inverse().get(version);
    }

    public static boolean checkKeyIsExist(String version) {
        return VERSION_MAPPING_DICT.containsKey(version);
    }

}