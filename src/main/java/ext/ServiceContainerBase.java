package ext;

import ext.exception.ServiceConstructException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * 服务容器类
 */
public abstract class ServiceContainerBase {
    /**
     * 私有构造，用于创造一个服务容器
     */
    protected ServiceContainerBase(){
        this.injectServices();
    }

    private final HashMap<String, Object> singletonServices = new HashMap<>();
    /**
     * 使用的服务声明
     */
    private final HashMap<String, Class<?>> transientServiceDeclares = new HashMap<>();
    private final HashMap<String, Class<?>> singletonServiceDeclares = new HashMap<>();
    protected final HashMap<String, Object> settings = new HashMap<>();

    /**
     * 注册服务组件
     * @param interfaceType 需要注册的服务类型
     * @param serviceType 服务的实现类型
     */
    public final void addTransient(Class<?> interfaceType, Class<?> serviceType){
        transientServiceDeclares.put(interfaceType.getName(), serviceType);
    }

    /**
     * 注册服务组件
     */
    public final void addTransient(Class<?> serviceType){
        transientServiceDeclares.put(serviceType.getName(), serviceType);
    }

    public final void addSingleton(Class<?> interfaceType, Class<?> serviceType){
        singletonServiceDeclares.put(interfaceType.getName(), serviceType);
    }

    /*\]];'  ;';'
     * 获取一个服务
     * @param interfaceType 服务类型
     * @return 返回的服务
     */
    public final <T> T getService(Class<T> interfaceType) throws ServiceConstructException {
        try{
            if(interfaceType.equals(this.getClass())){
                return (T)this;
            }

            Class<?> transientServiceType = transientServiceDeclares.get(interfaceType.getName());
            if(transientServiceType != null){
                return (T)createService(transientServiceType);
            }
            Class<?> singletonServiceType = singletonServiceDeclares.get(interfaceType.getName());
            if(singletonServiceType != null){
                Object service = singletonServices.get(interfaceType.getName());
                if(service == null){
                    service = createService(singletonServiceType);
                    singletonServices.put(interfaceType.getName(), service);
                }
                return (T)service;
            }
            return null;
        } catch (ServiceConstructException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw new ServiceConstructException("创建服务失败", e);
        }
    }

    private Object createService(Class<?> type) throws ServiceConstructException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] constructors = type.getConstructors();
        if(constructors.length > 1){
            throw new ServiceConstructException("服务的公有构造函数只能有一个");
        }
        Constructor<?> firstConstructor = constructors[0];
        Class<?>[] parameterTypes = firstConstructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        // 对之后的每一个服务创建一个对象。
        for (int i = 0; i < parameters.length; ++i) {
            parameters[i] = getService(parameterTypes[i]);
        }
        return firstConstructor.newInstance(parameters);
    }

    public Object getConfig(String key){
        return settings.get(key);
    }
    public void setConfig(String key, Object value){
        settings.put(key, value);
    }

    protected static ServiceContainerBase instance = null;

    protected abstract void injectServices();

    /**
     * 返回实例的单例
     * @return 返回的实例
     */
    public static <T extends ServiceContainerBase> T get(){
        return (T)instance;
    }



}
