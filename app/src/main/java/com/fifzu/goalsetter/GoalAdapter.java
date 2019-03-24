package com.fifzu.goalsetter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;

class GoalAdapter extends BaseAdapter {

    Context context;
   // String[] data;
   ArrayList<Goal> goalList;
   private MainActivity mainActivity;

    private static LayoutInflater inflater = null;

    public GoalAdapter(Context context, ArrayList<Goal> goalList, MainActivity mainActivity) {
        this.context = context;
        this.goalList = goalList;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return goalList.size();
    }

    @Override
    public Object getItem(int position) {
        return goalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.goal_row, null);

        ImageView image = vi.findViewById(R.id.goalImage);
        image.setImageResource(goalList.get(position).getGoalIcon());

        TextView goalName = vi.findViewById(R.id.goalName);
        goalName.setText(goalList.get(position).getName());

        TextView goalCount = vi.findViewById(R.id.goalCount);
        goalCount.setText(goalList.get(position).getCount().toString());

        /*
        TextView goalDaysLeft = vi.findViewById(R.id.goalDaysLeft);
        String validUntil = mainActivity.formatDuration(LocalDateTime.now(),goalList.get(position).getValidUntilDate());
        goalDaysLeft.setText(validUntil);
*/

        TextView goalValid = vi.findViewById(R.id.goalValid);
        String validUntil = " (" + mainActivity.formatDuration(LocalDateTime.now(),goalList.get(position).getValidUntilDate()) + ")";

        goalValid.setText(goalList.get(position).getValidUntil()+validUntil);

        CheckBox chkFixed = vi.findViewById(R.id.chkFixed);
        chkFixed.setChecked(goalList.get(position).getFixed());



        LinearLayout llLayout = vi.findViewById(R.id.goalDescription);

        final int goalID  = position;
        final MainActivity finalMainActivity = mainActivity;

        chkFixed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkGoal(goalList.get(goalID).getUniqueID(),((CheckBox) v).isChecked(), finalMainActivity);
            }
        });


        llLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                PopupMenu popup = new PopupMenu(context, view);
                popup.getMenuInflater().inflate(R.menu.goal_popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(final MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                deleteGoal(goalList.get(goalID).getUniqueID(),goalList.get(goalID).getName(),finalMainActivity);
                                break;
                            case R.id.confirm:
                                confirmGoal(goalList.get(goalID).getUniqueID(),goalList.get(goalID).getName(),finalMainActivity);
                                break;
                            case R.id.edit:
                                editGoal(goalList.get(goalID).getUniqueID(),finalMainActivity);
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        return vi;
    }

    private void deleteGoal(final int index, String goatName, final MainActivity myActivity) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        myActivity.deleteGoalWithID(index);
                        changeFragment(R.id.nav_manage_goals);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.delete_message) +"\n" + goatName).setPositiveButton(context.getString(R.string.yes), dialogClickListener)
                .setNegativeButton(context.getString(R.string.no), dialogClickListener).show();
    }

    private void confirmGoal(final int index, String goatName, final MainActivity myActivity) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        myActivity.confirmGoalWithID(index);
                        changeFragment(R.id.nav_manage_goals);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.confirm_message) +"\n" + goatName).setPositiveButton(context.getString(R.string.yes), dialogClickListener)
                .setNegativeButton(context.getString(R.string.no), dialogClickListener).show();
    }


    private void checkGoal(final int index, Boolean check, final MainActivity myActivity) {
        myActivity.checkGoal(index, check);
        this.notifyDataSetChanged();
    }



    private void editGoal(final int index, final MainActivity myActivity) {
        myActivity.editGoalWithID(index);
    }

    private void changeFragment(int fragmentId) {
        mainActivity.displayFragment(fragmentId);
    }
}
