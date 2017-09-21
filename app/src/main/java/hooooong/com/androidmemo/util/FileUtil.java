package hooooong.com.androidmemo.util;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Android Hong on 2017-09-20.
 */

public class FileUtil {

    /**
     * File 쓰기 함수
     *
     * @param context
     * @param fileName : 파일 이름
     * @param content  : 내용
     * @throws IOException
     */
    public static void write(Context context, String fileName, String content) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, MODE_PRIVATE);
            fos.write(content.getBytes());
        } catch (Exception e) {
            throw e;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * File 읽기 함수
     *
     * @param context
     * @param fileName : 읽을 파일 명
     * @return
     * @throws IOException
     */
    public static String read(Context context, String fileName) throws IOException {
        StringBuilder stringBuffer = new StringBuilder();

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = context.openFileInput(fileName);
            // 버퍼를 달고
            bis = new BufferedInputStream(fis);
            // 한번에 읽어올 버퍼 양을 설정한다.
            byte buffer[] = new byte[1024];
            // 현재 읽은 양을 담는 변수 설정
            int count = 0;
            // 읽은 값을 buffer 에 넣고, count 에 넣은 크기를 반환한다.

            /**
             * 파일 명 추가
             */
            stringBuffer.append("fileName//"+fileName+"\n");
            while ((count = bis.read(buffer)) != -1) {
                // readLine 과는 다르게 \n 을 문자열로 인식하기 때문에
                // 더해줄 필요가 없다.
                String data = new String(buffer, 0, count);
                stringBuffer.append(data);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    throw e;
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }

        return stringBuffer.toString();
    }

    /**
     * 파일 삭제 함수
     *
     * @param context
     * @param fileName
     */
    public static boolean delete(Context context, String fileName){
        return context.deleteFile(fileName);
    }

}
