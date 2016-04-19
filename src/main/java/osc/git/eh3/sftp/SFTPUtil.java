package osc.git.eh3.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * SFTP工具类
 * @author lixiangrong
 *
 */
public class SFTPUtil {
	private static String ip;
	private static String user;
	private static String psw;
	private static int port;

	private static Session session = null;
	private static Channel channel = null;

	static {
		try {
			InputStream propFile = SFTPUtil.class.getResourceAsStream("sftp.properties");
			if (propFile != null) {
				Properties p = new Properties();
				p.load(propFile);
				ip = p.getProperty("sftp.ip");
				user = p.getProperty("sftp.user");
				psw = p.getProperty("sftp.psw");
				String portStr = p.getProperty("sftp.port");
				port = (portStr != null ? Integer.parseInt(portStr) : -1);
				propFile.close();
				propFile = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ChannelSftp getSFTP() throws Exception {
		ChannelSftp sftp = null;
		JSch jsch = new JSch();
		if (port <= 0) {
			// 连接服务器，采用默认端口
			session = jsch.getSession(user, ip);
		} else {
			// 采用指定的端口连接服务器
			session = jsch.getSession(user, ip, port);
		}

		// 如果服务器连接不上，则抛出异常
		if (session == null) {
			throw new Exception("session is null");
		}

		// 设置登陆主机的密码
		session.setPassword(psw);// 设置密码
		// 设置第一次登陆的时候提示，可选值：(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "no");
		// 设置登陆超时时间
		session.connect(30000);

		try {
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			sftp = (ChannelSftp) channel;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sftp;
	}

	private static void closeSFTP() {
		if (session != null && session.isConnected()) {
			session.disconnect();
		}
		if (channel != null && channel.isConnected()) {
			channel.disconnect();
		}
	}

	/**
	 * SFTP上传文件
	 * @param desPath ftp服务器目录
	 * @param desFileName 上传后的文件名
	 * @param resFile 要上传的文件
	 * @throws Exception
	 */
	public static void putFile(String desPath, String desFileName, File resFile) throws Exception {
		try {
			ChannelSftp sftp = getSFTP();

			// 进入服务器指定的文件夹
			sftp.cd(desPath);

			OutputStream outstream = sftp.put(desFileName);
			InputStream instream = new FileInputStream(resFile);

			byte b[] = new byte[1024];
			int n;
			while ((n = instream.read(b)) != -1) {
				outstream.write(b, 0, n);
			}
			outstream.flush();
			outstream.close();
			instream.close();

			closeSFTP();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			SFTPUtil.putFile("/data", "212321.txt", new File("D:/1.txt"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
