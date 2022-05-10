package main;
/*
 * �̸�Ʈ EAPS � ���� �� �̹��� ���� ���� 
 * */
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class GftImgDelController {
	static Logger logger = Logger.getLogger(GftImgDelController.class);
	final static String winDirPath = "C:\\eaps\\GftretImage";			// Test
	final static String LinDirPath = "/app/webtob/eaps/GftretImage";	// �

	public static void main(String[] args) throws ParseException {
		String nowDate   = getNowDate().trim();					// ���� ��¥ 
		String monthDate = getCustomDate(nowDate).trim();		// 2�� �� ��¥
		logger.info("==================================================================================================");
		logger.info(String.format("[START] Delete image files process!! ==> ���� ��¥: %s / ���� ��� ��¥: %s", nowDate, monthDate));
		logger.info("--------------------------------------------------------------------------------------------------");
		
		try {
			if (System.getProperty("os.name").indexOf("Windows") > -1) {
				File dirPath = new File(winDirPath);
				logger.info(String.format("�˻� ���丮: %s", dirPath));

				File[] folder = dirPath.listFiles();
				for (File f : folder) {
					String dirName = f.getName().trim();
					if(dirName.equals(monthDate)) {
						// �ش� ���� ����
						String folderPath = winDirPath + "\\"+ dirName;
						deleteFolder(folderPath);
					}
				}
			} else {
				File dirPath = new File(LinDirPath);
				logger.info(String.format("�˻� ���丮: %s", dirPath));

				File[] folder = dirPath.listFiles();
				for (File f : folder) {
					String dirName = f.getName().trim();
					if(dirName.equals(monthDate)) {
						// �ش� ���� ����
						String folderPath = LinDirPath + "/"+ dirName;
						deleteFolder(folderPath);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			logger.info("--------------------------------------------------------------------------------------------------");
			logger.info(String.format("[ END ] Delete image files process!! ==> ���� ��¥: %s / ���� ��� ��¥: %s", nowDate, monthDate));
			logger.info("==================================================================================================");
		}
	}
	
	
	// ����
	public static void deleteFolder(String path) {
		File folder = new File(path);
		try {
			if(folder.exists()) {
				File[] folder_list = folder.listFiles();		// �����ȿ� ���� ����Ʈ ������
				
				for(int i =0; i < folder_list.length; i++) {
					if(folder_list[i].isFile()) {
						folder_list[i].delete();
						logger.info(String.format("%s ������ �����Ǿ����ϴ�.", folder_list[i].toString()));
					} else {
						deleteFolder(folder_list[i].getPath());
						logger.info(String.format("%s ������ �����Ǿ����ϴ�.", folder_list[i].toString()));
					}
					folder_list[i].delete();
				}
				folder.delete();
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	
	// ���� ��¥
	private static String getNowDate() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		return sdf1.format(new Date());
	}
	
	
	// �δ� �� ��¥
	private static String getCustomDate(String yyyymmdd) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -2);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
		return dateFormatter.format(cal.getTime());
	}
}
