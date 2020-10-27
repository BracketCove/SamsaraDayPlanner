package com.wiseassblog.samsaradayplanner.ui.dayview;

import com.wiseassblog.samsaradayplanner.domain.Day;
import com.wiseassblog.samsaradayplanner.domain.Tasks;

import java.util.Observable;
import java.util.Optional;

/**
 * This class exists solely for the purpose of storing "front end session state". I could also have
 * the Fragment or the Logic class store this state, but I prefer to keep it separate.
 *
 * According to AAC and Android docs, A ViewModel is a hybrid between a logic class and a front end
 * model (caching front end session state).
 *
 * I use ViewModels like the name implies: it stores models necessary to render the View, so that
 * I do not need to reload them every time.
 */
public class DayViewModel implements IDayViewContract.ViewModel {

    private Day day;
    private Tasks tasks;

    @Override
    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    @Override
    public Tasks getTasks() {
        return this.tasks;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public Day getDay() {
        return this.day;
    }
}
