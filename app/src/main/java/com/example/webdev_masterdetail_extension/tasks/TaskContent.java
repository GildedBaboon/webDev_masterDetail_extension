package com.example.webdev_masterdetail_extension.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskContent {

    private List<TaskItem> ITEMS = new ArrayList<TaskItem>();
    private Map<String, TaskItem> ITEM_MAP = new HashMap<String, TaskItem>();

    public void addItem(int position, String task, String details) {
        TaskItem item = new TaskItem(String.valueOf(position), task, details);
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public int getSize() {
        return ITEMS.size();
    }

    public List<TaskItem> getItems() {
        return ITEMS;
    }

    public Map<String, TaskItem> getItemMap() {
        return ITEM_MAP;
    }

    public void editTask(String id, String content, String details){
        TaskItem temp = new TaskItem(id, content, details);
        int intID = Integer.parseInt(id);
        ITEMS.set(intID, temp);
        ITEM_MAP.put(id, temp);
    }

    public static class TaskItem {
        public final String id;
        public final String content;
        public final String details;

        public TaskItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }
    }
}


