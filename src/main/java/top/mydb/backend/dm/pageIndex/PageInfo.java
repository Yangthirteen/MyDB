package top.mydb.backend.dm.pageIndex;

public class PageInfo {
    // 页号和空闲空间大小的信息
    public int pgno;
    public int freeSpace;

    public PageInfo(int pgno, int freeSpace) {
        this.pgno = pgno;
        this.freeSpace = freeSpace;
    }
}
