package com.neu.info7205.todo.service;

import com.neu.info7205.todo.model.Reminder;

import java.util.List;

public interface ReminderService {
    public Reminder addReminder(Reminder reminder);
    public List<Reminder> getAllReminders(int taskId);
    public void deleteRemindersForTask(int taskId);
}
