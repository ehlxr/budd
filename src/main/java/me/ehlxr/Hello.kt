package me.ehlxr

/**
 * Created by lixiangrong on 2018/8/17.
 */
fun main(args: Array<String>) {
    val strList = listOf("a", "ab", "abc","abcd","abcde","abcdef","abcdefg")
    // 非常好用的流式 API filter，flat，map 等等
    strList.forEach{str->
        run {
            str.length
            println(str)
        }
    }
}
