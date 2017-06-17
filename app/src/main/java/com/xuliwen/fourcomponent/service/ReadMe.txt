1. service运行在主线程，耗时任务需要在service中new一个线程来进行。

2. service的作用是当应用的界面关掉后还能继续运行，比如接收服务器的返回，控制音乐的播放等

3. 前台service和后台service的区别：
1）前台service的优先级更高，不容易被回收

4. 前台service和Notification的区别：
1）前台服务用户不能在状态栏手动删除，Notification可以状态栏手动删除

5. startService和bindService的区别：
bindService：
1）多个Activity可以绑定到同一个Service对象，当service被0个Activity绑定的时候，service会destroy
 也就是说调用一次unbindService只是切除了一个Activity与Service的联系，并不一定会destroy这个service。
 当所有Activity都destroy后，service也会跟随着destroy。
2）当Activity执行onDestroy一定会unbindService（如果没有手动调用unbindService会报错，但不会崩溃，
这时候系统会强制执行unbindService）
3）bindService后可以通过Binder来控制Service
4）应用场景：需要控制Service


startService：
1）多个Activity可以start同一个Service对象，但只要有一个Activity调用了stopService，那么Service
 就destroy了。当所有Activity都destroy后，service可以继续运行。
2）当Activity执行onDestroy不要求stopService
3）startService后只能在startCommand中做操作，之后就无法再去控制Service了
4）应用场景：需要让Service长期运行，或者只需要让Service做一些简单操作

联系：
1）同时starService，bindService后，必须同时stopService和unbindService才能关闭Service
2）应用场景：可以同时使用，starService保证service长期运行，使用bindService控制Service

6.service需要注意的地方
1） enabled="false"  能否实例化
    exported="true"  能否被外部应用访问到

2） Service被destroy后并不会杀死onStartCommand中创建的线程


7.IntentService
（1）优点：
1）执行完耗时任务后，自动destroy，普通的Service需要手动调用stopService或者stopSelf
2）IntentService运行在工作线程，不需要手动创建线程，方便
3）IntentService属于Service，相比手动创建线程，IntentService的优先级更高，适合执行优先级高的任务

（2）写法：
通过startService来开启一个工作任务

