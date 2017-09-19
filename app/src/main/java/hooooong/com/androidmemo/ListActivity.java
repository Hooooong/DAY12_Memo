package hooooong.com.androidmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hooooong.com.androidmemo.domain.Memo;

public class ListActivity extends AppCompatActivity {

    List<Memo> memoList = new ArrayList<>();

    ListView listView;
    FloatingActionButton btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setTitle("MemoList");


        for(int i = 0 ; i<100; i++){
            Memo memo = new Memo();
            memo.setTitle("타이틀 : " + i);
            memo.setAuthor("글쓴이 : " + i);
            memo.setNo(i);
            memoList.add(memo);
        }

        initVIew();
        initListener();
    }

    public void initVIew(){
        listView = (ListView)findViewById(R.id.listView);
        btnAdd = (FloatingActionButton)findViewById(R.id.btnAdd);

        CustomAdapter adapter = new CustomAdapter(getBaseContext(), memoList);
        listView.setAdapter(adapter);
    }

    public void initListener(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
