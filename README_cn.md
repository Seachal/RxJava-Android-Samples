#  通过示例学习RxJava for Android

这是一个存储库，其中包含使用RxJava和Android的实际有用示例。[它通常处于“正在进行中”（WIP）的恒定状态](http://blog.kaush.co/2014/09/15/learning-rxjava-with-android-by-example/)。

我也一直在使用这个回购中列出的许多例子来讨论学习Rx。

* [通过示例学习RxJava For Android：第1部分](https://www.youtube.com/watch?v=k3D0cWyNno4) [ [幻灯片](https://speakerdeck.com/kaushikgopal/learning-rxjava-for-android-by-example) ]（SF Android Meetup 2015）
* [通过示例学习Rx：第2部分](https://vimeo.com/190922794) [ [幻灯片](https://speakerdeck.com/kaushikgopal/learning-rx-by-example-2) ]（Øredev2016）

## [](https://github.com/Seachal/RxJava-Android-Samples#examples)例子：

1. [后台工作和并发（使用调度程序）](https://github.com/Seachal/RxJava-Android-Samples#1-background-work--concurrency-using-schedulers)
2. [累积调用（使用缓冲区）](https://github.com/Seachal/RxJava-Android-Samples#2-accumulate-calls-using-buffer)
3. [即时/自动搜索文本收听者（使用主题和去抖动）](https://github.com/Seachal/RxJava-Android-Samples#3-instantauto-searching-text-listeners-using-subjects--debounce)
4. [与Retrofit和RxJava联网（使用zip，flatmap）](https://github.com/Seachal/RxJava-Android-Samples#4-networking-with-retrofit--rxjava-using-zip-flatmap)
5. [TextViews的双向数据绑定（使用PublishSubject）](https://github.com/Seachal/RxJava-Android-Samples#5-two-way-data-binding-for-textviews-using-publishsubject)
6. [简单和高级轮询（使用interval和repeatWhen）](https://github.com/Seachal/RxJava-Android-Samples#6-simple-and-advanced-polling-using-interval-and-repeatwhen)
7. [简单和高级指数退避（使用延迟和重试时）](https://github.com/Seachal/RxJava-Android-Samples#7-simple-and-advanced-exponential-backoff-using-delay-and-retrywhen)
8. [表单验证（使用combineLatest）](https://github.com/Seachal/RxJava-Android-Samples#8-form-validation-using-combinelatest)
9. [伪缓存：首先从缓存中检索数据，然后是网络调用（使用concat，concatEager，合并或发布）](https://github.com/Seachal/RxJava-Android-Samples#9-pseudo-caching--retrieve-data-first-from-a-cache-then-a-network-call-using-concat-concateager-merge-or-publish)
10. [简单的定时演示（使用定时器，间隔或延迟）](https://github.com/Seachal/RxJava-Android-Samples#10-simple-timing-demos-using-timer-interval-and-delay)
11. [RxBus：使用RxJava的事件总线（使用RxRelay（永不终止主题）和debouncedBuffer）](https://github.com/Seachal/RxJava-Android-Samples#11-rxbus--event-bus-using-rxjava-using-rxrelay-never-terminating-subjects-and-debouncedbuffer)
12. [保留活动轮换数据（使用主题和保留的碎片）](https://github.com/Seachal/RxJava-Android-Samples#12-persist-data-on-activity-rotations-using-subjects-and-retained-fragments)
13. [与排球联网](https://github.com/Seachal/RxJava-Android-Samples#13-networking-with-volley)
14. [Rx分页（使用主题）](https://github.com/Seachal/RxJava-Android-Samples#14-pagination-with-rx-using-subjects)
15. [编排Observable：进行并行网络调用，然后将结果合并到一个数据点（使用flatmap和zip）](https://github.com/Seachal/RxJava-Android-Samples#15-orchestrating-observable-make-parallel-network-calls-then-combine-the-result-into-a-single-data-point-using-flatmap--zip)
16. [简单超时示例（使用超时）](https://github.com/Seachal/RxJava-Android-Samples#16-simple-timeout-example-using-timeout)
17. [设置和拆卸资源（使用`using`）](https://github.com/Seachal/RxJava-Android-Samples#17-setup-and-teardown-resources-using-using)
18. [组播游乐场](https://github.com/Seachal/RxJava-Android-Samples#18-multicast-playground)

## [](https://github.com/Seachal/RxJava-Android-Samples#description)描述

### [](https://github.com/Seachal/RxJava-Android-Samples#1-background-work--concurrency-using-schedulers)1.后台工作和并发（使用调度程序）

常见的要求是将冗长的重I / O密集型操作卸载到后台线程（非UI线程），并在完成时将结果反馈给UI /主线程。这是一个演示如何将长时间运行的操作卸载到后台线程。操作完成后，我们继续回到主线程。全部使用RxJava！可以将其视为AsyncTasks的替代品。

通过阻塞Thread.sleep调用来模拟长操作（因为这是在后台线程中完成的，我们的UI永远不会被中断）。

要真正看到这个例子闪耀。多次按下该按钮，看看按钮单击（这是一个UI操作）从未被阻止，因为长操作只在后台运行。

### [](https://github.com/Seachal/RxJava-Android-Samples#2-accumulate-calls-using-buffer)2.累积呼叫（使用缓冲区）

这是一个如何使用“缓冲”操作累积事件的演示。

提供了一个按钮，我们在一段时间内累计该按钮的点击次数，然后吐出最终结果。

如果你按下按钮一次，你会收到一条消息，说按钮被击中一次。如果你在2秒的时间内连续5次击中它，那么你得到一个日志，说你按下该按钮5次（相对于5个单独的日志说“按一下”按钮）。

注意：

如果您正在寻找一种更加万无一失的解决方案，它可以累积“连续”点击而不是一段时间内的点击次数，请查看使用和运算符组合的[EventBus演示](https://github.com/kaushikgopal/Android-RxJava/blob/master/app/src/main/java/com/morihacky/android/rxjava/rxbus/RxBusDemo_Bottom3Fragment.java)。有关更详细的说明，您还可以查看此[博客文章](http://blog.kaush.co/2015/01/05/debouncedbuffer-with-rxjava/)。`publish``buffer`[](http://blog.kaush.co/2015/01/05/debouncedbuffer-with-rxjava/)

### [](https://github.com/Seachal/RxJava-Android-Samples#3-instantauto-searching-text-listeners-using-subjects--debounce)3.即时/自动搜索文本听众（使用主题和去抖动）

这是一个演示如何以只有最后一个被尊重的方式吞下事件的演示。典型的例子是即时搜索结果框。当你输入单词“Bruce Lee”时，你不想执行B，Br，Bru，Bruce，Bruce，Bruce L等的搜索。但是要智能地等待几分钟，确保用户已经完成了整个单词的输入，然后为“李小龙”拍了一个电话。

当您在输入框中键入时，它不会在每个输入字符更改时发出日志消息，而是仅选择最后发出的事件（即输入）并记录它。

这是RxJava中的debounce / throttleWithTimeout方法。

### [](https://github.com/Seachal/RxJava-Android-Samples#4-networking-with-retrofit--rxjava-using-zip-flatmap)4.与Retrofit和RxJava联网（使用zip，flatmap）

[来自Square的改造](http://square.github.io/retrofit/)是一个令人惊叹的库，有助于轻松联网（即使你还没有跳到RxJava，你真的应该检查出来）。它与RxJava的效果更好，这些是点击GitHub API的例子，直接来自android半神开发者Jake Wharton在Netflix的演讲。您可以通过此链接[观看演讲](https://www.youtube.com/watch?v=aEuNBk1b5OE#t=2480)。顺便说一下，我使用RxJava的动机是在Netflix上参加这个演讲。

（注意：您最有可能以相当快的速度点击GitHub API配额，因此如果您希望经常运行这些示例，请将OAuth令牌作为参数发送）。

### [](https://github.com/Seachal/RxJava-Android-Samples#5-two-way-data-binding-for-textviews-using-publishsubject)5\. TextViews的双向数据绑定（使用PublishSubject）

自动更新视图是一件很酷的事情。如果您之前已经处理过Angular JS，它们有一个非常漂亮的概念，称为“双向数据绑定”，因此当HTML元素绑定到模型/实体对象时，它会不断“监听”该实体的更改，根据模型自动更新其状态。使用此示例中的技术，您可以非常轻松地使用类似于[Presentation View Model模式的模式](http://martinfowler.com/eaaDev/PresentationModel.html)。

虽然这里的例子非常简陋，但用于实现使用a的双重绑定的技术`Publish Subject`更有趣。

### [](https://github.com/Seachal/RxJava-Android-Samples#6-simple-and-advanced-polling-using-interval-and-repeatwhen)6.简单和高级轮询（使用interval和repeatWhen）

这是使用RxJava调度程序进行轮询的示例。这在您希望不断轮询服务器并可能获取新数据的情况下非常有用。网络调用是“模拟的”，因此在返回结果字符串之前会强制延迟。

这有两种变体：

1. 简单轮询：说你想每5秒执行一次任务
2. 增加延迟轮询：说当你想要在1秒内首先执行任务，然后在2秒内执行任务，然后再执行3，依此类推。

第二个例子基本上是[指数退避](https://github.com/kaushikgopal/RxJava-Android-Samples#exponential-backoff)的变种。

我们在这里使用RepeatWithDelay而不是使用RetryWithDelay。为了理解Retry（When）和Repeat（When）之间的区别，我想看看Dan [在这个主题上](http://blog.danlew.net/2016/01/25/rxjavas-repeatwhen-and-retrywhen-explained/)的[精彩帖子](http://blog.danlew.net/2016/01/25/rxjavas-repeatwhen-and-retrywhen-explained/)。

不使用延迟轮询的另一种方法`repeatWhen`是使用链式嵌套延迟可观察量。请参阅[ExponentialBackOffFragment示例中的startExecutingWithExponentialBackoffDelay](https://github.com/kaushikgopal/RxJava-Android-Samples/blob/master/app/src/main/java/com/morihacky/android/rxjava/fragments/ExponentialBackoffFragment.java#L111)。

### [](https://github.com/Seachal/RxJava-Android-Samples#7-simple-and-advanced-exponential-backoff-using-delay-and-retrywhen)7.简单和高级指数退避（使用延迟和重试时）

[指数退避](https://en.wikipedia.org/wiki/Exponential_backoff)是一种策略，其中基于来自特定输出的反馈，我们改变过程的速率（通常在重试或重新执行某个过程之前减少重试次数或增加等待时间）。

这个概念通过实例更有意义。RxJava使（实现这样的策略）（相对）简单。感谢[Mike](https://twitter.com/m_evans10)提出这个想法。

#### [](https://github.com/Seachal/RxJava-Android-Samples#retry-if-error-with-exponential-backoff)使用指数退避重试（如果错误）

假设您遇到网络故障。一个明智的策略是不要每1秒重试一次网络呼叫。它会变得很聪明（不......优雅！），以增加延迟来重试。所以你尝试在第二个1执行网络呼叫，没有骰子？10秒后尝试......否定？20秒后试试，没有饼干？1分钟后尝试。如果这件事仍然失败，你必须放弃网络哟！

我们使用RxJava与[`retryWhen`运算符](http://reactivex.io/documentation/operators/retry.html)模拟此行为。

`RetryWithDelay` 代码片段礼貌：

* [http://stackoverflow.com/a/25292833/159825](http://stackoverflow.com/a/25292833/159825)
* 通过@ [sddamico的](https://github.com/sddamico)另一个出色的实现：[https](https://gist.github.com/sddamico/c45d7cdabc41e663bea1)：[//gist.github.com/sddamico/c45d7cdabc41e663bea1](https://github.com/sddamico)[](https://gist.github.com/sddamico/c45d7cdabc41e663bea1)
* 这个包括对[@leandrofavarin的](https://github.com/leandrofavarin)抖动的支持：[http](http://leandrofavarin.com/exponential-backoff-rxjava-operator-with-jitter)：[//leandrofavarin.com/exponential-backoff-rxjava-operator-with-jitter](https://github.com/leandrofavarin)[](http://leandrofavarin.com/exponential-backoff-rxjava-operator-with-jitter)

另请参阅我们使用非常类似的指数退避机制的[轮询示例](https://github.com/kaushikgopal/RxJava-Android-Samples#polling-with-schedulers)。

#### [](https://github.com/Seachal/RxJava-Android-Samples#repeat-with-exponential-backoff)“重复”指数退避

指数退避策略的另一种变体是执行给定次数的操作但具有延迟的间隔。所以你从现在起1秒后执行某个操作，然后从现在起10秒后再执行一次，然后从现在起20秒后执行操作。总共3次​​之后你就会停止执行。

模拟此行为实际上比prevoius重试机制更简单。您可以使用`delay`运算符的变体来实现此目的。

### [](https://github.com/Seachal/RxJava-Android-Samples#8-form-validation-using-combinelatest)8.表格确认（使用[`.combineLatest`](http://reactivex.io/documentation/operators/combinelatest.html)）

感谢Dan Lew在[片段播客中](http://fragmentedpodcast.com/episodes/4/)给我这个想法[- 第4集](http://fragmentedpodcast.com/episodes/4/)（4:30左右）。

`.combineLatest`允许您在一个位置紧凑地监视多个可观察对象的状态。演示的示例显示了如何使用`.combineLatest`验证基本表单。此表单有3个主要输入被视为“有效”（电子邮件，密码和数字）。所有输入有效后，表格将变为有效（下面的文字变成蓝色：P）。如果不是，则针对无效输入显示错误。

我们有3个独立的observable跟踪每个表单字段的文本/输入更改（RxAndroid `WidgetObservable`可以方便地监视文本更改）。在从**所有** 3个输入中发现事件更改后，结果将“合并”并评估表单的有效性。

请注意，`Func3`检查有效性的函数仅在所有3个输入都收到文本更改事件后才启动。

当表单中有更多输入字段时，此技术的值会变得更加明显。用一堆布尔值来处理它会使代码混乱并且很难遵循。但是使用`.combineLatest`所有逻辑集中在一个很好的紧凑代码块中（我仍然使用布尔值，但这是为了使示例更具可读性）。

### [](https://github.com/Seachal/RxJava-Android-Samples#9-pseudo-caching--retrieve-data-first-from-a-cache-then-a-network-call-using-concat-concateager-merge-or-publish)9.伪缓存：首先从缓存中检索数据，然后是网络调用（使用concat，concatEager，合并或发布）

我们有两个源Observable：磁盘（快速）缓存和网络（新鲜）调用。通常，磁盘Observable比网络Observable快得多。但为了演示工作原理，我们还使用了一个虚假的“慢速”磁盘缓存来查看运算符的行为方式。

使用4种技术证明了这一点：

1. [`.concat`](http://reactivex.io/documentation/operators/concat.html)
2. [`.concatEager`](http://reactivex.io/RxJava/javadoc/rx/Observable.html#concatEager(java.lang.Iterable))
3. [`.merge`](http://reactivex.io/documentation/operators/merge.html)
4. [`.publish`](http://reactivex.io/RxJava/javadoc/rx/Observable.html#publish(rx.functions.Func1)) selector + merge + takeUntil

第四种技术可能是你最终想要使用的技术，但是通过技术的进展来理解原因是很有趣的。

`concat`是很棒的。它从第一个Observable（在我们的例子中是磁盘缓存）中检索信息，然后从后续网络Observable中检索信息。由于磁盘缓存可能更快，所以看起来都很好并且磁盘缓存快速加载，一旦网络调用完成，我们就会换出“新鲜”的结果。

问题`concat`是后续的observable甚至在第一个Observable完成之后才开始。这可能是个问题。我们希望所有可观测量同时开始，但以我们期望的方式产生结果。值得庆幸的是RxJava介绍了`concatEager`这一点。它启动两个observable，但缓冲后一个结果，直到前Observable结束。这是一个完全可行的选择。

有时候，您只想立即开始显示结果。假设第一个observable（由于一些奇怪的原因）需要很长时间才能遍历所有项目，即使第二个observable中的前几个项目已经下线，它也会被强制排队。你不一定要在任何Observable上“等待”。在这些情况下，我们可以使用`merge`运营商。它会在项目发出时交错。这很有效，并且一旦显示结果就会开始吐出结果。

与`concat`运算符类似，如果您的第一个Observable总是比第二个Observable快，那么您将不会遇到任何问题。但问题`merge`是：如果由于某些奇怪的原因，一个项目由缓存发出或者在更新/更新的observable *之后*更慢可观察，它将覆盖更新的内容。单击示例中的“MERGE（SLOWER DISK）”按钮以查看此问题的实际效果。@JakeWharton和@swankjesse的贡献是0！在现实世界中，这可能很糟糕，因为这意味着新数据将被过时的磁盘数据覆盖。

要解决此问题，您可以将merge与超级nifty `publish`运算符结合使用，该运算符包含“选择器”。我在[博客文章中](http://blog.kaush.co/2015/01/21/rxjava-tip-for-the-day-share-publish-refcount-and-all-that-jazz/)写过这个用法，但我有[Jedi JW](https://twitter.com/JakeWharton/status/786363146990649345)感谢提醒这个技术。我们`publish`网络可观察并提供一个选择器，它从磁盘缓存开始发射，直到网络可观察的开始发射。一旦网络可观察性开始发射，它将忽略磁盘可观察的所有结果。这是完美的，可以解决我们可能遇到的任何问题。

以前，我使用的是`merge`运算符，但是通过监视“resultAge”来克服结果被覆盖的问题。`PseudoCacheMergeFragment`如果您想要看到这个旧的实现，请参阅旧示例。

### [](https://github.com/Seachal/RxJava-Android-Samples#10-simple-timing-demos-using-timer-interval-and-delay)10.简单的定时演示（使用定时器，间隔和延迟）

这是一个超级简单明了的例子展示了如何使用RxJava的`timer`，`interval`和`delay`运营商来处理一堆案件要在特定时间间隔运行任务。基本上对Android说不`TimerTask`。

案例在这里展示：

1. 在延迟2秒后运行单个任务，然后完成
2. 每1秒运行一次任务（在第一个任务开始之前有1秒的延迟）
3. 每隔1秒运行一次任务（与上面相同，但在第一个任务开始之前没有延迟）
4. 每3秒运行一次任务，但运行5次后，自动终止
5. 运行任务A，暂停一段时间，然后执行任务B，然后终止

### [](https://github.com/Seachal/RxJava-Android-Samples#11-rxbus--event-bus-using-rxjava-using-rxrelay-never-terminating-subjects-and-debouncedbuffer)11\. RxBus：使用RxJava的事件总线（使用RxRelay（永不终止主题）和debouncedBuffer）

随附的博客文章在解释此演示的详细信息方面做得更好：

1. [使用RxJava实现事件总线](http://blog.kaush.co/2014/12/24/implementing-an-event-bus-with-rxjava-rxbus/)
2. [DebouncedBuffer用于演示的更高级变体](http://blog.kaush.co/2015/01/05/debouncedbuffer-with-rxjava/)
3. [分享/发布/引用计数](http://blog.kaush.co/2015/01/21/rxjava-tip-for-the-day-share-publish-refcount-and-all-that-jazz/)

### [](https://github.com/Seachal/RxJava-Android-Samples#12-persist-data-on-activity-rotations-using-subjects-and-retained-fragments)12.保留活动轮换数据（使用主题和保留的碎片）

在Android中使用RxJava时遇到的一个常见问题是，“如果发生配置更改（活动轮换，语言区域设置更改等），我如何恢复可观察的工作？”。

这个例子向您展示了一个策略即。使用保留的碎片。在读完[亚历克斯洛克伍德的](http://www.androiddesignpatterns.com/2013/04/retaining-objects-across-config-changes.html)这篇[精彩文章](http://www.androiddesignpatterns.com/2013/04/retaining-objects-across-config-changes.html)之后，我开始使用保留的片段作为“工人片段” 。

点击开始按钮，将屏幕旋转到心脏的内容; 你会看到它可以从它停止的地方继续观察。

*关于本例中使用的源可观察性的“热度”存在某些怪癖。查看[我的博客文章](http://blog.kaush.co/2015/07/11/a-note-about-the-warmth-share-operator/)，我在哪里解释具体细节。*

我已经用另一种方法改写了这个例子。虽然这种[`ConnectedObservable`方法有效，](https://github.com/kaushikgopal/RxJava-Android-Samples/blob/master/app/src/main/java/com/morihacky/android/rxjava/fragments/RotationPersist1WorkerFragment.java#L20)但它进入了“多播”的土地，这可能很棘手（线程安全，.refount等）。另一方面，受试者要简单得多。您可以[使用`Subject`此处](https://github.com/kaushikgopal/RxJava-Android-Samples/blob/master/app/src/main/java/com/morihacky/android/rxjava/fragments/RotationPersist2WorkerFragment.java#L22)重新编写它。

我写了[另一篇](https://tech.instacart.com/how-to-think-about-subjects-part-1/)关于如何思考主题的[博客文章](https://tech.instacart.com/how-to-think-about-subjects-part-1/)，其中我将介绍一些细节。

### [](https://github.com/Seachal/RxJava-Android-Samples#13-networking-with-volley)13.与Volley联网

[Volley](http://developer.android.com/training/volley/index.html)是[谷歌在IO '13](https://www.youtube.com/watch?v=yhv8l9F44qo)推出的另一个网络库。一个善良的github公民贡献了这个例子，所以我们知道如何将Volley与RxJava集成。

### [](https://github.com/Seachal/RxJava-Android-Samples#14-pagination-with-rx-using-subjects)14\. Rx分页（使用主题）

我在这里利用了一个主题的简单使用。老实说，如果你没有通过`Observable`已经下载的项目（如通过Retrofit或网络请求），没有充分的理由使用Rx并使事情变得复杂。

此示例基本上将页码发送给主题，主题处理添加项目。注意使用`concatMap`和返回`Observable<List>`from `_itemsFromNetworkCall`。

对于踢球，我还提供了一个`PaginationAutoFragment`例子，这个“自动分页”没有我们需要按一个按钮。如果您了解前一个示例的工作原理，那么应该很容易理解。

这里有一些其他花哨的实现（虽然我喜欢阅读它们，但我没有使用它们为我的真实世界应用程序，因为我个人认为这是不必要的）：

* [Matthias基于Rx的寻呼机的示例](https://gist.github.com/mttkay/24881a0ce986f6ec4b4d)
* [尤金的非常全面的分页样本](https://github.com/matzuk/PaginationSample)
* [递归分页示例](http://stackoverflow.com/questions/28047272/handle-paging-with-rxjava)

### [](https://github.com/Seachal/RxJava-Android-Samples#15-orchestrating-observable-make-parallel-network-calls-then-combine-the-result-into-a-single-data-point-using-flatmap--zip)15.编排Observable：进行并行网络调用，然后将结果合并到一个数据点（使用flatmap和zip）

下面的ascii图表示了我们下一个带有panache的例子的意图。f1，f2，f3，f4，f5本质上是网络调用，在进行时，返回将来计算所需的结果。

```
         (flatmap)
f1 ___________________ f3 _______
         (flatmap)               |    (zip)
f2 ___________________ f4 _______| ___________  final output
        \                        |
         \____________ f5 _______|

```

这个例子的代码已经由一个Mr.skehlet在interwebs中编写。转到代码[的要点](https://gist.github.com/skehlet/9418379)。它是用纯Java（6）编写的，所以如果你已经理解了前面的例子，那就很容易理解了。如果时间允许，我会在这里再次刷新它，或者我已经用完了其他令人信服的例子。

### [](https://github.com/Seachal/RxJava-Android-Samples#16-simple-timeout-example-using-timeout)16.简单超时示例（使用超时）

这是一个演示`.timeout`操作员使用的简单示例。按钮1将在超时约束之前完成任务，而按钮2将强制超时错误。

请注意我们如何提供一个自定义Observable，指示如何在超时异常下​​做出反应。

### [](https://github.com/Seachal/RxJava-Android-Samples#17-setup-and-teardown-resources-using-using)17.设置和拆卸资源（使用`using`）

对于谷歌来说，[运营商的`using`](http://reactivex.io/documentation/operators/using.html)知名度相对较低，而且非常难。它是一个漂亮的API，有助于设置（昂贵的）资源，使用它然后以干净的方式处理。

关于这个运算符的好处是它提供了一个mechansim以严格限制的方式使用可能代价高昂的资源。使用 - >设置，使用和处理。想想数据库连接（如Realm实例），套接字连接，线程锁等。

### [](https://github.com/Seachal/RxJava-Android-Samples#18-multicast-playground)18.组播游乐场

Rx中的多播就像一个黑暗的艺术。没有太多人知道​​如何毫无顾虑地将其拉下来。此示例包含两个订阅者（以按钮的形式），允许您在不同的时间点添加/删除订阅者，并查看不同的运算符在这些情况下的行为。

源observale是一个timer（`interval`）可观察的，选择它的原因是故意选择一个非终止的observable，这样你就可以测试/确认你的组播实验是否会泄漏。

*我还在[360 | Andev上详细介绍](https://speakerdeck.com/kaushikgopal/rx-by-example-volume-3-the-multicast-edition)了[多播](https://speakerdeck.com/kaushikgopal/rx-by-example-volume-3-the-multicast-edition)。如果你有倾向和时间，我强烈建议首先观看该话题（特别是多播运算符置换段）然后在这里搞乱这个例子。*

## [](https://github.com/Seachal/RxJava-Android-Samples#rx-2x)Rx 2.x

此处的所有示例都已迁移到使用RxJava 2.X.

* 看看[PR＃83，看看RxJava 1和2之间的变化差异](https://github.com/kaushikgopal/RxJava-Android-Samples/pull/83/files)
* [Rx 2.x有什么不同](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0)

在某些情况下，我们使用[David Karnok的Interop库](https://github.com/akarnokd/RxJava2Interop)，因为某些库（如RxBindings，RxRelays，RxJava-Math等）尚未移植到2.x.

## [](https://github.com/Seachal/RxJava-Android-Samples#contributing)贡献：

我试图确保这些例子不是过于苛刻，而是反映了一个现实世界的用例。如果您有类似的有用示例演示如何使用RxJava，请随时发送拉取请求。

我也是围绕着RxJava，所以如果你觉得有更好的方法来做上面提到的一个例子，那就打开一个问题来解释如何。更好的是，发送拉取请求。

## [](https://github.com/Seachal/RxJava-Android-Samples#license)执照

根据Apache许可证2.0版（“许可证”）获得许可。您可以在以下位置获取许可证副本

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

除非适用法律要求或书面同意，否则根据许可证分发的软件按“原样”分发，不附带任何明示或暗示的担保或条件。有关管理许可下的权限和限制的特定语言，请参阅许可证。

您同意以修补程序，请求，新示例等形式对此存储库的所有贡献都遵循上述许可。