package hooooong.com.androidmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import hooooong.com.androidmemo.domain.Memo;

public class DetailActivity extends AppCompatActivity {

    Memo memo;
    TextView textTitle;
    TextView textAuthor;
    TextView textDateTime;
    TextView textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        memo = (Memo)intent.getSerializableExtra("memo");

        initView();
        initData();
    }

    private  void initView(){
        textTitle = (TextView)findViewById(R.id.textTitle);
        textAuthor = (TextView)findViewById(R.id.textAuthor);
        textDateTime = (TextView)findViewById(R.id.textDateTime);
        textContent = (TextView)findViewById(R.id.textContent);
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
}
