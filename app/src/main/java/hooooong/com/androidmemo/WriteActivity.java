package hooooong.com.androidmemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import hooooong.com.androidmemo.domain.Memo;
import hooooong.com.androidmemo.util.FileUtil;

public class WriteActivity extends AppCompatActivity {

    EditText editAuthor;
    EditText editTitle;
    EditText editContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        //actionBar 객체를 가져올 수 있다.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("메모 작성");

        //메뉴바에 '<' 버튼이 생긴다.(두개는 항상 같이다닌다)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        initView();
    }

    public void initView() {
        editTitle = (EditText) findViewById(R.id.editTitle);
        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editContent = (EditText) findViewById(R.id.editContent);
    }

    /**
     * 뒤로가기 버튼 눌렀을 시
     */
    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    /**
     * 기기 버튼(뒤로가기) 눌렀을 시
     */
    @Override
    public void onBackPressed() {
        this.finish();
    }

    /**
     * Appbar 메뉴 생성 초기화
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write, menu);
        return true;
    }

    /**
     * Appbar 메뉴 선택 이벤트
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //메뉴 추가
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_write) {
            showDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Dialog 호출
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("저장");
        builder.setIcon(R.drawable.ic_save_black);
        builder.setMessage("현재 작성한 메모를 저장하시겠습니까?");
        String positiveText = "확인";
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Memo memo = getMemoFromScreen();
                write(memo);
            }
        });

        String negativeText = "취소";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 화면에 작성한 EditText 를 Memo 에 저장
     *
     * @return Memo : 작성된 Memo 객체
     */
    private Memo getMemoFromScreen() {
        Memo memo = new Memo();
        memo.setNo(1);
        memo.setTitle(editTitle.getText().toString());
        memo.setAuthor(editAuthor.getText().toString());
        memo.setContent(editContent.getText().toString());
        memo.setDateTime(System.currentTimeMillis());

        return memo;
    }

    /**
     * File 작성
     *
     * @param memo
     */
    private void write(Memo memo) {
        String fileName = System.currentTimeMillis()+".txt";
        try {
            FileUtil.write(getBaseContext(), fileName, memo.toString());
            Toast.makeText(getBaseContext(), "등록완료!", Toast.LENGTH_LONG).show();

            /**
             * 나를 호출한 Activity 에 성공(RESULT_OK) 또는 실패(RESULT_CANCEL) 값을 넘겨줌
             * OR
             * Intent 의 값을 넘겨줄 수 있음
             *
             * Intent intent = new Intent();
             * intent.putExtra("ResultValue", 82028202);
             *
             * setResult(RESULT_OK, intent);
             */
            setResult(RESULT_OK);
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "에러 : " + e.toString(), Toast.LENGTH_LONG).show();
        }
        finish();
    }

}

