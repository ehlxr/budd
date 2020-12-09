package me.ehlxr.pack;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

@SuppressWarnings("serial")
public class PackView extends JFrame {
	private JButton jb = new JButton();
	private JButton jb1 = new JButton();
	private JButton jb2 = new JButton();
	private String inputPath = "D:\\wins-dsp";
	private String outputPath = "C:\\Users\\ehlxr\\Desktop";
	private JLabel jl0 = new JLabel();
	private JButton cancel = new JButton("退出");
	private JTextPane jText1 = new JTextPane();
	private JTextPane jText2 = new JTextPane();
	public JTextArea jArea = new JTextArea();
	public JScrollPane p = new JScrollPane(this.jArea);

	private PackView() {
		setTitle("打包工具（By:Henry）");
		setBounds(400, 400, 500, 300);
		setLayout(null);
		setResizable(false);
		this.jb.setText("打包清单");
		this.jb1.setText("打包根目录");
		this.jb2.setText("输出目录");

		this.jText1.setText(this.inputPath);
		this.jText2.setText(this.outputPath);

		this.jb.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				if (PackView.this.packs()) {
					PackView.this.jl0.setText("成功打包！");
					PackView.this.jb.setText("...继续");
				} else {
					PackView.this.jl0.setText("打包失败！");
				}
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		this.jb1.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				PackView.this.choosePath(1);
				PackView.this.jText1.setText(PackView.this.inputPath);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		this.jb2.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				PackView.this.choosePath(2);
				PackView.this.jText2.setText(PackView.this.outputPath);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		this.cancel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				PackView.this.close();
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		this.jb1.setBounds(10, 5, 100, 30);
		this.jText1.setBounds(120, 5, 250, 30);

		this.jb2.setBounds(10, 40, 100, 30);
		this.jText2.setBounds(120, 40, 250, 30);

		this.jb.setBounds(10, 100, 100, 30);
		this.cancel.setBounds(120, 100, 100, 30);
		this.jl0.setBounds(230, 100, 100, 30);
		this.jArea.setLineWrap(true);
		this.jArea.setForeground(Color.red);
		this.jArea.setEditable(false);

		this.p.setBounds(10, 130, 480, 130);

		this.p.setVerticalScrollBarPolicy(22);
		this.p.setHorizontalScrollBarPolicy(32);

		add(this.jb1);
		add(this.jText1);
		add(this.jb2);
		add(this.jText2);
		add(this.jb);
		add(this.cancel);
		add(this.jl0);

		add(this.p);
		setVisible(true);
		setDefaultCloseOperation(3);
	}

	private List<String> chooseFile(int chooseMode) {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setMultiSelectionEnabled(true);
			fileChooser.setDialogTitle("文件打包");
			fileChooser.setDragEnabled(true);
			fileChooser.setAutoscrolls(true);

			fileChooser.setFileFilter(new FileFilter() {
				public boolean accept(File f) {
					if (f.isDirectory()) {
						return true;
					}
					if ((f.getName().endsWith(".TXT")) || (f.getName().endsWith(".txt"))) {
						return true;
					}
					return false;
				}

				public String getDescription() {
					return ".txt";
				}
			});
			fileChooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());

			fileChooser.setOpaque(true);
			fileChooser.setDoubleBuffered(true);
			int returnVal = -1;
			switch (chooseMode) {
			case 1:
				returnVal = fileChooser.showOpenDialog(this);
				break;
			case 2:
				returnVal = fileChooser.showSaveDialog(this);
			}
			File[] fileName;
			if (returnVal == 0) {
				fileName = fileChooser.getSelectedFiles();
			} else {
				fileName = (File[]) null;
			}
			List<String> list = new ArrayList<String>();
			System.out.println("打包文件路径列表：");
			String filePath = null;
			for (int i = 0; i < fileName.length; i++) {
				filePath = fileName[i].getAbsolutePath();
				if (filePath.toUpperCase().endsWith("TXT")) {
					list.add(filePath);
					System.out.println("序号   " + i + "   " + filePath);
				} else {
					System.out.println("序号   " + i + "   " + filePath + " >>该文件不能作为打包文件!   ");
				}
			}
			return list;
		} catch (Exception e) {
		}
		return null;
	}

	private boolean choosePath(int id) {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setMultiSelectionEnabled(true);
			switch (id) {
			case 1:
				fileChooser.setDialogTitle("打包文件根目录");
				fileChooser.setCurrentDirectory(new File(this.inputPath));
				break;
			case 2:
				fileChooser.setDialogTitle("输出文件目录");
				fileChooser.setCurrentDirectory(new File(this.outputPath));
			}
			fileChooser.setDragEnabled(true);
			fileChooser.setAutoscrolls(true);
			fileChooser.setAcceptAllFileFilterUsed(true);
			fileChooser.setOpaque(true);
			fileChooser.setDoubleBuffered(true);
			fileChooser.setFileSelectionMode(1);

			fileChooser.showOpenDialog(this);
			switch (id) {
			case 1:
				this.inputPath = fileChooser.getSelectedFile().toString();
				break;
			case 2:
				this.outputPath = fileChooser.getSelectedFile().toString();
			}
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	private void close() {
		dispose();
	}

	private boolean packs() {
		boolean flag = true;
		List<String> fileName = chooseFile(1);
		if ((fileName == null) || (fileName.size() <= 0)) {
			System.out.println("打包原始文件没有找到");
			flag = false;
		} else {
			for (int i = 0; i < fileName.size(); i++) {
				try {
					flag = FileUtil.becomePackage((String) fileName.get(i), this.inputPath, this.outputPath, this);
				} catch (Exception e) {
					return false;
				}
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		new PackView();
	}
}
