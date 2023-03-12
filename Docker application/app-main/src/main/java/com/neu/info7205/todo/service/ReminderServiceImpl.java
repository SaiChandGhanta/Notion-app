package com.neu.info7205.todo.service;

import com.neu.info7205.todo.dao.ReminderRepository;
import com.neu.info7205.todo.model.Reminder;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService{
    @Autowired
    ReminderRepository reminderRepository;

    @Override
    public Reminder addReminder(Reminder reminder){
        //check if taskId and listId exists for a user
        reminder.setReminder_created(new Date());
        reminder.setReminder_modified(new Date());
        if(getAllReminders(reminder.getTaskId()).size() < 5){
            return reminderRepository.save(reminder);
        }
        else
            throw new SystemException("Only 5 reminders are allowed", ErrorCode.REMINDER_LIMIT_EXCEEDED);

    }

    @Override
    public List<Reminder> getAllReminders(int taskId){
        //check if taskId and listId are for user
       return reminderRepository.findAllRemindersByTaskId(taskId);
    }

    @Override
    public void deleteRemindersForTask(int taskId) {
         reminderRepository.deleteAll(getAllReminders(taskId));
    }
}
