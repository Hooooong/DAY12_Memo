package hooooong.com.androidmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hooooong.com.androidmemo.domain.Memo;
import hooooong.com.androidmemo.util.FileUtil;

public class ListActivity extends AppCompatActivity {

    List<Memo> memoList = new ArrayList<>();

    ListView listView;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setTitle("MemoList");

        initVIew();
        initListener();

        init();
        loadData();
    }

    /**
     * List 의 갱신을 위해서
     * 가장 간편한 방법은 init() 을 다시 호출한다.
     */
    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init(){
        Log.d("ListActivity", "Called init()");

        ArrayList<Memo> memoList = loadData();
        ListAdapter adapter = new ListAdapter(getBaseContext(), memoList);
        listView.setAdapter(adapter);
    }

    /**
     * 파일 읽어서 Memo 로 만드는 작업
     *
     * @return
     */
    private ArrayList<Memo> loadData(){

        // 첫번째 파일 목록 가져오는 법
        // 파일 목록 가져오기
        /*
        // 1. 파일이 있는 Directory 절대 경로를 가져와야 한다.
        String dir_path = getFilesDir().getAbsolutePath();
        // 1-1. 파일을 생성
        File file = new File(dir_path);

        // 1-2. 파일의 목록을 배열로 반환
        File files[] = file.listFiles();

        for(File item : files){
            System.out.println(item.getName());
        }*/

        // 두번째 파일 목록 가져오는 법
        /*
        // 파일 목록 가져오기
        File files[] = getFilesDir().listFiles();

        ArrayList<String> list = new ArrayList<>();
        for(File item : files){
            list.add(item.getName());
        }*/

        // 세번째 파일 목록 가져오는 법
        //return getFilesDir().listFiles();


        ArrayList<Memo> memoList = new ArrayList<>();
        // 파일 목록에서 파일 하나씩 꺼낸 후에
        // Memo 클래스로 변환한 후 memoList 에 담는다.
        for(File item : getFilesDir().listFiles()){
            Memo memo;
            try {
                String text = FileUtil.read(getBaseContext(), item.getName());
                memo = new Memo(text);
                memoList.add(memo);
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "에러 : " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return memoList;
    }

    private void initVIew(){
        listView = (ListView)findViewById(R.id.listView);
        btnAdd = (FloatingActionButton)findViewById(R.id.btnAdd);


    }

    private static final int WRITE_INTENT = 99;
            private void initListener(){
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), WriteActivity.class);
                        //startActivity(intent);

                // startActivityForResult( intent, 호출된 코드)
                startActivityForResult(intent, WRITE_INTENT);
            }
        });
    }

    /**
     * startActivityForResult 를 통해 호출된 엑티비티가 종료되는 순간 호출되는 함수
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case WRITE_INTENT:
                if(resultCode == RESULT_OK){
                    /**
                     * getStringExtra();
                     *  - StringExtra 만 초기값이 null 이기 때문에 설정을 해주지 않아도 되지만
                     * getIntExtra(, 초기값);
                     *  - Int, Long, Float 등 데이터형은 null 이 없기 때문에 초기값 설정을 해줘야 한다.
                     */

                    //int resultValue = data.getIntExtra("ResultValue", 0);
                    init();
                }
                break;
        }
    }
}
