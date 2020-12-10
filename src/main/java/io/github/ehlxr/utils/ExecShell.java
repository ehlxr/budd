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

package io.github.ehlxr.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Java 执行 shell 命令
 *
 * @author ehlxr
 * @since 2020/5/6.
 */
public class ExecShell {
    private static final Logger log = LoggerFactory.getLogger(ExecShell.class);

    public static void execShellCommand(final String command) {
        Process process = null;
        DataOutputStream dataOutputStream = null;
        try {
            process = Runtime.getRuntime().exec("/bin/sh");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes(command + "\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            readStdStream(process);
            process.waitFor();
        } catch (IOException | InterruptedException | IllegalMonitorStateException e) {
            log.error("execShellCommand: {} error", command, e);

            throw new RuntimeException(e);
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (IOException e) {
                log.error("execShellCommand: {} close stream error", command, e);
            }
        }
    }

    /**
     * 读取 Shell 进程标准输出流和错误流
     */
    private static void readStdStream(final Process process) {
        String readLine;
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        try {
            // 读取Shell进程标准输出流
            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((readLine = stdInput.readLine()) != null) {
                log.info("[stdInputStream]: {}", readLine);
            }
            // 读取Shell进程标准错误流
            stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((readLine = stdError.readLine()) != null) {
                log.error("[stdErrorStream]: {}", readLine);
            }
        } catch (IOException e) {
            log.error("readStdStream error", e);

            throw new RuntimeException(e);
        } finally {
            try {
                if (stdInput != null) {
                    stdInput.close();
                }
                if (stdError != null) {
                    stdError.close();
                }
            } catch (IOException e) {
                log.error("readStdStream close stream error", e);
            }
        }
    }

    public static void main(String[] args) {
        ExecShell.execShellCommand("pwd");
    }
}