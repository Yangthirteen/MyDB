package top.mydb.backend.vm;

import top.mydb.backend.dm.DataManager;
import top.mydb.backend.tm.TransactionManager;

public interface VersionManager {
    // 向上层提供功能

    // 读取一个 entry
    byte[] read(long xid, long uid) throws Exception;
    // 将数据包裹成 Entry，交给 DM 插入
    long insert(long xid, byte[] data) throws Exception;
    boolean delete(long xid, long uid) throws Exception;

    // 开启一个事务，并初始化事务的结构，将其存放在 activeTransaction 中，用于检查和快照使用：
    long begin(int level);
    // 提交一个事务，主要就是 free 掉相关的结构，并且释放持有的锁，并修改 TM 状
    void commit(long xid) throws Exception;
    // 撤销或者回滚事务
    void abort(long xid);

    public static VersionManager newVersionManager(TransactionManager tm, DataManager dm) {
        return new VersionManagerImpl(tm, dm);
    }

}
