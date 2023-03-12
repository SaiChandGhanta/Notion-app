package com.neu.info7205.todo.service;

import com.neu.info7205.todo.dao.AttachmentRepository;
import com.neu.info7205.todo.model.Attachment;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Override
    public Attachment findById(int id) {
        return attachmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Attachment> findAll(int taskId) {
        return attachmentRepository.findByTaskId(taskId);
    }

    @Override
    public void deleteAttachment(int id) {
        attachmentRepository.deleteById(id);
    }

    @Override
    public Attachment save(Attachment attachment) {
        if(findAll(attachment.getTaskId()).size()<5){
            return attachmentRepository.save(attachment);
        }
        else{
            throw new SystemException("Only 5 Attachments are allowed for a task", ErrorCode.ATTACHMENT_LIMIT_EXCEEDED);
        }

    }

    @Override
    public void deleteAttachementsForTask(int taskId) {
        attachmentRepository.deleteAll(findAll(taskId));
    }
}
