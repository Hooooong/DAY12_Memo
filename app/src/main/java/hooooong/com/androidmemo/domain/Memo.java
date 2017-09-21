package hooooong.com.androidmemo.domain;

import java.io.Serializable;

/**
 * Created by Android Hong on 2017-09-19.
 */

public class Memo implements Serializable {

    private static final String DELIMETER = "//";

    /**
     * 파일 삭제하기 위한 fileName 추가;
     */
    private String fileName;

    private int no;
    private String title;
    private String author;
    private String content;
    private long dateTime;

    public Memo() {

    }

    /**
     * File 의 Text 를 통해
     * Memo 를 생성해 주기 위한 Memo 생성자 Overloading
     *
     * @param text
     */
    public Memo(String text) {
        // 1. 값을 줄(\n) 단위로 분해한다.
        String lines[] = text.split("\n");

        for (String line : lines) {
            // 2. 문자열을 행(":^:") 단위로 분해한다.
            String columns[] = line.split(DELIMETER);

            String key = "";
            String value = "";

            if (columns.length == 2) {
                key = columns[0];
                value = columns[1];
            } else {
                key = "";
                value = columns[0];
            }
            switch (key) {
                case "fileName":
                    setFileName(value);
                    break;
                case "no":
                    setNo(Integer.parseInt(value));
                    break;
                case "title":
                    setTitle(value);
                    break;
                case "author":
                    setAuthor(value);
                    break;
                case "datetime":
                    setDateTime(Long.parseLong(value));
                    break;
                case "content":
                    setContent(value);
                    break;
                default:
                    appendContent(value);
                    break;
            }
        }
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * File 을 작성하기 위한 toString Override
     *
     * ex)
     * no//1
     * author//이흥기
     * title//메모타이틀
     * content//메모내용
     * 오오오우오우오
     * ㅇ오우ㅗ우옹
     * datetime//1010293840
     *
     * @return
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("no").append(DELIMETER).append(no).append("\n");
        result.append("author").append(DELIMETER).append(author).append("\n");
        result.append("title").append(DELIMETER).append(title).append("\n");
        result.append("content").append(DELIMETER).append(content).append("\n");
        result.append("datetime").append(DELIMETER).append(dateTime).append("\n");

        return result.toString();
    }

    /**
     * Content 를 연결하기 위한 메소드
     *
     * @param value
     */
    public void appendContent(String value) {
        content += "\n" + value;
    }
}