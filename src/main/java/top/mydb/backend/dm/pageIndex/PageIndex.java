package top.mydb.backend.dm.pageIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import top.mydb.backend.dm.pageCache.PageCache;

public class PageIndex {
    // 将一页划成40个区间
    private static final int INTERVALS_NO = 40;
    private static final int THRESHOLD = PageCache.PAGE_SIZE / INTERVALS_NO;// 每个区间的空闲空间的大小范围

    private Lock lock;
    private List<PageInfo>[] lists;

    // 初始化页面索引数据结构：lock、list
    @SuppressWarnings("unchecked")
    public PageIndex() {
        lock = new ReentrantLock();
        lists = new List[INTERVALS_NO+1];
        for (int i = 0; i < INTERVALS_NO+1; i ++) {
            lists[i] = new ArrayList<>();
        }
    }

    // 插入页面空闲信息
    // 根据THRESHOLD选择插入的位置
    public void add(int pgno, int freeSpace) {
        lock.lock();
        try {
            int number = freeSpace / THRESHOLD;
            lists[number].add(new PageInfo(pgno, freeSpace));
        } finally {
            lock.unlock();
        }
    }

    // 根据所需空间大小选择页面
    // 返回页号信息
    public PageInfo select(int spaceSize) {
        lock.lock();
        try {
            int number = spaceSize / THRESHOLD;
            if(number < INTERVALS_NO) number ++; // 所需空间向上取整
            while(number <= INTERVALS_NO) {
                if(lists[number].size() == 0) {
                    number ++;
                    continue;
                }
                return lists[number].remove(0);
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

}
