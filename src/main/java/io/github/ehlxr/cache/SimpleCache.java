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

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class SimpleCache<K, V> {

	private final Lock lock = new ReentrantLock();
	private final int maxCapacity;
	private final Map<K, V> eden;
	private final Map<K, V> perm;

	public SimpleCache(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		this.eden = new ConcurrentHashMap<K, V>(maxCapacity);
		this.perm = new WeakHashMap<K, V>(maxCapacity);
	}

	public V get(K k) {
		V v = this.eden.get(k);
		if (v == null) {
			lock.lock();
			try {
				v = this.perm.get(k);
			} finally {
				lock.unlock();
			}
			if (v != null) {
				this.eden.put(k, v);
			}
		}
		return v;
	}

	public void put(K k, V v) {
		if (this.eden.size() >= maxCapacity) {
			lock.lock();
			try {
				this.perm.putAll(this.eden);
			} finally {
				lock.unlock();
			}
			this.eden.clear();
		}
		this.eden.put(k, v);
	}
}