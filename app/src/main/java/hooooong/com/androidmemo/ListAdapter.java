package hooooong.com.androidmemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import hooooong.com.androidmemo.domain.Memo;

/**
 * Created by Android Hong on 2017-09-19.
 */

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
