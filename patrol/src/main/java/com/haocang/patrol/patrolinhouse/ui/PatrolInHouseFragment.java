package com.haocang.patrol.patrolinhouse.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.ui.CommonActivity;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.PatrolConstans;
import com.haocang.patrol.manage.ui.PatrolErrorFragment;
import com.haocang.patrol.patroloutside.ui.PatrolOutsideFragment;

/**
 * 厂内巡检
 * Created by william on 2018/4/3.
 */

@Route(path = "/patrol/inhouse")
public class PatrolInHouseFragment extends Fragment {
    private TextView patrolStateTv;
    private TextView patrolStateTitleTv;
    private TextView patrolItemTv;
    private ImageView commonIv;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.patrol_inhouse_fragment, null);
        initView(view);
        return view;
    }

    /**
     *
     */
    private Fragment fragment = null;

    private void initView(final View view) {
        commonIv = view.findViewById(R.id.common_iv);
        if (PatrolConstans.STATE_ABNARMAL.equals(getActivity().getIntent().getStringExtra("state"))) {
            commonIv.setVisibility(View.VISIBLE);
            commonIv.setBackgroundResource(R.mipmap.icon_patrol_add);
        }
        patrolStateTv = view.findViewById(R.id.patrol_state_tv);
        patrolItemTv = view.findViewById(R.id.item_count_tv);
        patrolStateTitleTv = view.findViewById(R.id.patrol_down_tv);
        fragment = new PatrolPointListFragment();
        ((PatrolPointListFragment) fragment).setPatrolPointListData(new PatrolPointListFragment.PatrolPointListDataInterface() {
            @Override
            public void setCompleCount(String count) {
                patrolItemTv.setText(count);
            }

            @Override
            public void setState(String showState, String state) {
                patrolStateTitleTv.setText("状态：");
                patrolStateTv.setText(showState);
                patrolStateTv.setTextColor(Color.parseColor(PatrolOutsideFragment.getStateColor(state)));
            }

            @Override
            public void setDownTime(String time) {
                patrolStateTv.setText(time);
                patrolStateTitleTv.setText("倒计时：");
                patrolStateTv.setTextColor(Color.parseColor("#ff9b9b9b"));
            }

            @Override
            public void setMileage(String mileage) {

            }
        });
        toFragment(fragment);
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getTaskName());
        commonIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("fragmentName", PatrolErrorFragment.class.getName());
                intent.putExtra("taskId", getActivity().getIntent().getIntExtra("taskId", 0));
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 获取任务名称
     *
     * @return
     */
    public String getTaskName() {
        String taskName = getActivity().getIntent().getStringExtra("taskName");
        return taskName;
    }

    private void toFragment(final Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.patrol_container_inhouse, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
