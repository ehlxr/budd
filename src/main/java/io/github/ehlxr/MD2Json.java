package io.github.ehlxr;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by ehlxr on 2016/11/8.
 */
public class MD2Json {
    public static void main(String[] args) throws IOException {
        resume();
    }

    private static void resume() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("show", 1);
        String content = FileUtils.readFileToString(new File("E:\\ehlxr\\Git\\md-files\\resume.md"), "UTF-8");
        jsonObject.put("content", content);
        System.out.println(jsonObject.toString());
    }
}