package main;
/*
 * 이마트 EAPS 운영 서버 내 이미지 파일 제거 
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
	final static String LinDirPath = "/app/webtob/eaps/GftretImage";	// 운영

	public static void main(String[] args) throws ParseException {
		String nowDate   = getNowDate().trim();					// 현재 날짜 
		String monthDate = getCustomDate(nowDate).trim();		// 2달 전 날짜
		logger.info("==================================================================================================");
		logger.info(String.format("[START] Delete image files process!! ==> 현재 날짜: %s / 삭제 대상 날짜: %s", nowDate, monthDate));
		logger.info("--------------------------------------------------------------------------------------------------");
		
		try {
			if (System.getProperty("os.name").indexOf("Windows") > -1) {
				File dirPath = new File(winDirPath);
				logger.info(String.format("검색 디렉토리: %s", dirPath));

				File[] folder = dirPath.listFiles();
				for (File f : folder) {
					String dirName = f.getName().trim();
					if(dirName.equals(monthDate)) {
						// 해당 폴더 삭제
						String folderPath = winDirPath + "\\"+ dirName;
						deleteFolder(folderPath);
					}
				}
			} else {
				File dirPath = new File(LinDirPath);
				logger.info(String.format("검색 디렉토리: %s", dirPath));

				File[] folder = dirPath.listFiles();
				for (File f : folder) {
					String dirName = f.getName().trim();
					if(dirName.equals(monthDate)) {
						// 해당 폴더 삭제
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
			logger.info(String.format("[ END ] Delete image files process!! ==> 현재 날짜: %s / 삭제 대상 날짜: %s", nowDate, monthDate));
			logger.info("==================================================================================================");
		}
	}
	
	
	// 삭제
	public static void deleteFolder(String path) {
		File folder = new File(path);
		try {
			if(folder.exists()) {
				File[] folder_list = folder.listFiles();		// 폴더안에 파일 리스트 얻어오기
				
				for(int i =0; i < folder_list.length; i++) {
					if(folder_list[i].isFile()) {
						folder_list[i].delete();
						logger.info(String.format("%s 파일이 삭제되었습니다.", folder_list[i].toString()));
					} else {
						deleteFolder(folder_list[i].getPath());
						logger.info(String.format("%s 폴더가 삭제되었습니다.", folder_list[i].toString()));
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
	
	
	// 현재 날짜
	private static String getNowDate() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		return sdf1.format(new Date());
	}
	
	
	// 두달 전 날짜
	private static String getCustomDate(String yyyymmdd) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -2);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
		return dateFormatter.format(cal.getTime());
	}
}
