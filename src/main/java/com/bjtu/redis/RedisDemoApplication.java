package com.bjtu.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  SpringBootApplication
 * 用于代替 @SpringBootConfiguration（@Configuration）、 @EnableAutoConfiguration 、 @ComponentScan。
 * <p>
 * SpringBootConfiguration（Configuration） 注明为IoC容器的配置类，基于java config
 * EnableAutoConfiguration 借助@Import的帮助，将所有符合自动配置条件的bean定义加载到IoC容器
 * ComponentScan 自动扫描并加载符合条件的组件
 */

@SpringBootApplication
public class RedisDemoApplication {
    public static String key;
    public static String list;
    public static String keyField;
    public static HashMap<String, Counter> counters;
    public static HashMap<String, Action> actions;
    public static List<String> actionNames;
    public static void main(String[] args)  throws Exception{
//        counters = new HashMap<>();
//        actions = new HashMap<>();
//        actionNames = new ArrayList<>();
//        readActionConfig();
//        readCounterConfig();
//        Monitor monitorCounters = new Monitor();
//        monitorCounters.initFileMonitor("Counter.json");
        //初始化
        Initialize();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean ProgramExit = true;
        do {
            System.out.println("请选择您要进行的操作:");
            System.out.println("1. 查看所有Counters");
            System.out.println("2. 查看所有Actions");
            System.out.println("3. 执行Action");
            System.out.println("0. 退出程序");
            System.out.print("请输入您的选择:");
            String x = br.readLine();
            int size = 0;
            switch (x) {

                case "0":
                    ProgramExit = false;
                    break;
                case "1":
                    int cntt = 0;
                    for (Map.Entry<String, Counter> entry : counters.entrySet()) {
                        System.out.println(cntt+1 + " " + entry.getKey());
                        cntt++;
                    }
                    break;
                case "2":
                    size = actionNames.size();
                    for (int i = 0; i < size; ++i) {
                        System.out.println(i+1 + " " + actionNames.get(i));
                    }
                    break;
                case "3":
                    boolean exit = false;
                    while (!exit) {
                        System.out.println("********执行Action********");
                        System.out.println("请输入需要执行操作的序号:");
                        System.out.println("1. show");
                        System.out.println("2. incr");
                        System.out.println("3. decr");
                        System.out.println("4. incrFreq");
                        System.out.println("5. decrFreq");
                        System.out.println("0. 返回上一级目录");
                        System.out.print("请输入您的选择:");
                        String y = br.readLine();
                        switch (y) {

                            case "0":
                                exit = true;
                                break;
                            case "1":
                                Counter c0 = counters.get("show");
                                show(c0);
                                break;
                            case "2":
                                Counter b = counters.get("incr");
                                incr(b);
                                break;
                            case "3":
                                Counter d = counters.get("decr");
                                decr(d);
                                break;
                            case "4":
                                Counter e = counters.get("incrFreq");
                                incrFreq(e);
                                break;
                            case "5":
                                Counter f = counters.get("decrFreq");
                                decrFreq(f);
                                break;
                            default://输入错误
                                System.out.println("输入错误，请输入0-5的整数");
                                break;
                        }

                    }break;
                default://输入错误
                    System.out.println("输入错误，请输入0-3的整数");
                    break;
            }

        } while (ProgramExit);

    }

    public static void Initialize(){
        counters = new HashMap<>();
        actions = new HashMap<>();
        actionNames = new ArrayList<>();
        readActionConfig();
        readCounterConfig();
        Monitor monitorCounters = new Monitor();
        monitorCounters.initFileMonitor("Counter.json");
    }

    public static String readJsonFile(String fileName) {
        StringBuffer them = new StringBuffer();
        int i = 0;
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader jsonReader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);

            while ((i = jsonReader.read()) != -1) {
                them.append((char) i);
            }
            fileReader.close();
            jsonReader.close();

            return them.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static  String getString(String fileName){
        String path = RedisDemoApplication.class.getClassLoader().getResource(fileName).getPath();
        String string = readJsonFile(path);
        return string;

    }
    public static void readCounterConfig() {
        JSONObject counterss = JSONObject.parseObject(getString("Counter.json"));
        JSONArray array = counterss.getJSONArray("counters");
        for (Object obj : array) {
            Counter c = new Counter((JSONObject) obj);
            counters.put(c.getName(), c);
        }
    }

    public static void readActionConfig() {
        JSONObject actionss = JSONObject.parseObject(getString("Action.json"));
        JSONArray array = actionss.getJSONArray("actions");
        for (Object obj : array) {
            Action a = new Action((JSONObject) obj);
            actions.put(a.getName(), a);
            actionNames.add(a.getName());
        }
    }



    private static void show(Counter c) {
        key = c.getKey().get(0);

        RedisUtil redisUtil = new RedisUtil();
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy年-MM月dd日-HH:mm");
            Date date = new Date();
            String sDate=f.format(date);

            System.out.println("当前直播间" + key + "数目为: " + redisUtil.get(key) + "人，时间：" + sDate);
        } catch (Exception e) {
            System.out.println( Arrays.toString(e.getStackTrace()));
        }

    }

    private static void incr(Counter incr) {
        key = incr.getKey().get(0);
        list = incr.getKey().get(1);
        RedisUtil redisUtil = new RedisUtil();
        try {
            redisUtil.incr(key, incr.getValue());
            SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmm");
            Date date = new Date();
            String sDate=time.format(date);
            System.out.println("有"+incr.getValue()+"位用户在"+sDate.substring(0,4)+"年"+sDate.substring(4,6)+"月"+sDate.substring(6,8)
                    +"日"+sDate.substring(8,10)+":"+sDate.substring(10,12)+"进入了直播间。" + " 当前用户数目为： " + redisUtil.get(key));
            redisUtil.lpush(list,sDate);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private static void decr(Counter decr) {
        key = decr.getKey().get(0);
        list = decr.getKey().get(1);
        RedisUtil redisUtil = new RedisUtil();
        try {
            redisUtil.decr(key, decr.getValue());
            SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmm");
            Date date = new Date();
            String sDate=time.format(date);
            String exitNum = ""+decr.getValue();
            System.out.println("有"+decr.getValue()+"位用户在"+sDate.substring(0,4)+"年"+sDate.substring(4,6)+"月"+sDate.substring(6,8)
                    +"日"+sDate.substring(8,10)+":"+sDate.substring(10,12)+"进入了直播间。" + " 当前用户数目为： " + redisUtil.get(key));
            redisUtil.lpush(list,sDate);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public static void incrFreq(Counter counter){
        keyField = counter.getKey().get(0);
        RedisUtil redisUtil=new RedisUtil();
        try{
            for (int i = 0; i < redisUtil.llen(keyField); i++) {
                String sDate = redisUtil.lindex(keyField,i);
                System.out.println("用户在 "+sDate.substring(0,4)+"年"+sDate.substring(4,6)+"月"+sDate.substring(6,8)
                        +"日"+sDate.substring(8,10)+":"+sDate.substring(10,12)+" 进入直播间");

            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public static void decrFreq(Counter counter){
        keyField = counter.getKey().get(0);
        RedisUtil redisUtil=new RedisUtil();

        try{
            for (int i = 0; i < redisUtil.llen(keyField); i++) {
                String sDate = redisUtil.lindex(keyField,i);
                System.out.println("用户在 "+sDate.substring(0,4)+"年"+sDate.substring(4,6)+"月"+sDate.substring(6,8)
                        +"日"+sDate.substring(8,10)+":"+sDate.substring(10,12)+" 退出直播间");
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}


/*

总结：

1、获取运行环境信息和回调接口。例如ApplicationContextIntializer、ApplicationListener。
完成后，通知所有SpringApplicationRunListener执行started()。

2、创建并准备Environment。
完成后，通知所有SpringApplicationRunListener执行environmentPrepared()

3、创建并初始化 ApplicationContext 。例如，设置 Environment、加载配置等
完成后，通知所有SpringApplicationRunListener执行contextPrepared()、contextLoaded()

4、执行 ApplicationContext 的 refresh，完成程序启动
完成后，遍历执行 CommanadLineRunner、通知SpringApplicationRunListener 执行 finished()

参考：
https://blog.csdn.net/zxzzxzzxz123/article/details/69941910
https://www.cnblogs.com/shamo89/p/8184960.html
https://www.cnblogs.com/trgl/p/7353782.html

分析：

1） 创建一个SpringApplication对象实例，然后调用这个创建好的SpringApplication的实例方法

public static ConfigurableApplicationContext run(Object source, String... args)

public static ConfigurableApplicationContext run(Object[] sources, String[] args)

2） SpringApplication实例初始化完成并且完成设置后，就开始执行run方法的逻辑了，
方法执行伊始，首先遍历执行所有通过SpringFactoriesLoader可以查找到并加载的
SpringApplicationRunListener，调用它们的started()方法。


public SpringApplication(Object... sources)

private final Set<Object> sources = new LinkedHashSet<Object>();

private Banner.Mode bannerMode = Banner.Mode.CONSOLE;

...

private void initialize(Object[] sources)

3） 创建并配置当前SpringBoot应用将要使用的Environment（包括配置要使用的PropertySource以及Profile）。

private boolean deduceWebEnvironment()

4） 遍历调用所有SpringApplicationRunListener的environmentPrepared()的方法，通知Environment准备完毕。

5） 如果SpringApplication的showBanner属性被设置为true，则打印banner。

6） 根据用户是否明确设置了applicationContextClass类型以及初始化阶段的推断结果，
决定该为当前SpringBoot应用创建什么类型的ApplicationContext并创建完成，
然后根据条件决定是否添加ShutdownHook，决定是否使用自定义的BeanNameGenerator，
决定是否使用自定义的ResourceLoader，当然，最重要的，
将之前准备好的Environment设置给创建好的ApplicationContext使用。

7） ApplicationContext创建好之后，SpringApplication会再次借助Spring-FactoriesLoader，
查找并加载classpath中所有可用的ApplicationContext-Initializer，
然后遍历调用这些ApplicationContextInitializer的initialize（applicationContext）方法
来对已经创建好的ApplicationContext进行进一步的处理。

8） 遍历调用所有SpringApplicationRunListener的contextPrepared()方法。

9） 最核心的一步，将之前通过@EnableAutoConfiguration获取的所有配置以及其他形式的
IoC容器配置加载到已经准备完毕的ApplicationContext。

10） 遍历调用所有SpringApplicationRunListener的contextLoaded()方法。

11） 调用ApplicationContext的refresh()方法，完成IoC容器可用的最后一道工序。

12） 查找当前ApplicationContext中是否注册有CommandLineRunner，如果有，则遍历执行它们。

13） 正常情况下，遍历执行SpringApplicationRunListener的finished()方法、
（如果整个过程出现异常，则依然调用所有SpringApplicationRunListener的finished()方法，
只不过这种情况下会将异常信息一并传入处理）


private <T> Collection<? extends T> getSpringFactoriesInstances(Class<T> type)

private <T> Collection<? extends T> getSpringFactoriesInstances(Class<T> type,
			Class<?>[] parameterTypes, Object... args)

public void setInitializers

private Class<?> deduceMainApplicationClass()

public ConfigurableApplicationContext run(String... args)

private void configureHeadlessProperty()

private SpringApplicationRunListeners getRunListeners(String[] args)

public static List<String> loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader)


*/
