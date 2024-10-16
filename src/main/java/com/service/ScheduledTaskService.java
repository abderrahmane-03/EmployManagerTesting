package com.service;

import com.DAO.imp.EvaluationDAO;
import com.entity.Evaluation;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ScheduledTaskService {

    private EvaluationDAO evaluationDAO;
    private EmailService emailService;

    public ScheduledTaskService(EvaluationDAO evaluationDAO, EmailService emailService) {
        this.evaluationDAO = evaluationDAO;
        this.emailService = emailService;
    }

    public void startScheduledTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // Schedule the task to run every day at noon
        long initialDelay = calculateInitialDelayForNoon();
        scheduler.scheduleAtFixedRate(this::sendEvaluationReminder, initialDelay, 24, TimeUnit.HOURS);
    }

    private void sendEvaluationReminder() {
        List<Evaluation> upcomingEvaluations = evaluationDAO.getPendingEvaluationsWithin(7);  // Due in 7 days
        for (Evaluation eval : upcomingEvaluations) {
            String subject = "Reminder: Performance Evaluation Due";
            String message = "Please complete the performance evaluation for " + eval.getEmployee().getName() + " by " + eval.getDueDate();
            emailService.sendEmail(eval.getManager().getEmail(), subject, message);
        }
    }

    private long calculateInitialDelayForNoon() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextNoon = now.withHour(12).withMinute(0).withSecond(0);

        // If it's already past noon today, schedule for tomorrow
        if (now.isAfter(nextNoon)) {
            nextNoon = nextNoon.plusDays(1);
        }

        return ChronoUnit.MILLIS.between(now, nextNoon);
    }
}
