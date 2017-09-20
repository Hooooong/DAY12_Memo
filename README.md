# 메모장 예제

### 설명
____________________________________________________

![Memo](https://github.com/Hooooong/DAY12_Memo/blob/master/image/Android%20Memo.gif)

- ListView 를 통한 메모장 리스트 나열
- File I/O 를 통해 메모 작성


### KeyPoint
____________________________________________________

- File I/O

  - 안드로이드는 File Input 에는 2가지 종류가 있다.

      1. 내부저장소 - Internal : 개별 APP만 접근 가능, 파일 탐색기에서 보이지 않음

      2. 외부 저장소 - External : 모든 APP이 접근 가능(권한 필요)

  - 안드로이드는 File I/O 를 손쉽게 지원하는 메소드가 있다.

      - Input : `openFileInput(파일명)`

      - Output : `openFileOutput(파일명, MODE 속성)`

  - 파일 목록을 가져오는 방법

      - `getFileDir()` : 파일 경로를 불러올 수 있다. 파일 경로는 `/data/data/[패키지 이름]/files` 형식으로 되어 있다.

      - `getFilesDir().getAbsolutePath()` : 파일의 절대 경로를 불러온다.

      - `getFilesDir().listFiles()` 는 `/data/data/[패키지 이름]/files` 에 있는 File들의 목록을 불러온다.

      ```java
      // 첫번째 파일 목록 가져오는 법
      // 파일 목록 가져오기
      // 1. 파일이 있는 Directory 절대 경로를 가져와야 한다.
      String dir_path = getFilesDir().getAbsolutePath();
      // 1-1. 파일을 생성
      File file = new File(dir_path);

      // 1-2. 파일의 목록을 배열로 반환
      File files[] = file.listFiles();

      for(File item : files){
          System.out.println(item.getName());
      }

      // 두번째 파일 목록 가져오는 법
      // 파일 목록 가져오기
      File files[] = getFilesDir().listFiles();
      ArrayList<String> list = new ArrayList<>();
      for(File item : files){
          list.add(item.getName());
      }
      ```

  - Stream 중간 처리를 하지 않고 Buffer 그대로 처리

      - 읽을 Buffer의 크기(byte)를 설정한 후 While()문을 통해 처리

      - 특이하게도 `BufferdInputStream객체.read(byte객체)`를 하면 읽는 순간 byte 객체에 데이터가 들어가고 읽은 크기만큼 return해준다.

      ```java
      // 버퍼를 달고
      bis = new BufferedInputStream(fis);
      // 한번에 읽어올 버퍼 양을 설정한다.
      byte buffer[] = new byte[1024];
      // 현재 읽은 양을 담는 변수 설정
      int count = 0;
      // 읽은 값을 buffer 에 넣고, count 에 넣은 크기를 반환한다.
      while ((count = bis.read(buffer)) != -1) {
          // readLine 과는 다르게 \n 을 문자열로 인식하기 때문에
          // 더해줄 필요가 없다.
          String data = new String(buffer, 0, count);
          stringBuffer.append(data);
      }
      ```

- Activity 의 생명주기 와 Activity Result, Style.xml

    - 참조 : [Activity 생명주기, Acitivty 결과](https://github.com/Hooooong/DAY13_Activity_etc)

### Code Review
____________________________________________________

- Memo.java

  - Memo 객체를 생성하기 위한 생성자 overloading

  - Memo 객체를 출력하기 위한 toString Override

  - Intent 로 객체 자체를 넘겨주기 위해서는 `Serializable` 을 implements 해야 한다.

  ```java
  public class Memo implements Serializable {

      private static final String DELIMETER = "//";

      private int no;
      private String title;
      private String author;
      private String content;
      private long dateTime;

      public Memo() {}

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
  ```

- FileUtil.java

  - File I/O 를 위한 클래스

  - Output : `openFileOutput(파일명, MODE 코드)` 메소드를 사용하여 FileOutPutStream 설정 및 `write(byte단위 내용)`으로 파일을 출력한다.

  - Input :  `openFileInput(파일명)` 메소드를 사용하여 FileInputStream 설정 및 Stream 을 통해 파일을 읽는다.

  ```java
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
              // 읽은 값을 buffer 에 넣고, count 에
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
  }
  ```

- ListActivity.java

  - ListView 와 Adapter 설정한다.( FileUtil 클래스를 사용해 파일을 읽어 Adapter 에 데이터를 넘겨준다. )

  - startActivityForResult를 통해 호출된 Activity 의 결과 로직을 설정한다.

  ```java
  public class ListActivity extends AppCompatActivity {

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

      private void init() {
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
      private ArrayList<Memo> loadData() {

          ArrayList<Memo> memoList = new ArrayList<>();
          // 파일 목록에서 파일 하나씩 꺼낸 후에
          // Memo 클래스로 변환한 후 memoList 에 담는다.
          for (File item : getFilesDir().listFiles()) {
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

      private void initVIew() {
          listView = (ListView) findViewById(R.id.listView);
          btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
      }

      private static final int WRITE_INTENT = 99;

      private void initListener() {
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
          switch (requestCode) {
              case WRITE_INTENT:
                  if (resultCode == RESULT_OK) {
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
  ```

- ListAdapter.java

    - Holder 패턴을 사용하여 ListView 성능을 최대화한다.

    - Intent 에 값을 넘겨줄 때 Serialize 을 통해 객체 자체를 넘겨준다.

    ```java
    public class ListAdapter extends BaseAdapter {
        // 데이터 저장소를 Adapter 내부에 저장하는것이 관리하기 편하다.

        Context context;
        List<Memo> memoList;

        ListAdapter(Context context, List<Memo> memoList) {
            this.context = context;
            this.memoList = memoList;
        }

        @Override
        public int getCount() {
            // 현재 데이터의 총 갯수
            return memoList.size();
        }

        @Override
        public Object getItem(int position) {
            // 현재 뿌려질 데이터를 리턴해준다.
            // 호출되는 목록아이템의
            return memoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // VIew 의 Id 를 리턴
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            Memo memo = memoList.get(position);
            holder.setTextNo(memo.getNo());
            holder.setTextTitle(memo.getTitle());
            holder.setTextDateTime(memo.getDateTime());
            holder.setPosition(position);

            return convertView;
        }

        class Holder {
            private int position;
            private TextView textNo;
            private TextView textTitle;
            private TextView textDateTime;

            public Holder(View view) {
                textNo = (TextView) view.findViewById(R.id.textNo);
                textTitle = (TextView) view.findViewById(R.id.textTitle);
                textDateTime = (TextView) view.findViewById(R.id.textDateTime);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), DetailActivity.class);
                        // Intent 에 값을 넘겨주기 위해 putExtra 를 사용한다.

                        /**
                         * 값을 넘겨주는 방법
                         *
                         *
                         * 1. Intent 를 통해 객체를 Serialize 하여 넘겨준다.
                         * 2. Intent 를 통해 모든 값들을 넘겨준다.
                         *  - 위의 1, 2 번은 값을 복사하기 때문에 메모리 낭비가 될 수 있다.
                         *
                         * 3. ArrayList에 저장된 데이터를 static 으로 설정하여 Activity 에서 사용한다.
                         */
                        intent.putExtra("memo", memoList.get(position));
                        v.getContext().startActivity(intent);
                    }
                });
            }

            public void setTextNo(int no) {
                textNo.setText(no+"");
            }

            public void setTextTitle(String title) {
                textTitle.setText(title);
            }

            public void setPosition(int position){
                this.position = position;
            }

            public void setTextDateTime(long dateTime) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                textDateTime.setText(simpleDateFormat.format(dateTime));
            }
        }
    }
    ```

- WriteActivity.java

    - FileUtil.write 를 이용해 작성한 Memo 값을 저장한다.

    - 작성이 성공적으로 되면 onActivityResult 를 위해 `setResult(RESULT_OK)` 를 통해 requestCode 를 넘겨준다.

    ```java
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
    ```    

- DetailActivity.java

    - 넘겨받은 Memo 객체를 활용해 화면에 그려준다.

    ```java
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
    ```
