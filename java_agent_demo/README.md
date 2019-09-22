
首先 `./gradlew clean build -x test` 生成 `java_agent_demo-1.0-SNAPSHOT.jar` 。

测试类，全部在test目录下。

#### 以 JVM 启动参数 -javaagent:xxx.jar 的形式
在test目录下，`PersonTest` 运行时候，直接在intellij中运行，"Edit Configurations"，在"VM Options"中添加 `"-javaagent:/Users/github/java/java_agent_demo/build/libs/java_agent_demo-1.0-SNAPSHOT.jar"`
注意确定生成的jar包文件位置。
然后执行，将会出来结果
```
/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/bin/java -javaagent:/Users/yangxiaohuan/my_private/github/my_public/java/java_agent_demo/build/libs/java_agent_demo-1.0-SNAPSHOT.jar "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=49943:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/tools.jar:/Users/yangxiaohuan/my_private/github/my_public/java/java_agent_demo/out/test/classes:/Users/yangxiaohuan/my_private/github/my_public/java/java_agent_demo/out/production/classes:/Users/yangxiaohuan/.gradle/caches/modules-2/files-2.1/org.javassist/javassist/3.25.0-GA/442dc1f9fd520130bd18da938622f4f9b2e5fba3/javassist-3.25.0-GA.jar:/Users/yangxiaohuan/.gradle/caches/modules-2/files-2.1/junit/junit/4.12/2973d150c0dc1fefe998f834810d68f278ea58ec/junit-4.12.jar:/Users/yangxiaohuan/.gradle/caches/modules-2/files-2.1/org.hamcrest/hamcrest-core/1.3/42a25dc3219429f0e5d060061f71acb49bf010a0/hamcrest-core-1.3.jar PersonTest
premain
com.agent.MyTransformer
com.agent.MyCustomAgent
java.util.concurrent.ConcurrentHashMap$ForwardingNode
sun.reflect.DelegatingMethodAccessorImpl
sun.reflect.NativeMethodAccessorImpl
sun.instrument.InstrumentationImpl$1
[Ljava.lang.reflect.Method;
java.lang.instrument.ClassFileTransformer
java.security.BasicPermissionCollection
java.security.UnresolvedPermission
java.security.AllPermission
java.io.FilePermissionCollection
java.io.FilePermission$1
java.io.FilePermission
sun.net.www.MessageHeader
sun.net.www.protocol.file.FileURLConnection
sun.net.www.URLConnection
java.net.URLConnection
java.security.Permissions
java.security.PermissionCollection
sun.nio.ByteBuffered
java.lang.Package
sun.misc.URLClassPath$FileLoader$1
sun.misc.Resource
sun.misc.URLClassPath$FileLoader
sun.misc.IOUtils
sun.misc.ExtensionDependency
java.util.LinkedList$Node
java.util.LinkedList
java.util.AbstractSequentialList
java.util.zip.ZipFile$ZipFileInflaterInputStream
java.util.zip.InflaterInputStream
java.util.zip.ZStreamRef
java.util.zip.Inflater
java.util.zip.ZipFile$ZipFileInputStream
java.util.jar.JarFile$JarFileEntry
java.util.jar.JarEntry
java.util.zip.ZipEntry
sun.misc.JarIndex
java.nio.DirectLongBufferU
java.nio.LongBuffer
java.nio.DirectByteBuffer
java.nio.MappedByteBuffer
sun.nio.ch.DirectBuffer
sun.misc.PerfCounter$CoreCounters
sun.misc.Perf
sun.misc.Perf$GetPerfAction
sun.misc.PerfCounter
java.util.zip.ZipCoder
java.util.ArrayDeque
java.util.Deque
java.util.Queue
sun.nio.cs.UTF_16
sun.nio.cs.UTF_16LE
sun.nio.cs.UTF_16BE
sun.nio.cs.ISO_8859_1
sun.nio.cs.US_ASCII
java.nio.charset.StandardCharsets
java.util.jar.JavaUtilJarAccessImpl
sun.misc.JavaUtilJarAccess
java.util.jar.JarFile
sun.misc.FileURLMapper
sun.misc.URLClassPath$JarLoader$1
java.util.zip.ZipFile$1
sun.misc.JavaUtilZipFileAccess
java.util.zip.ZipFile
java.util.zip.ZipConstants
sun.misc.URLClassPath$JarLoader
sun.misc.URLClassPath$Loader
sun.misc.URLClassPath$3
sun.net.util.URLUtil
java.net.URLClassLoader$1
[Lsun.instrument.TransformerManager$TransformerInfo;
sun.instrument.TransformerManager$TransformerInfo
sun.instrument.TransformerManager
sun.instrument.InstrumentationImpl
java.lang.instrument.Instrumentation
java.lang.InternalError
java.lang.SystemClassLoaderAction
sun.misc.Launcher$AppClassLoader$1
sun.net.www.protocol.jar.Handler
sun.misc.URLClassPath
java.util.HashSet
[Ljava.security.Principal;
java.security.Principal
java.security.ProtectionDomain$Key
java.security.ProtectionDomain$2
sun.misc.JavaSecurityProtectionDomainAccess
java.security.ProtectionDomain$JavaSecurityAccessImpl
sun.misc.JavaSecurityAccess
[Ljava.net.URL;
sun.net.www.protocol.file.Handler
java.net.URLStreamHandler
java.net.Parts
java.lang.CharacterDataLatin1
java.lang.CharacterData
sun.util.locale.LocaleUtils
java.util.Locale$LocaleKey
sun.util.locale.LocaleObjectCache$CacheEntry
sun.util.locale.BaseLocale$Key
sun.util.locale.BaseLocale$Cache
sun.util.locale.BaseLocale
java.util.concurrent.ConcurrentHashMap$EntrySetView
java.util.concurrent.ConcurrentHashMap$ValuesView
java.util.concurrent.ConcurrentHashMap$KeySetView
java.util.concurrent.ConcurrentHashMap$CollectionView
[Ljava.util.concurrent.ConcurrentHashMap$CounterCell;
java.util.concurrent.ConcurrentHashMap$CounterCell
[Ljava.util.concurrent.ConcurrentHashMap$Node;
java.util.concurrent.ConcurrentHashMap$Node
[Ljava.util.concurrent.ConcurrentHashMap$Segment;
[Ljava.util.concurrent.locks.ReentrantLock;
[Ljava.util.concurrent.locks.Lock;
java.util.concurrent.ConcurrentHashMap$Segment
java.util.concurrent.locks.ReentrantLock
java.util.concurrent.locks.Lock
java.util.concurrent.ConcurrentHashMap
java.util.concurrent.ConcurrentMap
java.util.Locale$Cache
sun.util.locale.LocaleObjectCache
java.util.Locale
java.util.BitSet
sun.net.www.ParseUtil
java.io.FileInputStream$1
java.util.HashMap$TreeNode
java.lang.reflect.Array
java.nio.charset.CoderResult$2
java.nio.charset.CoderResult$1
java.nio.charset.CoderResult$Cache
java.nio.charset.CoderResult
java.nio.HeapCharBuffer
java.nio.CharBuffer
sun.nio.cs.StreamDecoder
java.io.FileReader
java.io.InputStreamReader
java.io.BufferedReader
java.io.Reader
java.lang.Readable
sun.misc.MetaIndex
[Ljava.io.File;
java.util.StringTokenizer
sun.misc.Launcher$ExtClassLoader$1
java.net.URLClassLoader$7
sun.misc.JavaNetAccess
java.util.WeakHashMap$KeySet
java.util.Collections$SetFromMap
[Ljava.util.WeakHashMap$Entry;
java.util.WeakHashMap$Entry
java.lang.ClassLoader$ParallelLoaders
sun.security.util.Debug
sun.misc.Launcher$Factory
java.net.URLStreamHandlerFactory
java.lang.Compiler$1
java.lang.Compiler
java.lang.IllegalArgumentException
java.lang.System$2
sun.misc.JavaLangAccess
sun.misc.OSEnvironment
[Ljava.lang.Integer;
[Ljava.lang.Number;
java.lang.Integer$IntegerCache
sun.misc.NativeSignalHandler
sun.misc.Signal
java.lang.Terminator$1
sun.misc.SignalHandler
java.lang.Terminator
java.lang.ClassLoader$NativeLibrary
java.util.LinkedHashMap$Entry
java.io.ExpiringCache$Entry
[Ljava.io.File$PathStatus;
[Ljava.lang.Enum;
java.lang.ClassLoader$3
java.lang.StringCoding$StringEncoder
java.nio.file.Path
java.nio.file.Watchable
java.io.File$PathStatus
java.lang.Enum
java.io.ExpiringCache$1
java.util.LinkedHashMap
java.io.ExpiringCache
java.io.UnixFileSystem
java.io.FileSystem
java.io.DefaultFileSystem
java.io.BufferedWriter
java.nio.Bits$1
sun.misc.JavaNioAccess
java.util.concurrent.atomic.AtomicLong
java.nio.ByteOrder
java.nio.Bits
java.nio.HeapByteBuffer
java.nio.ByteBuffer
sun.nio.cs.UTF_8$Encoder
java.nio.charset.CharsetEncoder
sun.nio.cs.ArrayEncoder
sun.security.action.GetPropertyAction
sun.nio.cs.StreamEncoder
java.io.OutputStreamWriter
java.io.Writer
java.io.BufferedOutputStream
java.io.PrintStream
java.io.FilterOutputStream
sun.reflect.misc.ReflectUtil
java.util.concurrent.atomic.AtomicReferenceFieldUpdater$AtomicReferenceFieldUpdaterImpl$1
java.security.PrivilegedExceptionAction
java.util.concurrent.atomic.AtomicReferenceFieldUpdater$AtomicReferenceFieldUpdaterImpl
java.util.concurrent.atomic.AtomicReferenceFieldUpdater
java.io.BufferedInputStream
java.io.FilterInputStream
java.io.FileOutputStream
java.io.OutputStream
java.io.Flushable
java.io.FileDescriptor$1
sun.misc.JavaIOFileDescriptorAccess
java.io.FileDescriptor
java.io.FileInputStream
sun.misc.Version
java.lang.Runtime
java.util.Hashtable$Enumerator
java.util.Iterator
java.util.Enumeration
java.util.Objects
java.util.Collections$SynchronizedSet
java.util.Collections$SynchronizedCollection
java.util.Hashtable$EntrySet
java.nio.charset.CodingErrorAction
sun.nio.cs.UTF_8$Decoder
java.nio.charset.CharsetDecoder
sun.nio.cs.ArrayDecoder
java.lang.StringCoding$StringDecoder
[Ljava.lang.ThreadLocal$ThreadLocalMap$Entry;
[Ljava.lang.ref.WeakReference;
[Ljava.lang.ref.Reference;
java.lang.ThreadLocal$ThreadLocalMap$Entry
java.lang.ThreadLocal$ThreadLocalMap
java.lang.StringCoding
sun.reflect.DelegatingConstructorAccessorImpl
sun.reflect.NativeConstructorAccessorImpl
sun.reflect.ReflectionFactory$1
java.lang.Class$1
[Ljava.lang.reflect.Constructor;
[Ljava.lang.reflect.Executable;
sun.nio.cs.UTF_8
sun.nio.cs.Unicode
sun.nio.cs.HistoricallyNamedCharset
java.util.Arrays
java.lang.reflect.ReflectAccess
sun.reflect.LangReflectAccess
java.lang.reflect.Modifier
java.lang.ClassValue$ClassValueMap
java.util.WeakHashMap
sun.reflect.annotation.AnnotationType
java.lang.Class$AnnotationData
sun.reflect.generics.repository.ClassRepository
sun.reflect.generics.repository.GenericDeclRepository
sun.reflect.generics.repository.AbstractRepository
[Ljava.lang.reflect.Field;
[Ljava.lang.reflect.Member;
[Ljava.lang.reflect.AccessibleObject;
java.lang.Class$Atomic
java.lang.Class$ReflectionData
java.lang.Class$3
java.util.concurrent.atomic.AtomicInteger
java.lang.ThreadLocal
sun.nio.cs.StandardCharsets$Cache
sun.nio.cs.StandardCharsets$Classes
sun.nio.cs.StandardCharsets$Aliases
sun.util.PreHashedMap
sun.nio.cs.StandardCharsets
sun.nio.cs.FastCharsetProvider
java.nio.charset.spi.CharsetProvider
java.nio.charset.Charset
java.lang.ref.Finalizer$FinalizerThread
java.lang.Math
[Ljava.util.Hashtable$Entry;
java.util.Hashtable$Entry
sun.misc.VM
[Ljava.util.HashMap$Node;
[Ljava.util.Map$Entry;
java.util.HashMap$Node
java.util.Map$Entry
java.util.HashMap
sun.reflect.Reflection
[Ljava.lang.String;
[Ljava.lang.CharSequence;
java.lang.NoSuchMethodError
java.lang.IncompatibleClassChangeError
sun.misc.SharedSecrets
java.lang.ref.Reference$1
sun.misc.JavaLangRefAccess
[Ljava.lang.Thread;
[Ljava.lang.Runnable;
java.lang.ref.ReferenceQueue$Lock
java.lang.ref.ReferenceQueue$Null
java.util.Collections$UnmodifiableRandomAccessList
java.util.Collections$UnmodifiableList
java.util.Collections$UnmodifiableCollection
java.util.Collections$EmptyMap
java.util.AbstractMap
java.util.Collections$EmptyList
java.util.Collections$EmptySet
java.util.AbstractSet
java.util.Set
java.util.Collections
java.util.ArrayList
java.lang.InterruptedException
java.lang.ref.Reference$ReferenceHandler
java.lang.ref.Reference$Lock
sun.reflect.ReflectionFactory
java.util.Stack
java.util.Vector
java.util.AbstractList
java.util.AbstractCollection
java.util.RandomAccess
java.util.List
java.util.Collection
java.lang.Iterable
[Ljava.security.cert.Certificate;
java.security.cert.Certificate
sun.reflect.ReflectionFactory$GetReflectionFactoryAction
java.security.PrivilegedAction
java.lang.reflect.ReflectPermission
java.security.AccessController
java.lang.RuntimePermission
java.security.BasicPermission
java.security.Permission
java.security.Guard
[Ljava.lang.StackTraceElement;
[Ljava.lang.ThreadGroup;
[Ljava.lang.Thread$UncaughtExceptionHandler;
java.lang.String$CaseInsensitiveComparator
java.util.Comparator
[Ljava.io.ObjectStreamField;
[Ljava.lang.Comparable;
java.io.ObjectStreamField
[Ljava.lang.OutOfMemoryError;
[Ljava.lang.VirtualMachineError;
[Ljava.lang.Error;
[Ljava.lang.Throwable;
java.lang.ArithmeticException
java.lang.NullPointerException
[Ljava.lang.Class;
[Ljava.io.Serializable;
[Ljava.lang.reflect.GenericDeclaration;
[Ljava.lang.reflect.AnnotatedElement;
[Ljava.lang.reflect.Type;
[Ljava.lang.Object;
java.lang.Long
java.lang.Integer
java.lang.Short
java.lang.Byte
java.lang.Double
java.lang.Float
java.lang.Number
java.lang.Character
java.lang.Boolean
java.nio.Buffer
java.lang.StackTraceElement
java.security.CodeSource
sun.misc.Launcher$ExtClassLoader
sun.misc.Launcher$AppClassLoader
sun.misc.Launcher
java.util.jar.Manifest
java.net.URL
java.net.URLClassLoader
java.io.File
java.io.ByteArrayInputStream
java.io.InputStream
java.io.Closeable
java.lang.AutoCloseable
sun.misc.Unsafe
java.lang.StringBuilder
java.lang.StringBuffer
java.lang.AbstractStringBuilder
java.lang.Appendable
java.lang.invoke.VolatileCallSite
java.lang.invoke.MutableCallSite
java.lang.invoke.ConstantCallSite
java.lang.invoke.CallSite
java.lang.BootstrapMethodError
java.lang.invoke.MethodType
java.lang.invoke.LambdaForm
java.lang.invoke.MethodHandleNatives
java.lang.invoke.MemberName
java.lang.invoke.DirectMethodHandle
java.lang.invoke.MethodHandle
sun.reflect.CallerSensitive
java.lang.annotation.Annotation
sun.reflect.UnsafeStaticFieldAccessorImpl
sun.reflect.UnsafeFieldAccessorImpl
sun.reflect.FieldAccessorImpl
sun.reflect.FieldAccessor
sun.reflect.ConstantPool
sun.reflect.DelegatingClassLoader
sun.reflect.ConstructorAccessorImpl
sun.reflect.ConstructorAccessor
sun.reflect.MethodAccessorImpl
sun.reflect.MethodAccessor
sun.reflect.MagicAccessorImpl
java.lang.reflect.Constructor
java.lang.reflect.Method
java.lang.reflect.Executable
java.lang.reflect.Parameter
java.lang.reflect.Field
java.lang.reflect.Member
java.lang.reflect.AccessibleObject
java.util.Properties
java.util.Hashtable
java.util.Dictionary
java.util.Map
java.lang.ThreadGroup
java.lang.Thread$UncaughtExceptionHandler
java.lang.Thread
java.lang.Runnable
java.lang.ref.ReferenceQueue
java.lang.ref.Finalizer
sun.misc.Cleaner
java.lang.ref.PhantomReference
java.lang.ref.FinalReference
java.lang.ref.WeakReference
java.lang.ref.SoftReference
java.lang.ref.Reference
java.lang.IllegalMonitorStateException
java.lang.StackOverflowError
java.lang.OutOfMemoryError
java.lang.VirtualMachineError
java.lang.ArrayStoreException
java.lang.ClassCastException
java.lang.NoClassDefFoundError
java.lang.LinkageError
java.lang.ClassNotFoundException
java.lang.ReflectiveOperationException
java.security.SecureClassLoader
java.security.AccessControlContext
java.security.ProtectionDomain
java.lang.SecurityManager
java.lang.RuntimeException
java.lang.Exception
java.lang.ThreadDeath
java.lang.Error
java.lang.Throwable
java.lang.System
java.lang.ClassLoader
java.lang.Cloneable
java.lang.Class
java.lang.reflect.Type
java.lang.reflect.GenericDeclaration
java.lang.reflect.AnnotatedElement
java.lang.String
java.lang.CharSequence
java.lang.Comparable
java.io.Serializable
java.lang.Object
[J
[I
[S
[B
[D
[F
[C
[Z
正在加载类：sun/nio/cs/ThreadLocalCoders
****
正在加载类：sun/nio/cs/ThreadLocalCoders$1
****
正在加载类：sun/nio/cs/ThreadLocalCoders$Cache
****
正在加载类：sun/nio/cs/ThreadLocalCoders$2
****
正在加载类：sun/misc/URLClassPath$JarLoader$2
****
正在加载类：java/util/jar/Attributes
****
正在加载类：java/util/jar/Manifest$FastInputStream
****
正在加载类：java/util/jar/Attributes$Name
****
正在加载类：sun/misc/ASCIICaseInsensitiveComparator
****
正在加载类：java/util/jar/JarVerifier
****
正在加载类：java/security/CodeSigner
****
正在加载类：java/util/jar/JarVerifier$3
****
正在加载类：java/io/ByteArrayOutputStream
****
正在加载类：sun/security/util/SignatureFileVerifier
****
正在加载类：sun/security/util/DisabledAlgorithmConstraints
****
正在加载类：sun/security/util/AbstractAlgorithmConstraints
****
正在加载类：java/security/AlgorithmConstraints
****
正在加载类：sun/security/util/AlgorithmDecomposer
****
正在加载类：java/util/regex/Pattern
****
正在加载类：java/util/regex/Pattern$4
****
正在加载类：java/util/regex/Pattern$Node
****
正在加载类：java/util/regex/Pattern$LastNode
****
正在加载类：java/util/regex/Pattern$GroupHead
****
正在加载类：java/util/regex/Pattern$Single
****
正在加载类：java/util/regex/Pattern$BmpCharProperty
****
正在加载类：java/util/regex/Pattern$CharProperty
****
正在加载类：java/util/regex/Pattern$Slice
****
正在加载类：java/util/regex/Pattern$SliceNode
****
正在加载类：java/util/regex/Pattern$Begin
****
正在加载类：java/util/regex/Pattern$First
****
正在加载类：java/util/regex/Pattern$Start
****
正在加载类：java/util/regex/Pattern$TreeInfo
****
正在加载类：java/util/regex/ASCII
****
正在加载类：java/util/regex/Pattern$SliceI
****
正在加载类：java/util/regex/Pattern$BranchConn
****
正在加载类：java/util/regex/Pattern$Branch
****
正在加载类：sun/security/util/AbstractAlgorithmConstraints$1
****
正在加载类：java/security/Security
****
正在加载类：java/security/Security$1
****
正在加载类：java/util/Properties$LineReader
****
正在加载类：java/util/ArrayList$SubList
****
正在加载类：java/util/ArrayList$SubList$1
****
正在加载类：java/util/ListIterator
****
正在加载类：sun/security/util/DisabledAlgorithmConstraints$Constraints
****
正在加载类：java/util/regex/Pattern$BnM
****
正在加载类：java/util/regex/Matcher
****
正在加载类：java/util/regex/MatchResult
****
正在加载类：java/util/Arrays$ArrayList
****
正在加载类：java/util/AbstractList$Itr
****
正在加载类：sun/security/util/DisabledAlgorithmConstraints$DisabledConstraint
****
正在加载类：sun/security/util/DisabledAlgorithmConstraints$Constraint
****
正在加载类：sun/security/util/DisabledAlgorithmConstraints$KeySizeConstraint
****
正在加载类：sun/security/util/DisabledAlgorithmConstraints$Constraint$Operator
****
正在加载类：sun/security/util/DisabledAlgorithmConstraints$1
****
正在加载类：sun/security/util/ManifestEntryVerifier
****
正在加载类：com/intellij/rt/execution/application/AppMainV2$Agent
****
正在加载类：com/intellij/rt/execution/application/AppMainV2
****
正在加载类：com/intellij/rt/execution/application/AppMainV2$1
****
正在加载类：java/lang/reflect/InvocationTargetException
****
正在加载类：java/lang/NoSuchMethodException
****
正在加载类：java/net/Socket
****
正在加载类：java/net/InetSocketAddress
****
正在加载类：java/net/SocketAddress
****
正在加载类：java/net/InetAddress
****
正在加载类：java/net/InetSocketAddress$InetSocketAddressHolder
****
正在加载类：sun/security/action/GetBooleanAction
****
正在加载类：java/net/InetAddress$1
****
正在加载类：java/net/InetAddress$InetAddressHolder
****
正在加载类：java/net/InetAddress$Cache
****
正在加载类：java/net/InetAddress$Cache$Type
****
正在加载类：java/net/InetAddressImplFactory
****
正在加载类：java/net/Inet6AddressImpl
****
正在加载类：java/net/InetAddressImpl
****
正在加载类：java/lang/invoke/MethodHandleImpl
正在加载类：java/net/InetAddress$2
****
正在加载类：sun/net/spi/nameservice/NameService
****
正在加载类：sun/net/util/IPAddressUtil
****
正在加载类：java/net/Inet4Address
****
正在加载类：java/net/SocksSocketImpl
****
正在加载类：java/net/SocksConsts
****
正在加载类：java/net/PlainSocketImpl
****
正在加载类：java/net/AbstractPlainSocketImpl
****
正在加载类：java/net/SocketImpl
****
正在加载类：java/net/SocketOptions
****
****
正在加载类：java/net/AbstractPlainSocketImpl$1
****
正在加载类：java/lang/invoke/MethodHandleImpl$1
****
正在加载类：java/lang/invoke/MethodHandleImpl$2
****
正在加载类：java/net/Inet6Address
****
正在加载类：java/util/function/Function
****
正在加载类：java/lang/invoke/MethodHandleImpl$3
****
正在加载类：java/net/Inet6Address$Inet6AddressHolder
****
正在加载类：java/lang/invoke/MethodHandleImpl$4
****
正在加载类：java/lang/ClassValue
****
正在加载类：java/lang/ClassValue$Entry
****
正在加载类：java/lang/ClassValue$Identity
****
正在加载类：java/lang/ClassValue$Version
****
正在加载类：java/lang/invoke/MemberName$Factory
****
正在加载类：java/lang/invoke/MethodHandleStatics
****
正在加载类：java/lang/invoke/MethodHandleStatics$1
正在加载类：java/net/SocketException
****
正在加载类：java/io/IOException
****
正在加载类：java/net/SocksSocketImpl$3
****
正在加载类：java/net/ProxySelector
****
正在加载类：sun/net/spi/DefaultProxySelector
****
正在加载类：sun/net/spi/DefaultProxySelector$1
****
正在加载类：sun/net/NetProperties
****
正在加载类：sun/net/NetProperties$1
****
正在加载类：java/net/URI
****
****
正在加载类：java/net/URI$Parser
****
正在加载类：sun/net/spi/DefaultProxySelector$NonProxyInfo
****
正在加载类：sun/net/spi/DefaultProxySelector$3
****
正在加载类：java/net/Proxy
****
正在加载类：java/net/Proxy$Type
****
正在加载类：sun/misc/PostVMInitHook
****
正在加载类：java/util/ArrayList$Itr
****
正在加载类：sun/usagetracker/UsageTrackerClient
****
正在加载类：sun/net/NetHooks
****
正在加载类：sun/net/sdp/SdpProvider
****
正在加载类：sun/net/NetHooks$Provider
****
正在加载类：java/net/NetworkInterface
****
正在加载类：java/net/NetworkInterface$1
****
正在加载类：java/util/concurrent/atomic/AtomicBoolean
****
正在加载类：sun/usagetracker/UsageTrackerClient$1
正在加载类：java/net/InterfaceAddress
****
****
正在加载类：java/net/DefaultInterface
****
正在加载类：sun/usagetracker/UsageTrackerClient$4
****
正在加载类：sun/usagetracker/UsageTrackerClient$3
****
正在加载类：java/net/NetworkInterface$2
****
正在加载类：java/io/FileOutputStream$1
****
正在加载类：sun/launcher/LauncherHelper
****
正在加载类：PersonTest
----
正在加载类：java/net/Socket$2
****
正在加载类：java/net/SocketInputStream
****
正在加载类：sun/nio/cs/US_ASCII$Decoder
****
javassist.CtClassType@6f539caf[changed public class PersonTest fields= constructors=javassist.CtConstructor@49097b5d[public PersonTest ()V],  methods=javassist.CtMethod@44a4fe33[public static main ([Ljava/lang/String;)V], ]
javassist.NotFoundException: test(..) is not found in PersonTest
	at javassist.CtClassType.getDeclaredMethod(CtClassType.java:1343)
	at com.agent.MyTransformer.transform(MyTransformer.java:38)
	at sun.instrument.TransformerManager.transform(TransformerManager.java:188)
	at sun.instrument.InstrumentationImpl.transform(InstrumentationImpl.java:428)
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:763)
	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
	at java.net.URLClassLoader.defineClass(URLClassLoader.java:467)
	at java.net.URLClassLoader.access$100(URLClassLoader.java:73)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:368)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:362)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.net.URLClassLoader.findClass(URLClassLoader.java:361)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:349)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:495)
正在加载类：sun/launcher/LauncherHelper$FXHelper
****
正在加载类：java/lang/Class$MethodArray
****
正在加载类：java/lang/Void
****
按数字键 1 调用测试方法
正在加载类：java/util/Scanner
****
正在加载类：java/util/regex/Pattern$CharPropertyNames
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$1
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$CharPropertyFactory
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$2
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$5
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$3
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$6
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$CloneableProperty
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$4
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$7
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$8
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$9
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$10
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$11
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$12
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$13
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$14
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$15
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$16
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$17
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$18
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$19
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$20
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$21
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$22
****
正在加载类：java/util/regex/Pattern$CharPropertyNames$23
****
正在加载类：java/util/regex/Pattern$Curly
****
正在加载类：java/util/regex/Pattern$All
****
正在加载类：java/util/regex/Pattern$BitClass
****
正在加载类：java/util/regex/Pattern$1
****
正在加载类：java/util/regex/Pattern$CharProperty$1
****
正在加载类：java/util/regex/Pattern$6
****
正在加载类：java/util/Scanner$1
****
正在加载类：sun/misc/LRUCache
****
正在加载类：java/util/Locale$Category
****
正在加载类：java/util/Locale$1
****
正在加载类：java/text/NumberFormat
****
正在加载类：java/text/Format
****
正在加载类：java/text/spi/NumberFormatProvider
****
正在加载类：java/util/spi/LocaleServiceProvider
****
正在加载类：sun/util/locale/provider/LocaleProviderAdapter
****
正在加载类：sun/util/locale/provider/JRELocaleProviderAdapter
****
正在加载类：sun/util/locale/provider/ResourceBundleBasedAdapter
****
正在加载类：sun/util/locale/provider/SPILocaleProviderAdapter
****
正在加载类：sun/util/locale/provider/AuxLocaleProviderAdapter
****
正在加载类：sun/util/locale/provider/AuxLocaleProviderAdapter$NullProvider
****
正在加载类：sun/util/locale/provider/LocaleProviderAdapter$Type
****
正在加载类：java/util/Collections$UnmodifiableCollection$1
****
正在加载类：sun/util/locale/provider/LocaleProviderAdapter$1
****
正在加载类：sun/util/locale/provider/NumberFormatProviderImpl
****
正在加载类：sun/util/locale/provider/AvailableLanguageTags
****
正在加载类：sun/util/locale/provider/LocaleDataMetaInfo
****
正在加载类：sun/util/locale/provider/JRELocaleProviderAdapter$1
****
正在加载类：sun/util/locale/LanguageTag
****
正在加载类：java/util/Collections$EmptyIterator
****
正在加载类：sun/util/locale/provider/SPILocaleProviderAdapter$1
****
正在加载类：java/util/ServiceLoader
****
正在加载类：java/util/ServiceLoader$LazyIterator
****
正在加载类：java/util/ServiceLoader$1
****
正在加载类：java/util/LinkedHashMap$LinkedEntrySet
****
正在加载类：java/util/LinkedHashMap$LinkedEntryIterator
****
正在加载类：java/util/LinkedHashMap$LinkedHashIterator
****
正在加载类：sun/misc/URLClassPath$2
****
正在加载类：java/lang/ClassLoader$2
****
正在加载类：sun/misc/URLClassPath$1
****
正在加载类：java/net/URLClassLoader$3
****
正在加载类：sun/misc/CompoundEnumeration
****
正在加载类：java/io/FileNotFoundException
****
正在加载类：java/security/PrivilegedActionException
****
正在加载类：java/net/URLClassLoader$3$1
****
正在加载类：java/util/ResourceBundle$Control
****
正在加载类：java/util/ResourceBundle$Control$CandidateListCache
****
正在加载类：java/util/AbstractList$ListItr
****
正在加载类：java/util/Collections$UnmodifiableList$1
****
正在加载类：sun/util/locale/provider/LocaleResources
****
正在加载类：sun/util/resources/LocaleData
****
正在加载类：sun/util/resources/LocaleData$1
****
正在加载类：sun/util/resources/LocaleData$LocaleDataResourceBundleControl
****
正在加载类：java/util/ResourceBundle
****
正在加载类：java/util/ResourceBundle$1
****
正在加载类：java/util/spi/ResourceBundleControlProvider
****
正在加载类：java/util/ResourceBundle$RBClassLoader
****
正在加载类：java/util/ResourceBundle$RBClassLoader$1
****
正在加载类：java/util/ResourceBundle$CacheKey
****
正在加载类：java/util/ResourceBundle$LoaderReference
****
正在加载类：java/util/ResourceBundle$CacheKeyReference
****
正在加载类：java/util/ResourceBundle$SingleFormatControl
****
正在加载类：sun/text/resources/FormatData
****
正在加载类：sun/util/resources/ParallelListResourceBundle
****
正在加载类：java/util/concurrent/atomic/AtomicMarkableReference
****
正在加载类：java/util/concurrent/atomic/AtomicMarkableReference$Pair
****
正在加载类：java/util/ResourceBundle$BundleReference
****
正在加载类：sun/text/resources/en/FormatData_en
****
正在加载类：sun/util/locale/provider/LocaleResources$ResourceReference
****
正在加载类：java/text/DecimalFormatSymbols
****
正在加载类：java/text/spi/DecimalFormatSymbolsProvider
****
正在加载类：sun/util/locale/provider/DecimalFormatSymbolsProviderImpl
****
正在加载类：sun/util/resources/ParallelListResourceBundle$KeySet
****
正在加载类：java/util/Currency
****
正在加载类：java/util/Currency$1
****
正在加载类：java/util/spi/CurrencyNameProvider
****
正在加载类：sun/util/locale/provider/LocaleServiceProviderPool
****
正在加载类：java/text/spi/BreakIteratorProvider
****
正在加载类：java/text/spi/CollatorProvider
****
正在加载类：java/text/spi/DateFormatProvider
****
正在加载类：java/text/spi/DateFormatSymbolsProvider
****
正在加载类：java/util/spi/LocaleNameProvider
****
正在加载类：java/util/spi/TimeZoneNameProvider
****
正在加载类：java/util/spi/CalendarDataProvider
****
正在加载类：sun/util/locale/provider/CurrencyNameProviderImpl
****
正在加载类：java/util/Currency$CurrencyNameGetter
****
正在加载类：sun/util/locale/provider/LocaleServiceProviderPool$LocalizedObjectGetter
****
正在加载类：sun/util/resources/CurrencyNames
****
正在加载类：sun/util/resources/LocaleNamesBundle
****
正在加载类：sun/util/resources/OpenListResourceBundle
****
正在加载类：java/util/HashMap$KeySet
****
正在加载类：java/text/DecimalFormat
****
正在加载类：java/text/FieldPosition
****
正在加载类：java/text/DigitList
****
正在加载类：java/math/RoundingMode
****
正在加载类：java/util/regex/Pattern$GroupTail
****
正在加载类：java/util/regex/Pattern$Ques
****
正在加载类：java/util/regex/Pattern$Loop
****
正在加载类：java/util/regex/Pattern$Prolog
****
正在加载类：sun/misc/VMSupport
****
正在加载类：java/util/Hashtable$KeySet
****
正在加载类：sun/nio/cs/ISO_8859_1$Encoder
****
正在加载类：sun/nio/cs/Surrogate$Parser
****
正在加载类：sun/nio/cs/Surrogate
****
正在加载类：java/util/Date
****
正在加载类：sun/util/calendar/CalendarSystem
****
正在加载类：sun/util/calendar/Gregorian
****
正在加载类：sun/util/calendar/BaseCalendar
****
正在加载类：sun/util/calendar/AbstractCalendar
****
正在加载类：java/util/TimeZone
****
正在加载类：sun/util/calendar/ZoneInfo
****
正在加载类：sun/util/calendar/ZoneInfoFile
****
正在加载类：sun/util/calendar/ZoneInfoFile$1
****
正在加载类：sun/util/calendar/ZoneInfoFile$ZoneOffsetTransitionRule
****
正在加载类：sun/util/calendar/ZoneInfoFile$Checksum
****
正在加载类：java/util/zip/CRC32
****
正在加载类：java/util/zip/Checksum
****
正在加载类：java/util/TimeZone$1
****
正在加载类：sun/util/calendar/Gregorian$Date
****
正在加载类：sun/util/calendar/BaseCalendar$Date
****
正在加载类：sun/util/calendar/CalendarDate
****
正在加载类：sun/util/calendar/CalendarUtils
****
正在加载类：sun/util/locale/provider/TimeZoneNameUtility
****
正在加载类：sun/util/locale/provider/TimeZoneNameProviderImpl
****
正在加载类：sun/util/locale/provider/TimeZoneNameUtility$TimeZoneNameGetter
****
正在加载类：sun/util/resources/TimeZoneNames
****
正在加载类：sun/util/resources/TimeZoneNamesBundle
****
正在加载类：sun/util/resources/en/TimeZoneNames_en
****
正在加载类：java/util/LinkedHashMap$LinkedKeySet
****
1
正在加载类：Person
----
javassist.CtClassType@4d405ef7[changed public class Person fields= constructors=javassist.CtConstructor@6193b845[public Person ()V],  methods=javassist.CtMethod@cbb76d13[public test ()Ljava/lang/String;], ]
获取方法名称：test
 动态插入的打印语句 
执行测试方法
I'm ok
```

TODO
用的这种方式，不知道为什么在纯命令行中无法出来这种结果，在intellij中却是可以的。
另外，对于这里的在intellij中出来的结果，有一个异常
```
javassist.CtClassType@6f539caf[changed public class PersonTest fields= constructors=javassist.CtConstructor@49097b5d[public PersonTest ()V],  methods=javassist.CtMethod@44a4fe33[public static main ([Ljava/lang/String;)V], ]
javassist.NotFoundException: test(..) is not found in PersonTest
	at javassist.CtClassType.getDeclaredMethod(CtClassType.java:1343)
	at com.agent.MyTransformer.transform(MyTransformer.java:38)
	at sun.instrument.TransformerManager.transform(TransformerManager.java:188)
	at sun.instrument.InstrumentationImpl.transform(InstrumentationImpl.java:428)
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:763)
	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
	at java.net.URLClassLoader.defineClass(URLClassLoader.java:467)
	at java.net.URLClassLoader.access$100(URLClassLoader.java:73)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:368)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:362)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.net.URLClassLoader.findClass(URLClassLoader.java:361)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:349)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:495)
```
原因暂时未知。

#### 以动态 attach 的方式运行
```
java PersonTest



javac -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/tools.jar:. AttachAgent.java

java -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/tools.jar:. AttachAgent
```

注意，在linux环境中，是用的`:.` ，在windows环境中，是用的`;.`

TODO
这个，还没有实现出想要的结果，原因还在查询中。

完全参考
[Java 调式、热部署、JVM 背后的支持者 Java Agent](https://www.cnblogs.com/fengzheng/p/11502963.html)

### Java 调式、热部署、JVM 背后的支持者 Java Agent

> 我们平时写 Java Agent 的机会确实不多，也可以说几乎用不着。但其实我们一直在用它，而且接触的机会非常多。下面这些技术都使用了 Java Agent 技术，看一下你就知道为什么了。

- 各个 Java IDE 的调试功能，例如 eclipse、IntelliJ ；

- 热部署功能，例如 JRebel、XRebel、 spring-loaded；

- 各种线上诊断工具，例如 Btrace、Greys，还有阿里的 Arthas；

- 各种性能分析工具，例如 Visual VM、JConsole 等；

Java Agent 直译过来叫做 Java 代理，还有另一种称呼叫做 Java 探针。首先说 Java Agent 是一个 jar 包，只不过这个 jar 包不能独立运行，它需要依附到我们的目标 JVM 进程中。我们来理解一下这两种叫法。

代理：比方说我们需要了解目标 JVM 的一些运行指标，我们可以通过 Java Agent 来实现，这样看来它就是一个代理的效果，我们最后拿到的指标是目标 JVM ,但是我们是通过 Java Agent 来获取的，对于目标 JVM 来说，它就像是一个代理；

探针：这个说法我感觉非常形象，JVM 一旦跑起来，对于外界来说，它就是一个黑盒。而 Java Agent 可以像一支针一样插到 JVM 内部，探到我们想要的东西，并且可以注入东西进去。

拿上面的几个我们平时会用到的技术举例子。拿 IDEA 调试器来说吧，当开启调试功能后，在 debugger 面板中可以看到当前上下文变量的结构和内容，还可以在 watches 面板中运行一些简单的代码，比如取值赋值等操作。还有 Btrace、Arthas 这些线上排查问题的工具，比方说有接口没有按预期的返回结果，但日志又没有错误，这时，我们只要清楚方法的所在包名、类名、方法名等，不用修改部署服务，就能查到调用的参数、返回值、异常等信息。

上面只是说到了探测的功能，而热部署功能那就不仅仅是探测这么简单了。热部署的意思就是说再不重启服务的情况下，保证最新的代码逻辑在服务生效。当我们修改某个类后，通过 Java Agent 的 instrument 机制，把之前的字节码替换为新代码所对应的字节码。

Java Agent 结构
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190922182615770.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhbmd5YW5ncmVucmVu,size_16,color_FFFFFF,t_70)

Java Agent 最终以 jar 包的形式存在。主要包含两个部分，一部分是实现代码，一部分是配置文件。

配置文件放在 META-INF 目录下，文件名为 MANIFEST.MF 。包括以下配置项：
```
Manifest-Version: 版本号
Created-By: 创作者
Agent-Class: agentmain 方法所在类
Can-Redefine-Classes: 是否可以实现类的重定义
Can-Retransform-Classes: 是否可以实现字节码替换
Premain-Class: premain 方法所在类
```
入口类实现 `agentmain` 和 `premain` 两个方法即可，方法要实现什么功能就由你的需求决定了。

### Java Agent 实现和使用
接下来就来实现一个简单的 Java Agent，基于 Java 1.8，主要实现两点简单的功能：

1、打印当前加载的所有类的名称；

2、监控一个特定的方法，在方法中动态插入简单的代码并获取方法返回值；

在方法中插入代码主要是用到了字节码修改技术，字节码修改技术主要有 javassist、ASM，已经 ASM 的高级封装可扩展 cglib，这个例子中用的是 javassist。所以需要引入相关的 maven 包。

```
<dependency>
    <groupId>javassist</groupId>
    <artifactId>javassist</artifactId>
    <version>3.12.1.GA</version>
</dependency>
```

实现入口类和功能逻辑

入口类上面也说了，要实现 agentmain 和 premain 两个方法。这两个方法的运行时机不一样。这要从 Java Agent 的使用方式来说了，Java Agent 有两种启动方式，一种是以 JVM 启动参数 -javaagent:xxx.jar 的形式随着 JVM 一起启动，这种情况下，会调用 premain方法，并且是在主进程的 main方法之前执行。另外一种是以 loadAgent 方法动态 attach 到目标 JVM 上，这种情况下，会执行 agentmain方法。

|加载方式 | 执行方法|
|:-----------:| -------------:|
|-javaagent:xxx.jar 参数形式 |premain|
|动态 attach | agentmain|
代码实现如下：
```
package kite.lab.custom.agent;

import java.lang.instrument.Instrumentation;

public class MyCustomAgent {
    /**
     * jvm 参数形式启动，运行此方法
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("premain");
        customLogic(inst);
    }

    /**
     * 动态 attach 方式启动，运行此方法
     * @param agentArgs
     * @param inst
     */
    public static void agentmain(String agentArgs, Instrumentation inst){
        System.out.println("agentmain");
        customLogic(inst);
    }

    /**
     * 打印所有已加载的类名称
     * 修改字节码
     * @param inst
     */
    private static void customLogic(Instrumentation inst){
        inst.addTransformer(new MyTransformer(), true);
        Class[] classes = inst.getAllLoadedClasses();
        for(Class cls :classes){
            System.out.println(cls.getName());
        }
    }
}
```
我们看到这两个方法都有参数 agentArgs 和 inst，其中 agentArgs 是我们启动 Java Agent 时带进来的参数，比如`-javaagent:xxx.jar agentArgs`。Instrumentation Java 开放出来的专门用于字节码修改和程序监控的实现。我们要实现的打印已加载类和修改字节码也就是基于它来实现的。其中` inst.getAllLoadedClasses()`一个方法就实现了获取所以已加载类的功能。

inst.addTransformer方法则是实现字节码修改的关键，后面的参数就是实现字节码修改的实现类，代码如下：
```
public class MyTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("正在加载类："+ className);
        if (!"kite/attachapi/Person".equals(className)){
            return classfileBuffer;
        }

        CtClass cl = null;
        try {
            ClassPool classPool = ClassPool.getDefault();
            cl = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            CtMethod ctMethod = cl.getDeclaredMethod("test");
            System.out.println("获取方法名称："+ ctMethod.getName());

            ctMethod.insertBefore("System.out.println(\" 动态插入的打印语句 \");");
            ctMethod.insertAfter("System.out.println($_);");

            byte[] transformed = cl.toBytecode();
            return transformed;
        }catch (Exception e){
            e.printStackTrace();

        }
        return classfileBuffer;
    }
}
```
以上代码的逻辑就是当碰到加载的类是 `kite.attachapi.Person`的时候，在其中的 `test `方法开始时插入一条打印语句，打印内容是"动态插入的打印语句"，在`test`方法结尾处，打印返回值，其中`$_`就是返回值，这是 javassist 里特定的标示符。

MANIFEST.MF 配置文件

在目录 resources/META-INF/ 下创建文件名为 MANIFEST.MF 的文件，在其中加入如下的配置内容：
```
Manifest-Version: 1.0
Created-By: fengzheng
Agent-Class: kite.lab.custom.agent.MyCustomAgent
Can-Redefine-Classes: true
Can-Retransform-Classes: true
Premain-Class: kite.lab.custom.agent.MyCustomAgent
```
配置打包所需的 pom 设置

最后 Java Agent 是以 jar 包的形式存在，所以最后一步就是将上面的内容打到一个 jar 包里。

在 pom 文件中加入以下配置
```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-assembly-plugin</artifactId>
            <configuration>
                <archive>
                    <manifestFile>src/main/resources/META-INF/MANIFEST.MF</manifestFile>
                </archive>
                <descriptorRefs>
                    <descriptorRef>jar-with-dependencies</descriptorRef>
                </descriptorRefs>
            </configuration>
        </plugin>
    </plugins>
</build>
```
用的是 maven 的 maven-assembly-plugin 插件，注意其中要用 manifestFile 指定 MANIFEST.MF 所在路径，然后指定 jar-with-dependencies ，将依赖包打进去。

上面这是一种打包方式，需要单独的 MANIFEST.MF 配合，还有一种方式，不需要在项目中单独的添加 MANIFEST.MF 配置文件，完全在 pom 文件中配置上即可。
```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-assembly-plugin</artifactId>
            <executions>
                <execution>
                    <goals>
                        <goal>attached</goal>
                    </goals>
                    <phase>package</phase>
                    <configuration>
                        <descriptorRefs>
                            <descriptorRef>jar-with-dependencies</descriptorRef>
                        </descriptorRefs>
                        <archive>
                            <manifestEntries>
                                <Premain-Class>kite.agent.vmargsmethod.MyAgent</Premain-Class>
                                <Agent-Class>kite.agent.vmargsmethod.MyAgent</Agent-Class>
                                <Can-Redefine-Classes>true</Can-Redefine-Classes>
                                <Can-Retransform-Classes>true</Can-Retransform-Classes>
                            </manifestEntries>
                        </archive>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
这种方式是将 MANIFEST.MF 的内容全部写作 pom 配置中，打包的时候就会自动将配置信息生成 MANIFEST.MF 配置文件打进包里。

运行打包命令

接下来就简单了，执行一条 maven 命令即可。
```
mvn assembly:assembly
```
最后打出来的 jar 包默认是以「项目名称-版本号-jar-with-dependencies.jar」这样的格式生成到 target 目录下。

运行打包好的 Java Agent

首先写一个简单的测试项目，用来作为目标 JVM，稍后会以两种方式将 Java Agent 挂到这个测试项目上。
```
package kite.attachapi;

import java.util.Scanner;

public class RunJvm {

    public static void main(String[] args){
        System.out.println("按数字键 1 调用测试方法");
        while (true) {
            Scanner reader = new Scanner(System.in);
            int number = reader.nextInt();
            if(number==1){
                Person person = new Person();
                person.test();
            }
        }
    }
}
```
以上只有一个简单的 main 方法，用 while 的方式保证线程不退出，并且在输入数字 1 的时候，调用 person.test()方法。

以下是 Person 类
```
package kite.attachapi;

public class Person {

    public String test(){
        System.out.println("执行测试方法");
        return "I'm ok";
    }
}
```
以命令行的方式运行

#### -javaagent:xxx.jar 参数形式
因为项目是在 IDEA 里创建的，为了省事儿，我就直接在 IDEA 的 「Run/Debug Configurations」里加参数了。
```
-javaagent:/java-agent路径/lab-custom-agent-1.0-SNAPSHOT-jar-with-dependencies.jar
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190922202726289.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhbmd5YW5ncmVucmVu,size_16,color_FFFFFF,t_70)
然后直接运行就可以看到效果了，会看到加载的类名称。然后输入数字键 "1"，会看到字节码修改后的内容。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190922202748679.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhbmd5YW5ncmVucmVu,size_16,color_FFFFFF,t_70)

#### 以动态 attach 的方式运行

测试之前先要把这个测试项目跑起来，并把之前的参数去掉。运行后，找到这个它的进程id，一般利用jps -l即可。

动态 attach 的方式是需要代码实现的，实现代码如下：
```
public class AttachAgent {

    public static void main(String[] args) throws Exception{
        VirtualMachine vm = VirtualMachine.attach("pid(进程号)");
        vm.loadAgent("java-agent路径/lab-custom-agent-1.0-SNAPSHOT-jar-with-dependencies.jar");
    }
}
```
运行上面的 main 方法 并在测试程序中输入“1”，会得到上图同样的结果。

> 发现了没，我们到这里实现的简单的功能是不是和 BTrace 和 Arthas 有点像呢。我们拦截了指定的一个方法，并在这个方法里插入了代码而且拿到了返回结果。如果把方法名称变成可配置项，并且把返回结果保存到一个公共位置，例如一个内存数据库，是不是我们就可以像 Arthas 那样轻松的检测线上问题了呢。当然了，Arthas 要复杂的多，但原理是一样的。

#### sun.management.Agent 的实现
不知道你平时有没有用过 visualVM 或者 JConsole 之类的工具，其实，它们就是用了 management-agent.jar 这个Java Agent 来实现的。如果我们希望 Java 服务允许远程查看 JVM 信息，往往会配置上一下这些参数：
```
-Dcom.sun.management.jmxremote
-Djava.rmi.server.hostname=192.168.1.1
-Dcom.sun.management.jmxremote.port=9999
-Dcom.sun.management.jmxremote.rmi.port=9999
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
```
这些参数都是 management-agent.jar 定义的。

我们进到 management-agent.jar 包下，看到只有一个 MANIFEST.MF 配置文件，配置内容为：
```
Manifest-Version: 1.0
Created-By: 1.7.0_07 (Oracle Corporation)
Agent-Class: sun.management.Agent
Premain-Class: sun.management.Agent
```
可以看到入口 class 为 sun.management.Agent，进到这个类里面可以找到 agentmain 和 premain，并可以看到它们的逻辑。在这个类的开始，能看到我们前面对服务开启远程 JVM 监控需要开启的那些参数定义。