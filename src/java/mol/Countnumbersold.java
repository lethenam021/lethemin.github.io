/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mol;

/**
 *
 * @author ADMIN
 */
public class Countnumbersold {

    private int pid;
    private int count;

    public Countnumbersold() {
    }

    public Countnumbersold(int pid, int count) {
        this.pid = pid;
        this.count = count;
    }

    public int getPid() {
        return pid;
    }

    public int getCount() {
        return count;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Countnumbersold{" + "pid=" + pid + ", count=" + count + '}';
    }

}
