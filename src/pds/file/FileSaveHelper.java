package pds.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class FileSaveHelper {
	
	private static Random random = new Random();
	
	//directroy 는 파일이 저장될 경로, InputStream은 올린 파일을 메모리나 임시 저장소에 저장한 item에서 inputStream을 연결한 것
	public static String save(String directory, InputStream is) 	throws IOException {
	
		//현재 시간
		long currentTime = System.currentTimeMillis();
		//랜덤한 숫자
		int randomValue = random.nextInt(50);
		
		//현재 시간을 밀리초 단위로 나타낸 long 숫자 +"_" + 1~50
		String fileName = Long.toString(currentTime) + "_" + Integer.toString(randomValue);

		//새로운 파일 생성.
		File file = new File(directory, fileName);
		 
		//아웃풋 스트림 객체변수 선언
		FileOutputStream os = null;
	
		try {
			
			//파일로 갈 아웃풋 스트림 생성
			os = new FileOutputStream(file);
			
			byte[] data = new byte[8096];
			int len = -1;
			while ((len = is.read(data)) != -1) {
				
				os.write(data, 0, len);
				
			}
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
				}
		}
		
		// 파일의 절대 경로이름을 리턴하는 메소드
		return file.getAbsolutePath();
		
	}
}