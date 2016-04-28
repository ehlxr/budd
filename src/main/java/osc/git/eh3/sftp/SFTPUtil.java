package osc.git.eh3.sftp;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * SFTP工具类
 * 
 * @author lixiangrong
 *
 */
public class SFTPUtil {
	private static Log log = LogFactory.getLog(SFTPUtil.class);
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
			log.error("读取sftp配置文件失败：" + e.getMessage());
			e.printStackTrace();
		}
	}

	private static ChannelSftp getSFTP() throws Exception {
		log.info("正在连接服务器[" + ip + "].....");
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
			log.error("连接服务器[" + ip + "]失败.....");
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
			log.info("连接服务器[" + ip + "]成功.....");
		} catch (Exception e) {
			log.error("服务器[" + ip + "]创建sftp通信通道失败：" + e.getMessage());
			e.printStackTrace();
			closeSFTP();
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
	 * 
	 * @param desPath
	 *            ftp服务器目录
	 * @param desFileName
	 *            上传后的文件名
	 * @param resFile
	 *            要上传的文件
	 * @throws Exception
	 */
	public static void putFile(String desPath, String desFileName, File resFile) {
		try {
			ChannelSftp sftp = getSFTP();
			mkdirs(sftp, desPath);

			// 进入服务器指定的文件夹
			sftp.cd(desPath);

			sftp.put(resFile.getAbsolutePath(), desFileName, ChannelSftp.OVERWRITE);
			log.info("文件[" + desPath + desFileName + "]上传完成...");
			closeSFTP();
		} catch (Exception e) {
			log.error("文件[" + desPath + desFileName + "]上传失败：" + e.getMessage());
			e.printStackTrace();
			closeSFTP();
		}
	}

	public static void main(String[] args) {
		try {
			SFTPUtil.putFile("/upload/222/1111", "212321.txt", new File("D:/1.txt"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 在远程服务器创建多级目录
	 * 
	 * @param sftp
	 * @param desPath
	 * @throws Exception
	 */
	private static void mkdirs(ChannelSftp sftp, String desPath) throws Exception {
		String[] paths = desPath.split("/");
		String path = "";
		for (int i = 0; i < paths.length; i++) {
			path += paths[i] + "/";
			if (!isExistDir(sftp, path)) {
				sftp.mkdir(path);
			}
		}
	}

	/**
	 * 判断远程目录是否存在
	 * 
	 * @param sftp
	 * @param desPath
	 * @return
	 */
	private static boolean isExistDir(ChannelSftp sftp, String desPath) {
		boolean isExist = false;
		try {
			Vector<?> content = sftp.ls(desPath);
			if (content != null) {
				isExist = true;
			}
		} catch (SftpException e) {
			isExist = false;
		}
		return isExist;
	}
}