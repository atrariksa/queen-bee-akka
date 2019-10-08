package com.queenbee.annotations;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public interface IMethodAnnotationScanner extends IAnnotationScanner {
    @Override
    void scan();
    Map<Class<?>, List<Method>> getAllMappedClassAndMethod();
}
