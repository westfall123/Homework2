package com.example.westf.homework2;

import java.util.Stack;

public class Edit {

    Stack<int[]> undo;
    Stack<int[]> redo;
    ClockController ck;

    public Edit(ClockController ck) {
        this.ck = ck;
        undo = new Stack();
        redo = new Stack();
    }

    public void addNewundo(int[] time) {
        undo.push(time);
    }

    public void undo(int[] currentTime) {
        if (checkundo()) {
            int[] tmp = undo.pop();
            ck.setTime(tmp[0], tmp[1], tmp[2]);
            redo.push(currentTime
            );
        }
    }

    public void redo(int[] currentTime) {
        if (checkRedo()) {
            checkRedo();
            int[] tmp = redo.pop();
            ck.setTime(tmp[0], tmp[1], tmp[2]);
            undo.push(currentTime);
        }
    }

    public boolean checkundo() {
        if (undo.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkRedo() {
        if (redo.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}
