package top.mydb.backend.vm;

import java.util.HashMap;
import java.util.Map;

import top.mydb.backend.tm.TransactionManagerImpl;

// vm对一个事务的抽象
// 来抽象一个事务，以保存快照数据
public class Transaction {
    public long xid;
    public int level; // 隔离级别：0代表读提交，否则是可重复读
    public Map<Long, Boolean> snapshot;
    public Exception err;
    public boolean autoAborted;

    //  active:保存着当前所有 active 的事务
    public static Transaction newTransaction(long xid, int level, Map<Long, Transaction> active) {
        Transaction t = new Transaction();
        t.xid = xid;
        t.level = level;
        if(level != 0) {
            t.snapshot = new HashMap<>();
            for(Long x : active.keySet()) {
                t.snapshot.put(x, true);
            }
        }
        return t;
    }

    // xid的事务是否在快照中
    public boolean isInSnapshot(long xid) {
        if(xid == TransactionManagerImpl.SUPER_XID) {
            return false;
        }
        return snapshot.containsKey(xid);
    }
}
