package ru.matevosyan.repository;

import org.hibernate.Query;

import ru.matevosyan.entity.Task;
import ru.matevosyan.utils.SessionManager;
import sun.java2d.pipe.SpanShapeRenderer;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * TaskRepository class for holding main method to manipulate with task.
 */
public class TaskRepository {
    private static final SessionManager TRANSACTION = SessionManager.TRANSACTION;

    /**
     * Add task to database.
     * @param task todoList.
     */
    public void add(Task task) {
        task.setCreateDate(new Timestamp(System.currentTimeMillis()));
        TRANSACTION.use(session -> session.save(task));
    }

    /**
     * GetAll method.
     * Get all task.
     * @return list of task
     */
    public List<Task> getAll() {
        return TRANSACTION.use(session -> {
                    Query query = session.createQuery("FROM Task order by createDate desc");
                    return (List<Task>) query.list();
        });
    }

    /**
     * Get the last added task.
     * @return last task
     */
    public Task getLastAddedTask() {
        return TRANSACTION.use(session -> {
            Query query = session.createQuery("FROM Task order by createDate asc");
            List<Task> quryList = (List<Task>) query.list();
            return quryList.get(quryList.size() - 1);
        });
    }

    /**
     * Change task done state in db.
     * @param stateButtonValue done state.
     * @param taskId task id.
     * @return count of updated rows.
     */
    public boolean changeTaskState(Boolean stateButtonValue, Integer taskId) {
        Integer updated = TRANSACTION.use(session -> {
            Query query = session.createQuery("UPDATE Task set done=:done WHERE id=:id");
            query.setBoolean("done", stateButtonValue);
            query.setInteger("id", taskId);
            return query.executeUpdate();
        });
        return updated == 1;
//        return TRANSACTION.use(session -> {
//            Query query = session.createQuery("SELECT done FROM Task WHERE id=:id");
//            query.setInteger("id", taskId);
//            List<Task> queryList = (List<Task>) query.list();
//            return queryList.get(0).getDone();
//        });
    }

//    /**
//     * Change task done state in db.
//     * @param taskId task id.
//     * @return count of updated rows.
//     */
//    public boolean changeTaskState(Integer taskId) {
//        Boolean doneFromDB = TRANSACTION.use(session -> {
//            Query query = session.createQuery("SELECT done FROM Task WHERE id=:id");
//            query.setInteger("id", taskId);
//            List<Task> queryList = (List<Task>) query.list();
//            return queryList.get(0).getDone();
//        });
//
//        boolean change = !doneFromDB;
//        TRANSACTION.use(session -> {
//            Query query = session.createQuery("UPDATE Task set done=:done WHERE taskId=:id");
//            query.setBoolean("done", change);
//            query.setInteger("id", taskId);
//            return query.executeUpdate();
//        });
//
//        return TRANSACTION.use(session -> {
//            Query query = session.createQuery("SELECT done FROM Task WHERE id=:id");
//            query.setInteger("id", taskId);
//            List<Task> queryList = (List<Task>) query.list();
//            return queryList.get(0).getDone();
//        });
//    }
}