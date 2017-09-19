package hooooong.com.androidmemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hooooong.com.androidmemo.domain.Memo;

/**
 * Created by Android Hong on 2017-09-19.
 */

public class CustomAdapter extends BaseAdapter {
    // 데이터 저장소를 Adapter 내부에 저장하는것이 관리하기 편하다.

    Context context;
    List<Memo> memoList;

    CustomAdapter(Context context, List<Memo> memoList) {
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
        holder.textNo.setText(Integer.toString(memoList.get(position).getNo()));
        holder.textTitle.setText(memoList.get(position).getTitle());
        //holder.textDate.setText(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date(memoList.get(position).getDateTime())));

        return convertView;
    }

    class Holder {
        TextView textNo;
        TextView textTitle;
        TextView textDate;

        Holder(View view) {
            textNo = (TextView) view.findViewById(R.id.textNo);
            textTitle = (TextView) view.findViewById(R.id.textTitle);
            textDate = (TextView) view.findViewById(R.id.textDate);
            setOnClickListener();
        }

        // 화면에 보여지는 View 는
        // 기본적으로 자신이 속한 Component 의 Context 를 그대로 가지고 잇다.
        public void setOnClickListener(){
            textTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    // Intent 에 값을 넘겨주기 위해
                    // putExtra 를 사용한다.
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
