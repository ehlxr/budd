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

package io.github.ehlxr.cache;

import java.util.ArrayList;

public class TestCache {
	public static void main(String[] args) {
		CacheManager.putCache("abc", new Cache("key", "value", 10, false));
		CacheManager.putCache("def", new Cache());
		CacheManager.putCache("ccc", new Cache());
		CacheManager.clearOnly("");
		Cache c = new Cache();
		for (int i = 0; i < 10; i++) {
			CacheManager.putCache("" + i, c);
		}
		CacheManager.putCache("aaaaaaaa", c);
		CacheManager.putCache("abchcy;alskd", c);
		CacheManager.putCache("cccccccc", c);
		CacheManager.putCache("abcoqiwhcy", c);
		System.out.println("删除前的大小：" + CacheManager.getCacheSize());
		ArrayList<String> cacheAllkey = CacheManager.getCacheAllkey();
		for (String key : cacheAllkey) {
			System.out.println(key + ":" + CacheManager.getCacheInfo(key).getValue());
		}
		CacheManager.clearAll("aaaa");
		System.out.println("删除后的大小：" + CacheManager.getCacheSize());
		cacheAllkey = CacheManager.getCacheAllkey();
		for (String key : cacheAllkey) {
			System.out.println(key);
		}
	}
}
