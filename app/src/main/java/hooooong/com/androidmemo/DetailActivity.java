package hooooong.com.androidmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import hooooong.com.androidmemo.domain.Memo;
import hooooong.com.androidmemo.util.FileUtil;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    Memo memo;
    TextView textTitle;
    TextView textAuthor;
    TextView textDateTime;
    TextView textContent;

    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        memo = (Memo)intent.getSerializableExtra("memo");

        initView();
        initData();
        initListener();
    }

    private  void initView(){
        textTitle = (TextView)findViewById(R.id.textTitle);
        textAuthor = (TextView)findViewById(R.id.textAuthor);
        textDateTime = (TextView)findViewById(R.id.textDateTime);
        textContent = (TextView)findViewById(R.id.textContent);

        btnDelete = (Button)findViewById(R.id.btnDelete);
    }

    /**
     * 넘겨받은 Memo 데이터를 통해 TextView 에 넣어주는 메소드
     */
    private void initData(){
        textTitle.setText(memo.getTitle());
        textAuthor.setText(memo.getAuthor());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        textDateTime.setText(simpleDateFormat.format(memo.getDateTime()));

        textContent.setText(memo.getContent());
    }

    private void initListener(){
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDelete:
                if(FileUtil.delete(getBaseContext(), memo.getFileName())){
                    Toast.makeText(getBaseContext(), "파일이 삭제되었습니다.",  Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getBaseContext(), "파일 삭제가 실패하였습니다.",  Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
