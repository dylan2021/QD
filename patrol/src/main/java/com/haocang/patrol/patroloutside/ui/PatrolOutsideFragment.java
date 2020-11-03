package com.haocang.patrol.patroloutside.ui;

import android.content.Context;
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
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.ui.CommonActivity;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.PatrolConstans;
import com.haocang.patrol.manage.ui.PatrolErrorFragment;
import com.haocang.patrol.patrolinhouse.ui.PatrolPointListFragment;

/**
 * 厂外巡检
 * 厂外巡检可以切换地图内巡检和列表查看
 * Created by william on 2018/4/3.
 */
@Route(path = ArouterPathConstants.Patrol.PATROL_OUTSIDE)
public class PatrolOutsideFragment extends Fragment implements View.OnClickListener {
    /**
     *
     */
    private Context ctx;
    /**
     *
     */
    private TextView patrolListtv;
    /**
     *
     */
    private TextView patrolMapTv;

    private TextView patrolStateTv;

    private TextView stateTv;//开始巡检的显示倒计时 ，反之显示状态

    private TextView compleCountTv;

    private TextView patrolMileageTv;

    private View patrolMapView;

    private View patrolMapList;

    private ImageView commonIv;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.patrol_outside_fragment, null);
        ctx = getActivity();
        initView(view);
        return view;
    }

    private void initView(final View view) {
        commonIv = view.findViewById(R.id.common_iv);
        if (PatrolConstans.STATE_ABNARMAL.equals(getActivity().getIntent().getStringExtra("state"))) {
            commonIv.setVisibility(View.VISIBLE);
            commonIv.setBackgroundResource(R.mipmap.icon_patrol_add);
        }
        patrolListtv = view.findViewById(R.id.patrol_list_tv);
        patrolMapTv = view.findViewById(R.id.patrol_map_tv);
        patrolStateTv = view.findViewById(R.id.patrol_state_tv);
        compleCountTv = view.findViewById(R.id.comple_count_tv);
        patrolMileageTv = view.findViewById(R.id.patrol_mileage_tv);
        patrolMapView = view.findViewById(R.id.patrol_map_view);
        patrolMapList = view.findViewById(R.id.patrol_map_list);
        stateTv = view.findViewById(R.id.state_tv);
        view.findViewById(R.id.patrol_list_tv).setOnClickListener(this);
        view.findViewById(R.id.patrol_map_tv).setOnClickListener(this);
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getTaskName());
        selectMap();
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
     * @return
     */
    public String getTaskName() {
        String taskName = getActivity().getIntent().getStringExtra("taskName");
        return taskName;
    }


    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.patrol_list_tv) {
            selectList();
        } else if (v.getId() == R.id.patrol_map_tv) {
            selectMap();
        }
    }

    /**
     *
     */
    private Fragment fragment;

    /**
     * 选择地图
     */
    private void selectMap() {
        patrolMapView.setVisibility(View.VISIBLE);
        patrolMapList.setVisibility(View.INVISIBLE);
        patrolMapTv.setTextSize(17);
        patrolListtv.setTextSize(15);
        patrolListtv.getPaint().setFakeBoldText(false);
        patrolMapTv.getPaint().setFakeBoldText(true);
        fragment = new PatrolOutsiteMapFragment();
        ((PatrolOutsiteMapFragment) fragment).setPatrolData(new PatrolOutsiteMapFragment.PatrolStateInterface() {
            @Override
            public void setState(String showState, String state) {
                stateTv.setText("状态：");
                patrolStateTv.setText("   " + showState);
                patrolStateTv.setTextColor(Color.parseColor(getStateColor(state)));
            }

            @Override
            public void setCompleCount(String compleCount) {
                compleCountTv.setText(compleCount);
            }

            @Override
            public void setMileage(String mileage) {
                patrolMileageTv.setText(mileage);
            }

            @Override
            public void setStartDownTime(String time) {
                patrolStateTv.setText(time);
                stateTv.setText("倒计时：");
//                patrolStateTv.setTextSize(13);
                patrolStateTv.setTextColor(Color.parseColor("#ff3c3c3c"));
            }
        });
        toFragment(fragment);
    }

    /**
     * 选择列表
     */
    private void selectList() {
        patrolMapTv.setTextSize(15);
        patrolListtv.setTextSize(17);
        patrolListtv.getPaint().setFakeBoldText(true);
        patrolMapTv.getPaint().setFakeBoldText(false);
        patrolMapView.setVisibility(View.INVISIBLE);
        patrolMapList.setVisibility(View.VISIBLE);
        fragment = new PatrolPointListFragment();
        ((PatrolPointListFragment) fragment).setPatrolPointListData(new PatrolPointListFragment.PatrolPointListDataInterface() {
            @Override
            public void setCompleCount(String count) {
                compleCountTv.setText(count);
            }

            @Override
            public void setState(String showState, String state) {
                stateTv.setText("状态：");
                patrolStateTv.setText("   " + showState);
                patrolStateTv.setTextColor(Color.parseColor(getStateColor(state)));
            }

            @Override
            public void setDownTime(String time) {
                patrolStateTv.setText(time);
                stateTv.setText("倒计时：");
//                patrolStateTv.setTextSize(13);
                patrolStateTv.setTextColor(Color.parseColor("#ff3c3c3c"));
            }

            @Override
            public void setMileage(String mileage) {
                patrolMileageTv.setText(mileage);
            }
        });
        toFragment(fragment);
    }

    /**
     * 跳转到对应到Fragment
     *
     * @param patrolOutsiteMapFragment 厂外巡检
     */
    private void toFragment(final Fragment patrolOutsiteMapFragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.patrol_container_fl, patrolOutsiteMapFragment);
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

    public static String getStateColor(final String state) {
        String resource = "#ff80bf22";
        if (PatrolConstans.STATE_UNASSIGNED.equals(state)) {
            resource = "#F5A623";
        } else if (PatrolConstans.STATE_TOBEEXCUTED.equals(state)) {
            resource = "#80BF22";
        } else if (PatrolConstans.STATE_EXECUTING.equals(state)) {
            resource = "#80BF22";
        } else if (PatrolConstans.STATE_INTERRUPT.equals(state)) {
            resource = "#C8C8C8";
        } else if (PatrolConstans.STATE_ABNARMAL.equals(state)) {
            resource = "#C8C8C8";
        } else if (PatrolConstans.STATE_FINISHG.equals(state)) {
            resource = "#C8C8C8";
        }
        return resource;
    }

}
