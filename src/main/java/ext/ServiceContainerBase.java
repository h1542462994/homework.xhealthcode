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
    protected ServiceContainerBase(){ }

    /**
     * 使用的服务声明
     */
    private HashMap<String, Class<?>> serviceDeclarations = new HashMap<>();

    /**
     * 注册服务组件
     * @param interfaceType 需要注册的服务类型
     * @param serviceType 服务的实现类型
     */
    public final void addTransient(Class<?> interfaceType, Class<?> serviceType){
        serviceDeclarations.put(interfaceType.getName(), serviceType);
    }

    /**
     * 注册服务组件
     */
    public final void addTransient(Class<?> serviceType){
        serviceDeclarations.put(serviceType.getName(), serviceType);
    }

    /**
     * 获取一个服务
     * @param interfaceType 服务类型
     * @return 返回的服务
     */
    public final <T> T getService(Class<T> interfaceType) throws ServiceConstructException {
        try{
            Class<?> serviceType = serviceDeclarations.get(interfaceType.getName());
            Constructor<?>[] constructors = serviceType.getConstructors();
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
            return (T)firstConstructor.newInstance(parameters);
        } catch (ServiceConstructException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw new ServiceConstructException("创建服务失败", e);
        }
    }

    private static ServiceContainerBase instance = null;

    protected abstract void injectServices();

    /**
     * 返回实例的单例
     * @return 返回的实例
     */
    protected static <T extends ServiceContainerBase> T get(Class<T> type){
        try {
            if(instance == null){
                instance = type.getConstructor().newInstance();
                instance.injectServices();
            }
            return (T)instance;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e){
            e.printStackTrace();
            return null;
        }
    }

    public static ServiceContainerBase assertGet(){
        return instance;
    }
}
