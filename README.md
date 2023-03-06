# MYDB

•	Transaction Manager事物管理器：维护事务的状态，并提供接口供其他模块来查询某个事务的状态。

•	Data Manager数据管理器：直接管理数据库 DB 文件和日志文件。

•	Version Manager版本管理器：基于两段锁协议实现了调度序列的可串行化，并实现了 MVCC 以消除读写阻塞。同时实现了两种隔离级别。

•	Index Manager索引管理器：通过B+树数据结构实现。

•	Table Manager字段与表管理器：实现了对字段和表的管理，同时，解析 SQL 语句，并根据语句操作表。


