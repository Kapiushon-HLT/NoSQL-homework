package com.bjtu.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
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

    public static void main(String[] args) {
        counters = new HashMap<>();
        readCounterConfig();
        Monitor monitorCounters = new Monitor();
        monitorCounters.initFileMonitor("Counter.json");

        while (true) {
            System.out.println("Please enter the number of operation you choose:");
            System.out.println("0 show");
            System.out.println("1 incr");
            System.out.println("2 decr");
            System.out.println("3 incr_freq");
            System.out.println("4 decr_freq");
            System.out.println("5 exit");
            Scanner scanner = new Scanner(System.in);
            int x = scanner.nextInt();

                switch (x) {

                    case 5:
                        System.exit(0);

                    case 0:
                        Counter c0 = counters.get("show");
                        show(c0);
                        break;
                    case 1:
                        Counter c11 = counters.get("show");
                        show(c11);
                        Counter c12 = counters.get("incr");
                        incr(c12);
                        break;
                    case 2:
                        Counter c21 = counters.get("show");
                        show(c21);
                        Counter c22 = counters.get("decr");
                        decr(c22);
                        break;
                    case 3:
                        Counter c3 = counters.get("incrFreq");
                        incrFreq(c3);
                        break;
                    case 4:
                        Counter c4 = counters.get("decrFreq");
                        decrFreq(c4);
                        break;

            }

        }
    }


    public static String readJsonFile(String fileName) {
        StringBuffer them = new StringBuffer();
        int i = 0;
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader jsonReader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");

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

    public static void readCounterConfig() {
        String path = RedisDemoApplication.class.getClassLoader().getResource("Counter.json").getPath();
        String countersString = readJsonFile(path);
        JSONObject counterss = JSONObject.parseObject(countersString);
        JSONArray array = counterss.getJSONArray("counters");
        for (Object obj : array) {
            Counter c = new Counter((JSONObject) obj);
            counters.put(c.getName(), c);
        }
    }

/********************************************************************************/

    private static void show(Counter c) {
        key = c.getKey().get(0);

        RedisUtil redisUtil = new RedisUtil();
        try {
            System.out.println("The value of " + key + " is " + redisUtil.get(key));
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }

    private static void incr(Counter incr) {
        key = incr.getKey().get(0);
        list = incr.getKey().get(1);
        RedisUtil redisUtil = new RedisUtil();
        try {
            redisUtil.incr(key, incr.getValue());
            System.out.println("The value of " + key + " increased by " + incr.getValue() + " and became " + redisUtil.get(key));
            SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmm");
            Date date = new Date();
            String string=time.format(date);
            redisUtil.lpush(list,string);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    private static void decr(Counter decr) {
        key = decr.getKey().get(0);
        list = decr.getKey().get(1);
        RedisUtil redisUtil = new RedisUtil();
        try {
            redisUtil.decr(key, decr.getValue());
            System.out.println("The value of " + key + " decreased by " + decr.getValue() + " and became " + redisUtil.get(key));
            SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmm");
            Date date = new Date();
            String string=time.format(date);
            redisUtil.lpush(list,string);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static void incrFreq(Counter counter){
        keyField = counter.getKey().get(0);
        RedisUtil redisUtil=new RedisUtil();
        try{
            for (int i = 0; i < redisUtil.llen(keyField); i++) {
                String t=redisUtil.lindex(keyField,i);
                System.out.println("USER在 "+t+" 进入");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decrFreq(Counter counter){
        keyField = counter.getKey().get(0);
        RedisUtil redisUtil=new RedisUtil();
        try{
            for (int i = 0; i < redisUtil.llen(keyField); i++) {
                System.out.println("USER在 "+redisUtil.lindex(keyField,i)+" 退出");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
