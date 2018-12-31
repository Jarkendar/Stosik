package com.jarkendar.stosik;

import java.util.Date;
import java.util.Objects;

public class Task {

    private String title;
    private int priority;
    private Date endDate;

    public Task(String title, int priority, Date endDate) {
        this.title = title;
        this.priority = priority;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return priority == task.priority &&
                Objects.equals(title, task.title) &&
                Objects.equals(endDate, task.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, priority, endDate);
    }
}
