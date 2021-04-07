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

package io.github.ehlxr.datastructure.linkedlist;

/**
 * @author ehlxr
 * @since 2021-04-04 14:19.
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(3, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(5, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(8, "林冲", "豹子头");

        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero2);
        doubleLinkedList.addByOrder(hero3);
        doubleLinkedList.addByOrder(hero4);

        doubleLinkedList.list();

        doubleLinkedList.update(new HeroNode2(3, "小卢", "玉麒麟--"));
        System.out.println("修改后的结果");
        doubleLinkedList.list();

        doubleLinkedList.del(3);
        System.out.println("删除后的结果");
        doubleLinkedList.list();
    }
}

class DoubleLinkedList {
    private final HeroNode2 head = new HeroNode2(0, "", "");

    public HeroNode2 getHead() {
        return head;
    }

    public void list() {
        HeroNode2 temp = head.next;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }
    }

    public void add(HeroNode2 heroNode) {
        HeroNode2 temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    public void addByOrder(HeroNode2 heroNode) {
        HeroNode2 temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }

            // 找到需要插入位置的前一个节点
            if (temp.next.no > heroNode.no) {
                break;
            }

            temp = temp.next;
        }

        heroNode.next = temp.next;
        heroNode.pre = temp;

        if (temp.next != null) {
            temp.next.pre = heroNode;
        }
        temp.next = heroNode;
    }

    public void update(HeroNode2 newHeroNode) {
        HeroNode2 temp = head.next;
        if (temp == null) {
            System.out.println("链表为空！");
        }

        while (true) {
            if (temp == null) {
                System.out.printf("没找到 %d 的节点\n", newHeroNode.no);
                break;
            }

            if (temp.no == newHeroNode.no) {
                temp.name = newHeroNode.name;
                temp.nickname = newHeroNode.nickname;

                break;
            }

            temp = temp.next;
        }
    }

    public void del(int no) {
        HeroNode2 temp = head.next;
        if (temp == null) {
            System.out.println("链表为空！");
        }

        while (true) {
            if (temp == null) {
                System.out.printf("没找到要删除的 %d 节点\n", no);
                break;
            }

            // 找到待删除节点
            if (temp.no == no) {
                temp.pre.next = temp.next;
                if (temp.next != null) {
                    temp.next.pre = temp.pre;
                }
                break;
            }

            temp = temp.next;
        }
    }
}

/**
 * 定义HeroNode ， 每个HeroNode 对象就是一个节点
 */
class HeroNode2 {
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next; //指向下一个节点
    public HeroNode2 pre;// 指向上一个节点

    public HeroNode2(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    /**
     * 为了显示方法，我们重新 toString
     */
    @Override
    public String toString() {
        return "HeroNode2 [no=" + no + ", name=" + name + ", nickname=" + nickname + "]";
    }

}
