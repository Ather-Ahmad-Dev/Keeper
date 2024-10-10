package com.example.keeper;

public class RecyclerViewModelClass {

    private String taskTitle, taskTime, tag, priority;
    private boolean isChecked;

    public RecyclerViewModelClass(String taskTitle, String taskTime, String tag, String priority, boolean isChecked){

        this.taskTitle = taskTitle;
        this.taskTime = taskTime;
        this.tag = tag;
        this.priority = priority;
        this.isChecked = isChecked;

    }
    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public String getTag() {
        return tag;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isChecked() {
        return isChecked;
    }

}
