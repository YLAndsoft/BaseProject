package pro.base.com.baseproject.demo1;

import android.content.Context;
import com.shizhefei.mvc.IDataSource;

import java.util.List;

import pro.base.com.baseproject.demo1.entity.User;

/**
 * Created by DN on 2018/2/8.
 */

public class UserDataSource implements IDataSource<List<User>> {

    public int page = 0;
    private int maxPage = 4;
    private Context context;
    public UserDataSource(Context context){
        this.context = context;
    }

    @Override
    public List<User> refresh() throws Exception {

        return loadUser(1);
    }

    @Override
    public List<User> loadMore() throws Exception {

        return loadUser(page + 1);
    }

    @Override
    public boolean hasMore() {
        return page<maxPage;
    }

    private List<User> loadUser(int page) {
        List<User>  user = DataUtils.getUserData("User"+page+".json",context);
        this.page = page;
        return user;
    }

}
