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

package io.github.ehlxr.hashing.consistent;

public class Main {
    public static void main(String[] args) {
        Cluster c = createCluster();

        Entry[] entries = {
                new Entry("i"),
                new Entry("have"),
                new Entry("a"),
                new Entry("pen"),
                new Entry("an"),
                new Entry("apple"),
                new Entry("applepen"),
                new Entry("pineapple"),
                new Entry("pineapplepen"),
                new Entry("PPAP")
        };

        for (Entry e : entries) {
            c.put(e);
        }

        c.addServer(new Server("achuguniadsfaang"));
        findEntries(c, entries);

    }

    private static Cluster createCluster() {
        Cluster c = new Cluster();
        c.addServer(new Server("international"));
        c.addServer(new Server("china"));
        c.addServer(new Server("japanjapan"));
        c.addServer(new Server("Amarica"));
        c.addServer(new Server("samsungtsisger"));
        c.addServer(new Server("achuguniang"));
        return c;
    }

    private static void findEntries(Cluster c, Entry[] entries) {
        for (Entry e : entries) {
            if (e == c.get(e)) {
                System.out.println("重新找到了entry: " + e);
            } else {
                System.out.println("entry已失效: " + e);
            }
        }
    }
}
