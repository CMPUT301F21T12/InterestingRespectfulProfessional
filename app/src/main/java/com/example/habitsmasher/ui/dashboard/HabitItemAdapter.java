package com.example.habitsmasher.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitsmasher.Habit;
import com.example.habitsmasher.HabitList;
import com.example.habitsmasher.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;

import java.util.HashSet;
import java.util.Locale;

/**
 * The habit item adapter is the custom adapter for the RecyclerView that holds the habit list
 */
public class HabitItemAdapter extends FirestoreRecyclerAdapter<Habit, HabitItemAdapter.HabitViewHolder> {

    private static final String DATE_PATTERN = "dd-MM-yyyy";
    private static final Locale LOCALE = Locale.CANADA;
    private static HabitList _habits;
    private static HabitListFragment _habitListFragment;
    private final String _username;
    public final ObservableSnapshotArray<Habit> _snapshots;
    private final FragmentActivity _activity;
    private Context _context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     * @param options the firestore entities
     * @param activity
     * @param habits the habit list
     * @param fragment the habitListFragment
     * @param username the username of the current user
     */
    public HabitItemAdapter(@NonNull FirestoreRecyclerOptions<Habit> options,
                            FragmentActivity activity,
                            HabitList habits,
                            HabitListFragment fragment,
                            String username) {
        super(options);
        _snapshots = options.getSnapshots();
        _activity = activity;
        _habits = habits;
        _habitListFragment = fragment;
        _username = username;
    }



    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        _context = parent.getContext();
        View view = LayoutInflater.from(_context).inflate(R.layout.habit_row, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull HabitViewHolder holder,
                                    int position,
                                    @NonNull Habit habit) {
        // set necessary elements of the habit
        holder._habitTitle.setText(habit.getTitle());


        setOnClickListenerForHabit(holder, position);
    }

    /** This method sets the click listener for each habit row
     * @param holder the ViewHolder that holds each habit
     * @param position the position of the clicked element
     */
    private void setOnClickListenerForHabit(@NonNull HabitViewHolder holder, int position) {
        holder._habitRows.setOnClickListener(view -> openHabitView(holder, position));
    }

    /**
     * This function opens the habit view
     *
     * @param holder   This holds the values for the selected habit item
     * @param position This is the position of the selected habit item
     */
    private void openHabitView(HabitViewHolder holder, int position) {
        // Get the selected habit
        Habit currentHabit = _snapshots.get(position);
        // Create a bundle to be passed into the habitViewFragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("habit", currentHabit);
        NavController controller = NavHostFragment.findNavController(_habitListFragment);
        // Navigate to the habitViewFragment
        controller.navigate(R.id.action_navigation_dashboard_to_habitViewFragment, bundle);
    }

    @Override
    public int getItemCount() {
        return _snapshots.size();
    }


    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        private final TextView _habitTitle;
        private final ConstraintLayout _habitRows;
        private Button _editButton;
        private Button _deleteButton;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            _habitRows = itemView.findViewById(R.id.habit_rows);
            _habitTitle = itemView.findViewById(R.id.habit_title);
        }
    }
}

