package pro.base.com.baseproject.demo1.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shizhefei.mvc.IDataAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo1.entity.User;

/**
 * Created by DN on 2018/2/7.
 */

public class UserAdapter extends BaseAdapter implements IDataAdapter<List<User>> {

    private List<User> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    public UserAdapter(Context context){
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public void notifyDataChanged(List<User> users, boolean isRefresh) {
        if (isRefresh) {
            mData.clear();
        }
        mData.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public List<User> getData() {
        return mData;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        UserHolder userHolder = null;
        if(view == null){
            userHolder = new UserHolder();
            view = inflater.inflate(R.layout.user_item_layout,null);
            x.view().inject(userHolder,view);
            view.setTag(userHolder);
        }else{
            userHolder = (UserHolder) view.getTag();
        }
        if(mData!=null){
            User user = mData.get(i);
            Glide.with(context).load(user.getUserHeadUrl()).error(R.drawable.ic_launcher).into(userHolder.userHeadUrl);
            userHolder.userName.setText(user.getUserName()+"");
            userHolder.userSignature.setText(user.getUserSignature()+"");
        }
        return view;

    }



    class UserHolder {
        @ViewInject(value = R.id.userHeadUrl)
        ImageView userHeadUrl;
        @ViewInject(value = R.id.userName)
        TextView userName;
        @ViewInject(value = R.id.userSignature)
        TextView userSignature;

    }

}
