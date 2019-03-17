package com.fifzu.goalsetter;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NavInfo extends Fragment{

    MainActivity myActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.nav_info, container, false);
        myActivity = (MainActivity) getActivity();



        TextView tvVersion = view.findViewById(R.id.tvVersion);
        PackageInfo pInfo = null;
        try {
            pInfo = getContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            String version = pInfo.versionName;
            tvVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView tvLastUpdate = view.findViewById(R.id.tvLastUpdate);
        tvLastUpdate.setText(myActivity.getLastUpdated());

        TextView tvDaysSinceStart = view.findViewById(R.id.tvDaysSinceStart);
        tvDaysSinceStart.setText(myActivity.getDaysSinceStart());

        int[] savedStatistics = myActivity.getSavedStatistics();

        TextView tvLeftSolvedShorts = view.findViewById(R.id.tvLeftSolvedShorts);
        tvLeftSolvedShorts.setText(Integer.toString(savedStatistics[0]));
        TextView tvLeftSolvedMediums = view.findViewById(R.id.tvLeftSolvedMediums);
        tvLeftSolvedMediums.setText(Integer.toString(savedStatistics[1]));
        TextView tvLeftSolvedLong = view.findViewById(R.id.tvLeftSolvedLong);
        tvLeftSolvedLong.setText(Integer.toString(savedStatistics[2]));
        TextView tvLeftSolvedFortune = view.findViewById(R.id.tvLeftSolvedFortune);
        tvLeftSolvedFortune.setText(Integer.toString(savedStatistics[3]));
        TextView tvLeftSolvedKnowledge = view.findViewById(R.id.tvLeftSolvedKnowledge);
        tvLeftSolvedKnowledge.setText(Integer.toString(savedStatistics[4]));
        TextView tvLeftSolvedReputation = view.findViewById(R.id.tvLeftSolvedReputation);
        tvLeftSolvedReputation.setText(Integer.toString(savedStatistics[5]));
        TextView tvLeftSolvedRomance = view.findViewById(R.id.tvLeftSolvedRomance);
        tvLeftSolvedRomance.setText(Integer.toString(savedStatistics[6]));
        TextView tvLeftSolvedFamily = view.findViewById(R.id.tvLeftSolvedFamily);
        tvLeftSolvedFamily.setText(Integer.toString(savedStatistics[7]));

        int total =0;

        for (int i = 0;i<3;i++){
            total=total+savedStatistics[i];
        }
        TextView tvTotal = view.findViewById(R.id.tvTotal);
        tvTotal.setText(Integer.toString(total));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.info));
    }
}
